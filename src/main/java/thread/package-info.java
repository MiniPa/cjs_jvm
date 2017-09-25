/**
 * thread: 线程处理
 * [深入理解线程：http://blog.csdn.net/iter_zc/article/details/41843595]
 * 0.线程：Thread JavaThread OSThread VMThread
 *   1) jvm角度： new, java, vm, native, blocked
 *   2) OS角度： runnable, monitor_wait, object_wait, sleeping
 *   3) Java： new, runnable, blocked, waiting, time_waiting, terminated, count
 *  线程共享变量==>主内存堆中, 线程自有变量==>线程栈线程工作内存中
 *  两大特性： 可见性 有序性(原子操作) ==> synchronized ,         ==> 主/私内存整体可见
 *            一致性 (不能保证有序性) ==> valatile, 弱同步不阻塞  ==> 主/私内存部分可见
 *
 *
 * 1.线程状态：
 *  1) new 新建状态 => start() => 2)
 *  2) available 可运行状态 [ => schedule开始调度 => 3), 执行条件 lock,scheduler,sleep ]
 *  3) runnable 运行状态
 *  4) waiting/block/sleeping 等待/阻塞/睡眠(线程是活的,但当前没有运行条件)
 *  5) dead 死亡状态 run()执行完毕,
 *     也许线程对象是活的,但已经不是单独执行的线程,强行再start,IllegalThreadStateException
 *
 * 2.线程操作：
 *  1) interrupted
 *  2) join
 *  3) sleep 不让lock,(告诉cpu未来n/s时间内,不会去争cpu,时间过了则按正常方式获取cpu)
 *  4) yeild 让出cpu 不让lock, 非同锁线程可以尝试获取cpu执行了
 *
 * 3.sleep && yeild:
 *  sleep: 给其他线程机会, yeild给同级或优先级高的线程机会
 *  sleep: 申明InterruptionException yeild无
 *  sleep: 比yeild具有更高移植性
 *  sleep: 能指定时间,yeild不行
 *
 * 4.threadPool
 *   fixedThreadPool cachedThreadPool singleThreadPool scheduledThreadPool
 *
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25 
 * 
 * shared by all free coders
 **/
package thread;