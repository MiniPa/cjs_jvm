package queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * QueueTest:
 *
 * @version 0.0.1  @date: 2020-11-12
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class QueueTest {


    public static void main(String[] args) {

        ArrayBlockingQueue que = new ArrayBlockingQueue<String>(10);
        que.offer("11111");
        que.offer("22222");
        que.offer("33333");
        que.offer("444444");
        que.offer("55555");
        que.offer("66666");

        System.out.println("1111" + que.peek());
        System.out.println("1111" + que.element());

        System.out.println("1111" + que.poll());
        System.out.println("2222" + que.remove());

        System.out.println("aaa" + que.offer("aaaaa"));
        System.out.println("bbb" + que.offer("bbbbb"));
        System.out.println(que.poll());
        System.out.println(que.poll());

    }



}
