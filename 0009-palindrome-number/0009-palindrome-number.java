class Solution {
    public boolean isPalindrome(int x) {
        //there are a few edge cases which we have to consider 

        // one edge case is if x is less than 0 or negative. they cant be palindromes.
        //another edge case is numbers ending with 0 cant be palindrome.
        if(x<0 || x%10 == 0 && x != 0){
            return false;
        }
        //take one variable where we will store the reversed number..
        int reversedHalf = 0;
        //when should i stop the following loop? whenever the reversed half becomes greater or equal to the given integer.
        while(x > reversedHalf ) {
        reversedHalf = reversedHalf * 10 + x % 10; x/= 10;}                                 return x == reversedHalf || x == reversedHalf / 10; } }