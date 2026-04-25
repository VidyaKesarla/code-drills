class Solution {
    public boolean canJump(int[] nums) {
        

        int farthest = 0;

        for (int i = 0; i < nums.length; i++) {
            // If the current index is greater than the farthest we can reach,
            // it means we've hit a zero and can't move forward.
            if (i > farthest) {
                return false;
            }

        farthest = Math.max(farthest, i + nums[i]);
            
            // Optimization: If we can already reach the end, stop early
            if (farthest >= nums.length - 1) {
                return true;
            }
    }
    return true;
    }


}