class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int low = 0;
        int high = m;

        while (low <= high) {
            int partitionX = (low + high) / 2;
            int partitionY = (m + n + 1) / 2 - partitionX;

            // If partitionX is 0, nothing is on the left side. Use -INF.
            // If partitionX is m, nothing is on the right side. Use +INF.
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == m) ? Integer.MAX_VALUE : nums1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == n) ? Integer.MAX_VALUE : nums2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                // Correct partition found
                if ((m + n) % 2 == 0) {
                    return ((double)Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2;
                } else {
                    return (double)Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) {
                // We are too far to the right in nums1, move left
                high = partitionX - 1;
            } else {
                // We are too far to the left in nums1, move right
                low = partitionX + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted.");
    }
}
/*
1. Time Complexity: $O(\log(\min(m, n)))$Binary Search: We are performing a standard binary search, which usually has a complexity of $O(\log N)$.Searching the Shorter Array: In the code, we check if (nums1.length > nums2.length) and swap them. This ensures that the binary search is always performed on the array with the smaller number of elements.Why $\min(m, n)$? If nums1 has 1,000,000 elements and nums2 has 10 elements, we only perform binary search on the 10 elements. The number of iterations will be $\log_2(10) \approx 4$, rather than $\log_2(1,000,000) \approx 20$.Constant Time Operations: Inside the while loop, all operations (calculating partitions, comparing maxLeft and minRight) are $O(1)$ arithmetic operations.2. Space Complexity: $O(1)$No Extra Storage: Unlike the "Merge" approach (where you might create a new array of size $m+n$), this approach only uses a few integer variables (low, high, partitionX, partitionY, etc.).In-Place Logic: We are only referencing the existing arrays by their indices.Recursion: The single recursive call at the beginning (to swap arrays) only happens once and does not scale with the input size, so it does not add to the space complexity in terms of the stack.

*/