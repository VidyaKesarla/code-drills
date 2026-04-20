// class Solution {
//     public boolean validPalindrome(String s) {
//         int left = 0;
//         int right = s.length() - 1;
//         int count = 0;
//         while(left<right) {
//         while(left < right && !Character.isLetterOrDigit(s.charAt(left))){
//             left++;
//         }

//         while(left < right && !Character.isLetterOrDigit(s.charAt(left))){
//             right--;
//         }

//         if(Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))){
//             count++;
//             if(count > 1)
//             return false;
//         }
//         left++;
//         right--;
//         }
//         return true;
//     }
// }
//greedy two pointer approach 
class Solution {
    public boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        while(left < right){
            if(Character.toLowerCase(s.charAt(left))!= Character.toLowerCase(s.charAt(right)) )
            return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right-1) ;
            left++;
            right--;
        }
        return true;

    }

    public boolean isPalindrome(String s, int i, int j){
        while(i<j){
            if (Character.toLowerCase(s.charAt(i))!= Character.toLowerCase(s.charAt(j))){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
//tc:O(n)
//sc: O(1)
/*
 * APPROACH: Two-Pointer with One-Chance Deletion
 * * 1. Initialize left = 0, right = s.length - 1.
 * 2. Loop while left < right:
 * - If s[left] == s[right]: Move both pointers inward (left++, right--).
 * - If s[left] != s[right]: 
 * We must delete one. Return true if EITHER s[left+1...right] 
 * OR s[left...right-1] is a palindrome.
 * 3. If loop completes, return true.
 *
 * DRY RUN: s = "cupuuc"
 * - left=0 ('c'), right=5 ('c') -> Match! (left=1, right=4)
 * - left=1 ('u'), right=4 ('u') -> Match! (left=2, right=3)
 * - left=2 ('p'), right=3 ('u') -> MISMATCH!
 * - Try isPalindrome(s, 3, 3) ["u"] -> True
 * - Try isPalindrome(s, 2, 2) ["p"] -> True
 * - Result: True
 */