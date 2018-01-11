package thread;

import org.junit.Test;

/**
 * 存在成员变量的类 ---- threadUnsafe: 成员变量可能发生非原子操作
 * 局部变量(方法内变量线程安全) ---- threadSafe
 * <p>
 * struts1: action Singleton 不建议给action成员变量
 * struts2: action 每次请求创建一个action 不用考虑线程安全问题
 * <p>
 * 线程不安全类解决方式: threadUnsafeSolution
 * 1.ThreadUnsafeCount 中 private int num; 变成count局部变量
 * ==> 每个thread各自调用对象 count()产生的局部变量
 *
 * 2.ThreadUnsafeCount count = new ThreadUnsafeCount(); 变成run()方法局部变量
 * ==> 每个thread启动时候 run()内局部变量
 *
 * 3.每次启动thread new一个新的线程类(不建议)
 **/
public class ThreadunSafeTest {

  @Test
  public void testThread1() {
    Runnable runnable = new Runnable() {
      ThreadUnsafeCount count = new ThreadUnsafeCount();

      @Override
      public void run() {
        count.count();
      }
    };
    for (int i = 0; i < 10; i++) {
      new Thread(runnable).start();
      System.out.println("启动线程: " + i + "号。");
      /*循环启动同一个线程类*/
    }
  }

}

/*线程不安全类,其成员变量可能会导致整个操作非原子, 会发生各种奇葩事情，预料不到的奇葩事情。*/
class ThreadUnsafeCount {
  /*成员变量,线程不安全*/
  private int num;

  @Test
  public void count(/*局部变量,线程安全*/) {
    System.out.println(Thread.currentThread().getName() + "start-num: " + num);
    for (int i = 1; i <= 100; i++) {
      num += 10;
    }
    System.out.println(Thread.currentThread().getName() + "end-num: " + num);
  }
}

/*TODO 线程安全类,放到局部变量中,如何实现多线程共享变量*/
/*class ThreadsafeCount {
  private int num;

  @Test
  public void count(*//*局部变量,线程安全*//*) {
    for (int i = 1; i <= 10; i++) {
      num += 1;
    }
    System.out.println(Thread.currentThread().getName() + "-num: " + num);
  }
}*/
