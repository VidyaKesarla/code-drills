/*
Naive recursion is inefficient for this problem because it creates a massive decision tree with a depth of amount. This leads to redundant calculations and risks a StackOverflowError for large inputs. Dynamic Programming (Bottom-Up) is the optimal approach to ensure each sub-problem is solved only once
recursion:
Main Call: coinChange(3)
│
├── Branch 1: Try Coin [1] -> Call coinChange(2)
│   │
│   ├── Sub-Branch 1.1: Try Coin [1] -> Call coinChange(1)
│   │   │
│   │   ├── Sub-Sub-Branch 1.1.1: Try Coin [1] -> Call coinChange(0)
│   │   │   └── RETURN 0 (Base Case)
│   │   │   
│   │   ├── Sub-Sub-Branch 1.1.2: Try Coin [2] -> Call coinChange(-1)
│   │   │   └── RETURN -1 (Invalid)
│   │   │
│   │   └── RESULT for coinChange(1): (0 + 1) = 1. RETURN 1.
│   │
│   ├── Sub-Branch 1.2: Try Coin [2] -> Call coinChange(0)
│   │   └── RETURN 0 (Base Case)
│   │
│   └── RESULT for coinChange(2): min((1+1), (0+1)) = 1. RETURN 1.
│
├── Branch 2: Try Coin [2] -> Call coinChange(1)
│   │
│   ├── Sub-Branch 2.1: Try Coin [1] -> Call coinChange(0)
│   │   └── RETURN 0 (Base Case)
│   │
│   ├── Sub-Branch 2.2: Try Coin [2] -> Call coinChange(-1)
│   │   └── RETURN -1 (Invalid)
│   │
│   └── RESULT for coinChange(1): (0+1) = 1. RETURN 1.
│
└── FINAL RESULT for coinChange(3): min(Branch 1 result + 1, Branch 2 result + 1)
                                  min(1 + 1, 1 + 1) = 2.
*/

