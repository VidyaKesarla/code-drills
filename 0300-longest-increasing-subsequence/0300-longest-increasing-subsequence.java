
/* * BRUTE FORCE APPROACH: Dynamic Programming
 * -------------------------------------------------------------------------
 * LOGIC AND INTUITION:
 * 1. Concept: Define dp[i] as the length of the LIS ending at index 'i'.
 * 2. Base Case: Every element is a subsequence of length 1 (dp[i] = 1).
 * 3. Look-back: For each element 'i', scan all previous elements 'j' (0 to i-1).
 * 4. Update: If nums[i] > nums[j], then dp[i] = Math.max(dp[i], dp[j] + 1).
 * * WHY THIS IS SLOW (O(n^2)):
 * - It uses nested loops. For every single element, we re-scan the entire 
 * prefix of the array.
 * - If n = 10^5, n^2 = 10,000,000,000 (10 billion) operations. 
 * Standard competitive programming limits are ~10^8 operations per second.
 * - This approach will result in a "Time Limit Exceeded" (TLE) on LeetCode.
 * * CODE STRUCTURE (O(n^2)):
 * for (int i = 0; i < n; i++) {
 * for (int j = 0; j < i; j++) {
 * if (nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
 * }
 * }
 * -------------------------------------------------------------------------
 */

// class Solution {
//     public int lengthOfLIS(int[] nums) {
//         // ... (Your O(n log n) Patience Sorting code here)
//     }
// }


/*
 * BRUTE FORCE APPROACH: Dynamic Programming (O(n^2))
 * --------------------------------------------------
 * Logic:
 * 1. Define dp[i] as the length of the Longest Increasing Subsequence 
 * ending exactly at index 'i'.
 * 2. For every element nums[i], look back at all previous elements nums[j] (0 to i-1).
 * 3. If nums[i] > nums[j], it means nums[i] can extend the sequence that ended at j.
 * 4. Transition: dp[i] = max(dp[i], dp[j] + 1).
 * 5. The final answer is the maximum value found in the entire dp array.
 *
 * Why this is "Bad" for Google-level constraints (n = 10^5):
 * - Time Complexity: O(n^2). For 10^5 elements, this is 10^10 operations.
 * Most judges (and Google production systems) limit you to ~10^8 operations.
 * - Performance: It will result in "Time Limit Exceeded" (TLE).
 * - Redundancy: It re-scans all previous elements instead of maintaining 
 * only the "best" candidates for future sequences.
 */

/* // Implementation for reference:
public int lengthOfLIS(int[] nums) {
    if (nums.length == 0) return 0;
    int[] dp = new int[nums.length];
    java.util.Arrays.fill(dp, 1);
    int maxOverall = 1;
    
    for (int i = 1; i < nums.length; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        maxOverall = Math.max(maxOverall, dp[i]);
    }
    return maxOverall;
}
*/



/* * APPROACH: Binary Search / Patience Sorting (O(n log n))
 * -------------------------------------------------------------------------
 * LOGIC AND INTUITION:
 * 1. Concept: We maintain an array 'tails' where tails[i] is the smallest 
 * tail (ending element) of all increasing subsequences of length i + 1.
 * 2. Why "Smallest Tail"? Because a smaller ending number gives us a 
 * better chance to extend the sequence with future numbers.
 * 3. Strategy: For each number 'x' in the input:
 * - If x is larger than all elements in 'tails', append it (LIS grows).
 * - Otherwise, find the smallest element in 'tails' that is >= x and 
 * replace it with x. This "tightens" the sequence tail.
 * 4. Finding the Replacement: Since 'tails' is always sorted, we use 
 * Binary Search to find the correct insertion/replacement point.
 * 5. Global Result: The final answer is simply the size of the 'tails' array.
 *
 * WHY THIS IS THE "GOOGLE-TIER" APPROACH:
 * - Time Complexity: O(n log n). We iterate through n elements and perform 
 * a log n binary search for each. For n = 10^5, this is only ~1.7M operations.
 * - Scalability: This is the only way to solve the "Russian Doll Envelopes" 
 * problem within the time limit.
 * - Space Complexity: O(n) to store the tails array.
 * -------------------------------------------------------------------------
 */

class Solution {
    public int lengthOfLIS(int[] nums) {
        // Approach: Patience Sorting (Greedy + Binary Search)
        // Time Complexity: O(n log n) | Space Complexity: O(n)
        if (nums == null || nums.length == 0) return 0;

        int[] tails = new int[nums.length];
        int size = 0;

        for (int x : nums) {
            int i = 0;
            int j = size;

            while (i != j) {
                // BUG FIX: Ensure correct midpoint calculation
                int mid = i + (j - i) / 2; 
                
                if (tails[mid] < x) {
                    i = mid + 1; // x is larger, search the right half
                } else {
                    j = mid;     // x is smaller or equal, search the left half
                }
            }

            // 'i' is now the insertion point for x
            tails[i] = x;

            // If x was placed at the end, the LIS length has increased
            if (i == size) size++;
        }
        return size;
    }

    /* * DRY RUN: nums = [10, 9, 2, 5, 3, 7, 101, 18]
     * -------------------------------------------------------------------------
     * INITIAL STATE: tails = [0,0,0,0,0,0,0,0], size = 0
     * * 1. x = 10: 
     * i=0, j=0. Loop doesn't run. 
     * tails[0] = 10. i == size (0==0), size = 1.
     * Current tails: [10] (Length 1 ends in 10)
     * * 2. x = 9: 
     * i=0, j=1. mid = 0. tails[0](10) < 9 is False. j = 0.
     * tails[0] = 9. (Replaced 10 with 9). i != size.
     * Current tails: [9] (Length 1 now ends in 9 - "Better" tail)
     * * 3. x = 2: 
     * i=0, j=1. mid = 0. tails[0](9) < 2 is False. j = 0.
     * tails[0] = 2. (Replaced 9 with 2). i != size.
     * Current tails: [2] (Length 1 now ends in 2 - "Best" tail)
     * * 4. x = 5: 
     * i=0, j=1. mid = 0. tails[0](2) < 5 is True. i = 1.
     * tails[1] = 5. i == size (1==1), size = 2.
     * Current tails: [2, 5] (Length 2 ends in 5)
     * * 5. x = 3: 
     * i=0, j=2. mid = 1. tails[1](5) < 3 is False. j = 1.
     * mid = 0. tails[0](2) < 3 is True. i = 1.
     * tails[1] = 3. (Replaced 5 with 3). i != size.
     * Current tails: [2, 3] (Length 2 now ends in 3 - "Better" tail)
     * * 6. x = 7: 
     * i=0, j=2. Binary search finds no element >= 7. i ends at 2.
     * tails[2] = 7. i == size (2==2), size = 3.
     * Current tails: [2, 3, 7] (Length 3 ends in 7)
     * * 7. x = 101: 
     * i=0, j=3. Binary search finds no element >= 101. i ends at 3.
     * tails[3] = 101. i == size (3==3), size = 4.
     * Current tails: [2, 3, 7, 101] (Length 4 ends in 101)
     * * 8. x = 18: 
     * i=0, j=4. Binary search finds first element >= 18 is 101 at index 3.
     * tails[3] = 18. (Replaced 101 with 18). i != size.
     * Current tails: [2, 3, 7, 18] (Length 4 now ends in 18 - "Better" tail)
     * * FINAL RESULT: size = 4
     * -------------------------------------------------------------------------
     */
}