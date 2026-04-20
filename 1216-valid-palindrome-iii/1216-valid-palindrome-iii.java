/*
 * APPROACH: Dynamic Programming (Bottom-Up) with O(n) Space
 * PROBLEM: 1216. Valid Palindrome III (Min Deletions version)
 * * LOGIC:
 * - dp[j] stores the minimum deletions needed for substring s[i...j].
 * - temp: Acts as a buffer to save the 'row below' (dp[i+1][j]) before overwriting.
 * - prev: Acts as the 'diagonal' (dp[i+1][j-1]), representing the cost of the inner substring.
 * * DRY RUN: s = "abcdeca", k = 2
 * 1. Initial State: dp = [0, 0, 0, 0, 0, 0, 0]
 * 2. Processing i = 2 ('c'):
 * - At j = 5 ('c'): MATCH!
 * - dp[5] becomes prev (Cost of inner "de" was 1, but we used the diagonal value).
 * 3. Processing i = 0 ('a'):
 * - When j = 5 ('c'): temp is 2 (cost of "bcdec"). We finish this iteration and set prev = 2.
 * - When j = 6 ('a'): MATCH!
 * - Characters s[0] and s[6] are both 'a'.
 * - dp[6] = prev (which is 2).
 * * FINAL RESULT:
 * - dp[n-1] = 2.
 * - Since 2 <= k (2), return true.
 * * TIME COMPLEXITY: O(n^2) - Nested loops for i and j.
 * SPACE COMPLEXITY: O(n) - Single 1D array instead of n*n matrix.
 */

class Solution {
    public boolean isValidPalindrome(String s, int k) {
        int n = s.length();
        int[] dp = new int[n];
        
        // We iterate backwards to build the solution from smallest substrings
        for (int i = n - 2; i >= 0; i--) {
            int prev = 0; // Represents the diagonal: dp[i+1][j-1]
            for (int j = i + 1; j < n; j++) {
                int temp = dp[j]; // Store dp[i+1][j] before it gets updated
                
                if (s.charAt(i) == s.charAt(j)) {
                    // Chars match: Cost is the same as the inner substring cost
                    dp[j] = prev;
                } else {
                    // Chars mismatch: 1 deletion + min of skipping left or right
                    dp[j] = 1 + Math.min(dp[j], dp[j - 1]);
                }
                // The current dp[i+1][j] becomes the diagonal for the next iteration (j+1)
                prev = temp; 
            }
        }
        
        return dp[n - 1] <= k;
    }
}

//https://claude.ai/share/fb5d1c9e-bfde-4b6d-9e0b-08c8f24af670