/**
 * 3. Longest Substring Without Repeating Characters
 * * BRUTE FORCE APPROACH (Conceptual):
 * - Use two nested loops to generate every possible substring O(n^2).
 * - Use a third loop or a Set to check if each substring has unique characters O(n).
 * - Total Time Complexity: O(n^3).
 * - Total Space Complexity: O(min(n, m)) where m is the size of the alphabet.
 * * OPTIMIZED APPROACH: Sliding Window + HashMap
 * - We use a 'sliding window' defined by [left, right].
 * - The HashMap stores the last seen index of each character.
 * - When we find a duplicate, we "jump" the left pointer to the right of the 
 * previous occurrence, but only if that occurrence is inside our current window.
 * * DRY RUN (Optimized): s = "abba"
 * 1. right = 0, char = 'a': Not in map. left = 0. Map: {a:0}. Max = 1. Window: [a]
 * 2. right = 1, char = 'b': Not in map. left = 0. Map: {a:0, b:1}. Max = 2. Window: [ab]
 * 3. right = 2, char = 'b': Found 'b' at index 1. 
 * left = Math.max(0, 1 + 1) = 2. Map: {a:0, b:2}. Max = 2. Window: [b]
 * 4. right = 3, char = 'a': Found 'a' at index 0. 
 * left = Math.max(2, 0 + 1) = 2. Map: {a:3, b:2}. Max = 2. Window: [ba]
 * Final Result: 2
 * * TIME COMPLEXITY: O(n) - Each character is visited once by the 'right' pointer.
 * SPACE COMPLEXITY: O(min(m, n)) - 'm' is the size of the character set (ASCII/Unicode).
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        
        int n = s.length();
        int maxLength = 0;
        // Map to store {Character : Last Seen Index}
        Map<Character, Integer> map = new HashMap<>();
        
        for (int right = 0, left = 0; right < n; right++) {
            char currentChar = s.charAt(right);
            
            // If the character is already in our map, it might be a duplicate
            if (map.containsKey(currentChar)) {
                /*
                 * The Safety Latch: We move 'left' to the right of the duplicate.
                 * Math.max ensures we don't move 'left' backwards to an old index
                 * that is already outside our current window.
                 */
                left = Math.max(left, map.get(currentChar) + 1);
            }
            
            // Update the map with the most recent index of this character
            map.put(currentChar, right);
            
            // Calculate current window size: (right - left + 1)
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
