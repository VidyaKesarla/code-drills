class Solution {
    public int subarraySum(int[] nums, int k) {
        // count stores the number of valid subarrays found
        int count = 0;
        // currentSum tracks the cumulative sum as we iterate
        int currentSum = 0;
        
        // HashMap to store: <PrefixSum, Frequency of that sum>
        Map<Integer, Integer> prefixSumMap = new HashMap<>();
        
        prefixSumMap.put(0, 1);
        
        for (int num : nums) {
            currentSum += num;
            
            if (prefixSumMap.containsKey(currentSum - k)) {
                count += prefixSumMap.get(currentSum - k);
            }
            

            prefixSumMap.put(currentSum, prefixSumMap.getOrDefault(currentSum, 0) + 1);
        }
        
        return count;
    }
}