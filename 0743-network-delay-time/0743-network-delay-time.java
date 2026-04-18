
/**
 * PROBLEM: Network Delay Time (LC 743)
 * Given a directed weighted graph of n nodes and a source k,
 * find the time it takes for a signal to reach ALL nodes.
 * If any node is unreachable, return -1.
 *
 * WHY DIJKSTRA?
 * ─────────────
 * We need shortest paths from a single source to ALL other nodes.
 * Dijkstra is the canonical algorithm for this on graphs with
 * non-negative edge weights (which this problem guarantees).
 *
 * WHY NOT BFS?
 *   BFS finds shortest paths only in unweighted graphs.
 *   Here edges have different weights, so BFS would give wrong answers.
 *
 * WHY NOT BELLMAN-FORD?
 *   Bellman-Ford works with negative weights, but is O(V·E) —
 *   much slower than Dijkstra's O((V+E) log V) with a heap.
 *   Since we have no negative weights, Dijkstra is strictly better.
 *
 * WHY NOT FLOYD-WARSHALL?
 *   Floyd-Warshall is O(V³) and computes all-pairs shortest paths.
 *   We only need single-source here, so that's wasteful.
 *
 * APPROACH SUMMARY:
 *   1. Build an adjacency list from the edge list.
 *   2. Run Dijkstra from source k using a min-heap.
 *   3. The answer is the maximum value in the dist[] array
 *      (because the signal reaches all nodes at that time).
 *      If any node remains unreachable (dist == ∞), return -1.
 *
 * TIME COMPLEXITY:  O((V + E) log V)
 *   - Each node is polled from the heap at most once: O(V log V)
 *   - Each edge relaxation may push to the heap: O(E log V)
 *
 * SPACE COMPLEXITY: O(V + E)
 *   - Adjacency list: O(V + E)
 *   - Heap: O(E) in the worst case (lazy deletion)
 *   - dist[]: O(V)
 */
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {

        // ── Step 1: Build adjacency list ─────────────────────────────────────
        // We use a HashMap<node, List<[neighbor, weight]>> so that
        // given any node, we can look up its outgoing edges in O(1).
        // Alternative: a 2D array, but HashMap handles sparse graphs better.
        Map<Integer, List<int[]>> graph = new HashMap<>();

        for (int[] time : times) {
            // time[0] = source node
            // time[1] = destination node
            // time[2] = travel time (edge weight)
            graph.computeIfAbsent(time[0], x -> new ArrayList<>())
                 .add(new int[]{time[1], time[2]});
            // computeIfAbsent: if time[0] has no entry yet, create an
            // empty list for it, then append this edge.
        }

        // ── Step 2: Initialize min-heap and dist[] array ─────────────────────
        // The heap stores int[]{distance, node}, sorted by distance (min first).
        // This is the core of Dijkstra: always process the globally closest
        // unvisited node next — the greedy choice that makes it correct.
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // dist[i] = shortest known distance from k to node i.
        // Initialize to MAX_VALUE (infinity) for all nodes except the source.
        int[] dist = new int[n + 1];  // 1-indexed; index 0 is unused
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;  // distance from source to itself is always 0

        // Seed the heap with the source node
        heap.offer(new int[]{0, k});

        // ── Step 3: Dijkstra's main loop ─────────────────────────────────────
        while (!heap.isEmpty()) {
            int[] curr = heap.poll();  // always pull the shortest-distance entry
            int d = curr[0];           // distance to reach this node
            int v = curr[1];           // the node itself

            // KEY OPTIMIZATION: lazy deletion
            // Because we never remove stale heap entries (when we find a
            // better path we just push a new entry), the heap may contain
            // outdated entries for a node we've already settled.
            // If d > dist[v], this entry is stale — skip it.
            // Without this check the algorithm still works but is slower.
            if (d > dist[v]) continue;

            // Relax all outgoing edges from v
            for (int[] edge : graph.getOrDefault(v, Collections.emptyList())) {
                int neighbor = edge[0];  // destination of this edge
                int weight   = edge[1];  // cost of this edge
                int newDist  = d + weight;

                // Only update if we found a strictly shorter path
                if (newDist < dist[neighbor]) {
                    dist[neighbor] = newDist;
                    // Push the improved distance — we don't remove the old
                    // entry; the 'lazy deletion' check above handles it.
                    heap.offer(new int[]{newDist, neighbor});
                }
            }
        }

        // ── Step 4: Compute answer ────────────────────────────────────────────
        // The signal must reach EVERY node.
        // The time for all nodes to receive the signal = the maximum
        // shortest-path distance from k across all nodes.
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                // This node was never reached → graph is not fully connected
                // from k → signal can never reach all nodes → return -1
                return -1;
            }
            ans = Math.max(ans, dist[i]);
        }
        return ans;
    }
}


