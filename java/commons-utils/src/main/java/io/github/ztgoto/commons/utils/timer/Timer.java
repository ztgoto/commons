package io.github.ztgoto.commons.utils.timer;

public interface Timer {
	
	void add(TimerTask timerTask);
	
	boolean advanceClock(long timeoutMs);
	
	int size();
	
	void shutdown();

}
