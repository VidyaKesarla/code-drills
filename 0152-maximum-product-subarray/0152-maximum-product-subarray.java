class Solution {
    /*
    Brute Force Approach ($O(n^2)$)This checks every possible subarray by fixing a starting point i and extending j

    /**
 * BRUTE FORCE TRACE: nums = [2, 3, -2, 4]
 * -----------------------------------------------------------------------
 * | i (Start) | j (End) | Subarray        | Calculation    | Product | Max |
 * |-----------|---------|-----------------|----------------|---------|-----|
 * | 0         | 0       | [2]             | 2              | 2       | 2   |
 * | 0         | 1       | [2, 3]          | 2 * 3          | 6       | 6   |
 * | 0         | 2       | [2, 3, -2]      | 6 * -2         | -12     | 6   |
 * | 0         | 3       | [2, 3, -2, 4]   | -12 * 4        | -48     | 6   |
 * | 1         | 1       | [3]             | 3              | 3       | 6   |
 * | 1         | 2       | [3, -2]         | 3 * -2         | -6      | 6   |
 * | 1         | 3       | [3, -2, 4]      | -6 * 4         | -24     | 6   |
 * | 2         | 2       | [-2]            | -2             | -2      | 6   |
 * | 2         | 3       | [-2, 4]         | -2 * 4         | -8      | 6   |
 * | 3         | 3       | [4]             | 4              | 4       | 6   |
 * -----------------------------------------------------------------------
 * Result: 6
 * Time Complexity: O(n^2) | Space Complexity: O(1)
 */

    public int maxProduct(int[] nums) {

        //best approach for this is using modified kadane's algorithm :

        /**
 * Optimized Kadane's Algorithm for Maximum Product Subarray
 * --------------------------------------------------------
 * Time Complexity:  O(n) - We visit each number exactly once.
 * Space Complexity: O(1) - We only store three variables (max, min, result).
 * * Logic Highlights:
 * 1. Zero Handling: If curr is 0, maxSoFar and minSoFar reset to 0. 
 * On the next iteration, Math.max(next, 0) effectively restarts the subarray.
 * * 2. Negative Handling: A swap(maxSoFar, minSoFar) is performed when curr < 0.
 * This captures the "double negative" effect, turning a large negative
 * minimum into a large positive maximum.
 * * 3. Min Tracking: We maintain minSoFar specifically to "carry" potential 
 * products that could become the maximum if multiplied by another negative.


 The Logic Recap

Positive Numbers: They just make big numbers bigger and small numbers smaller.

Negative Numbers: They act like a "switch." They turn your best case into your worst case, and your worst case (a big negative) into your best case (a big positive).

Zeros: If you hit a zero, both max_so_far and min_so_far will become 0, effectively "resetting" the calculation for the next sub-segment of the array.
 */

        //TC: O(n) SC: O(1) as we store only max, min and res
        //edge case: if array is empty:
        if(nums == null || nums.length == 0)
        return 0;

        //then i will initialise the variables with the first element
        int maxSoFar = nums[0];
        int minSoFar = nums[0];
        int result = nums[0];

        //start the for loop from the second element
        for(int i=1;i<nums.length;i++){
            int current = nums[i];

            if(current < 0){
                //swap min and max so far -> if we hit a negative number
                int temp = maxSoFar;
                maxSoFar = minSoFar;
                minSoFar = temp;
            }

        maxSoFar = Math.max(current, current * maxSoFar);
        minSoFar = Math.min(current, current * minSoFar);

        result = Math.max(result, maxSoFar);

        }
        return result;
        
    }
}