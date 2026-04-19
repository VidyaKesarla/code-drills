class Solution {
    public int maxProfit(int[] prices) {

        //initialise a new variable 
        int maxProfit = 0;

        //start from the second day(index 1)
        for(int i=1;i<prices.length;i++){
            if(prices[i] > prices[i-1]){
            maxProfit += prices[i] - prices[i-1];
            }
        }

        return maxProfit;
        
    }
    /*
 * Example: [1, 7, 2, 3, 6, 1]
 * Initial Profit = 0
 * * Day 2 (Price 7):
 * Is 7 > 1? Yes.
 * Profit = 0 + (7 - 1) = 6
 * * Day 3 (Price 2):
 * Is 2 > 7? No.
 * (Price dropped, we "sold" at 7 and are waiting to buy again)
 * Profit = 6
 * * Day 4 (Price 3):
 * Is 3 > 2? Yes.
 * Profit = 6 + (3 - 2) = 7
 * * Day 5 (Price 6):
 * Is 6 > 3? Yes.
 * Profit = 7 + (6 - 3) = 10
 * * Day 6 (Price 1):
 * Is 1 > 6? No.
 * Profit = 10
 * * Final Total Profit = 10
 */


    /*

    For the Greedy Algorithm we just discussed, the complexity is as efficient as it gets for this problem.Time Complexity: $O(n)$Why: We perform a single pass through the array of prices.The Math: We visit each price exactly once (from index $1$ to $n-1$). Inside the loop, we only perform a single comparison and one addition. Since these are constant-time operations, the total time scales linearly with the number of days ($n$).Space Complexity: $O(1)$Why: We are not using any additional data structures (like a list, stack, or hash map) that grow with the input size.The Math: We only store two variables: the loop index (i) and the running total of profit (maxProfit). These take up a fixed amount of memory regardless of whether the input has 10 prices or 10 million prices.


    */
}


//We can also do this problem using Dynamic p.  
/**
 * Intuition: Space-Optimized Dynamic Programming (State Machine)
 * * We track two states for each day:
 * 1. 'free': Max profit if we are NOT holding a stock (liquidated position).
 * 2. 'hold': Max profit if we ARE holding a stock (invested position).
 * * Since today's state only depends on yesterday's values, we can replace 
 * the O(n) DP table with two variables, reducing space to O(1).
 * * Time Complexity: O(n) - Single pass through prices.
 * Space Complexity: O(1) - Constant memory used regardless of input size.
 */
// class Solution {
//     public int maxProfit(int[] prices) {
//         int n = prices.length;
//         if (n == 0) return 0;

//         // Base cases for Day 0
//         int free = 0;           // No profit yet, no stock held
//         int hold = -prices[0];  // Bought first stock, balance is negative

//         for (int i = 1; i < n; i++) {
//             // Store current 'free' to use it in 'hold' calculation
//             // so we don't use the updated value in the same iteration.
//             int prevFree = free;

//             // Decision for 'free': Stay free OR sell the held stock today
//             free = Math.max(free, hold + prices[i]);

//             // Decision for 'hold': Keep holding OR buy a new stock using current profit
//             hold = Math.max(hold, prevFree - prices[i]);
//         }

//         return free; // Final profit is the cash in hand
//     }
// }

/*
/**
 * COMPARISON OF APPROACHES FOR "BUY AND SELL STOCK II"
 * ---------------------------------------------------
 * 1. RECURSIVE (Brute Force):
 * - Logic: For each day, branch into two choices: Buy/Sell or Skip.
 * - Time: O(2^n) - Exponential. Too slow for large inputs.
 * - Space: O(n) - Due to recursion stack.
 *
 * 2. DP (Top-Down with Memoization):
 * - Logic: Recursion + Cache (Memo) to store results of (day, holding_state).
 * - Time: O(n) - Each state is computed only once.
 * - Space: O(n) - For the memoization table and recursion stack.
 *
 * 3. DP (Bottom-Up Tabulation - O(n) Space):
 * - Logic: Build a 2D table dp[n][2] tracking 'Free' vs 'Hold' states day-by-day.
 * - Time: O(n) - Single pass.
 * - Space: O(n) - Stores the entire history of states in an array.
 *
 * 4. DP (Space-Optimized - O(1) Space):
 * - Logic: Same as Tabulation, but only keeps track of 'yesterday' using two variables.
 * - Time: O(n).
 * - Space: O(1) - Most memory-efficient DP variant.
 *
 * 5. GREEDY (Peak-Valley Addition):
 * - Logic: Simply sum all positive price differences (if prices[i] > prices[i-1]).
 * - Time: O(n).
 * - Space: O(1).
 * - Note: This is the fastest to implement for this specific version, but DP
 * is more flexible for variants with cooldowns or transaction fees.
 */

// class Solution {
//     public int maxProfit(int[] prices) {
//         if (prices == null || prices.length == 0) return 0;

//         // Using Space-Optimized DP approach
//         int free = 0;
//         int hold = -prices[0];

//         for (int i = 1; i < prices.length; i++) {
//             int prevFree = free;
//             free = Math.max(free, hold + prices[i]);
//             hold = Math.max(hold, prevFree - prices[i]);
//         }

//         return free;
//     }
// }
