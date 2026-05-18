// Brute force:  simplest solution for this problem is to consider every triplet out of the given nums array and check their product and find out the max product out of them  tc: O(n^3) sc: O(1)  Optimal approach is : 1. Sort the array 
// 1. Find the product of the last three numbers in the array. Find the product of the first two numbers(negative) and then multiply this with the largest number which is at the end of the array.
// 2. Compare both these products and then find the largest one. Return the same 
    class Solution { 
public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int candidate1 = nums[0] * nums[1] * nums[n-1];
        int candidate2 = nums[n-1] * nums[n-2] * nums[n-3];
        return Math.max(candidate1, candidate2);
    }
}


// Tc: 	O(n logn)
// Sc: O(logn)
