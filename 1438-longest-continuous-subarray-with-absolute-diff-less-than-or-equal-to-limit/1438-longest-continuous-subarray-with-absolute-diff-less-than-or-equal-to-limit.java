class Solution {
    //approach using double ended queue
    public int longestSubarray(int[] nums, int limit) {
        //Initialize two deques, maxDeque and minDeque.
        Deque<Integer> minDeque = new LinkedList<>(); //increasing
         Deque<Integer> maxDeque = new LinkedList<>();//decreasing

        //Initialize left to 0 to represent the start of the sliding window.
        int left = 0;
    
        //Initialize maxLength to 0 to store the length of the longest valid subarray.
        int maxLength = 0;

        //Iterate through the array nums from left to right using a variable right
        for(int right = 0;right<nums.length;right++){
            // Maintain the maxDeque in decreasing order
            while (!maxDeque.isEmpty() && maxDeque.peekLast() < nums[right]) {
                maxDeque.pollLast();
            }
            maxDeque.offerLast(nums[right]);
            // Maintain the minDeque in decreasing order
            while (!minDeque.isEmpty() && minDeque.peekLast() > nums[right]) {
                minDeque.pollLast();
            }
            minDeque.offerLast(nums[right]);
            
            while (maxDeque.peekFirst() - minDeque.peekFirst() > limit) {
                if (maxDeque.peekFirst() == nums[left]) {
                    maxDeque.pollFirst();
                }
                if (minDeque.peekFirst() == nums[left]) {
                    minDeque.pollFirst();
                }
                ++left;
            }

            maxLength = Math.max(maxLength, right - left + 1);
            }
            return maxLength;

        }

    }

    /*
================================================================================
🚀 BRUTE FORCE APPROACH & TRADE-OFFS
================================================================================
The naive approach is to generate every possible subarray, find the maximum 
and minimum elements within that subarray, and check if (max - min <= limit).

- How it works: Use nested loops to look at every subarray nums[i...j]. A third 
  loop (or dynamic tracking) is used to find the max and min values.
- Time Complexity: O(N^2) with optimized tracking, or O(N^3) naively.
- Space Complexity: O(1) auxiliary space.
- The Trade-off: While brute force achieves the absolute best space complexity 
  O(1), its time complexity makes it too slow for N <= 10^5, resulting in a 
  "Time Limit Exceeded" (TLE) error. We sacrifice O(N) memory to achieve an 
  optimal linear O(N) runtime.

================================================================================
⏱️ COMPLEXITY ANALYSIS (OPTIMAL APPROACH)
================================================================================
- Time Complexity: O(N)
  Every element in the array is pushed into and popped from both 'maxDeque' 
  and 'minDeque' at most ONCE. Even with the inner sliding window validation 
  while loop, the total work across the entire execution scales linearly.
  
- Space Complexity: O(N)
  In the worst-case scenario (such as a strictly increasing or strictly 
  decreasing array), a deque might store up to N elements simultaneously.

================================================================================
🔍 STEP-BY-STEP DRY RUN
================================================================================
Input: nums = [8, 2, 4, 7], limit = 4

1. right = 0, nums[0] = 8
   - maxDeque: [8] | minDeque: [8]
   - Diff: 8 - 8 = 0 (<= 4). Window is valid.
   - Window size: 0 - 0 + 1 = 1. maxLength = 1.

2. right = 1, nums[1] = 2
   - maxDeque: [8, 2] | minDeque: [2] (8 popped because 8 > 2)
   - Diff: 8 - 2 = 6 (> 4). Window is INVALID.
   - Shrink window from left: nums[left] is 8. Pop 8 from maxDeque. left becomes 1.
   - Window size: 1 - 1 + 1 = 1. maxLength = 1.

3. right = 2, nums[2] = 4
   - maxDeque: [4] (2 popped because 2 < 4) | minDeque: [2, 4]
   - Diff: 4 - 2 = 2 (<= 4). Window is valid.
   - Window size: 2 - 1 + 1 = 2. maxLength = max(1, 2) = 2.

4. right = 3, nums[3] = 7
   - maxDeque: [7] (4 popped because 4 < 7) | minDeque: [2, 4, 7]
   - Diff: 7 - 2 = 5 (> 4). Window is INVALID.
   - Shrink window from left: nums[left] is 2. Pop 2 from minDeque. left becomes 2.
   - Window size: 3 - 2 + 1 = 2. maxLength = max(2, 2) = 2.

Final Return Value: 2 (Valid windows were [2, 4] and [4, 7])
================================================================================
*/