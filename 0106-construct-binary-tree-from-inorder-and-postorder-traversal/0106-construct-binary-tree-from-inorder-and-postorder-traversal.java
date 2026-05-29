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
    int post_idx;
    int [] post_order;
    int [] in_order;
    //to store the inorder array's values and its particcular indices
    HashMap <Integer, Integer> idx_map = new HashMap<Integer, Integer>();

    public TreeNode helper(int in_left, int in_right){
        //if there are no elements to construct subtree ie, in_left > in_right
        if(in_left > in_right){
            return null;
        }

        //pick up the post_idx element as a root
        int root_val = post_order[post_idx];
        TreeNode root = new TreeNode(root_val);

        //root splits inorder list into left and right subtrees
        int index = idx_map.get(root_val);

        //recursion
        post_idx--;
        //build the right subtree
        root.right = helper(index + 1, in_right);
        //build the left subtree
        root.left = helper(in_left, index -1);

        return root;

    }


    public TreeNode buildTree(int[] inorder, int[] postorder) {
        this.in_order = inorder;
        this.post_order = postorder;
        //we have to start from the last postorder element and consider it as the root
        post_idx = post_order.length - 1;
        //build a hashmap which stores the inorder arrays values and its indices
        int idx = 0;
        for(Integer val: inorder){
            idx_map.put(val, idx++);
        }         
        //we have created a helper function to create a tree from its root
        return helper(0, in_order.length-1);
    }
}
// Start from not inorder traversal, usually it's a preorder or postorder one, and use the traversal picture above to define the strategy to pick the nodes. For example, for preorder traversal the first value is a root, then its left child, then its right child, etc. For postorder traversal the last value is a root, then its right child, then its left child, etc.

// The value picked from preorder/postorder traversal splits the inorder traversal into left and right subtrees. The only information one needs from inorder - if the current subtree is empty (= return None) or not (= continue to construct the subtree).

// Build hashmap value -> its index for inorder traversal.

// Return helper function which takes as the arguments the left and right boundaries for the current subtree in the inorder traversal. These boundaries are used only to check if the subtree is empty or not. Here is how it works helper(in_left = 0, in_right = n - 1):

// If in_left > in_right, the subtree is empty, return None.

// Pick the last element in postorder traversal as a root.

// Root value has index index in the inorder traversal, elements from in_left to index - 1 belong to the left subtree, and elements from index + 1 to in_right belong to the right subtree.

// Following the postorder logic, proceed recursively first to construct the right subtree helper(index + 1, in_right) and then to construct the left subtree helper(in_left, index - 1).

// Return root.