package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PoolThread {
	public static void main(String[] args) {
		
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		// 可重用固定线程集合threadPool 共享无界队列方式运行
		// 任务多的处于等待状态，任务不足空余线程不会销毁
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		// 缓存初始化线程 可创建新thread 销毁60s未使用thread
		ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
		// 单线程 任务异常终止,自动重启新thread继续任务
		// 不同于直接创建线程，也不同于newFixedThreadPool(1)
		
		for (int i = 0; i < 5; i++) {
			final int taskid = i;
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("第" + taskid + "任务,第" + j + "次执行");
					}
				}
			});
		}
	}
}

