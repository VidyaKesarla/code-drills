/*
an anagram is a word / phrase formed by rearranging letters of a different word or phrase. in short: same characters, same frequencies, but different order.

*/
//Clarifying questions i can ask the interviewer ie; a few edge cases:
// is Silent and Listen considered to be anagrams? should case sensitivity be considered?
//should we ignore spaces or special characters?
//is the input restricted to only english letters and other emoticons/ smileys are discarded? never in input?


/*
Brute force: simplest solution is to sort the strings. if both the strings have the same set of characters then they are valid anagrams, ie; sort t and sort s:
if they have the same then yeah they are anagrams. But time complexity for this is O(nlogn)=> which is quite high. Space complexity is:
O(1) or O(n) depending on whether the sorting is in place and if the language allows mutable strings
*/

/*
Since we care only about the count of each character: we can use a hash map or fixed size array.

//one thing we can check is if the length of each string is the same. if they are not then we have to return false.
count occurrences of each character in s and increment those values
subtract occurrences using t (Decrement)
if all counts equal to zero at the end of the loop then return true

In the following Frequency Array approach is generally preferred because it is highly efficient and avoids the overhead of a HashMap or the $O(n \log n)$ cost of sorting.
*/
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()){
            return false;
        }
        int charCounts [] = new int[26];
        for(int i =0;i<s.length();i++){
            charCounts[s.charAt(i) - 'a']++;
            charCounts[t.charAt(i) - 'a']--;
        }
        for (int count: charCounts) {
        if (count != 0) {
            return false;
        }
        }
        return true;
    }
}
//TC: O(N)
//SC: o(1)

// What if the inputs contain Unicode characters? (You’d need a Hash Map instead of a fixed size 26-count array).
// How would you optimize this for extremely large strings that don't fit in memory? (External sorting or streaming character counts).
// Can you solve this by only using one counter variable? (Increment for $s$, decrement for $t$, and check for any non-zero value).
// if by "one counter variable" you mean a single integer, the answer is no for general cases. A single integer cannot track the frequency of 26 different characters simultaneously without running into "collisions" (where different combinations of letters produce the same sum).

// However, if you mean using a single data structure (like one array or one hash map) instead of two, or if you are looking for a clever mathematical trick, here is how you can approach it:

// 1. The "Single Array" Approach (Standard)
// In an interview, this is usually what "one counter" refers to. You use one array to track the net balance of characters.

// Increment for every character in s.

// Decrement for every character in t.

// If the balance is zero for all indices, they are anagrams.
// The Prime Product Trick (Mathematical "One Variable")
// This is a "brain teaser" solution. You can represent each letter (a-z) with a unique prime number.

// a = 2, b = 3, c = 5, d = 7, and so on.

// Calculate the product of the primes corresponding to the characters in s.

// Calculate the product for t.

// If productS == productT, they are anagrams.