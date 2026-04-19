class Solution {
    public int maxSubArray(int[] nums) {
        if (nums.length == 0 || nums== null){
            return 0;
        }

        int maxSoFar = nums[0];
        int maxEnding = nums[0];

        for(int i=1;i<nums.length;i++){
            int current = nums[i];
            maxEnding = Math.max(current, current + maxEnding);
            maxSoFar = Math.max(maxEnding, maxSoFar);
        }
        return maxSoFar;

    }
}