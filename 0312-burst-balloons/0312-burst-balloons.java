/*
Brute force approach for this problem is to try every possible permutation of bursting balloons. For n balloons, there are n! ways to burst them
logiC: for each balloon, burst it, calculate its coins and recursively call the function for remaining balloons.
This will introduce time complexity: O(n!)
space complexity: O(n) for recursion stack

public int maxCoinsBruteForce(List<Integer> nums) {
    if (nums.isEmpty()) return 0;
    int max = 0;
    for (int i = 0; i < nums.size(); i++) {
        int left = (i == 0) ? 1 : nums.get(i - 1);
        int right = (i == nums.size() - 1) ? 1 : nums.get(i + 1);
        int currentCoins = left * nums.get(i) * right;

        // Create a new list without the current balloon
        List<Integer> remaining = new ArrayList<>(nums);
        remaining.remove(i);

        max = Math.max(max, currentCoins + maxCoinsBruteForce(remaining));
    }
    return max;
}

*/
// class Solution {
    
//     public int maxCoins(int[] nums) {
//         int n = nums.length;
//         // Step 1: Pad the array. [3, 1, 5] -> [1, 3, 1, 5, 1]
//         int[] arr = new int[n + 2];
//         arr[0] = 1;
//         arr[n + 1] = 1;
//         for (int i = 0; i < n; i++) arr[i + 1] = nums[i];

//         int[][] memo = new int[n + 2][n + 2];
//         return solve(arr, 1, n, memo);
//     }

//     /*
//     DRY RUN: nums = [3, 1, 5] | arr = [1, 3, 1, 5, 1]
//     Initial Call: solve(1, 3) -> Goal: Max coins for range index 1 to 3
    
//     Level 1: solve(1, 3) tries k = 1, 2, 3 as the LAST balloon:
    
//     - If k = 1 (Balloon '3' is last):
//         Cost = solve(1, 0) + solve(2, 3) + (arr[0]*arr[1]*arr[4])
//              = 0 + solve(2, 3) + (1 * 3 * 1)
//              *Inside solve(2, 3) for [1, 5]:
//                 - If k=2 last: solve(2, 1) + solve(3, 3) + (arr[1]*arr[2]*arr[4]) = 0 + 5 + (3*1*1) = 8
//                 - If k=3 last: solve(2, 2) + solve(4, 3) + (arr[1]*arr[3]*arr[4]) = 3 + 0 + (3*5*1) = 18
//                 - solve(2, 3) returns MAX(8, 18) = 18
//         Total for k=1: 0 + 18 + 3 = 21
        
//     - If k = 2 (Balloon '1' is last):
//         Cost = solve(1, 1) + solve(3, 3) + (arr[0]*arr[2]*arr[4])
//              = (1*3*1) + (1*5*1) + (1*1*1)
//              = 3 + 5 + 1 = 9
             
//     - If k = 3 (Balloon '5' is last):
//         Cost = solve(1, 2) + solve(4, 3) + (arr[0]*arr[3]*arr[4])
//              = solve(1, 2) + 0 + (1 * 5 * 1)
//              *Inside solve(1, 2) for [3, 1]:
//                 - If k=1 last: solve(1, 0) + solve(2, 2) + (arr[0]*arr[1]*arr[3]) = 0 + 15 + (1*3*5) = 30
//                 - If k=2 last: solve(1, 1) + solve(3, 2) + (arr[0]*arr[2]*arr[3]) = 3 + 0 + (1*1*5) = 8
//                 - solve(1, 2) returns MAX(30, 8) = 30
//         Total for k=3: 30 + 0 + 5 = 35
        
//     Final Result: MAX(21, 9, 35) = 35
//     */

//     private int solve(int[] arr, int i, int j, int[][] memo) {
//         if (i > j) return 0;
//         if (memo[i][j] > 0) return memo[i][j];

//         int max = 0;
//         for (int k = i; k <= j; k++) {
//             // Coins = (coins from bursting left side) 
//             //         + (coins from bursting right side) 
//             //         + (coins from bursting k using outer boundaries)
//             int cost = solve(arr, i, k - 1, memo) + 
//                        solve(arr, k + 1, j, memo) + 
//                        (arr[i - 1] * arr[k] * arr[j + 1]);
            
//             max = Math.max(max, cost);
//         }
//         return memo[i][j] = max;
//     }
// }

