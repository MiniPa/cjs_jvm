package thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Condition: 将Object监视器method(wait notify notifyAll)分解成不同的对象
 * 				这些对象可与Lock组合使用,为每个对象提供set(wait-set)
 * 			await()--wait()  signal()--notify() signalAll()--notifyAll()
 * Lock 替代 synchronized
 * Condition 替代 Object monitor 
 * 
 * Condition 和 传统线程通信区别：可以为多个线程间建立不同的Condition
 * 
 * 子线程循环10次 主线程循环100次 如此循环100次
 * */
public class ThreadCondition {
	public static void main(String[] args) {
		final BusinessCondition business = new BusinessCondition();
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadExecute(business, "main");
			}
		}).start();
		threadExecute(business, "sub");
	}
	public static void threadExecute(BusinessCondition business, String threadType) {
		for (int i = 0; i < 100; i++) {
			if ("main".equals(threadType)) {
				business.main(i);
			} else {
				business.sub(i);
			}
		}
	}
	
}
class BusinessCondition {
	private boolean bool = true;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	public /*synchronized*/ void main(int loop) {
		lock.lock();
		try {
			while(bool) {
				condition.await();//this.wait();
			}
			for (int i = 0; i < 100; i++) {
				System.out.println("main thread seq of " + i + "loop of " + loop);
			}
			bool = true;
			condition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public /*synchronized*/ void sub(int loop) {
		lock.lock();
		try {
			while(!bool) {
				condition.await();//
			}
			for (int i = 0; i < 10; i++) {
				System.out.println("sub thread seq of " + i + "loop of " + loop);
			}
			bool = false;
			condition.signal();//this.notify()
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}


