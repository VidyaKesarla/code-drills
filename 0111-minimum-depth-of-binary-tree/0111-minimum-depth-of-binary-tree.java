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
    
    private int dfs(TreeNode root){
        if(root == null)
        return 0;
        //if only one of the child is non null then go into that recursion
        if(root.left == null){
            return 1 + dfs(root.right);
        }
        if(root.right == null){
            return 1 + dfs(root.left);
        }
        //both children are non null: then go into that recursion
        return 1 + Math.min(dfs(root.left), dfs(root.right));
    }

    public int minDepth(TreeNode root) {
        return dfs(root);
        //ask is to find the minimum number of nodes between the root and any leaf node, including both.
        //if we break this into subproblems: we need to return the answer from the root of the current tree
        //if we know the answer: considering the left and right child of the root node: 
        //if the min depth for the root node's left child is x and minimum depth for the root node's right child is y then min depth for the whole tree with the root node will be 1+ min(x,y). 1 is for the root node

        //this way we can divide the current problem into subprobelsm and then solve them using recursion. base condition of this recursion would be when the node is NULL.
        //in this case i should return 0

        //one tricky thing to consider here: is when one of the childresn is NULL and the other one is not: we shouldnt move forward with recursion on the null child. if we do , we would return 0 due to the base condition and the count of nodes from the leaf node on the other side would be discarded as we are taking the min of the 2. in case both children are null, its fine to go into recursion as both would return 0 and the min of two wont cause an issue

        // we are first traversing to the deepest node and then backtracking to the parent node to find the min depth for it. hence its called DFS

    }

}
//tc: O(n)
//sc: O(n)

// Time complexity: O(N)

// We will traverse each node in the tree only once; hence, the total time complexity would be O(N).

// Space complexity: O(N)

// The only space required is the stack space; the maximum number of active stack calls would equal the maximum depth of the tree, which could equal the total number of nodes in the tree. Hence, the space complexity would equal O(N).