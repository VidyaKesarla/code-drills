/**
 * 💡 Intuition & Approach
 * The problem asks for the n-th Tribonacci number, where each term is the sum of the preceding three terms.
 * * - Brute Force Approach: A naive recursive solution directly follows the mathematical definition:
 * `return tribonacci(n-1) + tribonacci(n-2) + tribonacci(n-3)`. However, this recalculates the same
 * subproblems repeatedly, leading to a massive exponential time complexity.
 * * - Optimal Approach (Space-Optimized DP): Instead of storing the entire history or using recursion,
 * we only ever need the last three numbers (`a`, `b`, and `c`) to calculate the next one (`curr`).
 * By shifting these variables forward in each iteration, we can solve the problem in linear time 
 * while keeping space usage strictly constant.
 * * ---
 * * ⚖️ Brute Force vs. Optimal Trade-offs
 * * Metric           | Brute Force (Naive Recursion)       | Optimal (Iterative Space-Optimized)
 * -----------------|-------------------------------------|------------------------------------
 * Time Complexity  | O(3^n) — Explosive exponential       | O(n) — A single linear pass 
 * | growth due to redundant math.       | from 3 to n.
 * -----------------|-------------------------------------|------------------------------------
 * Space Complexity | O(n) — Due to the recursive         | O(1) — Only uses 4 primitive
 * | call stack depth.                   | integer variables.
 * -----------------|-------------------------------------|------------------------------------
 * Pros             | Very literal and easy to write.     | Highly efficient, scales perfectly,
 * |                                     | no risk of stack overflow.
 * -----------------|-------------------------------------|------------------------------------
 * Cons             | TLE (Time Limit Exceeded) for even  | Slightly more manual tracking of
 * | modestly large inputs.              | state variables.
 * * ---
 * * ⏱️ Complexity Analysis
 * - Time Complexity (TC): O(n). The `for` loop runs exactly n - 2 times for any n >= 3, 
 * which simplifies to linear time.
 * - Space Complexity (SC): O(1). We only maintain four integer variables (`a`, `b`, `c`, and `curr`),
 * utilizing a fixed amount of memory regardless of the input size.
 * * ---
 * * 🔄 Dry Run (Example: n = 5)
 * * Initialization:
 * a = 0, b = 1, c = 1
 * Loop condition: i runs from 3 to 5.
 * * Iteration (i) | curr = a + b + c | Update a      | Update b      | Update c
 * --------------|------------------|---------------|---------------|-----------------
 * i = 3         | 0 + 1 + 1 = 2    | 1 (old b)     | 1 (old c)     | 2 (old curr)
 * i = 4         | 1 + 1 + 2 = 4    | 1 (old b)     | 2 (old c)     | 4 (old curr)
 * i = 5         | 1 + 2 + 4 = 7    | 2 (old b)     | 4 (old c)     | 7 (old curr)
 * * End of Loop: Returns c -> 7
 */
class Solution {
    public int tribonacci(int n) {
        if(n >= 0 && n <2)
{
    return n;
}    
    int a = 0;
    int b = 1;
    int c = 1;
    for(int i =3;i<=n;i++){
        int curr = a + b + c;
        a = b;
        b = c;
        c = curr;
    }
    return c;
    }
}