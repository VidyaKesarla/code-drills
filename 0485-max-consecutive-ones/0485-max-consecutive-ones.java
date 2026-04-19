class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        //find the max number of consecutive ones
        //we will take two counts - maxCount and currentCount
        int maxCount = 0;
        int currentCount = 0;
        for(int num : nums) {
            if (num == 1) {
                currentCount++;
            }else  {
                maxCount = Math.max(maxCount, currentCount);
                currentCount = 0;
            }
        }
         maxCount = Math.max(maxCount, currentCount);
        return maxCount;
    }
}