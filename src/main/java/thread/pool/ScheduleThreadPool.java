package thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduleThreadPool:定时执行线程
 * 
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25 
 * 
 * shared by all free coders
 **/
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