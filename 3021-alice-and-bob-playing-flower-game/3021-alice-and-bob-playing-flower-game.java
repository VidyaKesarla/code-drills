class Solution {
    public long flowerGame(int n, int m) {
         long oddN = (n + 1) / 2;
        long evenN = n / 2;
        
        // Calculate number of odds and evens in range [1, m]
        long oddM = (m + 1) / 2;
        long evenM = m / 2;
        
        // Alice wins if x + y is odd.
        // This happens if:
        // 1. x is odd and y is even
        // 2. x is even and y is odd
        return (oddN * evenM) + (evenN * oddM);
    }
}

/**
 * Complexity Analysis:
 * Time Complexity: O(1) - The result is calculated using direct arithmetic.
 * Space Complexity: O(1) - No additional space is used.
 * * Logic & Data Types:
 * 1. Win Condition: Alice wins if (x + y) is odd. This occurs when one 
 * number is odd and the other is even.
 * 2. Counting: We calculate the number of odds/evens in ranges [1, n] and [1, m].
 * 3. Preventing Overflow: We use 'long' for these counts and the final product.
 * Even if n and m fit in an 'int' (max ~2*10^9), their product (e.g., 10^5 * 10^5) 
 * can exceed the 32-bit integer limit, causing "roll over" and incorrect 
 * negative results. Using 64-bit 'long' ensures accuracy up to 9*10^18.
 */
