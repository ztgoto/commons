package io.github.ztgoto.commons.utils.timer;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TimerTest {

	@Test
	public void testTimer(){
		SystemTimer timer = new SystemTimer("test", 1000L, 60, 1);

		Random random = new Random();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			timer.add(new Task(random.nextInt(120000)%120000+10000));
		}
		long end = System.currentTimeMillis();

		System.out.println("添加任务："+(end - begin)+"ms");
		System.out.println("current:"+System.currentTimeMillis());

		while (true) {
			System.out.println("task size:" + timer.size());
			timer.advanceClock(1000);
		}
	}

	public static class Task extends TimerTask {

		public Task(long delayMs) {
			super(delayMs);
		}

		@Override
		public void run() {
			System.out.println("----run");

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
