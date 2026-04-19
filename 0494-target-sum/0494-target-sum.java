// BRUTE FORCE APPROACH
// For each number, we have 2 choices: '+' or '-'
// We explore every possible combination using recursion (binary tree)
// At the end of each path, check if the expression equals target
// Time: O(2^n) - two choices per element, Space: O(n) - recursion stack depth

// class Solution {
//     private int count = 0;

//     public int findTargetSumWays(int[] nums, int target) {
//         recurse(nums, target, 0, 0);
//         return count;
//     }

//     private void recurse(int[] nums, int target, int index, int currentSum) {
//         // Base case: assigned signs to all numbers
//         // index == nums.length means we've walked off the end of the array
//         // i.e., every number has been given a '+' or '-'
//         if (index == nums.length) {
//             if (currentSum == target) count++; // valid expression found
//             return;                            // stop this branch either way
//         }

//         recurse(nums, target, index + 1, currentSum + nums[index]); // put '+' in front of nums[index]
//         recurse(nums, target, index + 1, currentSum - nums[index]); // put '-' in front of nums[index]
//     }
// }


class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int totalSum = 0;
        for (int n : nums) totalSum += n;

        // --- STEP 1: Mathematical Edge Cases ---
        // 1. If target is out of reach of the total possible sum, return 0.
        // 2. Based on (P - N = target) and (P + N = totalSum), 
        //    the subset sum P must be (target + totalSum) / 2.
        //    If (target + totalSum) is odd, no integer subset P exists.
        if (Math.abs(target) > totalSum || (target + totalSum) % 2 != 0) {
            return 0;
        }

        // --- STEP 2: Transform to Subset Sum Problem ---
        // We are now just looking for how many subsets sum up to 's'.
        int s = (target + totalSum) / 2;
        int[] dp = new int[s + 1];
        
        // Base case: There is 1 way to make a sum of 0 (the empty set).
        dp[0] = 1;

        // --- STEP 3: Dynamic Programming (0/1 Knapsack) ---
        for (int num : nums) {
            // We iterate backwards to ensure each number is used only once.
            // We stop when i < num because we can't form a smaller sum using 'num'.
            for (int i = s; i >= num; i--) {
                // The ways to make sum 'i' is:
                // (Existing ways without this num) + (Ways to make 'i - num')
                dp[i] += dp[i - num];
            }
        }

        // The answer is the number of ways to form the subset target 's'.
        return dp[s];

        /*
 * TRACE: nums = [1,1,1,1,1], target s = 4
 * dp[i] = number of ways to pick coins summing to i
 * Start: dp = [1, 0, 0, 0, 0]
 *
 * Outer Loop #1 (num=1): [1, 1, 0, 0, 0]
 *   i=1: dp[1] += dp[0]  =>  0 + 1 = 1
 *
 * Outer Loop #2 (num=1): [1, 2, 1, 0, 0]
 *   i=2: dp[2] += dp[1]  =>  0 + 1 = 1
 *   i=1: dp[1] += dp[0]  =>  1 + 1 = 2
 *
 * Outer Loop #3 (num=1): [1, 3, 3, 1, 0]
 *   i=3: dp[3] += dp[2]  =>  0 + 1 = 1
 *   i=2: dp[2] += dp[1]  =>  1 + 2 = 3
 *   i=1: dp[1] += dp[0]  =>  2 + 1 = 3
 *
 * Outer Loop #4 (num=1): [1, 4, 6, 4, 1]
 *   i=4: dp[4] += dp[3]  =>  0 + 1 = 1
 *   i=3: dp[3] += dp[2]  =>  1 + 3 = 4
 *   i=2: dp[2] += dp[1]  =>  3 + 3 = 6
 *   i=1: dp[1] += dp[0]  =>  3 + 1 = 4
 *
 * Outer Loop #5 (num=1): [1, 5, 10, 10, 5]
 *   i=4: dp[4] += dp[3]  =>  1 + 4 = 5  <-- ANSWER
 *   i=3: dp[3] += dp[2]  =>  4 + 6 = 10
 *   i=2: dp[2] += dp[1]  =>  6 + 4 = 10
 *   i=1: dp[1] += dp[0]  =>  4 + 1 = 5
 *
 * Why dp[4] = 5:
 *   dp[4]=1 (1 way to reach 4 using first 4 coins)
 * + dp[3]=4 (4 ways to reach 3 using first 4 coins;
 *            adding the 5th coin '1' to each = 4 new ways to reach 4)
 * = 5 total ways
 *
 * Note: each row of dp is a row of Pascal's Triangle — makes sense
 * since we're distributing identical items into subsets!
 *
 * return dp[s] = dp[4] = 5
 */
    }
}
