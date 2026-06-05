/**
 * 💡 Intuition & Rationale
 * Instead of tracing every single path forward (which quickly explodes exponentially), 
 * this approach tackles the problem **backwards** using Dynamic Programming.
 * * The core idea is to find the probability of the knight landing on a cell (i, j) at a specific step `moves`. 
 * To arrive at cell (i, j) on the current move, the knight must have been on a valid neighboring cell 
 * (prevI, prevJ) on the previous move (moves - 1). Since a knight chooses one of its 8 moves uniformly at random, 
 * the probability of transitioning from any valid previous cell to the current cell is exactly 1/8 (or 1.0 / 8.0). 
 * * By building this up step-by-step from moves = 1 to k, we can safely calculate the exact probability 
 * distribution across the entire board without repeating calculations.
 * * -----------------------------------------------------------------------------------------------------
 * * 🔄 Brute Force vs. Dynamic Programming Trade-off
 * * - The Brute Force Approach (DFS / Backtracking):
 * From the starting cell, we could recursively simulate all 8 moves for k steps. If a move goes out of bounds, 
 * We stop tracking that path. The number of paths grows at an exponential rate of 8^k. For k = 100, 8^100 
 * Is astronomically large, leading to a Time Limit Exceeded (TLE) error. It also recomputes the probability 
 * Of reaching the same board states repeatedly (overlapping subproblems).
 * * - The DP Trade-off:
 * By storing the states in a 3D grid, we trade space to eliminate redundant calculations. Instead of evaluating 
 * Branches exponentially, we iterate sequentially through the number of moves and the board dimensions, 
 * Keeping the execution highly deterministic and fast.
 * * -----------------------------------------------------------------------------------------------------
 * * ⏱️ Complexity Analysis
 * * - Time Complexity (TC): O(k * n^2)
 * We have three nested loops executing the core logic: the outer loop runs k times, and the two inner loops 
 * Iterate over the n x n chessboard. Inside, we check a constant 8 directions. Thus, the total operations 
 * Are bounded by 8 * k * n^2, which simplifies to O(k * n^2). Given n <= 25 and k <= 100, this executes 
 * Well within the time limit (~0ms in Java).
 * * - Space Complexity (SC): O(k * n^2)
 * We maintain a 3D array `dp` of size (k + 1) * n * n. 
 * (Optimization Note: Since dp[moves] only depends on dp[moves - 1], this space can be optimized to O(n^2) 
 * By keeping just two 2D arrays—prev_dp and curr_dp—to store the current and previous states).
 * * -----------------------------------------------------------------------------------------------------
 * * 🏃 Dry Run Example
 * Let's trace a simple setup: n = 3, k = 1, starting at row = 0, column = 0.
 * * Step 0: Initialization (moves = 0)
 * The knight starts at (0,0) with a 100% certainty (1.0). All other cells are 0.0.
 * dp[0] Matrix:
 * [1.0, 0.0, 0.0]
 * [0.0, 0.0, 0.0]
 * [0.0, 0.0, 0.0]
 * * Step 1: Processing moves = 1
 * The loops look at every cell (i, j) on the board and look backward to find where a knight could have come from.
 * - For cell (1, 2): One of its backward moves leads to (0, 0). 
 * dp[1][1][2] += dp[0][0][0] / 8.0 -> 0.0 + 1.0 / 8.0 = 0.125
 * - For cell (2, 1): One of its backward moves leads to (0, 0). 
 * dp[1][2][1] += dp[0][0][0] / 8.0 -> 0.0 + 1.0 / 8.0 = 0.125
 * - For all other cells, the valid backward moves only land on cells containing 0.0 in dp[0].
 * * dp[1] Matrix:
 * [0.0,   0.0,   0.0]
 * [0.0,   0.0,   0.125]
 * [0.0,   0.125, 0.0]
 * * Step 2: Final Summation
 * We sum up all the probabilities sitting in our final layer (dp[k] where k = 1):
 * Total Probability = 0.125 + 0.125 = 0.25
 * The knight has a 25% chance of remaining on the board after 1 move.
 */
class Solution {
    public double knightProbability(int n, int k, int row, int column) {
        //what are the different directions the knight can possibly move
        int [][] directions = {{1,2},{1,-2},{-1,2},{-1,-2},{2,1},{2,-1},{-2,1},{-2,-1}};

        //we will initialise the dp table:
        double[][][] dp = new double[k+1][n][n];
        //we mark the position we are at as 1
        dp[0][row][column] = 1.0;

        //we iterate over number of moves:
        for(int moves =1;moves<=k;moves++){
            //iterate over the cells on chessboard
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    //iterate over possible directions
                    for(int [] direction:directions){
                        int prevI = i - direction[0];
                        int prevJ = j - direction[1];
                        //check if the previous cell is within the chessboard
                        if(prevI >= 0 && prevI < n && prevJ >= 0 && prevJ < n){
                            dp[moves][i][j] += dp[moves-1][prevI][prevJ] / 8.0;
                        }
                    }
                }
            }
        }

        //calculate the probability by summing probablities for all cells:
        double totalProbability = 0.0;
        for(int i =0;i<n;i++){
            for(int j=0;j<n;j++){
                totalProbability += dp[k][i][j];
            }
        }

        return totalProbability;


    }
}