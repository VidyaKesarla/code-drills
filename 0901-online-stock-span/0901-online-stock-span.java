class StockSpanner {
    Stack <int[]> stack = new Stack<>();
    public StockSpanner() {
        
    }
    
    public int next(int price) {
        int ans = 1;
        while(!stack.isEmpty() && stack.peek()[0] <= price){
            ans= ans + stack.pop()[1];
        }
        stack.push(new int[] {price, ans});
        return ans;
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */


 /*
=============================================================================
LEETCODE 901: ONLINE STOCK SPAN (MONOTONIC STACK APPROACH)
=============================================================================

1. INTUITION:
-------------
The problem asks for the "span" of a stock's price today—which is the number of consecutive days 
(including today) that the price was less than or equal to today's price.

Instead of looking backward through an entire array every time (which we can't easily do in an 
online data stream), we can use a Monotonic Decreasing Stack. 

Think of it like a skyline: if today's price is higher than yesterday's, today's price "swallows" 
or shadows yesterday's price and its corresponding span. By storing elements in the stack as 
pair bundles of [price, calculated_span], a new higher price can simply pop smaller prices off 
the stack and add their accumulated spans to its own.

-----------------------------------------------------------------------------

2. BRUTE FORCE VS. OPTIMAL APPROACH TRADEOFFS:
----------------------------------------------
* Brute Force Approach:
  - Strategy: Keep an internal list/array of all incoming prices. For every next(price) call, 
    iterate backward from the end of the list until you find a price strictly greater than today's.
  - Time Complexity: O(N) per call. For N queries, the total time is O(N^2). This results in a TLE 
    (Time Limit Exceeded) verdict on LeetCode.
  - Space Complexity: O(N) to store the history of all prices.

* Optimal Monotonic Stack Approach (This Code):
  - Strategy: Only keep a history of prices that haven't been "swallowed" by a larger price yet. 
    By maintaining pairs of [price, span], we can skip entire blocks of smaller elements instantly.
  - Time Complexity: O(1) amortized per call. Total O(N) for N queries.
  - Space Complexity: O(N) in the worst case (when prices strictly decrease).

-----------------------------------------------------------------------------

3. COMPLEXITY ANALYSIS:
-----------------------
* Time Complexity: O(1) Amortized per next() invocation.
  - Reason: Even though there is a `while` loop inside `next()`, each price is pushed onto the stack 
    exactly once and popped from the stack at most once across the entire lifecycle of the stream. 
    Therefore, N calls to next() will perform at most N pushes and N pops, yielding O(2N) total 
    operations, which averages out to O(1) per call.

* Space Complexity: O(N)
  - Reason: In the worst-case scenario (e.g., prices arriving in strictly decreasing order like 
    [100, 90, 80, 70]), no elements will ever be popped, causing the stack to grow linearly with the 
    number of calls N.

-----------------------------------------------------------------------------

4. STEP-BY-STEP DRY RUN:
========================
Let's track the execution with the following sequence of `next()` calls:
Queries: [100, 80, 60, 70, 60, 75, 85]

Stack State Representation: Lower index represents bottom of the stack. Stack format: [[price, span], ...]

• Step 1: next(100)
  - Stack is empty. Loop skips.
  - ans = 1
  - Push [100, 1]
  - Stack: [[100, 1]]
  - Returns: 1

• Step 2: next(80)
  - stack.peek()[0] is 100. (100 <= 80) is False. Loop skips.
  - ans = 1
  - Push [80, 1]
  - Stack: [[100, 1], [80, 1]]
  - Returns: 1

• Step 3: next(60)
  - stack.peek()[0] is 80. (80 <= 60) is False. Loop skips.
  - ans = 1
  - Push [60, 1]
  - Stack: [[100, 1], [80, 1], [60, 1]]
  - Returns: 1

• Step 4: next(70)
  - stack.peek()[0] is 60. (60 <= 70) is True!
    -> pop [60, 1] from stack. ans = 1 + 1 = 2.
  - Next peek is 80. (80 <= 70) is False. Loop terminates.
  - Push [70, 2]
  - Stack: [[100, 1], [80, 1], [70, 2]]
  - Returns: 2

• Step 5: next(60)
  - stack.peek()[0] is 70. (70 <= 60) is False. Loop skips.
  - ans = 1
  - Push [60, 1]
  - Stack: [[100, 1], [80, 1], [70, 2], [60, 1]]
  - Returns: 1

• Step 6: next(75)
  - stack.peek()[0] is 60. (60 <= 75) is True!
    -> pop [60, 1]. ans = 1 + 1 = 2.
  - Next peek is 70. (70 <= 75) is True!
    -> pop [70, 2]. ans = 2 + 2 = 4.
  - Next peek is 80. (80 <= 75) is False. Loop terminates.
  - Push [75, 4]
  - Stack: [[100, 1], [80, 1], [75, 4]]
  - Returns: 4

• Step 7: next(85)
  - stack.peek()[0] is 75. (75 <= 85) is True!
    -> pop [75, 4]. ans = 1 + 4 = 5.
  - Next peek is 80. (80 <= 85) is True!
    -> pop [80, 1]. ans = 5 + 1 = 6.
  - Next peek is 100. (100 <= 85) is False. Loop terminates.
  - Push [85, 6]
  - Stack: [[100, 1], [85, 6]]
  - Returns: 6

Final Output Sequence Match: [1, 1, 1, 2, 1, 4, 6] -> Perfect match with LeetCode expectations!
=============================================================================
*/

