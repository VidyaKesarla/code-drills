// The brute force approach would be to loop over all subarrays in nums and check if their sums exceed k. The smallest one among them is our answer. However, this approach is too slow for our constraints.

// Let's identify the redundancies in the above approach. One major issue is that we keep recalculating the same subarray sums multiple times. We can solve this by creating a prefix sum array, which lets us quickly find the sum of any subarray. Using this array, we can look at each element and find an earlier prefix sum that, when subtracted from our current sum, gives us a value of at least k.

// However, searching for the best prefix sum for each index is still too slow. What we really need is a way to quickly find the "best" prefix sum – one with the lowest value that's also closest to our current position.

// This is where a heap (also called a priority queue) becomes useful. We can store pairs of [prefix sum, ending index] in the heap, arranged so that the lowest sum is always at the top. This helps us quickly find the best previous sum to use.

// Let's loop over the nums array now, keeping track of the running sum in a variable called cumulativeSum. We'll also keep track of our result in the variable shortestSubarrayLength. If the cumulativeSum meets our constraints, we consider it as a potential result. Otherwise, we'll loop over the top elements of the heap while the difference between cumulativeSum and the sum of the top element is ≥k. For each such element, we check if it is the minimum length subarray we've found till now. After checking an element in the heap, it can be discarded since all further sums in the loop will result in longer subarrays (and can never be the answer). Once we've exhausted all valid previous prefix sums, we can add the current sum and the index to the heap.

// After the loop completes, we can return cumulativeSum as the required shortest subarray with a sum of at least k.
class Solution {

    public int shortestSubarray(int[] nums, int k) {
    // Initialize a variable:
// n to store the length of the input array.
        int n = nums.length;

        // shortestSubarrayLength to store the minimum length of a valid subarray, setting it to the maximum possible integer value.
        int shortestSubarrayLength = Integer.MAX_VALUE;

        long cumulativeSum = 0;

        // Min-heap to store cumulative sum and its corresponding index
        // cumulativeSum to 0, which will maintain the running sum of elements.
// Initialize a min-heap prefixSumHeap to store pairs of cumulative sum and their corresponding indices, with pairs ordered by cumulative sum.
        PriorityQueue<Pair<Long, Integer>> prefixSumHeap = new PriorityQueue<>(
            (a, b) -> Long.compare(a.getKey(), b.getKey())
        );

       // Iterate through each index i from 0 to n-1:
// Add the current element to cumulativeSum.
        for (int i = 0; i < n; i++) {
            // Update cumulative sum
            cumulativeSum += nums[i];

// If cumulativeSum is greater than or equal to k:
// Update shortestSubarrayLength with the minimum of itself and i + 1.
            // If cumulative sum is already >= k, update shortest length
            if (cumulativeSum >= k) {
                shortestSubarrayLength = Math.min(
                    shortestSubarrayLength,
                    i + 1
                );
            }

// While the heap is not empty and the difference between the current cumulativeSum and heap's minimum cumulative sum is greater than or equal to k:
            // Remove subarrays from heap that can form a valid subarray
            while (
                !prefixSumHeap.isEmpty() &&
                cumulativeSum - prefixSumHeap.peek().getKey() >= k
            ) {
            // Remove the minimum element from the heap and update shortestSubarrayLength with the minimum of itself and (current index - removed element's index)
                // Update shortest subarray length
                shortestSubarrayLength = Math.min(
                    shortestSubarrayLength,
                    i - prefixSumHeap.poll().getValue()
                );
            }

// Add current cumulativeSum and index as a pair to the heap.
            // Add current cumulative sum and index to heap
            prefixSumHeap.offer(new Pair<>(cumulativeSum, i));
        }

        // Return -1 if no valid subarray found
        // Return -1 if shortestSubarrayLength remains unchanged at maximum integer value, otherwise return shortestSubarrayLength.
        return shortestSubarrayLength == Integer.MAX_VALUE
            ? -1
            : shortestSubarrayLength;
    }
}









