class Solution {
    public int maxProfit(int[] prices) {
        int minimumPrice = prices[0] ;
        int maxProfit = 0;
        int size = prices.length;
        for(int i=0;i<size;i++){
            int cost = prices[i] - minimumPrice;
            maxProfit = Math.max(cost, maxProfit);
            minimumPrice = Math.min(minimumPrice, prices[i]);
        }
        return maxProfit;  
    }
}