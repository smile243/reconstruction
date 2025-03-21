package base;

import lombok.NoArgsConstructor;

/**
 * @author yjl
 * @since 2024/12/28
 */
@NoArgsConstructor
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) { this.val = val; }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static int depth(TreeNode head) {
        if (head == null) {
            return 0;
        }
        return 1+Math.max(depth(head.left), depth(head.right));
    }

    public static TreeNode DEMO(){
        return build(new int[]{1,2,4,5,3,6,7}, new int[]{4,2,5,1,6,3,7}, 0,6,0,6);
    }

    public void buildTree(int[] preorder, int[] inorder) {
        build(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
    }
    public static TreeNode build(int[] preorder, int[] inorder, int preLeft, int preRight,int inLeft,int inRight){
        if(preLeft>preRight||inLeft>inRight){
            return null;
        }
        TreeNode r= new TreeNode(preorder[preLeft]);
        int pivot = inLeft;
        for(int i=inLeft;i<inRight;i++){
            if(inorder[i]==r.val){
                pivot = i;
                break;
            }
        }
        //pre 3 ｜9｜ 20 15 7
        //in 9 ｜3｜ 15 20 7
        r.left = build(preorder, inorder, preLeft+1, preLeft+pivot-inLeft, inLeft, pivot-1);
        r.right = build(preorder, inorder, preLeft+pivot-inLeft+1, preRight, pivot+1, inRight);
        return r;
    }
}
