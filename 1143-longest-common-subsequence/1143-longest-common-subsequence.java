/*
The brute force approach for solving this problem is using recursion.
What happens in recursion? 

i create a function 

public int longestCommonSubsequence(String text1, String text2){
    return solve(text1, text2, 0, 0);
}

public int solve(String s1, String s2, int i, int j){
    // if both the pointers arrive at the end of the strings we return 0
    if (i == s1.length || j == s2.length){
        return 0;
    }

    if(s1.charAt(i) == s2.charAt(j)){
        return 1 + solve(s1, s2, i+1, j+1);
    } else {
        return Math.max(solve(s1, s2, i+1, j), solve(s1, s2, i, j+1));
    }
}

why the above approach is expensive? whenever the characters dont match, i am 2^(n+m) is the binary choice we make at every step wheere the characters dont match. 
branching effect:
everytime s1.charAt(i) == s2.charAt(j) the function splits into two new recursive calls 
solve(i+1, j)
solve(i,j+1)

if we visualise this - it creates a binary tree. in the worst case scenario when no characters match this tree grows into a depth of n+m. 
the reason its so slow, is because its not just the above branching , we are calculating the same things thousand of times. 
overlapping subproblems:
solve(0,0) calls solve(1,0) and solve(0,1)
solve(1,0) calls solve(2,0). and solve(1,1)
solve(0,1) calls solve(1,1) and solve(0,2)
solve(0,0)
               /          \
        solve(1,0)      solve(0,1)
         /      \        /      \
  solve(2,0)  [solve(1,1)] [solve(1,1)]  solve(0,2)
    /    \      /    \      /    \      /    \
  ...    ...  ...    ...  ...    ...  ...    ...
solve(1,1) is repeated. when string gets longer, the number of times we calculate solve(1,1) will grow exponentially! for strings of length 50.. this might become 2^50 is over 1 quadrillion operations. this would take years to finish!
gemini:
/*
 * FROM EXPONENTIAL EXHAUSTION TO DP: WHY BRUTE FORCE FAILS
 * * 1. THE "WASTEFUL" OVERLAP
 * In the recursive branches solve(i+1, j) and solve(i, j+1), we often 
 * end up at the exact same sub-problem from different paths:
 * - Path A: Skip text1[0] -> solve(1, 0) -> then skip text2[0] -> solve(1, 1)
 * - Path B: Skip text2[0] -> solve(0, 1) -> then skip text1[0] -> solve(1, 1)
 * Both branches now perform the exact same work for (1, 1), unaware 
 * that the other is doing it too!
 * * 2. THE "EXPLOSION" EFFECT
 * This duplication multiplies exponentially as the tree grows deeper:
 * - String length 4: solve(1, 1) is called 2 times.
 * - String length 10: solve(5, 5) is called 252 times.
 * - String length 30: solve(15, 15) is recalculated MILLIONS of times.
 * * VISUALIZING THE WASTE:
 * solve(0,0)
 * /          \
 * solve(1,0)      solve(0,1)
 * /      \        /      \
 * solve(2,0)  [solve(1,1)] [solve(1,1)]  solve(0,2)  <-- DUPLICATION!
 * * 3. THE MAZE ANALOGY
 * Imagine a maze where you "clone" yourself at every fork (left/right).
 * - Problem: Many different paths lead to the EXACT SAME ROOM.
 * - Waste: Without memory, every clone that enters that room starts 
 * searching from scratch, even if a previous clone already finished it.
 * * THE FIX: MEMOIZATION (THE "NOTE PAD")
 * We use a 2D array memo[i][j] to stop the explosion:
 * 1. Check the pad: "Have I solved room (i, j) before?"
 * 2. If yes: Return the saved answer immediately.
 * 3. If no: Calculate it once, write it down, and then return.
 * * This collapses the Exponential Tree O(2^(n+m)) into a Flat Grid O(n*m).
 */

