package thread.communication;
/**
 * 子线程循环10次 主线程循环100次 如此循环100次
 * 1.wait()建议用while(), wait()和notify()必须位于 synchronized内部, 且只能由锁对象来调用
 */
public class ThreadCircle {
	public static void main(String[] args) {
		final Business business = new Business();
		new Thread(new Runnable() {
				@Override
			public void run() {
				threadExecute(business, "sub");
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadExecute(business, "main");
			}
		}).start();
//		threadExecute(business, "main");
	}
	public static void threadExecute(Business business, String threadType){
		for (int i = 0; i < 10; i++) {
			try {
				if ("main".equals(threadType)) {
					business.main(i);
				} else {
					business.sub(i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
class Business{
	private boolean bool = true;
	public synchronized void main(int loop) throws InterruptedException{
		while(bool){
			this.wait();
		}
		for (int i = 0; i < 5; i++) {
			System.out.println("main thread seq of " + i + ", loop of " + loop);
		}
		bool = true;
		this.notify();
	}
	public synchronized void sub(int loop) throws InterruptedException{
		while(!bool){
			this.wait();
		}
		for (int i = 0; i < 3; i++) {
			System.out.println("sub thread seq of " + i + ", loop of " + loop);
		}
		bool = false;
		this.notify();
	}
}










