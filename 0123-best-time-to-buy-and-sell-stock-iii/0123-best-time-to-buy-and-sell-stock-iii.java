class Solution {
    
    public int maxProfit(int[] prices) {

        int firstBuy = Integer.MIN_VALUE;
        int firstSell = 0;
        int secondBuy = Integer.MIN_VALUE;
        int secondSell = 0;

        for(int price: prices){
            firstBuy = Math.max(firstBuy, -price);

            firstSell = Math.max(firstSell, firstBuy + price);

            secondBuy = Math.max(secondBuy, firstSell - price);

            secondSell = Math.max(secondSell, secondBuy + price);

        }
        return secondSell;
        
    }
}

/**
 * Dry Run Trace: prices = [3, 1, 5, 8, 2, 4]
 * * Initial: firstBuy = -∞, firstSell = 0, secondBuy = -∞, secondSell = 0
 * * Day 1 (Price 3):
 * firstBuy:  max(-∞, -3)        = -3  (Spent 3)
 * firstSell: max(0, -3 + 3)     =  0
 * secondBuy: max(-∞, 0 - 3)     = -3
 * secondSell:max(0, -3 + 3)     =  0
 * * Day 2 (Price 1):
 * firstBuy:  max(-3, -1)        = -1  (Found cheaper buy: 1)
 * firstSell: max(0, -1 + 1)     =  0
 * secondBuy: max(-3, 0 - 1)     = -1
 * secondSell:max(0, -1 + 1)     =  0
 * * Day 3 (Price 5):
 * firstBuy:  max(-1, -5)        = -1
 * firstSell: max(0, -1 + 5)     =  4  (Profit of 4)
 * secondBuy: max(-1, 4 - 5)     = -1
 * secondSell:max(0, -1 + 5)     =  4
 * * Day 4 (Price 8):
 * firstBuy:  max(-1, -8)        = -1
 * firstSell: max(4, -1 + 8)     =  7  (Best 1st profit: 7)
 * secondBuy: max(-1, 7 - 8)     = -1
 * secondSell:max(4, -1 + 8)     =  7
 * * Day 5 (Price 2):
 * firstBuy:  max(-1, -2)        = -1
 * firstSell: max(7, -1 + 2)     =  7
 * secondBuy: max(-1, 7 - 2)     =  5  (Reinvested 7 profit; bought at 2; 5 left in pocket)
 * secondSell:max(7, 5 + 2)      =  7
 * * Day 6 (Price 4):
 * firstBuy:  max(-1, -4)        = -1
 * firstSell: max(7, -1 + 4)     =  7
 * secondBuy: max(5, 7 - 4)      =  5
 * secondSell:max(7, 5 + 4)      =  9  (Final Profit: 9)
 */


/**
 * BRUTE FORCE APPROACH:
 * Split the array at every possible index 'i'.
 * Profit = (Max profit with 1 transaction in prices[0...i]) 
 * + (Max profit with 1 transaction in prices[i...n-1])
 *
 * Trace for: [3, 1, 5, 8, 2, 4]
 * -----------------------------
 * Split at index 0: [3] | [1, 5, 8, 2, 4] -> 0 + 7 = 7
 * Split at index 1: [3, 1] | [5, 8, 2, 4] -> 0 + 3 = 3
 * Split at index 2: [3, 1, 5] | [8, 2, 4] -> 4 + 2 = 6
 * Split at index 3: [3, 1, 5, 8] | [2, 4] -> 7 + 2 = 9  <-- MAX
 * Split at index 4: [3, 1, 5, 8, 2] | [4] -> 7 + 0 = 7
 */
 /*
public int maxProfitBruteForce(int[] prices) {
    int n = prices.length;
    if (n < 2) return 0;
    int maxTotalProfit = 0;

    for (int i = 0; i < n; i++) {
        int leftMax = 0, leftMin = prices[0];
        for (int j = 0; j <= i; j++) {
            leftMin = Math.min(leftMin, prices[j]);
            leftMax = Math.max(leftMax, prices[j] - leftMin);
        }

        int rightMax = 0, rightMin = (i + 1 < n) ? prices[i + 1] : 0;
        for (int j = i + 1; j < n; j++) {
            rightMin = Math.min(rightMin, prices[j]);
            rightMax = Math.max(rightMax, prices[j] - rightMin);
        }
        maxTotalProfit = Math.max(maxTotalProfit, leftMax + rightMax);
    }
    return maxTotalProfit;
}

*/

/**
 * APPROACH COMPARISON & TRADE-OFFS:
 * * | Feature         | Brute Force            | Optimal DP (State-Based) |
 * | :-------------- | :--------------------- | :----------------------- |
 * | Time Complexity | O(n^2)                 | O(n)                     |
 * | Space Complexity| O(1)                   | O(1)                     |
 * | Scalability     | Poor (TLE on LeetCode) | Excellent                |
 * | Intuition       | High (Split & Solve)   | Medium (Wallet State)    |
 * * TRADE-OFFS:
 * 1. Performance vs. Simplicity: The Brute Force approach is easier to 
 * conceptualize but fails on large datasets. The DP approach requires 
 * tracking "hidden" states (your bank balance) but is highly efficient.
 * * 2. Generalization: Brute Force is hard to adapt for more transactions. 
 * The State-Based logic used here is the foundation for solving "at most 
 * K transactions" by simply expanding the number of states.
 */