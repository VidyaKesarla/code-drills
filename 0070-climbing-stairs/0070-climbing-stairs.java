import java.util.Arrays;

class Solution {
    public int climbStairs(int n) {
        // Handle small n early to avoid array size issues or extra calls
        if (n <= 2) return n;

        int[] storage = new int[n + 1];
        Arrays.fill(storage, -1);
        
        return helper(n, storage);
    }

    private int helper(int n, int[] storage) {
        // Base cases: 1 way for 1 step, 2 ways for 2 steps
        if (n <= 2) return n;

        // Check if we have already calculated this value
        if (storage[n] != -1) {
            return storage[n];
        }

        // Store and return the result
        storage[n] = helper(n - 1, storage) + helper(n - 2, storage);
        return storage[n];
    }
}

/*

Efficiency: Without the storage array, the time complexity would be O(2^n) (exponential). With memoization, you ensure each step is only calculated once, bringing it down to O(n).
*/



//another efficient approach or an approach better than this is :


/*Refined Iterative Version (O(1) Space)
This version avoids recursion depth issues and saves memory:
/*public int climbStairs(int n) {
    if (n <= 2) return n;

    int first = 1;  // Ways to reach 1st step
    int second = 2; // Ways to reach 2nd step
    
    for (int i = 3; i <= n; i++) {
        int current = first + second;
        first = second;
        second = current;
    }
    
    return second;
}*/


/*
 * APPROACH COMPARISON: Memoization (Top-Down) vs. Iterative (Bottom-Up)
 * * 1. DIRECTION:
 * - Memoization: Starts at the target 'n' and breaks it down (Recursion).
 * - Iterative: Starts at the base cases (1, 2) and builds up to 'n' (Loops).
 * * 2. SPACE COMPLEXITY:
 * - Memoization: O(n) for the array + O(n) for the Recursive Call Stack.
 * - Iterative: O(n) for an array, OR O(1) if only using two variables.
 * * 3. PERFORMANCE:
 * - Memoization: Slightly slower due to function call overhead and 
 * potential StackOverflow for very large 'n'.
 * - Iterative: Generally faster and more memory-efficient as it avoids 
 * the overhead of recursion.
 * * 4. INTUITION:
 * - Memoization: Easier to write if you already have a recursive 
 * mathematical formula.
 * - Iterative: Often considered the "final" optimized production version.
 */

