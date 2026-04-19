/*
 * APPROACH COMPARISON: Lowest Common Ancestor (LCA)
 * -----------------------------------------------
 * * 1. Path-Finding Approach (Current Solution):
 * - Logic: Find root-to-node paths for p and q, then find the last common node.
 * - Time:  O(N) - We traverse the tree to find both paths.
 * - Space: O(H) - We store two ArrayLists of length proportional to tree height.
 * - Pros:  Very intuitive and easy to debug.
 * * 2. Brute Force Approach:
 * - Logic: For every node, check if both p and q exist in its subtrees.
 * - Time:  O(N^2) - We perform an O(N) search for every node in the tree.
 * - Space: O(H) - Only recursion stack space; no extra lists.
 * - Cons:  Extremely slow for large/skewed trees.
 * * 3. Optimal One-Pass Recursive (Bottom-Up):
 * - Logic: Post-order traversal where each node returns p, q, or null.
 * - Time:  O(N) - Single traversal of the tree.
 * - Space: O(H) - No extra data structures, only the implicit recursion stack.
 * - Pros:  Most memory-efficient and fastest implementation.
 * * Note: H = Height of the tree (log N for balanced, N for skewed).
 */
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
























// class Solution {
//     public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//         if (root == null) return null;

//         // If both p and q are in the left subtree, LCA is in the left
//         if (exists(root.left, p) && exists(root.left, q)) {
//             return lowestCommonAncestor(root.left, p, q);
//         }
        
//         // If both p and q are in the right subtree, LCA is in the right
//         if (exists(root.right, p) && exists(root.right, q)) {
//             return lowestCommonAncestor(root.right, p, q);
//         }

//         // If they are split (one left, one right) or root is p or q, root is LCA
//         return root;
//     }

//     // Helper to check if a node exists in a subtree - O(N)
//     private boolean exists(TreeNode root, TreeNode target) {
//         if (root == null) return false;
//         if (root == target) return true;
//         return exists(root.left, target) || exists(root.right, target);
//     }
// }