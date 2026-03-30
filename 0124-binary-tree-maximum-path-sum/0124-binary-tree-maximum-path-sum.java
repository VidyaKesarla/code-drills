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
    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        calculateGain(root);
        return maxSum;
    }

    public int calculateGain(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // Logic: Recursively get the max gain, but ignore if it's negative (hence the Math.max with 0)
        int leftGain = Math.max(calculateGain(node.left), 0);
        int rightGain = Math.max(calculateGain(node.right), 0);

        // Update the global record (the "Arch")
        int currentPathSum = node.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, currentPathSum);

        // Return the best "one-way" branch to the parent
        return node.val + Math.max(leftGain, rightGain);
    }
}
    /*
    Time$O(N)$We visit each node once.Space (Best/Avg)$O(\log N)$Height of a balanced tree.Space (Worst)$O(N)$Height of a completely skewed tree.
    *//*

    1. The Stack "Height" vs. "Total Nodes"In a recursive algorithm, space is not determined by how many nodes exist in total ($N$), but by how many function calls are active at the exact same time. Imagine the tree as a building:Time Complexity ($O(N)$): You have to visit every single room in the building.Space Complexity ($O(H)$): This is the maximum number of "keys" you hold in your hand as you go from the ground floor to the roof. You only hold the keys for the specific path you are currently climbing.2. The Math of a Balanced TreeIn a perfectly balanced binary tree, every time you go down one level, you are cutting the remaining number of nodes in half.Level 0 (Root): 1 nodeLevel 1: 2 nodesLevel 2: 4 nodes...Level $H$: $2^H$ nodesIf you have $N$ total nodes, the height $H$ is roughly $\log_2 N$.Example:If $N = 1,000,000$ (one million nodes):The height $H$ of a balanced tree is only about 20.Space used: The recursion stack only ever needs to hold 20 function calls at once.This is why $O(\log N)$ is considered extremely efficient—it grows very slowly even as the data gets massive.3. Visualizing the Call StackLet's look at a balanced tree with 7 nodes ($N=7, H=3$):Plaintext      1 (Level 1)
     / \
    2   3 (Level 2)
   / \ / \
  4  5 6  7 (Level 3)
The code starts at 1. (Stack: [1])It calls the left child 2. (Stack: [1, 2])It calls the left child 4. (Stack: [1, 2, 4])4 finishes and is popped off the stack. (Stack: [1, 2])It calls the right child 5. (Stack: [1, 2, 5])5 finishes and is popped off. (Stack: [1, 2])2 finishes and is popped off. (Stack: [1])Notice that even though there are 7 nodes, the "stack" never had more than 3 items in it. 3 is the height ($H$), which is $\log_2(7+1)$.4. Why the "Worst Case" is $O(N)$If the tree is skewed (like a linked list):Plaintext  1
   \
    2
     \
      3
       \
        4
To reach the bottom node (4), the computer must keep the functions for 1, 2, and 3 all "waiting" in the stack. In this case, $H = N$, so space becomes $O(N)$.
*/