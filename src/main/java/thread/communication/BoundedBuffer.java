package thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BoundedBuffer: 自定义线程安全有界缓存
 *
 * 案例：缓存区----多threads下工作,也是ArrayBlockingQueue的实现逻辑
 * 单Condition：队列满了 无法区分唤醒写进程 或 读进程
 * 多Condition：
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25 
 * 
 * shared by all free coders
 **/
public class BoundedBuffer {
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();// 写进程条件
	final Condition notEmpty = lock.newCondition();// 读进程条件
	
	final Object[] items = new Object[10];// 缓存队列
	int putptr, /*写索引*/ takeptr, /*读索引*/ count;/*队列中存在的数据个数*/
	
	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while(count == items.length){// 队列满 阻塞写进程
				System.out.println("队列满，阻塞写");
				notFull.await();
			}
			items[putptr] = x;
			System.out.println("写入新对象putpstr：" + putptr + "唤醒读进程");
			if (++putptr == items.length) {
				putptr = 0; // 到达队列末端 将putptr置为0
			}
			++count;
			notEmpty.signal();// 唤醒读进程
		} finally {
			lock.unlock();
		}
	}

	public Object get() throws InterruptedException {
		lock.lock();
		try {
			while(count == 0) {
				System.out.println("队列空，阻塞读");
				notEmpty.await();
			}
			Object x = items[takeptr];
			System.out.println("读出新对象takepstr：" + takeptr + "唤醒写进程");
			if (++takeptr == items.length) {
				takeptr = 0;
			}
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final BoundedBuffer bb = new BoundedBuffer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						bb.put(new Object());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						bb.get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}








