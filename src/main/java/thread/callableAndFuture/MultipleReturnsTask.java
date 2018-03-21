package thread.callableAndFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MultipleReturnsTask: 多重返回值的线程任务处理
 * <p>
 * CompletionService: 提交到CompletionService中的Future 是按照完成的顺序排列的
 * ==> TODO 上面的描述需要仔细理解,最好翻看源码学习下
 *  上面的描述是不靠谱的,不知道为啥? 请参照下面眼结果,发现结果顺序是不确定的
 *  可能我理解有误区, for循环中先完成的线程不一定就是先启动的线程
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
 * <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25
 * <p>
 * shared by all free coders
 **/
public class MultipleReturnsTask {

  public static void main(String[] args) {
    ExecutorService threadPool = Executors.newCachedThreadPool();
    //
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
    /*可能做一些事情*/
    for (int i = 0; i < 5; i++) {
      try {
        System.out.println(cs.take().get());
        /* 结果 0 1 2 3 4 偶尔 (1 3 2 0 4) (0 1 2 4 3) .. */
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }
  }

}
