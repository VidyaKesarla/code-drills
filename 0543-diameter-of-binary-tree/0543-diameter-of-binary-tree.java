/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        calculateHeight(root);
        return maxDiameter;
    }

    public int calculateHeight(TreeNode root) {
        if(root == null) {
        return 0;
    }
    int lh = calculateHeight(root.left);
    int rh = calculateHeight(root.right);
    int currDiameter = lh + rh;
    maxDiameter = Math.max(maxDiameter, currDiameter);
    return 1 + Math.max(lh,rh);
    }
}