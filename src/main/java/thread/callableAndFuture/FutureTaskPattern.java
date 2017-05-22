package thread.callableAndFuture;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
/*
 * Callable: 可有返回值(Future去拿)  Runnable：无返回值
 * Future: 获取异步线程返回值    Future模式: http://openhome.cc/Gossip/DesignPattern/FuturePattern.htm
 * FutureTask: implements Runnable && Future
 */
public class FutureTaskPattern {
	public static void main(String[] args) {
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return new Random().nextInt(100);
			}
		};
		FutureTask<Integer> future = new FutureTask<Integer>(callable);
		new Thread(future).start();
		try {
			Thread.sleep(2000);
			System.out.println(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
