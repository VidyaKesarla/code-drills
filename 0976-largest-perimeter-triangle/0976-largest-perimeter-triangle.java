class Solution {
    public int largestPerimeter(int[] nums) {
        // Sort the array in ascending order
        Arrays.sort(nums);
        // Traverse from the largest element backwards
        for (int i = nums.length - 1; i >= 2; i--) {
            // Check if a valid triangle can be formed
            if (nums[i - 2] + nums[i - 1] > nums[i]) {
                return nums[i - 2] + nums[i - 1] + nums[i];
            }
        }
        return 0;
    }
}