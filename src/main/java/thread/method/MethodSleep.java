package thread.method;

import org.junit.Test;

/**
 * MethodSleep:
 *
 * sleep(long millis[,int naos])
 * sleep过程中,在synchronized线程同步内,是持有锁(monitor)的----关门睡觉
 *
 * Thread.Sleep(0): 触发操作系统立刻重新进行一次CPU竞争
 *  大循环里写此句,给了其他线程获取cpu的权力,界面则不会假死在那里
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25
 *
 * shared by all free coders
 **/
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
				System.out.println("由于要睡觉中断线程" +Thread.currentThread().getName()+"一会,门仍然关者,别的线程进不来.");
			} else {
				System.out.println(Thread.currentThread().getName() + "线程中断了一会,其间门锁着,别的线程进不来, 现在睡醒了,中断的线程继续。");
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