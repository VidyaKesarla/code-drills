class Solution {

    //approach : backtracking combined with in place state modification often called dfs with a visited state

    //to find all paths that hit every open square: you can't just move forward blindly: you have to explore a path: if it hits a dead end or reaches the target too early: you need to step backward and try a different turn.

    //algorithm tracks two crucial pieces: 
    //1. which squares have we stepped on?
    //2 step counter: how many empty squares do we still need to visit before we are allowed to finish
    

    int rows, cols;
    //grid : a mutable copy
    int [][] grid;
    //to record how many paths can be calculated
    int path_count;

    void backtrack(int row, int col, int remain){
        //base case for termination of backtracking
        if(this.grid[row][col] == 2 && remain == 1){
            //reach the destination
            this.path_count += 1;
            return;
        }

        //now i have to mark the square as visited
        int temp = grid[row][col];
        grid[row][col] = -4;
        //we just now have one sqaure to visit
        remain = remain - 1;

        //explore the 4 potential directions around
        int [] row_offsets = {0,0,1,-1};
        int [] col_offsets = {1,-1,0,0};

        for(int i =0;i<4;i++){
            int next_row = row + row_offsets[i];
            int next_col = col + col_offsets[i];


            if(next_row < 0 || next_row >= this.rows || next_col >= this.cols || next_col < 0){
                //invalid coordinate
                continue;
            }

            if(grid[next_row][next_col] < 0){
                //either an obstacle or a valid square
                continue;
            }

            backtrack(next_row, next_col, remain);
        }
        //unmark the square after the visit
        grid[row][col] = temp;

    }

    public int uniquePathsIII(int[][] grid) {
        //use a variable to calculate non obstacles
        int non_obstacles = 0;
        //use a start row
        int start_row = 0;
        //use a start col
        int start_col = 0;

        this.rows = grid.length;
        this.cols = grid[0].length;

        //initialise the conditions for backtracking:
        //initial and final state

        for(int row = 0; row < rows; row++){
            for(int col=0;col < cols;col++){
                int cell = grid[row][col];
                if (cell >= 0)
                    non_obstacles = non_obstacles + 1;
                if (cell == 1) {
                    start_row = row;
                    start_col = col;
                }
            }
        }

        this.path_count = 0;
        this.grid = grid;

        backtrack(start_row, start_col, non_obstacles);

        return this.path_count;
    }
}

//in place modification variant of backtracking it is an important technique that allows us to save some space in the algorithm

//in order to mark whatever cell i have as visited: often we use matrix or hashtable with boolean values: to keep track of state of each cell : whether it is visited or not

//but in this technique: we simply assign a specific value to the cell in the grid: rather than creating an additional matrix or hashtable 


//tc: 3^N = > for each step we have at most 3 directions to try 
//sc: O(N) = > recursion => call stack space
