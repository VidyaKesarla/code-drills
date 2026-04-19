class Solution {

/*

Time Complexity: $O(N \log N)$—this is the standard complexity for Arrays.sort() (which uses Dual-Pivot Quicksort for primitives or TimSort for objects).Space Complexity: $O(\log N)$ or $O(N)$ depending on the internal implementation of the sorting algorithm to maintain the stack.
Step 1: Sorting (The most critical part)

We sort the envelopes based on two rules:

Primary: Sort Width in Ascending order.

Secondary: If Widths are equal, sort Height in Descending order.

Why Descending Height? > If we have envelopes [5, 4] and [5, 6], they have the same width. They cannot fit inside each other.

If we sorted heights ascending: [4, 6]. LIS would pick both, which is wrong (same width!).

If we sort heights descending: [6, 4]. LIS will only pick one of them (either 6 or 4), which is correct.

Step 2: LIS on Heights

Once sorted, the width constraint is mostly "handled" by the sort order. We simply find the Longest Increasing Subsequence of the Heights.
Dry Run: [[5,4], [6,4], [6,7], [2,3]]

Sorting Phase:

Compare widths: [2,?], [5,?], [6,?], [6,?]

Sort width 6 heights descending: [6,7], [6,4]

Sorted Array: [[2,3], [5,4], [6,7], [6,4]]

Height LIS Phase ([3, 4, 7, 4]):

3 comes: tails = [3], size = 1

4 comes: tails = [3, 4], size = 2

7 comes: tails = [3, 4, 7], size = 3

4 comes: Binary search finds tails[1] is 4. Replaces 4 with 4. tails = [3, 4, 7], size = 3

Final Answer: 3.

The envelopes are: [2,3] -> [5,4] -> [6,7]



*/



    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes == null || envelopes.length == 0)
        return 0;

    Arrays.sort(envelopes, (a,b) -> {
            if(a[0] == b[0]){
                return b[1] - a[1]; //descending height
            } else {
                return a[0] - b[0]; //ascending width
            }
        });

        //extract only heights for Longest increasing subsequence
        int[] heights = new int[envelopes.length];

        for(int i=0;i<envelopes.length;i++){
            heights[i] = envelopes[i][1];
        }

        return lengthOfLIS(heights);
    }
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

}