/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        ArrayList<TreeNode> p1 = nodeToRootPath(root, p);
        ArrayList<TreeNode> q1 = nodeToRootPath(root, q);

        if (p1 == null || q1 == null) return null;

        int i = p1.size() - 1;
        int j = q1.size() - 1;

        TreeNode lca = null;
        while(i>=0 && j>=0 && p1.get(i) == q1.get(j)){
            lca = p1.get(i);
            i--; j--;
        }

        

        return lca;
    }

    public ArrayList<TreeNode> nodeToRootPath(TreeNode root, TreeNode k) {

        if (root == null){
            return null;
        }

        if (root == k){
            ArrayList<TreeNode> list = new ArrayList<>();
            list.add(root);
            return list;
        }

        ArrayList<TreeNode> p1 = nodeToRootPath(root.left, k);
        if (p1 != null){
            
            p1.add(root);
            return p1;
        }

        ArrayList<TreeNode> q1 = nodeToRootPath(root.right, k);
        if (q1 != null){
            q1.add(root);
            return q1;
        }


        return null;

    }


}