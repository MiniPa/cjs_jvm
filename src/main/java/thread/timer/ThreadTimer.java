package thread.timer;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

/*
 * Timer: thread设施 存储并调度 TimerTask的队列 顺序调度
 * TimerTask: abstract class implements Runnable 具有多线程能力
 */
public class ThreadTimer {
	@Test
	public void TimerTest() throws InterruptedException {
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask1(), 2000);
		Thread.sleep(3000);
	}
	static class MyTimerTask1 extends TimerTask {
		@Override
		public void run() {
			System.out.println("爆炸 ! ! !");
		}
	}
}
