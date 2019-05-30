package io.github.ztgoto.commons.utils.timer;

public abstract class TimerTask implements Runnable {
	
	protected long delayMs;
	
	protected TimerTaskEntry timerTaskEntry;
	
	public TimerTask() {
		
	}
	
	public TimerTask(long delayMs) {
		this.delayMs = delayMs;
	}
	
	public void cancel() {
		synchronized (this) {
			if (timerTaskEntry != null) {
				timerTaskEntry.remove();
			}
			timerTaskEntry = null;
		}
	}
	
	
	
	public void setTimerTaskEntry(TimerTaskEntry entry) {
		synchronized (this) {
			if (this.timerTaskEntry != null && this.timerTaskEntry != entry) {
				timerTaskEntry.remove();
			}
			this.timerTaskEntry = entry;
		}
	}



	public TimerTaskEntry getTimerTaskEntry() {
		return timerTaskEntry;
	}



	public long getDelayMs() {
		return delayMs;
	}



	public void setDelayMs(long delayMs) {
		this.delayMs = delayMs;
	}

	

}
