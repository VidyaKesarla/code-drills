
/**
 * BRUTE FORCE APPROACH TRADEOFF:
 * A naive brute-force approach would use recursion to explore every possible path 
 * from (0,0) to (m-1, n-1) by only moving down or right. 
 * - Time Complexity: O(2^(m + n)) because at every step we have 2 choices. This 
 * results in a Time Limit Exceeded (TLE) for larger grids due to massive redundant 
 * calculations of overlapping subproblems.
 * - Space Complexity: O(m + n) for the recursion stack depth.
 * * OPTIMAL DP TRADEOFF (Implemented Below):
 * By breaking the problem down and working backwards from the destination (bottom-up DP), 
 * we reuse the answers to subproblems. Modifying the grid in-place eliminates extra space.
 * - Time Complexity: Optimally reduced to O(m * n).
 * - Space Complexity: Optimally reduced to O(1) by reusing the input array.
 * (Tradeoff: We mutate the original input grid. If the original data must be 
 * preserved, we would need O(m * n) or O(n) extra space).
 * * ---
 * * DRY RUN:
 * Input Grid: nums = [[1, 3], 
 * [1, 5]]
 * m = 2, n = 2
 * * Outer Loop (i from 1 to 0), Inner Loop (j from 1 to 0):
 * * 1. i = 1, j = 1 (Bottom-Right Corner):
 * - Hits `if (i == 1 && j == 1) -> continue;`
 * - Grid stays: [[1, 3], [1, 5]]
 * * 2. i = 1, j = 0 (Bottom Row):
 * - Hits `else if (i == 1 && j != 1)`
 * - nums[1][0] = nums[1][0] + nums[1][1] = 1 + 5 = 6
 * - Grid becomes: [[1, 3], [6, 5]]
 * * 3. i = 0, j = 1 (Right Column):
 * - Hits `else if (i != 1 && j == 1)`
 * - nums[0][1] = nums[0][1] + nums[1][1] = 3 + 5 = 8
 * - Grid becomes: [[1, 8], [6, 5]]
 * * 4. i = 0, j = 0 (Top-Left / Inner Cell):
 * - Hits final `else` block
 * - nums[0][0] = nums[0][0] + Math.min(nums[0][1], nums[1][0])
 * = 1 + Math.min(8, 6) = 1 + 6 = 7
 * - Grid becomes: [[7, 8], [6, 5]]
 * * Return nums[0][0] -> 7
 * * ---
 * * COMPLEXITY ANALYSIS:
 * - Time Complexity: O(m * n) - We traverse each cell in the m x n grid exactly once.
 * - Space Complexity: O(1) - The grid is modified in-place, using no additional memory.
 */

class Solution {
    public int minPathSum(int[][] nums) {
        int m = nums.length;
        int n = nums[0].length;

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // Base Case: Destination cell remains unchanged
                if (i == m - 1 && j == n - 1) {
                    continue;
                }
                // Last column: can only move down
                else if (i != m - 1 && j == n - 1) {
                    nums[i][j] = nums[i][j] + nums[i + 1][j];
                } 
                // Last row: can only move right
                else if (i == m - 1 && j != n - 1) {
                    nums[i][j] = nums[i][j] + nums[i][j + 1];
                } 
                // Inner cells: take current value + min(right neighbor, bottom neighbor)
                else {
                    nums[i][j] = nums[i][j] + Math.min(nums[i][j + 1], nums[i + 1][j]);
                }
            }
        }
        return nums[0][0];
    }
}