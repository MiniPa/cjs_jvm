
/**
 * thread: 线程处理
 *
 * 1.线程状态：
 *  1) new 新建状态 => start() => 2)
 *  2) available 可运行状态 [ => schedule开始调度 => 3), 执行条件 lock,scheduler,sleep ]
 *  3) running 运行状态
 *  4) waiting/block/sleeping 等待/阻塞/睡眠(线程是活的,但当前没有运行条件)
 *  5) dead 死亡状态 run()执行完毕,
 *     也许线程对象是活的,但已经不是单独执行的线程,强行再start,IllegalThreadStateException
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-25 
 * 
 * shared by all free coders
 **/
package thread;