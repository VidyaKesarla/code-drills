import java.util.*;

class Solution {
    /**
     * APPROACH: MULTI-SOURCE BFS
     * * INTUITION:
     * Instead of starting from every '1' to find a '0' (Brute Force), we start 
     * from ALL '0's simultaneously. Think of it as lighting multiple fires 
     * at once; the time it takes for a fire to reach a cell is its shortest distance.
     * * DRY RUN EXAMPLE:
     * Input: 
     * [ 0  1 ]
     * [ 1  1 ]
     * * 1. INITIALIZATION:
     * - Queue: [(0,0)] (The only '0' in the grid)
     * - Matrix: Any '1' becomes '-1' (unvisited)
     * Queue: [(0,0)], Matrix: [[0, -1], [-1, -1]]
     * * 2. STEP 1 (Level 0):
     * - Pop (0,0). Neighbors: (0,1) and (1,0).
     * - Both are '-1', so set mat[0][1] = 0+1 and mat[1][0] = 0+1.
     * - Add (0,1) and (1,0) to Queue.
     * Queue: [(0,1), (1,0)], Matrix: [[0, 1], [1, -1]]
     * * 3. STEP 2 (Level 1):
     * - Pop (0,1). Neighbor (1,1) is '-1'.
     * - Set mat[1][1] = 1+1 = 2.
     * - Add (1,1) to Queue.
     * Queue: [(1,0), (1,1)], Matrix: [[0, 1], [1, 2]]
     * * 4. STEP 3 (Level 1 cont.):
     * - Pop (1,0). All neighbors already visited or out of bounds.
     * - Pop (1,1). All neighbors already visited.
     * Queue: Empty. Result Returned.
     * * COMPLEXITY:
     * - Time: O(M * N) - Every cell is visited exactly once.
     * - Space: O(M * N) - To maintain the queue.
     */
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        // Populate initial sources
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                } else {
                    mat[i][j] = -1; // Marker for unvisited
                }
            }
        }
        
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            
            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];
                
                // If neighbor is valid and unvisited (-1)
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && mat[nr][nc] == -1) {
                    mat[nr][nc] = mat[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        
        return mat;
    }
}
