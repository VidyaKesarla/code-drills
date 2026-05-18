class Solution {
    // The brute force approach is really simple. We consider every possible subarray within the given array and count the number of zeros and ones in each subarray. Then, we find out the maximum size subarray with equal no. of zeros and ones out of them. tc: O(n^2)
    public int findMaxLength(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int maxLen = 0;
        int count = 0;
        map.put(0,-1);
        for(int i=0;i<nums.length;i++){
            count = count + (nums[i] == 1 ? 1 : -1);
            if(map.containsKey(count)){
                maxLen = Math.max(maxLen, i - map.get(count));
            } else {
                map.put(count, i);
            }
        }
        return maxLen;
    }
}

// time complexity : O(n). The entire array is traversed only once.

// Space complexity : O(n). Maximum size of the HashMap map will be n, if all the elements are either 1 or 0.