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

 /**
 * Complexity Analysis:
 * * Time Complexity: O(N)
 * - We traverse the preorder array once to create each node (N nodes).
 * - The HashMap lookup for the 'mid' index in the inorder array is O(1).
 * - Total time is O(N) to build the entire tree.
 * * Space Complexity: O(N)
 * - HashMap stores N elements: O(N).
 * - Recursion Stack: In the worst case (a skewed tree), the stack depth is O(N).
 * - In a balanced tree, the stack depth would be O(log N).
 * - Overall space complexity remains O(N).
 */

 /**
 * TIME COMPLEXITY: O(N)
 * - We process each of the N nodes exactly once.
 * - The HashMap provides O(1) average time lookups to find the root's index in the inorder array.
 * * SPACE COMPLEXITY: O(N)
 * - O(N) to store the inorder index map.
 * - O(H) for the recursion stack, where H is the height of the tree. 
 * In the worst case (skewed tree), H = N.
 * * WHY BRUTE FORCE IS SUBOPTIMAL:
 * - A brute force approach would search the inorder array for the rootValue 
 * using a linear scan (O(N)) inside every recursive call.
 * - This leads to O(N^2) time complexity. For a tree with 10^5 nodes, 
 * O(N) takes ~100,000 operations, while O(N^2) would take 10,000,000,000 
 * operations, likely resulting in a Time Limit Exceeded (TLE) error.
 */
class Solution {
    // 1. Consistency: Use the same name everywhere (e.g., inorderMap)
    private Map<Integer, Integer> inorderMap = new HashMap<>();
    private int preIdx = 0;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preIdx = 0;
        inorderMap.clear(); // Good practice to clear for multiple test cases
        
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i); // Fix name here
        }

        return arrayToTree(preorder, 0, inorder.length - 1);
    }

    private TreeNode arrayToTree(int[] preorder, int left, int right) {
        if (left > right) return null;

        int rootValue = preorder[preIdx++];
        TreeNode root = new TreeNode(rootValue);

        int mid = inorderMap.get(rootValue); // Fix name here

        // FIX: Pass the 'preorder' array, NOT 'rootValue'
        // FIX: The right-side range should be (mid + 1, right)
        root.left = arrayToTree(preorder, left, mid - 1);
        root.right = arrayToTree(preorder, mid + 1, right);

        return root;
    }
}