class Solution {
    /**
     * APPROACH: Interval Dynamic Programming (Bottom-Up Tabulation)
     * * WHY THIS WORKS:
     * Instead of picking the first balloon to burst (which makes neighbors unpredictable), 
     * we pick the LAST balloon (k) to burst in a range (i, j). 
     * This makes the sub-problems to the left and right of 'k' independent.
     *
     * --------------------------------------------------------------------------------------------------
     * TRACE TABLE (nums = [3, 1, 5] | padded arr = [1, 3, 1, 5, 1])
     * --------------------------------------------------------------------------------------------------
     * Phase | len | i | j | k (last) | dp[i][k] | dp[k][j] | arr[i]*arr[k]*arr[j] | Total | dp[i][j] Max
     * --------------------------------------------------------------------------------------------------
     * 1     | 1   | 0 | 2 | 1 (val 3)| 0        | 0        | 1 * 3 * 1 = 3        | 3     | dp[0][2] = 3
     * 1     | 1   | 1 | 3 | 2 (val 1)| 0        | 0        | 3 * 1 * 5 = 15       | 15    | dp[1][3] = 15
     * 1     | 1   | 2 | 4 | 3 (val 5)| 0        | 0        | 1 * 5 * 1 = 5        | 5     | dp[2][4] = 5
     * --------------------------------------------------------------------------------------------------
     * 2     | 2   | 0 | 3 | 1 (val 3)| 0        | 15 (1,3) | 1 * 3 * 5 = 15       | 30    | 
     * 2     | 2   | 0 | 3 | 2 (val 1)| 3 (0,2)  | 0        | 1 * 1 * 5 = 5        | 8     | dp[0][3] = 30
     * --------------------------------------------------------------------------------------------------
     * 2     | 2   | 1 | 4 | 2 (val 1)| 0        | 5 (2,4)  | 3 * 1 * 1 = 3        | 8     | 
     * 2     | 2   | 1 | 4 | 3 (val 5)| 15 (1,3) | 0        | 3 * 5 * 1 = 15       | 30    | dp[1][4] = 30
     * --------------------------------------------------------------------------------------------------
     * 3     | 3   | 0 | 4 | 1 (val 3)| 0        | 30 (1,4) | 1 * 3 * 1 = 3        | 33    |
     * 3     | 3   | 0 | 4 | 2 (val 1)| 3 (0,2)  | 5 (2,4)  | 1 * 1 * 1 = 1        | 9     | 
     * 3     | 3   | 0 | 4 | 3 (val 5)| 30 (0,3) | 0        | 1 * 5 * 1 = 5        | 35    | dp[0][4] = 35
     * --------------------------------------------------------------------------------------------------
     *

     /*
    * FINAL DP TABLE VISUALIZATION (nums = [3, 1, 5])
    * Padded Array: [1, 3, 1, 5, 1]
    * Indices:       0  1  2  3  4
    *     * j=0   j=1   j=2   j=3   j=4
    * i=0 [  0     0     3    30    35  ]  <-- dp[0][4] is the final answer
    * i=1 [  0     0     0    15    30  ]
    * i=2 [  0     0     0     0     5  ]
    * i=3 [  0     0     0     0     0  ]
    * i=4 [  0     0     0     0     0  ]
    * * Note: Only the upper triangle is filled because i < j always.

     * COMPLEXITY:
     * - Time: O(n^3) -> Three nested loops (length, start index, and k-split point).
     * - Space: O(n^2) -> 2D DP table to store results for every possible interval.
     *
     * ADVANTAGES:
     * 1. Better than Brute Force O(n!): Uses memoization to avoid re-calculating sub-ranges.
     * 2. Better than Top-Down: Avoids StackOverflow risk and has better CPU cache locality.
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // 1. Pad the array with 1s at both ends to handle boundaries
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        // 2. dp[i][j] stores max coins for balloons strictly between index i and j
        int[][] dp = new int[n + 2][n + 2];

        // 3. len is the number of balloons we are currently bursting in our window
        for (int len = 1; len <= n; len++) {
            // i is the left boundary (wall)
            for (int i = 0; i <= n - len; i++) {
                int j = i + len + 1; // j is the right boundary (wall)
                
                // k is the index of the balloon burst LAST in the current range (i, j)
                for (int k = i + 1; k < j; k++) {
                    // coins = left sub-range + right sub-range + final burst of k
                    int currentCoins = dp[i][k] + dp[k][j] + (arr[i] * arr[k] * arr[j]);
                    
                    dp[i][j] = Math.max(dp[i][j], currentCoins);
                }
            }
        }

        return dp[0][n + 1];
    }
}
