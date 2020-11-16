/**
 * BinaryNode: 二叉树层序遍历
 *
 * @version 0.0.1  @date: 2020-11-14
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class BinaryNode {
    private Object data;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode() {
    }

    public BinaryNode(Object data, BinaryNode left, BinaryNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    public BinaryNode createNode(){
        BinaryNode node = new BinaryNode("1",null,null) ;
        BinaryNode left2 = new BinaryNode("2",null,null) ;
        BinaryNode left3 = new BinaryNode("3",null,null) ;
        BinaryNode left4 = new BinaryNode("4",null,null) ;
        BinaryNode left5 = new BinaryNode("5",null,null) ;
        BinaryNode left6 = new BinaryNode("6",null,null) ;
        node.setLeft(left2) ;
        left2.setLeft(left4);
        left2.setRight(left6);
        node.setRight(left3);
        left3.setRight(left5) ;
        return node;
    }
}
