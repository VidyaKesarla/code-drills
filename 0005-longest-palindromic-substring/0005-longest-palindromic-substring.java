class Solution {
    public String longestPalindrome(String s) {
        if(s== null || s.length() < 1){ return "";}

        int start = 0;
        int end = 0;

        String result = "";
        for(int i=0;i < s.length();i++){
            //2 edge cases to consider
            //if the string is odd start and end index would be the same
            //recursive call
            int len1 = expandFromCentre(s, i, i);
            //if the string is even start and end index would not be the same
            int len2 = expandFromCentre(s,i,i+1);

            int maxLen = Math.max(len1, len2);
            //if we found a longer substring then 
            if(maxLen > end - start){
                //i here is centre
                start = i - (maxLen - 1)/2;
                end = i + maxLen / 2;
            }

        }
        /*
 * DRY RUN TRACE: s = "babad"
 * Initial: start = 0, end = 0
 * * i = 0 ('b'): 
 * - Odd Expansion (0,0): "b" (len 1)
 * - Even Expansion (0,1): "ba" (not match, len 0)
 * - maxLen = 1. Update: start = 0, end = 0.
 * * i = 1 ('a'): 
 * - Odd Expansion (1,1): "bab" (len 3)
 * - Even Expansion (1,2): "ab" (not match, len 0)
 * - maxLen = 3. Update: 
 * start = 1 - (3-1)/2 = 0
 * end   = 1 + 3/2     = 2  (Result: "bab")
 * * i = 2 ('b'): 
 * - Odd Expansion (2,2): "aba" (len 3)
 * - Even Expansion (2,3): "ba" (not match, len 0)
 * - maxLen = 3. (3 > 2-0 is False). No update.
 * * i = 3 ('a'): 
 * - Odd Expansion (3,3): "a" (len 1)
 * - Even Expansion (3,4): "ad" (not match, len 0)
 * - maxLen = 1. (1 > 2-0 is False). No update.
 * * i = 4 ('d'): 
 * - Odd Expansion (4,4): "d" (len 1)
 * - maxLen = 1. No update.
 * * Final Return: s.substring(0, 3) -> "bab"
 */
        return s.substring(start, end+1);  
    }

    ///write a helper function expandFromCentre to find the length of the longest substring
    public int expandFromCentre(String s, int left, int right){
        while(left >= 0&& right < s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        //length of the substring
        return right - left - 1;
    }
}



//brute force for this question:


// Logic: Two nested loops generate all substrings, and a third loop checks if that substring is a palindrome.

// Why it's slow: For a string of length 1000, you are doing roughly 1 billion operations. It will likely Time Limit Exceeded (TLE) on LeetCode.

// public String longestPalindromeBruteForce(String s){
//     int n = s.length;
//     if(n<2) return s;

//     //take one String res which is empty.
//     String result = "";
//     for(int i=0;i<n;i++){
//         for(int j=i;j<n;j++){
//             String subString = s.subString(i, j+1);
//             if(isPalidrome(subString) && subString.length > result.length){
//                 result = subString;
//             }
//         }
//     }
//     return result;
// }

// private boolean isPalindrome(String s){
//     int left = 0, right = s.length() - 1;
//     while(left < right){
//         if(s.charAt(left++) != s.charAt(right--))
//         return false;
//     }
//     return true;
// }


/*/**
 * 5. Longest Palindromic Substring
 * * APPROACH: Expand Around Center
 * -------------------------------------------------------------------------
 * TIME COMPLEXITY: O(n^2)
 * - We iterate through the string n times.
 * - For each character, we expand outwards, which takes O(n) in the worst case.
 * * SPACE COMPLEXITY: O(1)
 * - We only store two integer pointers (start, end) regardless of input size.
 * - This is superior to the O(n^2) space required by Dynamic Programming.
 * -------------------------------------------------------------------------
 * * DRY RUN TRACE: s = "babad"
 * Initial: start = 0, end = 0
 * * i = 0 ('b'): 
 * - Odd (0,0): "b" (len 1) | Even (0,1): "ba" (len 0)
 * - maxLen = 1. Update: start=0, end=0.
 * * i = 1 ('a'): 
 * - Odd (1,1): "bab" (len 3) | Even (1,2): "ab" (len 0)
 * - maxLen = 3. Update: 
 * start = 1 - (3-1)/2 = 0
 * end   = 1 + 3/2     = 2  => Result: "bab"
 * * i = 2 ('b'): 
 * - Odd (2,2): "aba" (len 3) | Even (2,3): "ba" (len 0)
 * - maxLen = 3. (3 > 2-0 is False). No update.
 * * Final Return: s.substring(0, 3) -> "bab"
 * -------------------------------------------------------------------------
 * * WHY NOT DYNAMIC PROGRAMMING (DP)?
 * 1. Space Efficiency: DP requires an O(n^2) boolean table. For a string 
 * of 5000 chars, that's 25 million entries (Memory Limit Exceeded risk).
 * 2. Redundancy: DP must solve every sub-problem. Expansion "Short-circuits" 
 * immediately when characters don't match, saving thousands of operations.
 */
/*
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            // Check odd-length (center is i) and even-length (center is gap i, i+1)
            int len1 = expandFromCenter(s, i, i);
            int len2 = expandFromCenter(s, i, i + 1);
            int maxLen = Math.max(len1, len2);

            // If a longer palindrome is found, update start and end indices
            if (maxLen > end - start) {
                // Integer division handles both odd and even centering
                start = i - (maxLen - 1) / 2;
                end = i + maxLen / 2;
            }
        }
        // Substring end index is exclusive in Java
        return s.substring(start, end + 1);
    }

    private int expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // Returns length: (right - 1) - (left + 1) + 1 simplifies to right - left - 1
        return right - left - 1;
    }
}


DP considered inferior to the "Expand Around Center" approach for two very specific reasons: Memory Hogging and Redundancy.Here is why DP is usually the "wrong" choice for this specific problem in an interview.1. The Memory Trap: $O(n^2)$ SpaceThe DP approach requires you to create a 2D boolean table (boolean[][] dp) of size $n \times n$.If the input string is 1,000 characters, the table has 1,000,000 entries.If the input string is 5,000 characters (a common LeetCode limit), the table has 25,000,000 entries.The Failure: On many platforms, this will trigger a Memory Limit Exceeded error. Even if it passes, it uses significantly more RAM than the "Expand Around Center" method, which uses zero extra memory ($O(1)$ space).2. "Filling the Whole Table" (Redundancy)In DP, you have to solve every single subproblem to find the answer. You are forced to determine if every possible substring is a palindrome, even if it couldn't possibly be the longest one.DP Logic: To know if s[1...10] is a palindrome, you must first calculate if s[2...9] is a palindrome.Expansion Logic: If s[5...6] is not a palindrome, the "Expand Around Center" method stops immediately. It doesn't waste time checking s[4...7], s[3...8], etc.


/**
 * 5. Longest Palindromic Substring
 * -------------------------------------------------------------------------
 * | Method                  | Time Complexity | Space Complexity | Notes                                   |
 * |-------------------------|-----------------|------------------|-----------------------------------------|
 * | Brute Force             | O(n^3)          | O(1)             | Very slow, will likely TLE.             |
 * | Dynamic Programming     | O(n^2)          | O(n^2)           | Uses a boolean table to store results.  |
 * | Expand Around Center    | O(n^2)          | O(1)             | Most efficient for interviews.          |
 * | Manacher's Algorithm    | O(n)            | O(n)             | Overkill for most interviews.           |
 * -------------------------------------------------------------------------
 * * * DRY RUN TRACE: s = "babad"
 * - i = 0 ('b'): Odd (1), Even (0). maxLen=1. Update: start=0, end=0.
 * - i = 1 ('a'): Odd (3), Even (0). maxLen=3. Update:
 * start = 1 - (3-1)/2 = 0
 * end   = 1 + 3/2     = 2  => "bab"
 * - i = 2 ('b'): Odd (3), Even (0). maxLen=3. (3 > 2-0 is False). No update.
 * - Final Result: "bab"
 */
