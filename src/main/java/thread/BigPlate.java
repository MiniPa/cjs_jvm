package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/*
 * ArrayBlockQueue: 数组支持 有界阻塞队列 FIFO 必须明确大小
 * LinkedBlockQuue: 可变大小 FIFO 未明确大小则默认大小Integer.MAX_VALUE
 * 			吞吐量高于基于数组的队列,但在大多数并发应用程序中,可预知的性能要低.
 * PriorityBlockingQueue: 类似Linked 排序-对象自然排序(或更具构造函数Comparator决定)
 * SynchronousQueue: 同步队列 没有任何容量,每个插入必须等另一个线程移除
 */
public class BigPlate {
	private BlockingQueue<Object> eggBasket = new ArrayBlockingQueue<Object>(5);
	
	public void putEgg(Object egg) {
		try {
			eggBasket.put(egg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 下处输出有时候不明确,因为与put操作不是一个原子操作
		System.out.println("放入鸡蛋,现在有" + eggBasket.size());
	}

	public Object getEgg() {
		Object egg = null;
		try {
			// 从盘子取鸡蛋, 盘子空了 则线程阻塞
			egg = eggBasket.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("取到鸡蛋,现在有" + eggBasket.size());
		return egg;
	}
	
	static class AddThread extends Thread {
		private BigPlate plate;
		private Object egg = new Object();
		public AddThread(BigPlate plate) {
			this.plate = plate;
		}
		@Override
		public void run() {
			plate.putEgg(egg);
		}
	}
	static class GetThread extends Thread {
		private BigPlate plate;
		public GetThread(BigPlate plate) {
			this.plate = plate;
		}
		@Override
		public void run() {
			plate.getEgg();
		}
	}
	
	public static void main(String[] args) {
		BigPlate plate = new BigPlate();
		for (int i = 0; i < 10; i++) {
			new Thread(new AddThread(plate)).start();
		}
		for (int i = 0; i < 10; i++) {
			new Thread(new GetThread(plate)).start();
		}
	}
}










