package thread.synchronize;

import java.util.ArrayList;
import java.util.List;
/* JDK1.5前传统线程通信方式
 	Add thread 判断plate：1)为空 放入egg 唤醒阻塞队列中1个 Get thread  2)不为空 等待 进入阻塞队列
 	Get thread 判断palte: 1)为空 等待 进入阻塞队列   2)不为空 取得egg 唤醒阻塞队列1个 Add thread 
 */
public class Plate_ProducerCustomer {
	List<Object> eggs = new ArrayList<Object>();
	
	public synchronized Object getEgg(){
		while (eggs.size() <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Object egg = eggs.get(0);
		eggs.clear();
		notify();
		System.out.println(Thread.currentThread().getName() 
					+ " get a eggg and plate contain " + eggs.size() + "eggs");
		return egg;
	}
	
	public synchronized void putEgg(Object egg){
		while(eggs.size() > 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		eggs.add(egg);
		notify();
		System.out.println(Thread.currentThread().getName() 
				+ " set a egg and plate contain " + eggs.size() + "eggs");
	}
	
	static class AddThread implements Runnable {
		private Plate_ProducerCustomer plate;
		private Object egg = new Object();
		public AddThread(Plate_ProducerCustomer plate) {
			this.plate = plate;
		}
		@Override
		public void run() {
			plate.putEgg(egg);
		}
	}
	
	static class GetThread implements Runnable{
		private Plate_ProducerCustomer plate;
		public GetThread(Plate_ProducerCustomer plate) {
			this.plate = plate;
		}
		@Override
		public void run() {
			plate.getEgg();
		}
	}
	
	public static void main(String[] args) {
		Plate_ProducerCustomer plate = new Plate_ProducerCustomer();
		for (int i = 0; i < 10; i++) {
			new Thread(new AddThread(plate)).start();
			new Thread(new GetThread(plate)).start();
		}
	}
}

















