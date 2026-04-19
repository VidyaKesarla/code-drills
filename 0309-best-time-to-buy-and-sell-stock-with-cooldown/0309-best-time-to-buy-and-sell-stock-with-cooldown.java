class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        // Initialize states
        // hold: We start with -infinity because we haven't bought anything yet
        int hold = Integer.MIN_VALUE;
        // sold: Max profit if we just sold a stock today
        int sold = 0;
        // reset: Max profit if we are in a cooldown or just resting
        int reset = 0;

        for (int price : prices) {
            int preHold = hold;
            int preSold = sold;
            int preReset = reset;

            // 1. To be in 'hold' state: 
            // Stay in 'hold' OR buy today (must have been in 'reset' state yesterday)
            hold = Math.max(preHold, preReset - price);

            // 2. To be in 'sold' state:
            // Must have held a stock yesterday and sold it at today's price
            sold = preHold + price;

            // 3. To be in 'reset' state:
            // Stay in 'reset' OR just came out of a 'sold' state yesterday
            reset = Math.max(preReset, preSold);
        }

        // The maximum profit will be either from having just sold or resting
        return Math.max(sold, reset);
    }
}

/*
 * Example: prices = [1, 2, 3, 0, 2]
 * * Initial States:
 * hold  = -∞  (We haven't bought anything)
 * sold  = 0   (We haven't sold anything)
 * reset = 0   (Starting point)
 * * --- Day 1 (Price: 1) ---
 * hold  = max(-∞, 0 - 1) = -1    // Bought stock at 1
 * sold  = -∞ + 1 = -∞            // Can't sell if we didn't hold before
 * reset = max(0, 0) = 0          // Just resting
 * Result: hold: -1, sold: -∞, reset: 0
 * * --- Day 2 (Price: 2) ---
 * hold  = max(-1, 0 - 2) = -1    // Keep holding the one bought at 1
 * sold  = -1 + 2 = 1             // Sold today! Profit = 1
 * reset = max(0, -∞) = 0         // Stayed resting
 * Result: hold: -1, sold: 1, reset: 0
 * * --- Day 3 (Price: 3) ---
 * hold  = max(-1, 0 - 3) = -1    // Still holding from day 1
 * sold  = -1 + 3 = 2             // Sold today! Profit = 2
 * reset = max(0, 1) = 1          // Cooldown day! (Came from Day 2's 'sold')
 * Result: hold: -1, sold: 2, reset: 1
 * * --- Day 4 (Price: 0) ---
 * hold  = max(-1, 1 - 0) = 1     // Bought at 0 using profit from reset (1)
 * sold  = -1 + 0 = -1            // Sold today (not profitable)
 * reset = max(1, 2) = 2          // Cooldown day! (Came from Day 3's 'sold')
 * Result: hold: 1, sold: -1, reset: 2
 * * --- Day 5 (Price: 2) ---
 * hold  = max(1, 2 - 2) = 1      // Keep holding the one bought at 0
 * sold  = 1 + 2 = 3              // Sold today! 2 (price) + 1 (prev profit) = 3
 * reset = max(2, -1) = 2         // Stayed resting
 * Result: hold: 1, sold: 3, reset: 2
 * * Final Answer: max(sold, reset) = 3
 */

 /*
 1. Why return max(sold, reset)?We return the maximum of these two because they represent the only two ways to not be holding a stock at the very end.sold: You sold the stock on the very last day. You have your cash in hand.reset: You were already in a "waiting" or "cooldown" state on the last day. You also have your cash in hand.Why not hold?If you are still in the hold state on the last day, it means you spent money to buy a stock but never sold it. In the world of profit, holding a stock at the end is always worse (or at best, equal) to having sold it earlier or never buying that last one at all.2. Does reset = 2 mean we rest for two days?No. The number 2 in this context refers to money (profit), not a count of days.In the example trace, reset = 2 means: "The most money I can have in my pocket while being in the 'Resting' state today is $2$."The state itself only tracks what you are doing today.If you stay in the reset state for multiple days, the value simply carries over because reset = max(previous_reset, previous_sold)



 */


/*
 * Day 0: Initial State
 * Wallet: hold = -∞, sold = 0, rest = 0
 * * Day 1 (Price 1):
 * - Buy? Pocket -1. (hold = -1)
 * - rest = 0
 *
 * Day 2 (Price 2):
 * - Hold? Still -1.
 * - Sell? -1 + 2 = +1 profit. (sold = 1)
 * - rest = 0
 *
 * Day 3 (Price 3):
 * - Hold? Still -1.
 * - Sell? -1 + 3 = +2 profit. (sold = 2)
 * - rest? Yesterday I sold for 1, so today I rest with 1. (rest = 1)
 *
 * Day 4 (Price 0):
 * - Buy? I have 1 from resting, minus price 0 = +1. (hold = 1)
 * - rest? Yesterday I sold for 2, so today I rest with 2. (rest = 2)
 * * Day 5 (Price 2):
 * - Hold? Still 1.
 * - Sell? 1 (wallet) + 2 (price) = 3. (sold = 3)
 * - rest? Still 2.
 *
 * Max of 3 and 2 is 3. Final Profit = 3.
 */
/*

Time Complexity: $O(n)$Single Pass: We iterate through the prices array exactly once.Constant Time Operations: Inside the loop, we perform a fixed number of comparisons (Math.max) and additions. These operations take $O(1)$ time regardless of the size of the input.Total: If $n$ is the number of days, the total time is $O(n)$.Space Complexity: $O(1)$Variable Storage: Instead of using a 1D or 2D Dynamic Programming table (which would take $O(n)$ space), we only maintain a few primitive integer variables (hold, sold, reset, and their "previous" counterparts).No Recursion: Since the solution is iterative, there is no overhead from a recursion stack.Total: The memory usage remains constant regardless of how many days of stock prices are provided. */


/*
class Solution {
    public int maxProfit(int[] prices) {
        return calculate(prices, 0, false);
    }

    private int calculate(int[] prices, int day, boolean holding) {
        // Base case: No more days left to trade
        if (day >= prices.length) {
            return 0;
        }

        if (holding) {
            // Option 1: Sell today (must skip next day, so move to day + 2)
            int sell = prices[day] + calculate(prices, day + 2, false);
            // Option 2: Don't sell (just move to next day)
            int skip = calculate(prices, day + 1, true);
            
            return Math.max(sell, skip);
        } else {
            // Option 1: Buy today (move to next day as holding)
            int buy = -prices[day] + calculate(prices, day + 1, true);
            // Option 2: Don't buy (just move to next day)
            int skip = calculate(prices, day + 1, false);
            
            return Math.max(buy, skip);
        }
    }
}
3. Complexity AnalysisTime Complexity: $O(2^n)$This is exponential. At each step, you are branching into two decisions (Buy/Skip or Sell/Skip). For an array of size $n$, this creates a recursion tree with $2^n$ nodes. If the input prices.length is 30, you're looking at over 1 billion operations!Space Complexity: $O(n)$Even though we aren't storing data in a table, the recursion stack will go as deep as the number of days $n$ in the worst case.

 */