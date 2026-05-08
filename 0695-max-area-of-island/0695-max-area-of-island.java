/*
 * APPROACH: Depth-First Search (DFS) with Visited Tracking
 * 
 * 💡 INTUITION:
 * This solution treats the 2D grid as a graph where each '1' (land) is a node. 
 * An island is a "Connected Component." By using DFS, we exhaustively 
 * traverse every connected land cell to calculate the total area of each island.
 * 
 * 🛠 STRATEGY:
 * 1. Initialize a 'seen' boolean matrix to keep track of visited coordinates.
 * 2. Iterate through every cell (r, c) in the grid.
 * 3. For each cell, call the 'area' function:
 *    - Base Case: If out of bounds, already seen, or water (0), return area 0.
 *    - Recursive Step: Mark as 'seen', then return 1 + sum of 4-directional neighbors.
 * 4. Use Math.max to update the global 'ans' with the largest area found.
 * 
 * 🚩 EDGE & CORNER CASES:
 * - All Water: 'area' always returns 0, 'ans' remains 0. Correct.
 * - Single Island: One DFS call explores the whole grid; returns M * N.
 * - Diagonal Land: The if-condition only checks 4 directions, correctly 
 *   treating diagonals as separate islands.
 * - Grid Boundaries: Handled by the first if-statement in the 'area' method.
 * 
 * ⚖️ TRADEOFFS:
 * - Time Complexity: O(M * N). Every cell is visited once by the nested loops 
 *   and once by the DFS logic.
 * - Space Complexity: O(M * N). This is due to the 'seen' matrix AND the 
 *   recursion stack (which, in the worst case of all land, reaches M * N depth).
 * - Immutability: Unlike the "sink" method, this preserves the original 
 *   'grid' by using extra space for the 'seen' array.
 * 
 * 🧠 DRY RUN:
 * Input: [[1, 1, 0], [0, 0, 1]]
 * 1. Loop finds grid[0][0] == 1. area(0,0) starts.
 * 2. seen[0][0]=true -> checks neighbors. area(0,1) returns 1. 
 * 3. Total for first island = 2. ans = 2.
 * 4. Loop reaches (0,1). seen[0][1] is true, returns 0 immediately.
 * 5. Loop reaches (1,2). area(1,2) starts. Returns 1.
 * 6. ans = Math.max(2, 1) -> Final Result: 2.
 */
class Solution {
    int[][] grid;
    boolean[][] seen; 
    public int maxAreaOfIsland(int[][] grid) {
        this.grid = grid;
        seen = new boolean[grid.length][grid[0].length];
        int ans = 0;

        for(int r=0;r<grid.length;r++){
            for(int c=0;c<grid[0].length;c++){
                ans = Math.max(ans, area(r,c));
            }
        }
        return ans;
    }

    public int area(int r, int c){
        if(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || seen[r][c] || grid[r][c] == 0){
            return 0;
        }
        seen[r][c] = true;

        return 1 + area(r+1,c) + area(r-1,c) + area(r,c+1) + area(r,c-1);
    }
}