// class Solution {
// //     Time Complexity: O(m \times n). Every unique pair of (i, j) is calculated exactly once.
// // • Space Complexity: O(m \times n) for the memo table, plus O(m + n) for the recursion stack.
//         //top down memoisation approach. where we store the solved subproblems
//     public int longestCommonSubsequence(String text1, String text2) {
//         //lets take the length of both the strings
//         int m = text1.length();
//         int n = text2.length();

//         //create a memoisation or dp array 
//         Integer[][] memo = new Integer[m][n];

//         return solve(text1, text2, 0, 0, memo);
//     }

//     private int solve(String s1, String s2, int i, int j, Integer[][] memo){
//         //edge case 
//         if(i == s1.length() || j== s2.length()){
//             return 0;
//         }

//         //if we have already calculated  that array positions value and stored it we return the same
//         if(memo[i][j] != null){
//             return memo[i][j];
//         }

//         int result;

//         if(s1.charAt(i) == s2.charAt(j)){
//         result = 1 + solve(s1, s2, i+1, j+1, memo);
//     } else {
//         result = Math.max(solve(s1, s2, i+1, j,memo), solve(s1, s2, i, j+1,memo));
//     }

//         memo[i][j] = result;
//         return result;

//     }
// }


/*
 * BOTTOM-UP DP: BUILDING THE ANSWER BRICK-BY-BRICK
 * * 1. THE CONCEPT:
 * Instead of recursion, we use a 2D grid (dp[m+1][n+1]) to store LCS lengths.
 * We fill the table from the "Bottom-Right" (empty strings) up to the "Top-Left".
 *
 * 2. THE RULES:
 * - MATCH: text1[i] == text2[j] -> dp[i][j] = 1 + dp[i+1][j+1] (Diagonal + 1)
 * - NO MATCH: text1[i] != text2[j] -> dp[i][j] = Max(dp[i+1][j], dp[i][j+1])
 *
 * 3. DRY RUN (text1: "abcde", text2: "ace"):
 * Starting from i=4, j=2 and moving backwards:
 * -------------------------------------------------------------------------
 * i=4 ('e'): Match at j=2! dp[4][2] = 1 + 0 = [1]. Row 4: [1, 1, 1, 0]
 * i=3 ('d'): No matches. Max logic spreads the '1'. Row 3: [1, 1, 1, 0]
 * i=2 ('c'): Match at j=1! dp[2][1] = 1 + dp[3][2] = 1 + 1 = [2]. Row 2: [2, 2, 1, 0]
 * i=1 ('b'): No matches. Max logic spreads the '2'. Row 1: [2, 2, 1, 0]
 * i=0 ('a'): Match at j=0! dp[0][0] = 1 + dp[1][1] = 1 + 2 = [3]. Row 0: [3, 2, 1, 0]
 * -------------------------------------------------------------------------
 * * 4. FINAL DP TABLE VISUALIZATION:
 * a (0)  c (1)  e (2)  "" (3)
 * a(0) [ 3 ]   2      1      0    <-- Result is here (LCS of full strings)
 * b(1)   2     2      1      0
 * c(2)   2    [ 2 ]   1      0    <-- Match found here ('c' == 'c')
 * d(3)   1     1      1      0
 * e(4)   1     1     [ 1 ]   0    <-- Match found here ('e' == 'e')
 * ""(5)  0     0      0      0    <-- Base cases (Empty strings)
 *
 * Complexity: Time O(M*N) | Space O(M*N)
 */

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // Extra row and column to handle base case (comparing with empty string)
        int[][] dp = new int[m + 1][n + 1];

        // Fill table from bottom-to-top, right-to-left
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                
                if (text1.charAt(i) == text2.charAt(j)) {
                    // Current characters match: 1 + result of remaining suffixes
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                } else {
                    // No match: take the best from either skipping char in text1 or text2
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        // dp[0][0] contains the length of the longest common subsequence
        return dp[0][0];
    }
}