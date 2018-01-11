package thread.synchronize;
/**
	1.volatile: java多线程同步机制: JLS(Java Language Specifications)
		valatile 修饰变量 ==> 内存模型保证所有threads看到一致变量: 一致性 (主内存 线程栈内存)
			但不能保证并发有序性, 在printAll()获取i&j时间区间内, addAll()可能被执行多次,导致j值大于

	2.volatile: 弱同步,不阻塞 某些情况下效率高
		读操作时候,加与不加貌似无影响
		写操作,可能消耗性能更多些  ==> 把握不住尽量用synchronized
	
	3.volatile & final不能修饰同一字段

  4.TODO 想明白在什么特定情况下使用volatile才是正途
    如下案例，最终i j都会加到10, 但是addAll() 和 pringAll() 并发有序性不能得到保证,
      很可能在printAll()期间，addAll()执行了多次
 */
public class ThreadValatile {
	public static void main(String[] args) {
		Runnable runnableAdd = new Runnable() {
			@Override
			public void run() {
				ThreadUnsafteCaculate.addAll();
			}
		};
		Runnable runnablePrint = new Runnable() {
			@Override
			public void run() {
				ThreadUnsafteCaculate.printAll();
			}
		};
		for (int i = 0; i < 10; i++) {
			new Thread(runnableAdd).start();
			new Thread(runnablePrint).start();
			//循环启动同一个线程类
		}
	}
}

class ThreadUnsafteCaculate{
//	static int i = 0, j = 0;
	static volatile int i = 0, j = 0;
	static void addAll() {
//	static synchronized void addAll() {
		i++;
		j++;
	}
	static void printAll() {
//	static synchronized void printAll() {
		System.out.println("i="+i+" j="+j);
	}
}
/**
i=1 j=1
i=1 j=2
i=1 j=1
i=5 j=5
i=5 j=5
i=7 j=7
i=7 j=7
i=8 j=8
i=9 j=9
i=9 j=9

i=1 j=1
i=1 j=1
i=3 j=3
i=3 j=4
i=4 j=5
i=5 j=5
i=5 j=5
i=5 j=5
i=7 j=8
i=8 j=8

i=2 j=2
i=3 j=3
i=2 j=2
i=4 j=4
i=5 j=5
i=6 j=6
i=6 j=6
i=6 j=6
i=10 j=10
i=10 j=10

*/
