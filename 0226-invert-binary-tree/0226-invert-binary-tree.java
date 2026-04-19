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
// Space: O(h) — recursion stack height

class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;   // base case

        // swap left and right
        TreeNode temp  = root.left;
        root.left      = root.right;
        root.right     = temp;

        // recursively invert both subtrees
        invertTree(root.left);
        invertTree(root.right);

        return root;
    }
}

// DRY RUN
// tree:    4
//         / \
//        2   7
//
// invertTree(4)
//   swap: left=7, right=2
//   tree is now: 4
//               / \
//              7   2
//   invertTree(7)
//     swap: left=null, right=null. nothing changes.
//   invertTree(2)
//     swap: left=null, right=null. nothing changes.
//   return node 4  ✓
