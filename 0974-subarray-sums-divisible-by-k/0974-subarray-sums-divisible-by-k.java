class Solution {
    public int subarraysDivByK(int[] nums, int k) {
        HashMap <Integer, Integer> remainderCount = new HashMap<>();

        remainderCount.put(0,1);
        int prefixSum = 0;
        int result = 0;

        for(int num: nums){
            prefixSum = prefixSum + num;
            //to handle a negative integer
            int r = ((prefixSum%k)+k)%k;
            result = result + remainderCount.getOrDefault(r,0);
            remainderCount.put(r, remainderCount.getOrDefault(r,0) +1);
        }

        return result;

    }
}