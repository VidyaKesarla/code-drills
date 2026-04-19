

class Solution {
    public int trapRainWater(int[][] heightMap) {
        // 1. BASE CASE: If the grid is too small (less than 3x3), 
        // there are no "inner" cells to trap water. Everything is a boundary.
        if (heightMap == null || heightMap.length <= 2 || heightMap[0].length <= 2) {
            return 0; 
        }

        int m = heightMap.length;    // Rows
        int n = heightMap[0].length; // Columns
        
        // 2. VISITED TRACKER: We need this so we don't process 
        // the same cell twice or get stuck in a loop.
        boolean[][] visited = new boolean[m][n];
        
        // 3. THE MIN-HEAP (The Engine): 
        // It stores [row, col, height]. 
        // The lambda (a, b) -> a[2] - b[2] ensures we always pull the SHORTEST wall first.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

        // 4. INITIALIZE THE BOUNDARY: 
        // We add all the edge cells into the heap. These are our initial "dams."
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    pq.offer(new int[]{i, j, heightMap[i][j]});
                    visited[i][j] = true; // Mark edges as visited
                }
            }
        }

        int totalWater = 0;
        // 5. DIRECTIONS: Helper array to look Up, Down, Left, Right easily.
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // 6. THE CORE LOOP: Process the boundary from the lowest point inward.
        while (!pq.isEmpty()) {
            // Poll the cell with the lowest height (our "leakage" point).
            int[] cell = pq.poll();
            int r = cell[0];
            int c = cell[1];
            int h = cell[2]; // This 'h' is the "Effective Wall Height" (Ground + Water)

            // Look at all 4 neighbors of this low point.
            for (int[] dir : dirs) {
                int nr = r + dir[0]; // Neighbor Row
                int nc = c + dir[1]; // Neighbor Column

                // Check if the neighbor is inside the grid and hasn't been visited.
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    
                    // 7. TRAP WATER LOGIC:
                    // If the neighbor's actual ground height (heightMap[nr][nc]) 
                    // is LOWER than our current wall height (h), it traps water!
                    totalWater += Math.max(0, h - heightMap[nr][nc]);
                    
                    // 8. UPDATE THE BOUNDARY:
                    // We push the neighbor into the heap. 
                    // Its NEW height is the MAX of its ground height or the water level.
                    // This creates the "new" wall for the next step.
                    pq.offer(new int[]{nr, nc, Math.max(h, heightMap[nr][nc])});
                }
            }
        }

        return totalWater; // Return the sum of all trapped pockets.
    }
}

/*

/**
 * Problem: LeetCode 407 - Trapping Rain Water II
 * Strategy: Boundary-First Search (Greedy BFS) using a Min-Heap
 * * Logic:
 * 1. Initialize: All boundary cells (edges) are potential leak points. 
 * Add them to a Min-Heap [row, col, height] and mark as visited.
 * 2. Process: Always poll the LOWEST cell from the heap (the "weakest link").
 * 3. Explore: Check its 4 neighbors. If a neighbor is shorter than the 
 * current boundary height, it traps water!
 * 4. Update: Water = BoundaryHeight - NeighborHeight. Push neighbor into 
 * the heap with its NEW effective height: Max(NeighborHeight, BoundaryHeight).
 * * Complexity:
 * - Time: O(M * N * log(M * N)) -> Each cell is added/removed from the heap once.
 * - Space: O(M * N) -> For the visited matrix and the PriorityQueue.
 */


 /**
 * COMPLEXITY ANALYSIS & COMPARISON:
 * * 1. Brute Force / Naive (Pathfinding per cell)
 * - TC: O((M*N)^2) -> Too slow, checks all paths for every cell.
 * - SC: O(M*N)
 *
 * 2. 1D Scan (Independent Row/Column Scanning)
 * - TC: O(M*N) -> Fast, but mathematically INCORRECT (ignores diagonal leaks).
 * - SC: O(M*N)
 *
 * 3. Min-Heap + BFS (This Solution - Optimal)
 * - TC: O(M*N log(M*N)) 
 * Each cell is added/removed from the PriorityQueue exactly once. 
 * Log(M*N) is the cost of maintaining the heap's min-property.
 * - SC: O(M*N) 
 * We maintain a 'visited' matrix and a PriorityQueue which, in the 
 * worst case (rugged terrain), stores all cells.
 *
 * WHY THIS WORKS:
 * Water level is determined by the "lowest boundary" (the bottleneck). 
 * By using a Min-Heap, we always process the path of least resistance 
 * first, ensuring we never overestimate the trapped water.
 */


