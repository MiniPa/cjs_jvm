package thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockScope: lock相对于synchronized 具有更大的灵活性
 *
 * @author: Chengjs, version:1.0.0, 2017/4/24
 */
public class LockScope {
  private int j;
  private Lock lock = new ReentrantLock();

  public static void main(String[] args) {
    LockScope ls = new LockScope();
    for (int i = 0; i < 2; i++) {
      new Thread(ls.new Adder()).start();
      new Thread(ls.new SubTractor()).start();
    }
  }

  private class SubTractor implements Runnable {
    @Override
    public void run() {
      while (true) {
        /**
         * synchornized(LockScope.this){
         *  System.out.println("j--="+j--)
         * }
         */
        lock.lock();
        try {
          System.out.println("j--=" + j--);
        } finally {
          lock.unlock();
        }
      }
    }
  }

  private class Adder implements Runnable {
    @Override
    public void run() {
        /**
         * synchornized(LockScope.this){
         *  System.out.println("j++="+j++)
         * }
         */
      while (true) {
        lock.lock();
        try {
          System.out.println("j++=" + j++);
        } finally {
          lock.unlock();
        }
      }
    }
  }
}
