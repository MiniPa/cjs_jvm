package thread.parallel;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/*
 * ForkJoin 并行计算框架
 */
public class SumTask extends RecursiveTask<Integer> {
	
	private static final long serialVersionUID = -1054283494278538276L;
	private static final int THRESHOLD = 10000000;
//	private static final int THRESHOLD = 90000000;
	private long[] array;
	private int low;
	private int high;
	
	public SumTask(long[] array, int low, int high) {
		this.array = array;
		this.low = low;
		this.high = high;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		if (high - low < THRESHOLD) {// 小于阈值直接计算
			for (int i = 0; i < array.length; i++) {
				sum += array[i];
			}
		} else {
			int mid = (low + high) >>> 1;// 大任务分割成2个小任务
			SumTask left = new SumTask(array, low, mid);
			SumTask right = new SumTask(array, mid, high);
			left.fork();// 分别计算
			right.fork();
			System.out.println("left.join() " + left.join() + " right.join() " + right.join());
			sum = left.join() + right.join();
		}
		return sum;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long[] array = genArray(90000000);
//		System.out.println(Arrays.toString(array));
		SumTask sumTask = new SumTask(array, 0, array.length-1);
		
		long begin = System.currentTimeMillis();
		
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		forkJoinPool.submit(sumTask);
		Integer result = sumTask.get();
		
		long end = System.currentTimeMillis();
		
		System.out.println(String.format("结果 %s 耗时 %sms", result, end-begin));
	}
	
	private static long[] genArray(int size) {
		long[] array = new long[size];
		for (int i = 0; i < size; i++) {
			array[i] = new Random().nextLong();
		}
		return array;
	}

}








