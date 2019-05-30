package io.github.ztgoto.commons.utils.timer;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TimingWheel {
	
	/**
	 * 一次tick代表的毫秒数
	 */
	private long tickMs;
	
	/**
	 * 一圈的tick数
	 */
	private int wheelSize;
	
	/**
	 * 一圈总毫秒数
	 */
	private long interval;
	
	/**
	 * 当前tick时间
	 */
	private long currentTime;
	
	private AtomicInteger taskCounter;
	
	private DelayQueue<TimerTaskList> queue;
	
	private volatile TimingWheel overflowWheel;
	
	private TimerTaskList[] buckets;
	
	public TimingWheel(long tickMs, int whellSize, long startMs, AtomicInteger taskCounter, DelayQueue<TimerTaskList> queue ) {
		this.tickMs = tickMs;
		this.wheelSize = whellSize;
		this.interval = tickMs * whellSize;
		this.taskCounter = taskCounter;
		this.currentTime = startMs - (startMs % tickMs);
		this.queue = queue;
		
		buckets = new TimerTaskList[whellSize];
		
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new TimerTaskList(taskCounter);
		}
		
	}
	
	private synchronized void addOverflowWheel() {
		if (this.overflowWheel == null) {
			this.overflowWheel = new TimingWheel(this.interval, this.wheelSize, this.currentTime, this.taskCounter, this.queue);
		}
	}
	
	public boolean add(TimerTaskEntry timerTaskEntry) {
		
		long expiration = timerTaskEntry.getExpirationMs();
		
		if (timerTaskEntry.cancelled()) {
			return false;
		} else if (expiration < this.currentTime + this.tickMs) {
			return false;
		} else if (expiration < this.currentTime + this.interval) {
			long virtualId = expiration / this.tickMs;
			TimerTaskList bucket = this.buckets[(int) (virtualId % this.wheelSize)];
			bucket.add(timerTaskEntry);
			if (bucket.setExpiration(virtualId * this.tickMs)) {
				queue.offer(bucket);
			}
			return true;
		} else {
			if (this.overflowWheel == null) addOverflowWheel();
			return overflowWheel.add(timerTaskEntry);
		}
		
	}
	
	public void advanceClock(long timeMs) {
		if (timeMs >= this.currentTime + this.tickMs) {
			this.currentTime = timeMs - (timeMs % this.tickMs);
			
			if (this.overflowWheel != null) this.overflowWheel.advanceClock(currentTime); 
		}
	}
	

}
