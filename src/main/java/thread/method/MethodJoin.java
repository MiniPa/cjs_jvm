package thread.method;
/*
 * join() 等待直到该线程终止
 * join(long millis[,int nanos]) 等待结束 或时间结束
 * 效果类似 把线程插入到另一线程过程中
 * 
 * Thread priority : Thread.MAX_PRIORITY 10, NORM_PRIORITY 5, MIN_PRIORITY 1
 */
public class MethodJoin {
	public static void main(String[] args) throws InterruptedException {
		ThreadJoin j1 = new ThreadJoin("t1");
		ThreadJoin j2 = new ThreadJoin("t2");
		j1.start();
		j2.start();
		j1.join();
		j2.join();
	}
}
class ThreadJoin extends Thread {
	public ThreadJoin(String name) {
		super(name);
	}
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + "执行了 " + i + "次");
		}
	}
}