// ===================================================================
// SWIM IN RISING WATER
// ===================================================================
// INTUITION:
// Find path from (0,0) to (n-1,n-1) where the maximum elevation
// on the path is minimized. Use a min-heap (Dijkstra variant) to
// always explore the cheapest reachable cell first.
//
// KEY FORMULA: newTime = max(currentTime, neighbor's elevation)
// → water must cover BOTH current cell and neighbor to move
//
// -------------------------------------------------------------------
// DRY RUN: grid = [[0,2],[1,3]]
//
//   Grid:        Elevations:
//   (0,0)(0,1)     0  2
//   (1,0)(1,1)     1  3
//
// INIT:
//   Push [t=0, (0,0)] ← elevation of start = 0
//   visited[0][0] = true
//   Heap: [ [0,(0,0)] ]
//
// STEP 1 — Pop [t=0, (0,0)]:
//   Not destination. Explore neighbors:
//   → (0,1) elev=2: newTime = max(0,2) = 2 → push [2,(0,1)]
//   → (1,0) elev=1: newTime = max(0,1) = 1 → push [1,(1,0)]
//   Heap: [ [1,(1,0)], [2,(0,1)] ]   ← sorted by t
//
// STEP 2 — Pop [t=1, (1,0)]:
//   Not destination. Explore neighbors:
//   → (0,0) already visited ✗
//   → (1,1) elev=3: newTime = max(1,3) = 3 → push [3,(1,1)]
//   Heap: [ [2,(0,1)], [3,(1,1)] ]
//
// STEP 3 — Pop [t=2, (0,1)]:
//   Not destination. Explore neighbors:
//   → (0,0) already visited ✗
//   → (1,1) already visited ✗
//   Heap: [ [3,(1,1)] ]
//
// STEP 4 — Pop [t=3, (1,1)]:
//   row == n-1 && col == n-1 → DESTINATION reached!
//   return 3 ✅
//
// -------------------------------------------------------------------
// TIME COMPLEXITY: O(n² log n)
//   - Each of the n² cells is pushed/popped from heap at most once
//   - Each heap operation (push/pop) costs O(log n²) = O(log n)
//   - Total: n² × log n = O(n² log n)
//
// SPACE COMPLEXITY: O(n²)
//   - visited[][] array     → O(n²)
//   - minHeap               → O(n²) at most n² items at any time
// ===================================================================

class Solution {
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

         //we use minHeap to find out 
// //         Compare item a vs item b. Who should come out first?”
// // 	•	If result is negative → a comes first
// // 	•	If result is positive → b comes first
// // 	•	If result is zero → equal priority
// // So a[0] - b[0] means: compare by time value (index 0). Smaller time = higher priority.

        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        minHeap.offer(new int[]{grid[0][0], 0, 0});
        visited[0][0] = true;

        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int t   = curr[0];
            int row = curr[1];
            int col = curr[2];

            if (row == n-1 && col == n-1) return t;

            for (int[] d : dirs) {
                int nr = row + d[0];
                int nc = col + d[1];

                if (nr>=0 && nr<n && nc>=0 && nc<n && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    int newTime = Math.max(t, grid[nr][nc]);
                    minHeap.offer(new int[]{newTime, nr, nc});
                }
            }
        }
        return -1;
    }
}

