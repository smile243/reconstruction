package base;

/**
 * @Description: 
 * @author: yjl
 * @date: 2022年08月06日 22:18
 */
public class Node {
    private Integer val;
    private Node left;
    private Node right;

    public Node(Integer val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public Node() {
    }
}
