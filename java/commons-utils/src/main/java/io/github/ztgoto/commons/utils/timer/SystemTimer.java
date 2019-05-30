package io.github.ztgoto.commons.utils.timer;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemTimer implements Timer {
	
	private static final Logger LOG = LoggerFactory.getLogger(SystemTimer.class);
	
	private ExecutorService taskExecutor;
	
	private String executorName;
	
	private long tickMs;
	
	private int wheelSize;
	
	private long startMs;
	
	private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();
	
	private AtomicInteger taskCounter = new AtomicInteger(0);
	
	private TimingWheel timingWheel;
	
	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
	
	public SystemTimer(String executorName, long tickMs, int wheelSize, int executorThreadSize) {
		this.executorName = executorName;
		this.tickMs = tickMs < 1 ? 1 : tickMs;
		this.wheelSize = wheelSize <= 1 ? 20 : wheelSize;
		this.startMs = System.currentTimeMillis();
		this.taskExecutor = Executors.newFixedThreadPool(executorThreadSize<1?1:executorThreadSize, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				
				return new Thread(r, "executor-" + executorName);
			}
		});
		this.timingWheel = new TimingWheel(this.tickMs, this.wheelSize, this.startMs, this.taskCounter, this.delayQueue);
	}
	
	private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
		if (!this.timingWheel.add(timerTaskEntry)) {
			if (!timerTaskEntry.cancelled()) {
				this.taskExecutor.submit(timerTaskEntry.getTimerTask());
			}
		}
	}
	
	

	@Override
	public void add(TimerTask timerTask) {
		this.readLock.lock();
		try {
			addTimerTaskEntry(new TimerTaskEntry(timerTask, timerTask.delayMs + System.currentTimeMillis()));
		} finally {
			this.readLock.unlock();
		}

	}
	

	@Override
	public boolean advanceClock(long timeoutMs) {
		try {
			TimerTaskList bucket = this.delayQueue.poll(timeoutMs, TimeUnit.MILLISECONDS);
			if (bucket != null) {
				writeLock.lock();
				try {
					while (bucket != null) {
						this.timingWheel.advanceClock(bucket.getExpiration());
						bucket.flush(entry -> {
							addTimerTaskEntry(entry);
						});
						bucket = this.delayQueue.poll();
					}
				} finally {
					writeLock.unlock();
				}
				return true;
			} else {
				return false;
			}
		} catch (InterruptedException e) {
			LOG.error(e.getMessage(),e);
		}
		return false;
	}

	@Override
	public int size() {
		
		return this.taskCounter.get();
	}

	@Override
	public void shutdown() {
		this.taskExecutor.shutdown();

	}

	public String getExecutorName() {
		return executorName;
	}
	

}
