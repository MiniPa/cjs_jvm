package thread.callableAndFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultipleReturnsTask {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		// CompletionService: 提交到CompletionService中的Future 是按照完成的顺序排列的
		//也可用集合代替,用Executor将线程返回的future放入集合：future按照添加顺序排列的
		CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
		for (int i = 0; i < 5; i++) {
			final int taskid = i;
			cs.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					return taskid;
				}
			});
		}
		// 可能做一些事情
		for (int i = 0; i < 5; i++) {
			try {
				System.out.println(cs.take().get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
