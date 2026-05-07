class Solution {
    public boolean canPartition(int[] nums) {
        int n = nums.length;

        if(n == 0)
        return false;

        int totalSum = 0;

        for(int element: nums){
            totalSum += element;
        }

        if(totalSum % 2 != 0)
        return false;

        int subsetSum = totalSum / 2;

        boolean dp[] = new boolean[subsetSum + 1];
        dp[0] = true;

        for(int curr: nums){
            for(int j=subsetSum;j>=curr;j--){
                dp[j] = dp[j] | dp[j-curr];
            }
        }
        return dp[subsetSum];

    }
}

// Time Complexity : O(m⋅n), where m is the subSetSum, and n is the number of array elements. 

// Space Complexity: O(m), As we use an array of size m to store the result of subproblems.