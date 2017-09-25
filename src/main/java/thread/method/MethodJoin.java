package thread.method;

/**
 * MethodJoin:
 *
 * t1.join() 等待直到t1线程终止,再开始继续执行本线程.
 *  想像一下本线程被卡住,跳到另外的t1线程进行执行,t1结束之后才能跳回来继续执行本线程
 *
 * join(long millis[,int nanos]) 等待结束 或时间结束
 * 效果类似 把线程接到到另一线程过程中
 *
 * Thread priority : Thread.MAX_PRIORITY 10, NORM_PRIORITY 5, MIN_PRIORITY 1
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25 
 * 
 * shared by all free coders
 **/
public class MethodJoin {
	public static void main(String[] args) throws InterruptedException {
		ThreadJoin j1 = new ThreadJoin("t1");
		ThreadJoin j2 = new ThreadJoin("t2");
		j1.start();
		j2.start();
		j1.join();
		j2.join();
		/*循环次数越大,已j2结束的几率越高*/
	}
}
class ThreadJoin extends Thread {
	public ThreadJoin(String name) {
		super(name);
	}
	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			System.out.println(Thread.currentThread().getName() + "执行了 " + i + "次");
		}
	}
}