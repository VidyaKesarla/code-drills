// /*
// # Intuition
// The problem asks for the longest substring where we can make all characters identical by 
// changing at most 'k' characters. 

// 1. **The Core Logic**: A window is "valid" if the number of characters we need to change 
//    is less than or equal to $k$.
//    The formula for this is:
//    $$\text{Window Length} - \text{Count of the Most Frequent Character} \le k$$
//    - **Window Length**: $(right - left + 1)$
//    - **Most Frequent Character**: The character that appears most often in the current window.

// # Approach
// We use a **Sliding Window** (Two Pointers) technique to avoid the $O(n^2)$ brute force cost:
// 1. **Expand**: Move the `right` pointer to include a new character and update its frequency in a count array.
// 2. **Update Max Frequency**: Maintain `maxFreq`, which tracks the highest frequency of any single character 
//    seen in the current window.
// 3. **Validate**: If $(\text{Window Length} - maxFreq) > k$, the window contains too many "other" 
//    characters to replace. 
// 4. **Shrink**: When invalid, increment the `left` pointer and decrement the count of the character 
//    leaving the window. 
// 5. **Result**: The maximum window size achieved during the process is our answer.

// # Complexity
// - **Time complexity**: $O(n)$
//   Each character in the string is visited at most twice (once by the `right` pointer 
//    and once by the `left` pointer).
  
// - **Space complexity**: $O(1)$
//   We only use a fixed-size integer array of length 26 to store character counts, 
//   regardless of the input string size.
// */
// class Solution {
//     public int characterReplacement(String s, int k) {
//         //initially itself i have been given a string which has uppercase letters
//         //i can choose any letter in the string and i can change it to any other uppercase english character
//         //i can perform this k times

//         //OUTPUT expected is i have to form a substring which has same letters consecutively and the longest one. It can have many outputs

//         //ip : "ABAB" OP: "BBBB" / "AAAA"

//         //ip: "AABABBA" OP: "AABBBBB" / "AAAAABA"




//         //frequency array of alphabetical characters which will tell me how many counts does each letter have. array is better here because lookup time if faster than hashMap. O(1)
        
//         int count[] = new int[26];
//         //highest frequency of character in the current window
//         int maxFreq = 0;
//         //the starting point of the current window
//         int l = 0;
//         //this is the answer, where we store the length of the longest substring
//         int maxWindow = 0;



//         for(int r=0;r<s.length;r++){
//             // TRACK LOCAL MAX: We only care if the current character becomes the new 
//             // most frequent character in our window.
//             maxFreq = Math.max(maxFreq, ++count[s.charAt(r) - 'A']);
//             /* * 3. VALIDITY CHECK:
//              * A window is valid if: (Window Size - Most Frequent Char Count) <= k
//              * This tells us how many "other" characters we must replace to make 
//              * the whole window uniform.
//              */
//             while((r-l+1) - maxFreq > k){
//                 // UPDATE STATE: Map character to index 0-25 and increment its count
//                 // SHRINK (INVALID STATE):
//                 //  * If we need more than 'k' replacements, the current window is impossible.
//                 //  * We shift the 'left' pointer to the right to look for a new window.
//                 //  * Note: We don't need to decrement 'maxFreq' here. We are only 
//                 //  * interested in windows that BEAT our current 'maxFreq' record.
//                 //  */
//                 count[s.charAt(l) - 'A']--;
//                 l++;
//             }
//             /*
//              * 5. UPDATE GLOBAL RESULT:
//              * After potentially shrinking, the current window [left, right] is 
//              * guaranteed to be valid. We track the maximum size seen so far.
//              */
//             maxWindow = Math.max(maxWindow. r-l+1);
//         }

//         return maxWindow;

//     }
// }

/*
# Intuition
The problem asks for the longest substring where we can make all characters identical by 
changing at most 'k' characters. 

1. **The Core Logic**: A window is "valid" if the number of characters we need to change 
   is less than or equal to $k$.
   The formula for this is:
   $$\text{Window Length} - \text{Count of the Most Frequent Character} \le k$$
   - **Window Length**: $(right - left + 1)$
   - **Most Frequent Character**: The character that appears most often in the current window.

# Approach
We use a **Sliding Window** (Two Pointers) technique to avoid the $O(n^2)$ brute force cost:
1. **Expand**: Move the `right` pointer to include a new character and update its frequency in a count array.
2. **Update Max Frequency**: Maintain `maxFreq`, which tracks the highest frequency of any single character 
   seen in the current window.
3. **Validate**: If $(\text{Window Length} - maxFreq) > k$, the window contains too many "other" 
   characters to replace. 
4. **Shrink**: When invalid, increment the `left` pointer and decrement the count of the character 
   leaving the window. 
5. **Result**: The maximum window size achieved during the process is our answer.

# Complexity
- **Time complexity**: $O(n)$
  Each character in the string is visited at most twice.
  
- **Space complexity**: $O(1)$
  Fixed-size integer array of length 26.
*/
class Solution {
    public int characterReplacement(String s, int k) {
        // frequency array of alphabetical characters. 
        // Array lookup is faster than HashMap: O(1)
        int[] count = new int[26];
        
        // highest frequency of a single character in the current window
        int maxFreq = 0;
        
        // the starting point (left boundary) of the current window
        int l = 0;
        
        // this is the answer, where we store the length of the longest valid window
        int maxWindow = 0;

        for (int r = 0; r < s.length(); r++) {
            // TRACK LOCAL MAX: Increment count and update the most frequent character count
            // found in the window so far.
            maxFreq = Math.max(maxFreq, ++count[s.charAt(r) - 'A']);
            
            /* * VALIDITY CHECK:
             * A window is valid if: (Window Size - Most Frequent Char Count) <= k
             * If invalid, we shrink from the left.
             */
            while ((r - l + 1) - maxFreq > k) {
                // SHRINK (INVALID STATE):
                // We shift 'l' to the right and update the frequency map.
                // We don't need to decrement 'maxFreq' because only a LARGER maxFreq
                // could ever give us a better result than what we've already seen.
                count[s.charAt(l) - 'A']--;
                l++;
            }
            
            /*
             * UPDATE GLOBAL RESULT:
             * Track the maximum valid window size encountered.
             */
            maxWindow = Math.max(maxWindow, r - l + 1);
        }

        return maxWindow;
    }
}