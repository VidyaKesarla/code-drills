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
    public boolean isCompleteTree(TreeNode root) {
        //lets go ahead with the depth first approach
        //a complete binary tree has an interesting property:  that we cna use to find the children and parents of any node
        //it can be represented as an array.  

        //if the index of a node in array is i: the element at index 2i+1 will be its left child
        //the element at index 2i+2 will be its right child.

        //if there are total of n nodes in a complete binary tree: it can be represented with an array where the nodes are ordered level by level left to right.

        // we can therefore use this property to solve the problem

        //starting with the root node: assignig it an index of 0: 

        //we can use the above property to assign indices to all the other nodes in the tree

        //let n represent the total number of nodes in the tree.

        //as we saw before: the assigned index of every node must be smaller than or equal to n for the given tree to form a complete binary tree

        //if the index of the node is greater or equal to n, it means a node is missing from the first n indices. in such a case, the tree is not a complete binary tree. 

        //if index < n we proceed to its children, we use index as 2*index + 1 for the left child node.left and 2*index+2 for the right child. 


        //one solution for this is: for every node: we can recursively iterate over its left and right children and verify if the assigned indices are smaller than n. 
        //we can use a dfs to perform this recursive traversal

        //in dfs, we use a recursive function to explore nodes as far as possible along each branch. upon reaching the end of this branch: wwe backtrack to previous node and continue exploring the next branches

        return dfs(root, 0, countNodes(root));

    }

    //lets create a count nodes method that takes root as a parameter and it returns the total number of nodes in the subtree of the root.

    public int countNodes(TreeNode root){
        if(root == null)
        return 0;

        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    public boolean dfs(TreeNode node, int index, int n){
        if(node == null){
            return true;
        }

        //if index assigned to current node is greater than or equal to the number of nodes
        //in tree then the given tree is not a complete binary tree
        if(index >= n){
            return false;
        }

        return dfs(node.left, 2*index+1, n) && dfs(node.right, 2*index+2, n);

    }


}