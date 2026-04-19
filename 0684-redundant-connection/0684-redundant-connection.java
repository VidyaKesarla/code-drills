class Solution {
    // parent[i] = captain/leader of node i's group
    // find(i) gives the ROOT captain (may not be direct parent)
    private int[] parent;

    // size[i] = how many nodes are in the group led by root i
    // Only accurate for root nodes
    private int[] size;

    // ─────────────────────────────────────────────
    // MAIN METHOD
    // ─────────────────────────────────────────────
    // APPROACH: DSU (Disjoint Set Union)
    // IDEA: Every node starts in its own group.
    //       For each edge [u,v], try to merge u and v's groups.
    //       If they're ALREADY in the same group → cycle found → return that edge.
    //
    // WHY BETTER THAN BRUTE FORCE DFS:
    //   Brute Force: For each edge, run full DFS to check connectivity → O(N²)
    //   DSU:         Connectivity check is ~O(1) using parent[] lookup → O(N·α(N))
    //
    // TIME:  O(N·α(N)) ≈ O(N)  [α = inverse Ackermann, always < 5 in practice]
    // SPACE: O(N)               [parent[] + size[] arrays + recursion stack]
    // ─────────────────────────────────────────────
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        // n edges, n nodes (problem guarantee)
        // allocate n+1 because nodes are 1-indexed (1 to N)
        // index 0 exists but is never used
        parent = new int[n + 1];
        size   = new int[n + 1];

        // Everyone starts alone: their own captain, group of 1
        // parent[1]=1, parent[2]=2 ... means "I lead myself"
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
            size[i]   = 1;
        }

        // Process edges left to right
        // First edge where union() returns false = redundant edge
        // Processing left→right guarantees we return the LAST redundant edge
        // (earlier edges can't be removed without disconnecting the graph)
        for (int[] edge : edges) {
            int u = edge[0]; // first city of this road
            int v = edge[1]; // second city of this road

            // union() returns false if u and v are already in same group
            // = already connected = this edge creates a cycle
            if (!union(u, v)) {
                return edge; // redundant edge found
            }
            // union() returned true = safe merge happened, continue
        }

        // Never reached with valid input
        // Java requires a return statement here
        return new int[]{};
    }

    // ─────────────────────────────────────────────
    // FIND METHOD
    // ─────────────────────────────────────────────
    // QUESTION: "Who is the ultimate captain of node x's group?"
    //
    // PATH COMPRESSION TRICK:
    //   Without it: find() walks a long chain  → O(N) per call
    //   With it:    after finding root, point everyone 
    //               on the path DIRECTLY to root  → O(α(N)) per call
    //
    // EXAMPLE (without compression):  5→4→3→2→1  (slow chain)
    // EXAMPLE (with compression):     5→1, 4→1, 3→1, 2→1  (flat, instant)
    //
    // TIME:  O(α(N)) amortized with path compression
    // SPACE: O(N) recursion stack in worst case
    // ─────────────────────────────────────────────
    public int find(int x) {
        if (parent[x] != x) {
            // x is NOT its own captain → someone above it is
            // Recursively find root AND compress path:
            // set parent[x] to point DIRECTLY to root (skip intermediaries)
            parent[x] = find(parent[x]);
        }
        // parent[x] is now the root (either always was, or we just set it)
        // CRITICAL: return parent[x], NOT x
        // returning x would return the node itself, not the root → wrong answers
        return parent[x];
    }

    // ─────────────────────────────────────────────
    // UNION METHOD
    // ─────────────────────────────────────────────
    // QUESTION: "Can we safely merge the groups of x and y?"
    //
    // UNION BY SIZE TRICK:
    //   Always attach SMALLER group under LARGER group's root
    //   This keeps the tree SHALLOW → find() stays fast
    //   Without this: tree can become a long chain → O(N) per find()
    //   With this:    tree depth stays ≤ log(N)    → O(log N) per find()
    //   Combined with path compression              → O(α(N)) per find()
    //
    // RETURNS: false = same group = cycle detected
    //          true  = different groups = safe merge done
    //
    // TIME:  O(α(N)) — just two find() calls + O(1) merge
    // SPACE: O(1)    — no extra space needed
    // ─────────────────────────────────────────────
    public boolean union(int x, int y) {
        int rootX = find(x); // ultimate captain of x's group
        int rootY = find(y); // ultimate captain of y's group

        // Same captain = same group = already connected
        // Adding edge x-y would create a loop → report cycle
        if (rootX == rootY) return false;

        // Different groups → safe to merge
        // IMPORTANT: compare SIZE not node numbers
        // size[rootX] vs size[rootY] tells us which group is bigger
        // rootX vs rootY are just arbitrary node ID numbers — meaningless here
        if (size[rootX] >= size[rootY]) {
            // X's group is bigger (or equal) → Y's group joins X
            parent[rootY] = rootX;       // Y's captain reports to X's captain
            size[rootX]  += size[rootY]; // X's group grows by Y's size
        } else {
            // Y's group is bigger → X's group joins Y
            parent[rootX] = rootY;       // X's captain reports to Y's captain
            size[rootY]  += size[rootX]; // Y's group grows by X's size
        }

        return true; // merge successful, no cycle
    }
}

