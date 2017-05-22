package thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

 public class ScheduleThreadPool {
	public static void main(String[] args) {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
		scheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("爆炸!!!");
			}
		},3,TimeUnit.SECONDS);
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("爆炸!!!!");
			}
		}, 3, 1, TimeUnit.SECONDS);
	}
}