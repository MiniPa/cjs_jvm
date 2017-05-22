package thread;

import org.junit.Test;

/**
	存在成员变量的类 ---- threadUnsafe: 成员变量可能发生非原子操作
	局部变量(方法内变量线程安全) ---- threadSafe
	
	struts1: action Singleton 不建议给action成员变量
	struts2: action 每次请求创建一个action 不用考虑线程安全问题
	
	线程不安全类解决方式: threadUnsafeSolution
	1.ThreadUnsafeCount 中 private int num; 变成count局部变量 
			==> 每个thread各自调用对象 count()产生的局部变量
	2.ThreadUnsafeCount count = new ThreadUnsafeCount(); 变成run()方法局部变量 
			==> 每个thread启动时候 run()内局部变量
	3.每次启动thread new一个新的线程类(不建议)
**/
public class ThreadunSafeTest {
	
	@Test
	public void testThread1(){
		Runnable runnable = new Runnable() {
			ThreadUnsafeCount count = new ThreadUnsafeCount();
			@Override
			public void run() {
				count.count();
			}
		};
		for (int i = 0; i < 10; i++) {
			new Thread(runnable).start();
			//循环启动同一个线程类
		}
	}

}

class ThreadUnsafeCount {
	private int num;
	@Test
	public void count() {
		for (int i = 1; i <= 10; i++) {
			num += i;
		}
		System.out.println(Thread.currentThread().getName() + "-num: " + num);
	}
}
