package thread.method;

import org.junit.Test;

/**
 * MethodInterrupted: 中断
 *  1.interrupted() 设置thread中断状态==>true
 *  2.isInterrupted() 判断thread中断状态
 *  3.Object:
 *      wait() wait(long) wait(int)
 *      join() join(long) join(long, int)
 *      sleep(long) sleep(long, int)
 *      的方法过程中受阻, 则清除thread中断状态
 *  4.Thread.interrupted() 判断当前thread中断状态, 判断同时会清除thread中断状态
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
 * @version: 1.0.0, 2017-09-25 
 **/
public class MethodInterrupted {
	@Test
	public void interruptedTest() throws InterruptedException{
		SubThread t = new SubThread("SubThread");
		t.start();
		Thread.sleep(1);
		t.interrupt();
	}
}

class SubThread extends Thread {
	int i  = 0;
	public SubThread(String name) {
		super(name);
	}
	@Override
	public void run() {
//		while(true){
		while(!isInterrupted()){
			System.out.println(getName()+" "+getId()+" 执行了"+ ++i +"次");
		}
	}
}