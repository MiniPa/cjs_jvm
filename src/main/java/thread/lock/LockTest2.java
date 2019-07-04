package thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockTest2: 线程测试
 *
 * @version 0.0.1  @date: 2019-06-29
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class LockTest2 {

    private int j;

    /**
     * 可重入锁
     */
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        LockTest2 tt = new LockTest2();
        for (int i = 0; i < 2; i++) {
            new Thread(tt.new Adder()).start();
            new Thread(tt.new Subtractor()).start();
        }
    }

    private class Subtractor implements Runnable {

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    System.out.println("j--" + j--);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private class Adder implements Runnable {

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    System.out.println("j++" + j++);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
