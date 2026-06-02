
// Note : -
// - Modify the function or parameters if needed.
// - Signatures function may vary, adjust parameters if required.
/*
=======================================================================================
DRY RUN EXECUTION (Sliding Window Approach)
=======================================================================================
Input Array: nums = [3, 2, 20, 1, 1, 3], x = 10

Initial Computations:
  - totalSum = 3 + 2 + 20 + 1 + 1 + 3 = 30
  - target = totalSum - x => 30 - 10 = 20
  Goal: Find the longest continuous middle window that sums exactly up to 20.

Variables Tracked:
  - left = 0, currSum = 0, maxLen = -1

Step-by-Step Loop:
---------------------------------------------------------------------------------------
| right | nums[right] | currSum | Condition (currSum > target) ? | maxLen Updated?    |
|-------|-------------|---------|--------------------------------|--------------------|
|  0    |      3      |    3    | 3 > 20 (No)                    | No change (-1)     |
|  1    |      2      |    5    | 5 > 20 (No)                    | No change (-1)     |
|  2    |     20      |   25    | 25 > 20 (Yes! Shrink window):   | Yes!               |
|       |             |         | - left=0: currSum - 3 = 22     | currSum == target  |
|       |             |         | - left=1: currSum - 2 = 20     | maxLen = max(-1,   |
|       |             |         | Window breaks loop. left is 2. | 2 - 2 + 1) = 1     |
|  3    |      1      |   21    | 21 > 20 (Yes! Shrink window):   | No change (1)      |
|       |             |         | - left=2: currSum - 20 = 1     |                    |
|       |             |         | Window breaks loop. left is 3. |                    |
|  4    |      1      |    2    | 2 > 20 (No)                    | No change (1)      |
|  5    |      3      |    5    | 5 > 20 (No)                    | No change (1)      |
---------------------------------------------------------------------------------------

Final Calculation:
  - Result = nums.length - maxLen => 6 - 1 = 5 operations.

=======================================================================================
COMPLEXITY ANALYSIS
=======================================================================================
- Time Complexity: O(n)
  The 'right' pointer traverses the array sequentially from 0 to n-1. The 'left' pointer 
  only advances forward inside the nested while loop. Because each element is visited 
  at most twice (once added by 'right', once subtracted by 'left'), the runtime is linear.

- Space Complexity: O(1)
  The algorithm tracks sums and window bounds in-place using scalar variables, keeping 
  memory allocation strictly independent of input size.

=======================================================================================
BRUTE FORCE TRADE-OFF
=======================================================================================
- Brute Force Strategy:
  We could use recursion/backtracking to branch at every step—deciding whether to remove 
  the leftmost element or the rightmost element.

- Why Brute Force Is Inefficient:
  1. Time Complexity: O(2^k) where k is the number of operations required. At each choice point, 
     the algorithm splits into 2 independent states. This easily results in a Time Limit Exceeded (TLE)
     for large values of k or array sizes.
  2. Space Complexity: O(k) due to the deep recursive call stack overhead.

- The Trade-Off:
  By reframing the problem from "choosing elements from the edges" to "finding a single stable window 
  in the middle", we trade an exponential, messy search path (O(2^k)) for a single predictable linear 
  scan (O(n)) with flawless space efficiency.
*/
class Solution {
    public int minOperations(int[] nums, int x) {
        int total = 0;
        for(int i: nums){
            total += i;
        }
        int target = total - x;
        if(target == 0) return nums.length;
        if(target < 0)
        return -1;

        int maxLen = -1;
        int left = 0;
        int currSum = 0;
        for(int right = 0;right<nums.length;right++){
            currSum = currSum + nums[right];
            while(currSum > target && left <= right){

                currSum -= nums[left++];}

              if(currSum == target){
                maxLen = Math.max(maxLen, right - left + 1);
              }
        }

        return (maxLen == -1) ? -1 : nums.length - maxLen;
    }
}