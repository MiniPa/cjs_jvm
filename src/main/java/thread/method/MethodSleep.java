package thread.method;

import org.junit.Test;
/*
 * sleep(long millis[,int naos])
 * sleep过程中,在synchronized线程同步内,是持有锁(monitor)的----关门睡觉
 */
public class MethodSleep {
	
	public static void main(String[] args) {
		Service service = new Service();
		SleepThread s1 = new SleepThread("s1",service);
		SleepThread s2 = new SleepThread("s2",service);
		s1.start();
		s2.start();
	}
	@Test
	public void sleepTest(){
		Service service = new Service();
		SleepThread s1 = new SleepThread("s1",service);
		SleepThread s2 = new SleepThread("s2",service);
		s1.start();
		s2.start();
	}
}
class SleepThread extends Thread {
	private Service service;
	public SleepThread(String name, Service service) {
		super(name);
		this.service = service;
	}
	@Override
	public void run() {
		service.calc();
	}
}
class Service {
	public synchronized void calc() {
		System.out.println(Thread.currentThread().getName() + " 开始计算");
		System.out.println(Thread.currentThread().getName() + " 累了 开始睡觉");
		try {
			Thread.sleep(1000);
			if (Thread.interrupted()) {
				System.out.println("睡觉 中断了线程");
			} else {
				System.out.println("醒了 中断了线程");
			}
		} catch (InterruptedException e) {
			if (Thread.interrupted()) {
				System.out.println("Exception 睡觉 中断了线程");
			}
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " 醒了 继续干活");
		System.out.println(Thread.currentThread().getName() + " 结束计算");
	}
}