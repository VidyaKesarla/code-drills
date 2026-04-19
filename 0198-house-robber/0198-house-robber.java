class Solution {
    //brute force approach for this question is using recursion
    // we explore ervery possible combination of non adjacent house. this results in a branching tree of decisions

    //TC& SC of it is O(2^n) as we make two recursive clals for each house
    // and space complexity is O(n) due to the depth of recursion stack

    // public int rob(int [] nums){
    //     return calculate(nums, nums.length -1);
    // }

    // int calculate(int [] nums, int i){
    //     if(i<0)
    //     return 0;
    //     int robCurrent = nums[i] + calculate(nums, i-2);
    //     int skipCurrent = calculate(nums, i-1);
    //     return Math.max(robCurrent, skipCurrent);
    // }

    //Optimal approach is using DP
    //we can improve the above approach using dp: while calculating the maximum loot for house i, we only ever need the result from house i-1, i-2. instead of an entier array we use two variables to track these values as we iterate.

    //TC O(n) -> we pass throught the array exactly once!
    //SC: O(1)=> we only store two integer variables
    public int rob(int[] nums) {
        //edge cases to consider if nums is null or its lenght is 0 obviously there is no loot.
        if(nums == null || nums.length == 0)
        return 0;

        if(nums.length == 1)
        return nums[0];

        //i'll keep two variables with me prev1 and prev2
        int prev1 = 0;
        int prev2 = 0;

        for(int currentHouseMoney: nums){
            //recurrence relation: Max(current + 2 houses ago, 1 house ago)
            int currentmax = Math.max(currentHouseMoney + prev2, prev1);

            prev2 = prev1;
            prev1 = currentmax;
        }

        return prev1;
    }

    
}

/**
 * 🏠 HOUSE ROBBER - DRY RUN (Example 2: [2, 7, 9, 3, 1])
 * --------------------------------------------------------
 * The logic uses two variables to track the maximum loot:
 * prev2: Max loot from 2 houses ago (dp[i-2])
 * prev1: Max loot from 1 house ago (dp[i-1])
 * * | House (n) | Logic: max(n + prev2, prev1) | prev2 | prev1 (Max) |
 * |-----------|-----------------------------|-------|-------------|
 * | Initial   | -                           | 0     | 0           |
 * | 2         | max(2 + 0, 0)               | 0     | 2           |
 * | 7         | max(7 + 0, 2)               | 2     | 7           |
 * | 9         | max(9 + 2, 7)               | 7     | 11          |
 * | 3         | max(3 + 7, 11)              | 11    | 11          |
 * | 1         | max(1 + 11, 11)             | 11    | 12          |
 * * Final Result: 12
 * * 🚀 Complexity:
 * Time: O(n) - Single pass through the array.
 * Space: O(1) - Only two variables used (no extra DP array).
 */
