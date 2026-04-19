class Solution {
    public int singleNumber(int[] nums) {
        //one way to find this is using linear search, but this will cost us n*n time complexity because of nested loops, instead best way for this is using xor(according to research)(Bit manipulation)
        /*
        Identity: Any number XORed with zero is the number itself: $a \oplus 0 = a$.Self-Inverse: Any number XORed with itself is zero: $a \oplus a = 0$.Commutative and Associative: $a \oplus b \oplus c = c \oplus a \oplus b$.
        */
        int xorr = 0;

        // XOR all elements — duplicates cancel each other out
        for (int num : nums) {
            xorr ^= num;
        }

        return xorr;

    }
}