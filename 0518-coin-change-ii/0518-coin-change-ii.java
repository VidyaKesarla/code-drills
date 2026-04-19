class Solution {
    public int change(int amount, int[] coins) {

        /*
         * APPROACH: Dynamic Programming (Bottom-Up)
         *
         * dp[i] = number of combinations to make amount i
         *
         * BASE CASE:
         * dp[0] = 1 → one way to make amount 0: use no coins
         *
         * TRANSITION:
         * dp[i] += dp[i - coin]
         * → If I pick this coin, remaining amount = i - coin
         * → Ways to make i = ways to make (i - coin)
         * → += because multiple coins contribute to same dp[i]
         *
         * LOOP ORDER (critical!):
         * Outer = coins, Inner = amounts → counts COMBINATIONS
         * (prevents counting {1,2} and {2,1} separately)
         *
         * ──────────────────────────────────────────
         * DRY RUN: coins = [1, 2, 5], amount = 5
         * ──────────────────────────────────────────
         *
         * Initial:
         * dp = [1, 0, 0, 0, 0, 0]
         *       0  1  2  3  4  5
         *
         * ── coin = 1 ──
         * i=1: dp[1] += dp[1-1] = dp[0] = 1  → dp[1] = 1  {1}
         * i=2: dp[2] += dp[2-1] = dp[1] = 1  → dp[2] = 1  {1,1}
         * i=3: dp[3] += dp[3-1] = dp[2] = 1  → dp[3] = 1  {1,1,1}
         * i=4: dp[4] += dp[4-1] = dp[3] = 1  → dp[4] = 1  {1,1,1,1}
         * i=5: dp[5] += dp[5-1] = dp[4] = 1  → dp[5] = 1  {1,1,1,1,1}
         *
         * dp = [1, 1, 1, 1, 1, 1]
         *
         * ── coin = 2 ──
         * i=2: dp[2] += dp[2-2] = dp[0] = 1  → dp[2] = 2  {2}
         * i=3: dp[3] += dp[3-2] = dp[1] = 1  → dp[3] = 2  {2,1}
         * i=4: dp[4] += dp[4-2] = dp[2] = 2  → dp[4] = 3  {2,2}
         * i=5: dp[5] += dp[5-2] = dp[3] = 2  → dp[5] = 3  {2,2,1}
         *
         * dp = [1, 1, 2, 2, 3, 3]
         *
         * ── coin = 5 ──
         * i=5: dp[5] += dp[5-5] = dp[0] = 1  → dp[5] = 4  {5}
         *
         * dp = [1, 1, 2, 2, 3, 4]
         *
         * ── ANSWER: dp[5] = 4 ──
         * The 4 combinations:
         * {5}
         * {2, 2, 1}
         * {2, 1, 1, 1}
         * {1, 1, 1, 1, 1}
         *
         * TIME:  O(amount × n)  where n = number of coins
         * SPACE: O(amount)
         */

        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }

        return dp[amount];
    }
}

 /*
         * WHY "BOTTOM UP"?
         *
         * "Bottom" = smallest subproblem  → dp[0]
         * "Top"    = final answer         → dp[amount]
         *
         * We START from the bottom (dp[0], which we know for sure)
         * and BUILD UP to the top (dp[amount], which we want).
         *
         * dp[0] → dp[1] → dp[2] → dp[3] → dp[4] → dp[5]
         *  ↑                                          ↑
         * bottom                                     top
         * (start here)                         (answer here)
         *
         * The OPPOSITE is "Top Down" (recursion + memoization):
         * → Starts at change(5), breaks it DOWN to change(0)
         *
         * BOTTOM UP        vs        TOP DOWN
         * ─────────────────────────────────────
         * dp[0] → dp[5]        change(5) → change(0)
         * Iterative (loops)    Recursive
         * Starts from base     Starts from answer
         * Builds upward        Breaks downward
         *
         * Bottom Up is preferred here because:
         * → No recursion stack overhead
         * → No risk of stack overflow
         * → Faster in practice
         */