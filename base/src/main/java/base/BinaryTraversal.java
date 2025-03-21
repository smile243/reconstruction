package base;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;

/**
 * 迭代遍历需要画出图和栈
 * @author yjl
 * @since 2024/12/28
 */
public class BinaryTraversal {
    public static void main(String[] args) {
        TreeNode treeNode = TreeNode.DEMO();
        BinaryTraversal traversal = new BinaryTraversal();
        traversal.traversalByRecursion(treeNode);
    }

    public void traversalByRecursion(TreeNode treeNode) {
        List<Integer> result = Lists.newArrayList();
        preOrderIteration(treeNode, result);
        System.out.println("前序遍历" + result);
        result.clear();
        inOrderIteration(treeNode, result);
        System.out.println("中序遍历" + result);
        result.clear();
        postOrderIteration(treeNode, result);
        System.out.println("后序遍历" + result);
        result.clear();
        level(treeNode, result);
        System.out.println("层序遍历" + result);
        result.clear();
    }

    public void level(TreeNode root, List<Integer> result) {
        if (null == root) {
            return;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        list.offer(root);
        TreeNode cur;
        while(!list.isEmpty()){
            int size = list.size();
            for(int i=0;i<size;i++){
                cur=list.pop();
                result.add(cur.val);
                if(null!=cur.left){
                    list.offer(cur.left);
                }
                if(null!=cur.right){
                    list.offer(cur.right);
                }
            }
        }
    }

    public void preOrderRecursion(TreeNode root, List<Integer> result) {
        if (null == root) {
            return;
        }
        result.add(root.val);
        preOrderRecursion(root.left, result);
        preOrderRecursion(root.right, result);
    }

    /**
     * 1.访问根节点，将节点入栈，一直向左遍历直到最左边
     * 2.出栈，访问右子树，循环以上步骤
     * @param root
     * @param result
     */
    public void preOrderIteration(TreeNode root, List<Integer> result) {
        if (null == root) {
            return;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        TreeNode cur = root;
        while (cur!=null||!list.isEmpty()) {
            while (null != cur) {
                result.add(cur.val);
                list.push(cur);
                cur = cur.left;
            }
            cur = list.pop().right;
        }
        /**
         * 非常规思路
         */
//        list.push(root);
//        TreeNode node;
//        while (!list.isEmpty()) {
//            node = list.pop();
//            result.add(node.val);
//            if (node.right != null) {
//                list.push(node.right);
//            }
//            if (node.left != null) {
//                list.push(node.left);
//            }
//        }
    }

    public void inOrderRecursion(TreeNode root, List<Integer> result) {
        if (null == root) {
            return;
        }
        inOrderRecursion(root.left, result);
        result.add(root.val);
        inOrderRecursion(root.right, result);
    }

    public void inOrderIteration(TreeNode root, List<Integer> result) {
        if (null == root) {
            return;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        TreeNode cur = root;
        while (cur!=null||!list.isEmpty()) {
            while (null != cur) {
                list.push(cur);
                cur = cur.left;
            }
            cur = list.pop();
            result.add(cur.val);
            cur = cur.right;
        }
    }

    public void postOrderRecursion(TreeNode root, List<Integer> result) {
        if (null == root) {
            return;
        }
        postOrderRecursion(root.left, result);
        postOrderRecursion(root.right, result);
        result.add(root.val);
    }



    public void postOrderIteration(TreeNode root, List<Integer> result) {
        if (null == root) {
            return;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        TreeNode cur = root;
        TreeNode last = null;
        while (cur!=null||!list.isEmpty()) {
            while (null != cur) {
                list.push(cur);
                cur = cur.left;
            }
            cur = list.peek();
            //右子树被访问过，或者没有右子树
            if (cur.right == null || cur.right == last) {
                result.add(cur.val);
                list.pop();
                last = cur;
                cur = null;
            } else {
                cur = cur.right;
            }

        }
    }
}
