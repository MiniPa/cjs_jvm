import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

/**
 * LinkLoop: 链表是否有环
 *
 * 方案：快慢指针，一个走快些，一个慢些，最后相遇就证明有环
 *
 * @version 0.0.1  @date: 2020-11-14
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class LinkLoop {

    public static class Node {
        private Object data;
        public Node next;

        public Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }

        public Node(Object data) {
            this.data = data;
        }
    }

    /**
     * 判断链表是否有环
     *
     * @param node
     * @return
     */
    public boolean isLoop(Node node) {
        Node slow = node;
        Node fast = node.next;

        while (slow.next != null) {
            Object dataSlow = slow.data;
            Object dataFast = fast.data;

            // 说明有环
            if (dataFast == dataSlow) {
                return true;
            }

            // 一共只有两个节点，但却不是环形链表的情况，判断 NPE
            if (fast.next == null) {
                return false;
            }

            // slow 走慢点, fast 走快点
            slow = slow.next;
            fast = fast.next;
            // 如果走快发现为 null, 说明不存在环
            if (fast == null){
                return false ;
            }
            fast = fast.next
            if (fast == null){
                return false ;
            }
        }
        return false;
    }

    public class LinkLoopTest {

        /**
         * 无环
         * @throws Exception
         */
        @Test
        public void isLoop() throws Exception {
            LinkLoop.Node node3 = new LinkLoop.Node("3");
            LinkLoop.Node node2 = new LinkLoop.Node("2") ;
            LinkLoop.Node node1 = new LinkLoop.Node("1") ;

            node1.next = node2 ;
            node2.next = node3 ;

            LinkLoop linkLoop = new LinkLoop() ;
            boolean loop = linkLoop.isLoop(node1);
            Assert.assertEquals(loop,false);
        }

        /**
         * 有环
         * @throws Exception
         */
        @Test
        public void isLoop2() throws Exception {
            LinkLoop.Node node3 = new LinkLoop.Node("3");
            LinkLoop.Node node2 = new LinkLoop.Node("2") ;
            LinkLoop.Node node1 = new LinkLoop.Node("1") ;

            node1.next = node2 ;
            node2.next = node3 ;
            node3.next = node1 ;

            LinkLoop linkLoop = new LinkLoop() ;
            boolean loop = linkLoop.isLoop(node1);
            Assert.assertEquals(loop,true);
        }

        /**
         * 无环
         * @throws Exception
         */
        @Test
        public void isLoop3() throws Exception {
            LinkLoop.Node node2 = new LinkLoop.Node("2") ;
            LinkLoop.Node node1 = new LinkLoop.Node("1") ;

            node1.next = node2 ;

            LinkLoop linkLoop = new LinkLoop() ;
            boolean loop = linkLoop.isLoop(node1);
            Assert.assertEquals(loop,false);
        }

    }

}
