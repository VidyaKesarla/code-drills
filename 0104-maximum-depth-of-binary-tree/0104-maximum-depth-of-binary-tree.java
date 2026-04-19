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
// Time: O(n) — visit every node once
// Space: O(h) — h = height of tree, recursion stack
//        O(log n) for balanced tree, O(n) worst case (skewed tree)

class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;      // base case: empty tree has depth 0

        int leftDepth  = maxDepth(root.left);   // depth of left subtree
        int rightDepth = maxDepth(root.right);  // depth of right subtree

        return 1 + Math.max(leftDepth, rightDepth);  // current node adds 1
    }
}

// DRY RUN
// tree:     1
//          / \
//         2   3
//        / \
//       4   5
//
// maxDepth(1)
//   maxDepth(2)
//     maxDepth(4)
//       maxDepth(null) → 0
//       maxDepth(null) → 0
//       return 1 + max(0,0) = 1
//     maxDepth(5)
//       maxDepth(null) → 0
//       maxDepth(null) → 0
//       return 1 + max(0,0) = 1
//     return 1 + max(1,1) = 2
//   maxDepth(3)
//     maxDepth(null) → 0
//     maxDepth(null) → 0
//     return 1 + max(0,0) = 1
//   return 1 + max(2,1) = 3  ✓
