package thread.synchronize;
/**
1.Java线程2特性:
	1)可见性: threads见传递数据只能通过共享变量实现, 
		被共享变量==>在主内存(堆内存中),thread有自己的工作内存(线程栈)
		thread操作共享变量,先将其从主内存copy到线程栈,然后执行.最后将栈内存刷新到主内存
		一对象在多threads中存在副本,一内存修改了共享变量,其他threads应该能够看到被修改的值==>可见性
	2)有序性：多threads执行,cpu随机调度,线程执行无序. 保证线程操作共享变量先后有序==>有序性

2.synchronized 保证有序性&可见性 ==> 可视为 原子操作
	1)synchronized 包含互斥代码 上互斥锁, synchronized(obj){ 互斥代码 } obj必须为threads共享变量,否则无效
	2)synchronized 锁定互斥方法 public synchronized void mehtod();
			相当于synchronized(this)锁定方法的代码块
			synchronized static method(); 相当于 synchronized(this.class)互斥锁
	3)锁对象(JLS叫monitor): 就绪队列 阻塞队列
			1.获得锁->2.清空线程栈->3.copy主存->4.执行->5.刷新主存->6.释放锁
**/
public class TraditionalThreadSynchronized {
	public static void main(String[] args) {
		final Outputer outputer = new Outputer();
		new Thread(){
			public void run(){
				outputer.output("111111111");
			}
		}.start();
		new Thread(){
			public void run(){
				outputer.output("aaaaaaaaa");
			}
		}.start();
	}
}

class Outputer{
//	public synchronized void output(String name){
	public void output(String name){
//		synchronized (this) {
			for (int i = 0; i < name.length(); i++) {
				System.out.print(name.charAt(i));
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
//		}
	}
}









