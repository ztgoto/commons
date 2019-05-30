package io.github.ztgoto.commons.utils.timer;

public class TimerTaskEntry implements Comparable<TimerTaskEntry>{
	
	public volatile TimerTaskList list;
	
	public TimerTaskEntry next;
	
	public TimerTaskEntry prev;
	
	private TimerTask timerTask;
	
	private long expirationMs;
	
	public TimerTaskEntry(TimerTask timerTask, long expirationMs) {
		if (timerTask != null) {
			timerTask.setTimerTaskEntry(this);
		}
		this.timerTask = timerTask;
		this.expirationMs = expirationMs;
	}
	
	public boolean cancelled() {
		return this.timerTask.getTimerTaskEntry() != this;
	}
	
	public void remove() {
		TimerTaskList currentList = list;
		// If remove is called when another thread is moving the entry from a task entry list to another,
	    // this may fail to remove the entry due to the change of value of list. Thus, we retry until the list becomes null.
	    // In a rare case, this thread sees null and exits the loop, but the other thread insert the entry to another list later.
        while (currentList != null) {
            currentList.remove(this);
            currentList = list;
        }
	}
	

	@Override
	public int compareTo(TimerTaskEntry o) {
		if (this.expirationMs < o.expirationMs) {
			return -1;
		} else if (this.expirationMs > o.expirationMs) {
			return 1;
		} else {
			return 0;
		}
	}

	public TimerTask getTimerTask() {
		return timerTask;
	}

	public long getExpirationMs() {
		return expirationMs;
	}

}
