class Solution {
    public int maxProfit(int k, int[] prices) {
        //find the length of the array 
        int n = prices.length;
        if(n<=1 || k<=0){
        return 0;
        }

        //if k is large we can treat this as an optimization.
        //we will just maximise profit and return it. we can treat this as infinite transactions.

        if( k >= n/2){
            int maxProfit = 0;
            for(int i=1;i<n;i++){
                if(prices[i] >= prices[i-1]){
                    maxProfit += prices[i] - prices[i-1];
                }
            }
            return maxProfit;
        }

        int [] buy = new int[k+1];
        int [] sell = new int[k+1];

        //in the buy array i am iniliazing everything with -1

        for(int i=0;i<=k;i++){
            buy[i] = Integer.MIN_VALUE;
        }

        for(int price: prices){
            for(int j=1;j<=k;j++){
                buy[j] = Math.max(buy[j], sell[j-1] - price);
                sell[j] = Math.max(sell[j], buy[j] + price);
            }

        }

        return sell[k];
        
    }
}

/*


/*
DRY RUN: prices = [3, 2, 6, 5, 0, 3], k = 2
-------------------------------------------------------------------------
Initial: buy = [-∞, -∞], sell = [0, 0] 

Day 1 (Price 3):
  j=1: buy[1] = max(-∞, 0 - 3) = -3 | sell[1] = max(0, -3 + 3) = 0
  j=2: buy[2] = max(-∞, 0 - 3) = -3 | sell[2] = max(0, -3 + 3) = 0
  Result: buy: [-3, -3], sell: [0, 0]

Day 2 (Price 2):
  j=1: buy[1] = max(-3, 0 - 2) = -2 | sell[1] = max(0, -2 + 2) = 0
  j=2: buy[2] = max(-3, 0 - 2) = -2 | sell[2] = max(0, -2 + 2) = 0
  Result: buy: [-2, -2], sell: [0, 0] (Better buying price found)

Day 3 (Price 6):
  j=1: buy[1] = max(-2, 0 - 6) = -2 | sell[1] = max(0, -2 + 6) = 4
  j=2: buy[2] = max(-2, 4 - 6) = -2 | sell[2] = max(0, -2 + 6) = 4
  Result: buy: [-2, -2], sell: [4, 4] (First profit realized)

Day 4 (Price 5):
  j=1: buy[1] = max(-2, 0 - 5) = -2 | sell[1] = max(4, -2 + 5) = 4
  j=2: buy[2] = max(-2, 4 - 5) = -1 | sell[2] = max(4, -1 + 5) = 4
  Result: buy: [-2, -1], sell: [4, 4] (buy[2] improves using profit from sell[1])

Day 5 (Price 0):
  j=1: buy[1] = max(-2, 0 - 0) =  0 | sell[1] = max(4, 0 + 0) = 4
  j=2: buy[2] = max(-1, 4 - 0) =  4 | sell[2] = max(4, 4 + 0) = 4
  Result: buy: [0, 4], sell: [4, 4] (Huge drop; buy[2] balance is now 4)

Day 6 (Price 3):
  j=1: buy[1] = max(0, 0 - 3)  = 0 | sell[1] = max(4, 0 + 3) = 4 (actually stays 4)
  j=2: buy[2] = max(4, 4 - 3)  = 4 | sell[2] = max(4, 4 + 3) = 7
  Result: buy: [0, 4], sell: [4, 7] (Second profit realized)

Final Answer: sell[2] = 7
-------------------------------------------------------------------------
*/