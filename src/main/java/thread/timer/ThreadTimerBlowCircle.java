package thread.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*
 * JDK5之后，可以用ScheduledThreadPoolExecutor来替代Timer
 */
public class ThreadTimerBlowCircle {

	static class ThreadTimer1 extends TimerTask {
		@Override
		public void run() {
			System.out.println("blow timer1 !!!");
			new Timer().schedule(new ThreadTimer2(), 2000);
		}
	}
	static class ThreadTimer2 extends TimerTask {
		@Override
		public void run() {
			System.out.println("blow timer2 !!!");
			new Timer().schedule(new ThreadTimer1(), 3000);
		}
	}
	public static void main(String[] args) {
		new Timer().schedule(new ThreadTimer2(), 2000);
		while(true) {
			System.out.println(new Date().getSeconds());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
