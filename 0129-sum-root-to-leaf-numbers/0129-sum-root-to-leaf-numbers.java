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
//Iterative and recursive preorder traversal gives you good time complexity,but they consume space O(H), hence we need to use the morris preorder traversal for this 

//the basic idea of morris traversal is to use no space but to traverse the tree.
//idea: set the temporary link between the node and its predecessor

    //predecessor =
// one step left and then right till you can
// successor =
// one step right and then left till you can
// successor
    public int sumNumbers(TreeNode root) {
        int rootToLeaf = 0;
        int currNumber = 0;
        int steps;
        TreeNode predecessor;

        while(root != null){
        //if there is a left child => compute the predecessor
        //if there is no link => predecessor.rigth = root -> set it
        //if there is a link predecessort.right = root -- > break it

        if(root.left != null){
            //basically we can find out the predecessor of a tree by traversing towards left and then right till you can.
            predecessor = root.left;
            steps = 1;
            while(predecessor.right != null && predecessor.right != root){
                predecessor = predecessor.right;
                ++steps;
            }

            //now i set the link predecessor.right = root and go to explore the left subtree

            if(predecessor.right == null){
                currNumber = currNumber * 10 + root.val;
                //link to the root now
                predecessor.right = root;
                root = root.left;
            }

            //break the link predecessor.right = root
            //once the link is broken, its time to change subtree and go to the right

            else {
                if(predecessor.left == null){
                    //if we are on the lead => update the sum
                    rootToLeaf = rootToLeaf + currNumber;
                }
                //so if we have already explored this:
                //backtrack
                for(int i =0;i<steps;i++){
                    currNumber = currNumber / 10;
                }
                // ??
                predecessor.right = null;
                root = root.right;
            }

        } else {
            //if there is no left child: just go right
    // ???
            currNumber = currNumber * 10 + root.val;
            if(root.right == null){
                rootToLeaf = rootToLeaf + currNumber;
            }
            root = root.right;
        }
        }

        return rootToLeaf;
        
    }
}