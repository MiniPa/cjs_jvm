package thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*  
 * Lock interface
 * try: 放置互斥区  finally:unlock()	
 * */
public class LockTest {
	public static void main(String[] args) {
		final LockOutputer lot = new LockOutputer();
		new Thread() {
			@Override
			public void run() {
				lot.out("zhangsan");
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				lot.out("lisi");
			}
		}.start();
	}
}

/**
 * ReentrantLock
 * lock.lock()
 */
class LockOutputer {
	private Lock lock = new ReentrantLock();
	public void out(String name) {
		lock.lock();
		try {
			for (int i = 0; i < name.length(); i++) {
				System.out.print(name.charAt(i));
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
