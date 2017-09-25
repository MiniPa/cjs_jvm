package thread.method;

import org.junit.Test;

/**
 * MethodYeild:
 *
 * yeild() 暂停当前执行的thread对象,并执行其他thread / 正在执行的thread,让出CPU
 * 若存在synchronized线程同步,yeild()不释放锁(monitor)
 * 
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25 
 * 
 * shared by all free coders
 **/
public class MethodYeild {
	@Test
	public void testYeild() throws InterruptedException{
		YeildSubThread y1 = new YeildSubThread("ta");
		YeildSubThread y2 = new YeildSubThread("tb");
		y1.start();
		y2.start();
		Thread.sleep(10);
		y1.interrupt();
		y2.interrupt();
	}
}
class YeildSubThread extends Thread{
	int i = 0;
	public YeildSubThread(String name) {
		super(name);
	}
	@Override
	public void run() {
		while(!isInterrupted()){
			++i;
			System.out.println(getName()+"----"+i);
			if (i % 10 == 0) {
				System.out.println(getName()+" 执行了  "+ i +"次");
				Thread.yield();
			}
		}
	}
}