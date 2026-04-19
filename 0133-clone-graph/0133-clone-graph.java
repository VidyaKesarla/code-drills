/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
/**
 * CLONE GRAPH - BFS APPROACH
 * * 1. WHY HASHMAP?
 * - To handle CYCLES: If Node 1 -> Node 2 -> Node 1, the map prevents 
 * infinite recursion by checking if Node 1 was already cloned.
 * - To maintain STRUCTURE: It ensures that if multiple nodes share the 
 * same neighbor, they all point to the SAME cloned instance, not 
 * different copies of the same value.
 * - O(1) Lookup: Allows us to retrieve a cloned node instantly.
 * * 2. BFS vs DFS:
 * - BFS is generally safer in Java for very deep/large graphs to avoid 
 * a StackOverflowError (uses Heap memory via Queue instead of Call Stack).
 * * 3. COMPLEXITY:
 * - Time: O(N + E) -> Each Node (N) is visited once, and each Edge (E) 
 * is traversed twice (once from each end).
 * - Space: O(N) -> To store the mapping of original nodes to clones 
 * and the BFS queue.
 */
/*
 * DRY RUN (Graph: 1-2-3-1, Start: Node 1)
 * -----------------------------------------
 * Initialization:
 * - Map: {1: C1}, Queue: [1] (C1 is Clone of 1)
 * * Step 1: Poll 1 from Queue.
 * - Neighbor 2: Not in Map. Create C2. Map: {1:C1, 2:C2}, Queue: [2]. 
 * Link C1.neighbors -> C2.
 * - Neighbor 3: Not in Map. Create C3. Map: {1:C1, 2:C2, 3:C3}, Queue: [2, 3]. 
 * Link C1.neighbors -> C3.
 * * Step 2: Poll 2 from Queue.
 * - Neighbor 1: ALREADY in Map. 
 * Link C2.neighbors -> C1. (Closes the loop 1-2)
 * - Neighbor 3: ALREADY in Map (added in step 1). 
 * Link C2.neighbors -> C3. (Creates edge 2-3)
 * * Step 3: Poll 3 from Queue.
 * - Neighbor 1: ALREADY in Map. 
 * Link C3.neighbors -> C1. (Closes the loop 3-1)
 * - Neighbor 2: ALREADY in Map. 
 * Link C3.neighbors -> C2. (Closes the loop 3-2)
 * * Final Result: Queue is empty. All clones are interconnected correctly. Return C1.
 */

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;

        // Map to store Original Node -> Cloned Node
        HashMap<Node, Node> map = new HashMap<>();
        
        // Queue for BFS traversal
        Queue<Node> queue = new LinkedList<>();

        // Initialize with the starting node
        map.put(node, new Node(node.val, new ArrayList<>()));
        queue.add(node);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();

            // Iterate through neighbors of the original node
            for (Node neighbor : curr.neighbors) {
                // If neighbor hasn't been cloned yet
                if (!map.containsKey(neighbor)) {
                    // Clone the neighbor and add to map
                    map.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                    // Add original neighbor to queue to process its neighbors later
                    queue.add(neighbor);
                }
                // Add the cloned neighbor to the current cloned node's neighbor list
                map.get(curr).neighbors.add(map.get(neighbor));
            }
        }

        return map.get(node);
    }
}