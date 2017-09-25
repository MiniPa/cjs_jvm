package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * PoolThread:线程池
 * 
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25 
 * 
 * shared by all free coders
 **/
public class PoolThread {
	public static void main(String[] args) {

	  /*1.可重用固定线程集合threadPool 共享无界队列方式运行
	  * 2.任务多的处于等待状态，任务不足空余线程不会销毁
	  * */
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

		/*缓存初始化线程 可创建新thread 销毁60s未使用thread*/
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

		/*单线程 任务异常终止,自动重启新thread继续任务
		* 不同于直接创建线程，也不同于newFixedThreadPool(1)
		* */
		ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

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

