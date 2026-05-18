class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        

        /* Given two arrays they are sorted in increasing(non-decreasing) order 
         two integers -> m and n -> will represent the number of elements in these            twoarrays        
         nums1 and nums2 
         the final sorted array you have to store it in nums1 not create a new array altogether.
        to accomodate both these arrays, obviously nums1 should have length of m + n.
        where m denotes the elements that should be merged. 
        last n elements are set to 0
        nums2 has a length of n.
         */

         // Pointers for nums1, nums2, and the placement position
        int p1 = m - 1;
        int p2 = n - 1;
        int i = m + n - 1;

        // Iterate backwards to avoid overwriting elements in nums1
        while (p2 >= 0) {
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[i] = nums1[p1];
                p1--;
            } else {
                nums1[i] = nums2[p2];
                p2--;
            }
            i--;
        }

        // // Return the modified nums1 array
        // return nums1;
         


    }
}