// ═══════════════════════════════════════════════════════════
// DRY RUN — edges = [[1,2], [1,3], [2,3]], n = 3
// ═══════════════════════════════════════════════════════════
//
// INITIAL STATE:
//   parent = [0, 1, 2, 3]   (index 0 unused)
//   size   = [1, 1, 1, 1]
//   Groups: {1}  {2}  {3}
//
// ───────────────────────────────
// EDGE [1,2]:
//   find(1) → parent[1]=1 → return 1         rootX = 1
//   find(2) → parent[2]=2 → return 2         rootY = 2
//   rootX(1) == rootY(2)? NO → merge
//   size[1]=1 >= size[2]=1 → YES (equal)
//     parent[2] = 1   (node 2 joins node 1's group)
//     size[1]   = 2
//   parent = [0, 1, 1, 3]
//   size   = [1, 2, 1, 1]
//   Groups: {1,2}  {3}
//   Tree:   1
//           │
//           2
//
// ───────────────────────────────
// EDGE [1,3]:
//   find(1) → parent[1]=1 → return 1         rootX = 1
//   find(3) → parent[3]=3 → return 3         rootY = 3
//   rootX(1) == rootY(3)? NO → merge
//   size[1]=2 >= size[3]=1 → YES
//     parent[3] = 1   (node 3 joins node 1's group)
//     size[1]   = 3
//   parent = [0, 1, 1, 1]
//   size   = [1, 3, 1, 1]
//   Groups: {1,2,3}
//   Tree:   1
//          / \
//         2   3
//
// ───────────────────────────────
// EDGE [2,3]:
//   find(2):
//     parent[2]=1, 1≠2 → parent[2] = find(1)
//       find(1): parent[1]=1 → return parent[1]=1
//     parent[2] = 1 (already was, no change)
//     return parent[2] = 1                   rootX = 1
//   find(3):
//     parent[3]=1, 1≠3 → parent[3] = find(1) = 1
//     return parent[3] = 1                   rootY = 1
//   rootX(1) == rootY(1)? YES → CYCLE!
//   return false
//   !union = true → return edge [2,3] ✅
//
// ═══════════════════════════════════════════════════════════
// BRUTE FORCE vs DSU COMPARISON
// ═══════════════════════════════════════════════════════════
//
// ┌─────────────────┬──────────────────────┬────────────────────────┐
// │                 │   BRUTE FORCE DFS    │         DSU            │
// ├─────────────────┼──────────────────────┼────────────────────────┤
// │ Core question   │ "Is there a PATH     │ "Are u and v in the    │
// │                 │  between u and v?"   │  SAME GROUP?"          │
// ├─────────────────┼──────────────────────┼────────────────────────┤
// │ How             │ Walk graph via DFS   │ Compare root leaders   │
// ├─────────────────┼──────────────────────┼────────────────────────┤
// │ Work per edge   │ O(N) full DFS        │ O(α(N)) ≈ O(1)        │
// ├─────────────────┼──────────────────────┼────────────────────────┤
// │ Total time      │ O(N²)                │ O(N·α(N)) ≈ O(N)      │
// ├─────────────────┼──────────────────────┼────────────────────────┤
// │ Space           │ O(N)                 │ O(N)                   │
// ├─────────────────┼──────────────────────┼────────────────────────┤
// │ Memory of past  │ NONE — re-explores   │ YES — parent[] encodes │
// │ decisions       │ everything each time │ all past merges        │
// ├─────────────────┼──────────────────────┼────────────────────────┤
// │ N = 100,000     │ 10,000,000,000 ops❌ │ ~400,000 ops ✅        │
// ├─────────────────┼──────────────────────┼────────────────────────┤
// │ Google signal   │ "I know naive"       │ "This is my answer"    │
// └─────────────────┴──────────────────────┴────────────────────────┘
//
// WHY DSU WINS:
//   Brute force re-explores the entire graph for every edge.
//   After 50 edges, checking edge 51 walks all 50 edges again from scratch.
//
//   DSU remembers everything in parent[].
//   After 50 merges, checking edge 51 is two find() calls → done.
//   All past connectivity is already encoded.
//
// ═══════════════════════════════════════════════════════════
// TIME & SPACE COMPLEXITY
// ═══════════════════════════════════════════════════════════
//
// DSU:
//   Time:  O(N·α(N))
//          N edges × α(N) per union/find call
//          α(N) = inverse Ackermann, always < 5 for any real input
//          Effectively O(N)
//
//   Space: O(N)
//          parent[] → N+1 integers
//          size[]   → N+1 integers
//          find() recursion stack → O(N) worst case, 
//                                   O(α(N)) with compression
//
// BRUTE FORCE:
//   Time:  O(N²)
//          N edges × O(N) DFS per edge
//
//   Space: O(N)
//          adjacency list → N edges
//          visited[]      → N+1 booleans
//          DFS call stack → up to O(N) deep