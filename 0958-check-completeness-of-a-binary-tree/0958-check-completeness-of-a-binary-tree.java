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

/*
--------------------------------------------------------------------------------
APPROACH ANALYSIS: "BRUTE FORCE" INDEX SIMULATION VS. STANDARD BFS
--------------------------------------------------------------------------------
The approach implemented above uses the 0-based array representation property of 
binary trees ($2 \cdot index + 1$ for left child, $2 \cdot index + 2$ for right child) 
coupled with a pre-calculation of total nodes ($n$). 

While this approach is conceptually elegant and runs in 0ms (beats 100%), it exhibits 
distinct trade-offs compared to the standard level-order traversal (BFS):

1. Integer Overflow Vulnerability (The biggest trade-off):
   - In a skewed tree (e.g., a straight line of 32 left or right children), the index 
     grows exponentially ($2^i$). 
   - Even though $n$ might only be 32, the `index` variable will quickly exceed 
     Integer.MAX_VALUE ($2^{31}-1$), leading to arithmetic overflow and incorrect 
     negative index evaluations.
   - The standard BFS queue-based approach avoids indexing altogether and is completely 
     immune to overflow, making it production-safe for highly deep or skewed trees.

2. Multiple Passes:
   - This approach requires two full traversals: one to count the nodes via `countNodes` 
     and another to validate the indices via `dfs`. 
   - A traditional BFS checks completeness in a single pass, stopping the moment a 
     `null` node is pulled out of the queue before non-null nodes.

--------------------------------------------------------------------------------
DRY RUN
--------------------------------------------------------------------------------
Let's dry run this code with a valid Complete Binary Tree:
       1
      / \
     2   3
    /
   4

Step 1: countNodes(root)
- Node 1: 1 + count(2) + count(3)
- Node 2: 1 + count(4) + count(null) -> 1 + 1 + 0 = 2
- Node 4: 1 + count(null) + count(null) -> 1 + 0 + 0 = 1
- Node 3: 1 + count(null) + count(null) -> 1 + 0 + 0 = 1
Total Nodes (n) = 1 + 2 + 1 = 4.

Step 2: dfs(root, index=0, n=4)
- dfs(Node 1, index=0):
  - Checks: 0 >= 4 (False). Valid.
  - Moves to Left: dfs(Node 2, index = 2*0 + 1 = 1, n=4)
  - Moves to Right: dfs(Node 3, index = 2*0 + 2 = 2, n=4)

- dfs(Node 2, index=1):
  - Checks: 1 >= 4 (False). Valid.
  - Moves to Left: dfs(Node 4, index = 2*1 + 1 = 3, n=4)
  - Moves to Right: dfs(null, index = 2*1 + 2 = 4, n=4) -> Returns True immediately.

- dfs(Node 4, index=3):
  - Checks: 3 >= 4 (False). Valid.
  - Both children are null -> Returns True && True = True.

- dfs(Node 3, index=2):
  - Checks: 2 >= 4 (False). Valid.
  - Both children are null -> Returns True && True = True.

All recursive calls resolve to 'True'. The tree is complete!

--------------------------------------------------------------------------------
COMPLEXITY ANALYSIS
--------------------------------------------------------------------------------
Time Complexity: $O(N)$
- `countNodes(root)` visits every single node exactly once $\rightarrow O(N)$.
- `dfs(root, ...)` visits every node at most once because it returns false early 
  if an out-of-bounds index is detected $\rightarrow O(N)$.
- Total Time Complexity = $O(N) + O(N) = O(N)$, where $N$ is the number of nodes.

Space Complexity: $O(H)$
- No extra heap memory data structures (like Queues or Lists) are used.
- However, space is consumed by the implicit system call stack during recursion.
- In the worst-case scenario (a highly skewed tree), the recursion stack can go 
  as deep as the height of the tree ($H$).
- For a balanced tree, $H = \log N$. For a skewed tree, $H = N$.
- Total Space Complexity = $O(H)$ framework stack overhead.
*/

/*
--------------------------------------------------------------------------------
DFS APPROACH BREAKDOWN
--------------------------------------------------------------------------------
This solution utilizes a Depth-First Search (DFS) traversal to validate the tree's 
completeness by leveraging the 0-indexed array property of binary trees.

1. How it works:
   - First, a DFS helper function (`countNodes`) counts the total number of nodes ($n$).
   - Second, a main DFS function (`dfs`) traverses the tree starting from the root at index 0.
   - For any given node at `index`, its left child is assigned `2 * index + 1` and its 
     right child is assigned `2 * index + 2`.
   - If the tree is complete, the assigned index of every single valid node must be 
     strictly less than the total count of nodes ($index < n$). If we encounter a node 
     where `index >= n`, it means a gap exists earlier in the level-order sequence, 
     making the tree incomplete.

2. Trade-offs of this DFS simulation:
   - Risk of Integer Overflow: Because indices grow exponentially ($2^i$), a highly 
     skewed or deep tree (e.g., a straight line of 32 nodes) will cause the `index` variable 
     to exceed `Integer.MAX_VALUE` ($2^{31} - 1$). This causes arithmetic overflow into negative 
     numbers, breaking the logic. Standard BFS avoids indices completely and is immune to this.
   - Two-Pass Requirement: This approach requires two full traversals (one to count nodes, 
     one to validate positions), whereas a standard level-order BFS can determine completeness 
     in a single pass.
*/