package thread.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * LockReadWrite: 读写互斥 写写互斥 == 读读不互斥
 *
 * 适用情景：读的并发量特别高时候,有效减缓锁压力,大幅度提高性能
 *
 * TODO 是否有缺陷：大量高并发的读 + 较长的读时间 ==> 导致 写操作不能及时写入(error)
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
 * <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25
 * <p>
 * shared by all free coders
 **/
public class LockReadWrite {
  public static void main(String[] args) {
    final Data data = new Data();
    /*读写各起3个线程*/
    for (int i = 0; i < 3; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          for (int j = 1; j <= 5; j++) {
            data.get();
          }
        }
      }, "read" + i).start();
      new Thread(new Runnable() {
        @Override
        public void run() {
          for (int j = 1; j <= 5; j++) {
            data.set(j);
          }
        }
      }, "write" + i).start();
    }
  }
}

/**
 * 请验证****> ====>出现是否符合代码中描述
 * <p>
 * ReentrantReadWriteLock
 * rrwl.writeLock()
 * rrwl.readlock()
 */
class Data {
  private int data;
  private ReadWriteLock rwl = new ReentrantReadWriteLock();

  /*写操作*/
  public void set(int data) {
    rwl.writeLock().lock();
    try {
      System.out.println(Thread.currentThread().getName() + " ready to write data.**w**>  ");
      /*在有写操作block时候,由于读写互斥,写写互斥,所以不会有其他的写完成. 上下2日期必然成对连续出现.*/
      Thread.sleep(20);
      this.data = data;
      System.out.println("<**w**" + Thread.currentThread().getName() + " write data:  " + this.data);
      System.out.println(String.format("当前Data值: %s。", data));
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      rwl.writeLock().unlock();
    }
  }

  /*读操作*/
  public void get() {
    rwl.readLock().lock();
    try {
      System.out.println(Thread.currentThread().getName() + " ready to read data.  ==r==>");
      /*在有读操作时候,读写互斥,所以上下两日志间不会有新写操作; 读读不互斥,所以可能有其读操作插入。*/
      Thread.sleep(2000);
      System.out.println("<==r==" + Thread.currentThread().getName() + " read data: " + this.data);
      System.out.println(String.format("当前Data值: %s。", data));
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      rwl.readLock().unlock();
    }
  }

}










