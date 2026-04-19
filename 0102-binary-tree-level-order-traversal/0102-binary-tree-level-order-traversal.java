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

 * COMPLEXITY ANALYSIS:
 * Time Complexity (TC): O(n)
 * - Each node is added to the queue once and removed once.
 * - n is the total number of nodes in the tree.
 * * Space Complexity (SC): O(w)
 * - w is the maximum width of the tree (max nodes at any level).
 * - In a full binary tree, the last level has roughly n/2 nodes, so SC is O(n) in the worst case.
 *
 * DRY RUN (Tree: [3, 9, 20, null, null, 15, 7]):
 * 1. Initialize result = [], queue = [3]
 * 2. Level 1: currentLevelSize = 1
 * - Poll 3. levelArray = [3]. Add children (9, 20).
 * - result = [[3]], queue = [9, 20]
 * 3. Level 2: currentLevelSize = 2
 * - Poll 9. levelArray = [9]. No children.
 * - Poll 20. levelArray = [9, 20]. Add children (15, 7).
 * - result = [[3], [9, 20]], queue = [15, 7]
 * 4. Level 3: currentLevelSize = 2
 * - Poll 15. levelArray = [15]. 
 * - Poll 7. levelArray = [15, 7].
 * - result = [[3], [9, 20], [15, 7]], queue = [] -> STOP

 /*
 * APPROACH COMPARISON:
 * * 1. Brute Force (DFS per Level):
 * - Strategy: Find tree height (H), then loop from 1 to H, calling a 
 * recursive function to find nodes at that specific depth.
 * - Time Complexity: O(N^2) in the worst case (skewed tree) because 
 * upper nodes are re-visited multiple times for every lower level.
 * - Space Complexity: O(H) for the recursion stack.
 *
 * 2. Optimized BFS (Queue - Current Solution):
 * - Strategy: Use a Queue to process nodes level-by-level in a single pass.
 * - Time Complexity: O(N) because each node is visited exactly once.
 * - Space Complexity: O(W) where W is the max width of the tree.
 * - WHY IT'S BETTER: It avoids redundant work. Once a node is processed, 
 * we never touch it again.
 */

 
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        
        // CRITICAL: Early return to prevent processing nulls
        if (root == null) return result;

        // ArrayDeque is more memory-efficient than LinkedList
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            
            // Create the list ONCE per level
            List<Integer> levelArray = new ArrayList<>(currentLevelSize);

            for (int i = 0; i < currentLevelSize; i++) {
                TreeNode currentNode = queue.poll();
                
                // Add value directly
                levelArray.add(currentNode.val);

                // Add children only if they exist
                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
            }
            result.add(levelArray);
        }
        return result;
    }
}
