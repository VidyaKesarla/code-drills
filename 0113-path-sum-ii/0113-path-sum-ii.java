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

    private void recurseTree(TreeNode node,int remainingSum, List<Integer> pathNodes, List<List<Integer>>pathList){
        if (node == null)
        return;
        // Add the current node to the path's list
        pathNodes.add(node.val);
        // Check if the current node is a leaf and also, if it
        // equals our remaining sum. If it does, we add the path to
        // our list of paths
        if(remainingSum == node.val && node.left == null && node.right == null){
            pathList.add(new ArrayList<> (pathNodes));
        } else {
            // Else, we will recurse on the left and the right children
            this.recurseTree(node.left, remainingSum - node.val, pathNodes, pathList);
            this.recurseTree(node.right, remainingSum - node.val, pathNodes, pathList);
        }
        // We need to pop the node once we are done processing ALL of it's
        // subtrees.
        pathNodes.remove(pathNodes.size() - 1);
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> pathList = new ArrayList<List<Integer>>();
        List<Integer> pathNodes = new ArrayList<Integer>();
        this.recurseTree(root, targetSum, pathNodes, pathList);
        return pathList;
    }
}

// Why Breadth First Search is bad for this problem?

// We did touch briefly on this in the intuition section. BFS would solve this problem perfectly. However, note that the problem statement actually asks us to return a list of all the paths that add up to a particular sum. Breadth first search moves one level at a time. That means, we would have to maintain the pathNodes lists for all the paths till a particular level/depth at the same time.

// Say we are at the level 10 in the tree and that level has e.g. 20 nodes. BFS uses a queue for processing the nodes. Along with 20 nodes in the queue, we would also need to maintain 20 different pathNodes lists since there is no backtracking here. That is too much of a space overhead.

// The good thing about depth first search is that it uses recursion for processing one branch at a time and once we are done processing the nodes of a particular branch, we pop them from the pathNodes list thus saving on space. At a time, this list would only contain all the nodes in a single branch of the tree and nothing more. Had the problem statement asked us the total number of paths that add up to a particular sum (root to leaf), then breadth first search would be an equally viable approach.

/*
 * 🚀 Why DFS Backtracking Outperforms BFS here: Trade-offs, Dry Run & Complexity
 *
 * When solving Path Sum II, the requirement to return EVERY complete root-to-leaf path 
 * creates a massive performance fork between Depth-First Search (DFS) and Breadth-First Search (BFS). 
 * While both can technically get an Accepted (AC) verdict, their underlying memory allocation strategy 
 * is night and day.
 *
 * ---------------------------------------------------------------------------------------------
 * 1. THE BRUTE-FORCE TRADE-OFF: MEMORY FRAGMENTATION
 * ---------------------------------------------------------------------------------------------
 * * THE BFS PROBLEM: BFS expands level-by-level (horizontally). Because it moves across branches 
 * concurrently, it cannot use a single shared list to backtrack. To know the exact path leading 
 * to a node at level 15, EVERY single node currently sitting in the BFS queue must store its own 
 * unique, independent history list. If a level has hundreds of nodes, you have hundreds of 
 * deep-copied arrays active in memory simultaneously.
 *
 * * THE DFS ADVANTAGE: DFS dives deep down a single branch (vertically). This allows us to use a 
 * SINGLE, SHARED global list (`pathNodes`). We append a node's value, explore its deeper children, 
 * and cleanly pop it off (`backtrack`) using O(1) operations when shifting branches. 
 *
 * 📌 Core Trade-off: BFS forces massive heap-memory allocation by storing thousands of partial 
 * path clones concurrently. DFS uses exactly ONE dynamic list that scales predictably with the tree's height.
 *
 * ---------------------------------------------------------------------------------------------
 * 2. BACKTRACKING DRY RUN (Visualizing the Shared List)
 * ---------------------------------------------------------------------------------------------
 * Let's trace how the shared `pathNodes` array dynamically shifts without needing clones, 
 * using this sample tree where targetSum = 7:
 *
 * 5
 * / \
 * 2   4
 * /
 * 1
 *
 * * Step 1: Visit 5 -> pathNodes = [5]. Not a leaf. Recurse Left.
 * * Step 2: Visit 2 -> pathNodes = [5, 2]. Not a leaf. Recurse Left.
 * * Step 3: Visit 1 -> pathNodes = [5, 2, 1]. Leaf found! Remaining sum matches (7 - 5 - 2 = 1).
 * Action: Deep copy current state [5, 2, 1] to our final pathList.
 * * Step 4: Backtrack from 1 -> pathNodes.remove(pathNodes.size() - 1) -> pathNodes = [5, 2].
 * * Step 5: Backtrack from 2 -> pathNodes.remove(pathNodes.size() - 1) -> pathNodes = [5].
 * * Step 6: Visit 4 -> pathNodes = [5, 4]. Leaf found, but sum doesn't match.
 * * Step 7: Backtrack from 4 -> pathNodes.remove(pathNodes.size() - 1) -> pathNodes = [5].
 *
 * ---------------------------------------------------------------------------------------------
 * 3. COMPLEXITY ANALYSIS (Let N = total nodes in the tree)
 * ---------------------------------------------------------------------------------------------
 * ⏰ TIME COMPLEXITY: O(N^2) (Worst Case)
 * * Tree Traversal: We visit every node exactly once, taking O(N) time.
 * * Path Copying Overhead: Whenever we hit a valid leaf node, we run `new ArrayList<>(pathNodes)`. 
 * In a Skewed Tree (a single long spine where alternating nodes branch off to individual leaves), 
 * the depth approaches O(N). Summing up the deep-copy actions over linear paths yields: 
 * 1 + 2 + 3 + ... + N/2 = O(N^2).
 * * Note: In a balanced/complete tree, maximum depth is O(log N), keeping time complexity to O(N log N).
 *
 * 💾 SPACE COMPLEXITY: O(N) (Worst Case)
 * * Recursion Stack: In a completely skewed tree, the call stack will hold O(N) frames. 
 * * Path Tracking: The single `pathNodes` list stores at most O(N) elements at any given millisecond 
 * due to immediate backtracking. 
 * * Note: Memory used by the final output list is excluded from auxiliary space metrics.
 *
 * ---------------------------------------------------------------------------------------------
 * 4. DFS VS. BFS SUMMARY MATRIX
 * ---------------------------------------------------------------------------------------------
 * | Evaluation Metric | Depth-First Search (DFS)       | Breadth-First Search (BFS)            |
 * |-------------------|--------------------------------|---------------------------------------|
 * | Worst-Case Time   | O(N^2) (Leaf path duplication) | O(N^2) (Queue element path copying)   |
 * | Auxiliary Space   | O(N) (Stack + 1 shared array)  | O(N^2) (O(N) nodes holding sequences) |
 * | Memory Pressure   | Low heap footprint (Reused)    | High heap overhead (Constant clones)  |
 * | When to use?      | Finding complete root-to-leaf  | Finding shortest paths                |
 */