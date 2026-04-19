//the brute force appraoch is bad because it will iterate through every prefix.
// if a prefix already existss in the dictionary we recursively call the function on remaining suffix

/*
public class Solution{
    public boolean wordBreak(String s, List<String> wordDict){
        return canBreak(s, new HashSet<>(wordDict));
    }

    private boolean canBreak(String s, Set<String> wordSet){
        if(s.isEmpty()) return true;

        for(int i=1;i<=s.length;i++){
            // if prefixs[0...i] is in dict and suffix can be broken
            //we are just checking if the prefix exists that is for example:
            //input : s = "catsand" , wordDict = ["cat","cats","sand"]
            //the following code will start to look for a valid prefix at the beginning of string. once it finds one, it chops it off and treats the suffix as a brand new problem.
            // canBreak(s.substring(i) - a new substring from i.
            if(wordSet.contains(s.substring(0,i)) && canBreak(s.substring(i), wordSet)) {
                return true;
            }
        }
        return false;
    }
}
why this approach is bad

/**
gemini:
 * 139. Word Break

 1. Exponential Time Complexity O(2^n)

 * * APPROACH 1: Brute Force (Recursion) - WHY IT IS A BAD APPROACH:
 * -----------------------------------------------------------
 * 1. Exponential Time Complexity: O(2^n). For a string of length n, there are 2^(n-1) 
 * possible ways to split it. The recursion explores every single one.
 Every character in the string s represents a potential "split point." For a string of length n, there are 2^{n-1} ways to partition it.
• If n=10, that’s about 1,024 checks.
• If n=30, that’s over 1 Billion checks!
 * 2. Overlapping Subproblems: The algorithm recalculates the same suffixes 
 * multiple times. If s = "aaaaaaaaab", the code will solve "aaaaab" 
 * hundreds of times from different recursive branches.
 * 3. Memory Overhead: In Java, s.substring() creates new String objects, 
 * leading to heavy memory allocation and GC pressure in deep recursion.
 * Result: Time Limit Exceeded (TLE) on LeetCode.
 *
 */
class Solution {
    //we can optimise the above approach in recursion to dynamic programming. how will we do this?
    //by storing subproblems. we will use a boolean array dp[] where dp[i] is true if the first characters of string can be segnmented

    public boolean wordBreak(String s, List<String> wordDict) {

//    why we use a hashset?     1. The Speed Difference (O(1) vs. O(K))
// When your code asks, "Is this substring in the dictionary?", the computer has to go look for it.
// • With a List: The computer starts at index 0 and checks every single word one by one until it finds a match or hits the end. If the dictionary has 1,000 words (K), this takes O(K) time.
// • With a HashSet: A HashSet uses a "Hash Table." It calculates a mathematical code for the string and jumps directly to the exact spot where that word should be. This takes O(1) time on average (constant time).
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for(int i=1;i<= s.length(); i++){
            for(int j=0;j<i;j++){
                //if s[0...j] is valid and s[j...i] is in the dictionary
                if(dp[j] && wordSet.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}

/**
 * Logic: Dynamic Programming (Bottom-Up)
 * * 1. dp[i] represents if the prefix s[0...i] can be segmented into words from the dictionary.
 * 2. We use a HashSet for the dictionary to achieve O(1) lookups.
 * 3. For every index 'i' (end of current substring), we look back at all previous 
 * indices 'j' (start of current substring).
 * 4. A position 'i' is marked TRUE (dp[i] = true) IF:
 * - dp[j] is true (meaning the string up to 'j' was already successfully segmented)
 * - AND the substring from j to i exists in the dictionary.
 * * Time Complexity: O(n^3) 
 * - Two nested loops (n^2) 
 * - String substring + HashSet lookup (n)
 * Space Complexity: O(n) for the DP array and the HashSet.
 */

/* Gemini dry run trace:
Input: * s = "applepen" (Length 8)
* wordDict = ["apple", "pen"]
1. Initialization
* wordSet: {"apple", "pen"} (Converted from list to HashSet for $O(1)$ lookup).
* dp array: new boolean[9] (Indices 0 to 8).
* Base Case: dp[0] = true (The empty string is always "reachable").
Initial DP State:
| Index | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| Value | T | F | F | F | F | F | F | F | F |

2. The Nested Loops (Tracing i and j)
We are looking for "Safe Stepping Stones." i is the stone we want to reach, and j is the stone we are standing on.
i = 1 to 4 ("a", "ap", "app", "appl")
* For each i, the inner loop checks all j < i.
* It finds dp[0] is True, but s.substring(0, i) (like "app") is not in wordSet.
* Result: dp[1] to dp[4] remain False.
i = 5 ("apple")
* When j = 0:
    * Is dp[0] True? Yes.
    * Is s.substring(0, 5) ("apple") in wordSet? Yes.
* Action: Set dp[5] = true and break (we found a path to index 5!).
i = 6 to 7 ("applep", "applepe")
* For i = 6, it checks j=0 (False) and j=5 (s.substring(5,6) is "p" - False).
* Result: dp[6] and dp[7] remain False.
i = 8 ("applepen")
The inner loop j checks 0 through 7:
* j = 0: dp[0] is True, but s.substring(0, 8) ("applepen") is not in dict.
* j = 1, 2, 3, 4: dp[j] are all False. (Cannot start a bridge from a broken stone).
* j = 5:
    * Is dp[5] True? Yes! (We reached "apple" earlier).
    * Is s.substring(5, 8) ("pen") in wordSet? Yes!
* Action: Set dp[8] = true and break.

3. Final State of DP Array
Index	0	1	2	3	4	5	6	7	8
Char	""  a	p	p	l	e	p	e	n
Value	T	F	F	F	F	T	F	F	T
The function returns dp[8], which is true.

Key Summary of the Logic
1. dp[5] = true means we successfully built a bridge from the start to the end of "apple".
2. dp[8] = true was only possible because:
    * We were standing on a "safe" spot at index 5 (dp[5]).
    * We found a valid word ("pen") to carry us from index 5 to index 8.
Would you like to try a "False" example (like "applep") to see exactly where it gets stuck?
*/