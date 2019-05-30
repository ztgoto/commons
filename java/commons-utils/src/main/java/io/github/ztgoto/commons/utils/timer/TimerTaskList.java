package io.github.ztgoto.commons.utils.timer;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class TimerTaskList implements Delayed {
	
	private AtomicInteger taskCounter;
	
	private TimerTaskEntry root;
	
	private AtomicLong expiration;
	
	private AtomicInteger size = new AtomicInteger(0);
	
	public TimerTaskList(AtomicInteger taskCounter) {
		this.taskCounter = taskCounter;
		this.expiration = new AtomicLong(-1L);
		this.root = new TimerTaskEntry(null, -1L);
		this.root.next = this.root;
		this.root.prev = this.root;
	}
	
	public boolean setExpiration(long expirationMs) {
		return this.expiration.getAndSet(expirationMs) != expirationMs;
	}
	
	public long getExpiration() {
		return this.expiration.get();
	}
	
	public synchronized void foreach(Consumer<TimerTask> f) {
		TimerTaskEntry entry = this.root.next;
		while (entry != this.root) {
			TimerTaskEntry nextEntry = entry.next;
			
			if(!entry.cancelled()) f.accept(entry.getTimerTask()); 
			
			entry = nextEntry;
			
		}
	}
	
	public void add(TimerTaskEntry timerTaskEntry) {
		boolean done = false;
	    while (!done) {
	      // Remove the timer task entry if it is already in any other list
	      // We do this outside of the sync block below to avoid deadlocking.
	      // We may retry until timerTaskEntry.list becomes null.
	      timerTaskEntry.remove();

	      synchronized (this) {
	    	  synchronized (timerTaskEntry) {
	          if (timerTaskEntry.list == null) {
	            // put the timer task entry to the end of the list. (root.prev points to the tail entry)
	        	TimerTaskEntry tail = this.root.prev;
	            timerTaskEntry.next = this.root;
	            timerTaskEntry.prev = tail;
	            timerTaskEntry.list = this;
	            tail.next = timerTaskEntry;
	            this.root.prev = timerTaskEntry;
	            taskCounter.incrementAndGet();
	            size.incrementAndGet();
	            done = true;
	          }
	        }
	      }
	    }
	}
	
	public void remove(TimerTaskEntry timerTaskEntry) {
		synchronized (this) {
			synchronized (timerTaskEntry) {
				if (timerTaskEntry.list == this) {
					timerTaskEntry.next.prev = timerTaskEntry.prev;
					timerTaskEntry.prev.next = timerTaskEntry.next;
					timerTaskEntry.next = null;
					timerTaskEntry.prev = null;
					timerTaskEntry.list = null;
					taskCounter.decrementAndGet();
					size.decrementAndGet();
				}
			}
		}
	}
	
	public synchronized void flush(Consumer<TimerTaskEntry> f) {
		TimerTaskEntry head = this.root.next;
		while (head != this.root) {
			remove(head);
			f.accept(head);
			head = root.next;
		}
		expiration.set(-1L);
	}

	@Override
	public int compareTo(Delayed o) {
		TimerTaskList target = (TimerTaskList) o;
		if (getExpiration() < target.getExpiration()) {
			return -1;
		} else if (getExpiration() > target.getExpiration()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public long getDelay(TimeUnit unit) {
		
		return unit.convert(Long.max(this.expiration.get() - System.currentTimeMillis(), 0), TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return String.format("TimerTaskList [size: %d]", this.size.get());
	}
	
	

}
