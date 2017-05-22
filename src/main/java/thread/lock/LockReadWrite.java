package thread.lock;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/*
 * 读写互斥 写写互斥 == 读读不互斥
 * 缺陷：大量高并发的读 + 较长的读时间 ==> 导致 写操作不能及时写入(error)
 * 	不存在这样的缺陷
 */
public class LockReadWrite {
	public static void main(String[] args) {
		final Data data = new Data();
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						data.get();
					}
				}
			}, "read"+i).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						data.set(j);
					}
				}
			}, "write"+i).start();
		}
	}
}

/**
 * ReentrantReadWriteLock
 * rrwl.writeLock()
 * rrwl.readlock()
 */
class Data {
	private int data;
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	public void set(int data) {
		rwl.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + " ready to write " + data);
			Thread.sleep(20);
			this.data = data;
			System.out.println(Thread.currentThread().getName() + " write " + this.data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rwl.writeLock().unlock();
		}
	}
	public void get() {
		rwl.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + " ready to read ");
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + " read " + this.data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rwl.readLock().unlock();
		}
	}
}










