class Solution {
    public int orangesRotting(int[][] grid) {
        //edge case check if grid is empty or not
        if(grid == null || grid.length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;

        for(int r=0;r<rows;r++){
            for(int c=0;c<cols;c++){
                if(grid[r][c] == 2){
                    queue.offer(new int[]{r,c});
                } else if (grid[r][c] == 1) {
                    freshCount++;
                }
            }
        }

        if(freshCount == 0) return 0;

        int mins = 0;
        int [][] directions ={{1,0},{-1,0},{0,1},{0,-1}};

        while(!queue.isEmpty()){
            int size = queue.size();
            boolean rottedThisTurn = false;
            for(int i=0;i<size;i++){
                int[] curr = queue.poll();
                for(int [] d: directions){
                    int nr = curr[0] + d[0];
                    int nc = curr[1] + d[1];

                    if(nr >= 0 && nr < rows && nc >=0 && nc < cols && grid[nr][nc] == 1){
                        grid[nr][nc] = 2;
                        queue.offer(new int[]{nr, nc});
                        freshCount--;
                        rottedThisTurn = true;
                    }
                }
            }
            if (rottedThisTurn) mins++;
        }
        return freshCount == 0 ? mins : -1;
    }
}
// Time Complexity: $O(M \times N)$We traverse the entire grid once to find initial rotten oranges ($O(M \times N)$).In the worst case, every orange becomes rotten, meaning every cell is added to and removed from the queue exactly once.Space Complexity: $O(M \times N)$In the worst case (e.g., all oranges are rotten at the start), the queue will store all $M \times N$ cells.


// Dry Run Example

// Input: grid = [[2,1,1],[1,1,0],[0,1,1]]

// Initialization:

// rows = 3, cols = 3

// queue = [(0,0)] (The initial rotten orange)

// freshCount = 6 (Oranges at (0,1), (0,2), (1,0), (1,1), (2,1), (2,2))

// Minute 1:

// Pop (0,0), infect (0,1) and (1,0).

// queue = [(0,1), (1,0)], freshCount = 4, mins = 1.

// Minute 2:

// Pop (0,1), infect (0,2). Pop (1,0), infect (1,1).

// queue = [(0,2), (1,1)], freshCount = 2, mins = 2.

// Minute 3:

// Pop (0,2), nothing new. Pop (1,1), infect (2,1).

// queue = [(2,1)], freshCount = 1, mins = 3.

// Minute 4:

// Pop (2,1), infect (2,2).

// queue = [(2,2)], freshCount = 0, mins = 4.

// Minute 5:

// Pop (2,2), no more fresh neighbors. rottedThisTurn stays false.

// Loop ends. Return mins = 4.