/**
 * DRY RUN (Step-by-Step with Input):
 * Input: [[1,4,3,1,3,2], [3,2,1,3,2,4], [2,3,3,2,3,1]]
 * * 1. INITIALIZE BOUNDARY:
 * - All outer cells added to Min-Heap (PQ).
 * - Smallest in PQ: [0,0, h=1], [0,3, h=1], [2,5, h=1].
 * * 2. FIRST ITERATION (Poll h=1):
 * - Poll [0,3, h=1]. Neighbor is [1,3] (h=3).
 * - h(3) > h(1) -> No water trapped.
 * - Push [1,3, h=3] to PQ. New boundary wall created at height 3.
 * * 3. LATER ITERATION (Trapping Water):
 * - Eventually, PQ polls [1,4, h=2] (pushed earlier from boundary [2,5] or [0,5]).
 * - Neighbor is [1,2] (h=1).
 * - h(1) < h(2) -> WATER TRAPPED! 
 * - totalWater += (2 - 1) = 1.
 * - Push [1,2, h=2] to PQ (Effective height = Ground 1 + Water 1).
 * * 4. CONCLUSION:
 * - Because the outer boundary has multiple '1's, the "sea level" remains low.
 * - Most internal cells won't trap much water because it "leaks" out of the height 1 holes in the outer wall.
 */

 /*

 /**
 * CORRECTED DETAILED TRACE LOG (Input: 3x6 Matrix):
 * [1, 4, 3, 1, 3, 2]  <- Row 0 (Boundary)
 * [3, 2, 1, 3, 2, 4]  <- Row 1 (Internal: [1,1]=2, [1,2]=1, [1,3]=3, [1,4]=2)
 * [2, 3, 3, 2, 3, 1]  <- Row 2 (Boundary)
 *
 * 1. INITIAL PQ (Sorted by Height):
 * - All 14 boundary cells pushed.
 * - Current Top: [0,0, h=1], [0,3, h=1], [2,5, h=1]
 *
 * 2. ITERATION 1 (Poll [0,3, h=1]):
 * - Neighbor [1,3] is unvisited (Ground height 3).
 * - Neighbor(3) > Current(1) -> No water trapped.
 * - Action: Push [1,3, h=3] to PQ (This blocks the '1' leak for cells further in).
 *
 * 3. ITERATION 2 (Poll [0,5, h=2]):
 * - Neighbor [1,4] is unvisited (Ground height 2).
 * - Neighbor(2) == Current(2) -> No water trapped.
 * - Action: Push [1,4, h=2] to PQ.
 *
 * 4. ITERATION 3 (Processing [1,4, h=2]):
 * - Neighbor [1,3] is already visited.
 * - Neighbor [0,4], [2,4], [1,5] are all boundaries/visited.
 * - Result: No water trapped yet.
 *
 * 5. ITERATION 4 (Trapping Water via Row 1, Col 0 boundary):
 * - Eventually, PQ polls boundary [1,0, h=3] (or [2,1, h=3]).
 * - Neighbor [1,1] (Ground 2) is unvisited.
 * - h(2) < Current(3) -> WATER TRAPPED: 3 - 2 = 1 unit.
 * - Action: totalWater += 1. Push [1,1, h=3] to PQ (Water level raised to 3).
 *
 * 6. ITERATION 5 (Spreading to neighbors):
 * - Poll [1,1, h=3]. Neighbor [1,2] (Ground 1) is unvisited.
 * - h(1) < Current(3) -> WATER TRAPPED: 3 - 1 = 2 units.
 * - Action: totalWater += 2. Push [1,2, h=3] to PQ.
 *
 * FINAL RESULT: totalWater = 4 (1 from [1,1], 2 from [1,2], 1 from [1,4] via [0,5] path).
 * WHY: The internal cells are "hemmed in" by walls of height 3 or 4. 
 * Even though Row 0 has '1's, those leaks are blocked by the height 3 
 * internal walls or higher boundaries.
 */