/*
 * COIN CHANGE — LEETCODE #322
 * Approach: Bottom-Up Dynamic Programming (2D)
 *
 * INPUT:  coins = [1, 2, 5], amount = 11
 * OUTPUT: 3  (11 = 5 + 5 + 1)
 *
 * ============================================================
 * DP TABLE DEFINITION
 * ============================================================
 * dp[i][j] = minimum coins needed to make amount j
 *            using only the first i coin types
 *
 * Dimensions: (numCoins+1) rows × (amount+1) cols
 *             = 4 rows × 12 cols
 *
 * ============================================================
 * INITIALIZATION
 * ============================================================
 * All cells = INF (impossible by default)
 * dp[0][0]  = 0   (base case: 0 coins needed to make amount 0)
 *
 *        j=0  j=1  j=2  j=3  j=4  j=5  j=6  j=7  j=8  j=9  j=10  j=11
 * i=0  [  0,  INF, INF, INF, INF, INF, INF, INF, INF, INF,  INF,  INF ]
 * i=1  [ INF, INF, INF, INF, INF, INF, INF, INF, INF, INF,  INF,  INF ]
 * i=2  [ INF, INF, INF, INF, INF, INF, INF, INF, INF, INF,  INF,  INF ]
 * i=3  [ INF, INF, INF, INF, INF, INF, INF, INF, INF, INF,  INF,  INF ]
 *
 * ============================================================
 * TWO CHOICES PER CELL
 * ============================================================
 * Option 1 — Skip coin:  dp[i][j] = dp[i-1][j]
 * Option 2 — Use coin:   dp[i][j] = dp[i][j - coin] + 1
 *                        (only if j >= coin, else index goes negative)
 * Final:                 dp[i][j] = min(Option1, Option2)
 *
 * Reading from dp[i][...] (same row) allows the same coin
 * to be reused unlimited times (unbounded knapsack).
 *
 * ============================================================
 * ITERATION — i=1, coin = coins[0] = 1, checking j >= 1
 * ============================================================
 * j=0:  0 >= 1? NO  → inherit dp[0][0] = 0
 * j=1:  1 >= 1? YES → min(dp[0][1]=INF, dp[1][0]+1) = min(INF, 1) = 1
 * j=2:  2 >= 1? YES → min(dp[0][2]=INF, dp[1][1]+1) = min(INF, 2) = 2
 * j=3:  3 >= 1? YES → min(dp[0][3]=INF, dp[1][2]+1) = min(INF, 3) = 3
 * j=4:  4 >= 1? YES → min(dp[0][4]=INF, dp[1][3]+1) = min(INF, 4) = 4
 * j=5:  5 >= 1? YES → min(dp[0][5]=INF, dp[1][4]+1) = min(INF, 5) = 5
 * j=6:  6 >= 1? YES → min(dp[0][6]=INF, dp[1][5]+1) = min(INF, 6) = 6
 * j=7:  7 >= 1? YES → min(dp[0][7]=INF, dp[1][6]+1) = min(INF, 7) = 7
 * j=8:  8 >= 1? YES → min(dp[0][8]=INF, dp[1][7]+1) = min(INF, 8) = 8
 * j=9:  9 >= 1? YES → min(dp[0][9]=INF, dp[1][8]+1) = min(INF, 9) = 9
 * j=10: 10 >= 1? YES → min(dp[0][10]=INF, dp[1][9]+1) = min(INF, 10) = 10
 * j=11: 11 >= 1? YES → min(dp[0][11]=INF, dp[1][10]+1) = min(INF, 11) = 11
 *
 * coin=1 fits every amount → guard never blocks → each cell costs j ones
 *
 * After i=1:
 *        j=0  j=1  j=2  j=3  j=4  j=5  j=6  j=7  j=8  j=9  j=10  j=11
 * i=1  [  0,   1,   2,   3,   4,   5,   6,   7,   8,   9,   10,   11  ]
 *
 * ============================================================
 * ITERATION — i=2, coin = coins[1] = 2, checking j >= 2
 * ============================================================
 * j=0:  0 >= 2? NO  → inherit dp[1][0] = 0
 * j=1:  1 >= 2? NO  → inherit dp[1][1] = 1         (2-coin too big for amount 1)
 * j=2:  2 >= 2? YES → min(dp[1][2]=2, dp[2][0]+1) = min(2, 1) = 1   (one 2-coin)
 * j=3:  3 >= 2? YES → min(dp[1][3]=3, dp[2][1]+1) = min(3, 2) = 2   (2+1)
 * j=4:  4 >= 2? YES → min(dp[1][4]=4, dp[2][2]+1) = min(4, 2) = 2   (2+2)
 * j=5:  5 >= 2? YES → min(dp[1][5]=5, dp[2][3]+1) = min(5, 3) = 3   (2+2+1)
 * j=6:  6 >= 2? YES → min(dp[1][6]=6, dp[2][4]+1) = min(6, 3) = 3   (2+2+2)
 * j=7:  7 >= 2? YES → min(dp[1][7]=7, dp[2][5]+1) = min(7, 4) = 4   (2+2+2+1)
 * j=8:  8 >= 2? YES → min(dp[1][8]=8, dp[2][6]+1) = min(8, 4) = 4   (2+2+2+2)
 * j=9:  9 >= 2? YES → min(dp[1][9]=9, dp[2][7]+1) = min(9, 5) = 5   (2+2+2+2+1)
 * j=10: 10 >= 2? YES → min(dp[1][10]=10, dp[2][8]+1) = min(10, 5) = 5 (2+2+2+2+2)
 * j=11: 11 >= 2? YES → min(dp[1][11]=11, dp[2][9]+1) = min(11, 6) = 6 (2+2+2+2+2+1)
 *
 * j=0,1 blocked — 2-coin can't fit. From j=2 onward, costs roughly halved.
 *
 * After i=2:
 *        j=0  j=1  j=2  j=3  j=4  j=5  j=6  j=7  j=8  j=9  j=10  j=11
 * i=2  [  0,   1,   1,   2,   2,   3,   3,   4,   4,   5,    5,    6  ]
 *
 * ============================================================
 * ITERATION — i=3, coin = coins[2] = 5, checking j >= 5
 * ============================================================
 * j=0:  0 >= 5? NO  → inherit dp[2][0] = 0
 * j=1:  1 >= 5? NO  → inherit dp[2][1] = 1         (5-coin too big)
 * j=2:  2 >= 5? NO  → inherit dp[2][2] = 1         (5-coin too big)
 * j=3:  3 >= 5? NO  → inherit dp[2][3] = 2         (5-coin too big)
 * j=4:  4 >= 5? NO  → inherit dp[2][4] = 2         (5-coin too big)
 * j=5:  5 >= 5? YES → min(dp[2][5]=3, dp[3][0]+1) = min(3, 1) = 1   (one 5-coin)
 * j=6:  6 >= 5? YES → min(dp[2][6]=3, dp[3][1]+1) = min(3, 2) = 2   (5+1)
 * j=7:  7 >= 5? YES → min(dp[2][7]=4, dp[3][2]+1) = min(4, 2) = 2   (5+2)
 * j=8:  8 >= 5? YES → min(dp[2][8]=4, dp[3][3]+1) = min(4, 3) = 3   (5+2+1)
 * j=9:  9 >= 5? YES → min(dp[2][9]=5, dp[3][4]+1) = min(5, 3) = 3   (5+2+2)
 * j=10: 10 >= 5? YES → min(dp[2][10]=5, dp[3][5]+1) = min(5, 2) = 2  (5+5)
 * j=11: 11 >= 5? YES → min(dp[2][11]=6, dp[3][6]+1) = min(6, 3) = 3  (5+5+1) ✅
 *
 * j=0 to j=4 blocked — 5-coin can't fit. From j=5 onward, costs drop drastically.
 *
 * After i=3 (FINAL TABLE):
 *        j=0  j=1  j=2  j=3  j=4  j=5  j=6  j=7  j=8  j=9  j=10  j=11
 * i=3  [  0,   1,   1,   2,   2,   1,   2,   2,   3,   3,    2,    3  ]
 *
 * ============================================================
 * RESULT
 * ============================================================
 * dp[3][11] = 3 < INF → return 3 ✅
 * Coins used: 5 + 5 + 1 = 11
 *
 * ============================================================
 * COMPLEXITY
 * ============================================================
 * Time:  O(numCoins × amount) — fill every cell once
 * Space: O(numCoins × amount) — 2D table
 *        → reducible to O(amount) using a 1D array
 */

class Solution {
    public int coinChange(int[] coins, int amount) {
        final int INF = 1 << 30;

        int numCoins = coins.length;
        int targetAmount = amount;

        int[][] dp = new int[numCoins + 1][targetAmount + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= numCoins; i++) {
            for (int j = 0; j <= targetAmount; j++) {
                dp[i][j] = dp[i - 1][j];

                if (j >= coins[i - 1]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - coins[i - 1]] + 1);
                }
            }
        }

        return dp[numCoins][targetAmount] >= INF ? -1 : dp[numCoins][targetAmount];
    }
}