/**
 * DRY RUN EXAMPLE
 * ───────────────
 * Input: times = [[1,2,1],[1,3,4],[2,3,2],[2,4,5],[3,4,1]], n=4, k=1
 *
 * Graph (adjacency list after build):
 *   1 → [(2,1), (3,4)]
 *   2 → [(3,2), (4,5)]
 *   3 → [(4,1)]
 *
 * dist[] = [-, 0, ∞, ∞, ∞]   (index 0 unused)
 * heap   = [(0,1)]
 *
 * ════════════════════════════════════════════════
 * ITERATION 1
 * ════════════════════════════════════════════════
 * Poll  → (d=0, v=1)
 * d=0 == dist[1]=0 → not stale, process
 *
 * Neighbors of 1:
 *   → neighbor=2, weight=1: newDist = 0+1 = 1 < dist[2]=∞  ✓ update dist[2]=1, push (1,2)
 *   → neighbor=3, weight=4: newDist = 0+4 = 4 < dist[3]=∞  ✓ update dist[3]=4, push (4,3)
 *
 * dist[] = [-, 0, 1, 4, ∞]
 * heap   = [(1,2), (4,3)]
 *
 * ════════════════════════════════════════════════
 * ITERATION 2
 * ════════════════════════════════════════════════
 * Poll  → (d=1, v=2)
 * d=1 == dist[2]=1 → not stale, process
 *
 * Neighbors of 2:
 *   → neighbor=3, weight=2: newDist = 1+2 = 3 < dist[3]=4  ✓ update dist[3]=3, push (3,3)
 *   → neighbor=4, weight=5: newDist = 1+5 = 6 < dist[4]=∞  ✓ update dist[4]=6, push (6,4)
 *
 * dist[] = [-, 0, 1, 3, 6]
 * heap   = [(3,3), (4,3), (6,4)]
 *
 * ════════════════════════════════════════════════
 * ITERATION 3
 * ════════════════════════════════════════════════
 * Poll  → (d=3, v=3)
 * d=3 == dist[3]=3 → not stale, process
 *
 * Neighbors of 3:
 *   → neighbor=4, weight=1: newDist = 3+1 = 4 < dist[4]=6  ✓ update dist[4]=4, push (4,4)
 *
 * dist[] = [-, 0, 1, 3, 4]
 * heap   = [(4,3), (4,4), (6,4)]
 *
 * ════════════════════════════════════════════════
 * ITERATION 4
 * ════════════════════════════════════════════════
 * Poll  → (d=4, v=3)
 * d=4 > dist[3]=3 → STALE, skip
 *
 * dist[] = [-, 0, 1, 3, 4]
 * heap   = [(4,4), (6,4)]
 *
 * ════════════════════════════════════════════════
 * ITERATION 5
 * ════════════════════════════════════════════════
 * Poll  → (d=4, v=4)
 * d=4 == dist[4]=4 → not stale, process
 * Node 4 has no outgoing edges → nothing pushed
 *
 * dist[] = [-, 0, 1, 3, 4]
 * heap   = [(6,4)]
 *
 * ════════════════════════════════════════════════
 * ITERATION 6
 * ════════════════════════════════════════════════
 * Poll  → (d=6, v=4)
 * d=6 > dist[4]=4 → STALE, skip
 *
 * heap   = []  → loop ends
 *
 * ════════════════════════════════════════════════
 * FINAL ANSWER
 * ════════════════════════════════════════════════
 * dist[] = [-, 0, 1, 3, 4]
 * No ∞ values → all nodes reachable
 * ans = max(0, 1, 3, 4) = 4
 * return 4
 */
