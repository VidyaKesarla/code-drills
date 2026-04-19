public class Solution {
    /*
 * Problem: Edit Distance (LeetCode 72)
 * Strategy: Dynamic Programming (Bottom-Up)
 * * The dp[i][j] table stores the minimum operations to convert word1[0...i-1] to word2[0...j-1].
 * * Visualization of the "Joystick" Logic:
 * [D] Diagonal (i-1, j-1) -> REPLACE (consume both chars)
 * [A] Above    (i-1, j)   -> DELETE  (remove from word1)
 * [L] Left     (i, j-1)   -> INSERT  (add to word1)
 * * Example: "COAT" to "CAT"
 * |   | "" | C | A | T |
 * |---|----|---|---|---|
 * | ""| 0  | 1 | 2 | 3 |  <- Base cases (Insertions)
 * | C | 1  | 0 | 1 | 2 |  <- Row 1: 'C' matches 'C', pulls 0 from [D]
 * | O | 2  | 1 | 1 | 2 |  <- Row 2: 'O' vs 'C' pulls 0 from [A] + 1 (Delete)
 * | A | 3  | 2 | 1 | 2 |  
 * | T | 4  | 3 | 2 | 1 |  <- Final Answer is dp[m][n]
 Time$O(m \times n)$Proportional to the total number of cells.
 Why? We use a nested loop: the outer loop runs $m$ times (length of word1) and the inner loop runs $n$ times (length of word2).Every single cell in the $(m+1) \times (n+1)$ grid is visited exactly once, and the work done inside each cell (comparing characters and finding a minimum of three numbers) is a constant time $O(1)$ operation.
Space$O(m \times n)$Size of the 2D dp array.
 Why? We are creating a 2D integer array (the dp table) of size $(m+1) \times (n+1)$ to store the results of our subproblems.Wait, can we do better? Yes! If you notice, to calculate any cell, we only ever look at the cell above it, the one to its left, and the diagonal one. This means we only really need the current row and the previous row to solve the problem.Space-Optimized SC: $O(\min(m, n))$ if we only store two rows at a time.
 */
 
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // dp[i][j] stores the min operations to convert 
        // word1.substring(0, i) to word2.substring(0, j)
        int[][] dp = new int[m + 1][n + 1];

        // Base Case 1: Convert word1 to empty string (Deletions)
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        
        // Base Case 2: Convert empty string to word2 (Insertions)
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // If characters are the same, no new operation needed
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Otherwise, pick the best of the 3 possible moves:
                    // 1 + dp[i-1][j-1] -> Replace
                    // 1 + dp[i][j-1]   -> Insert
                    // 1 + dp[i-1][j]   -> Delete
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], 
                                   Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }
}