/**
 * ## Intuition
 * The naive recursive approach (brute force) to find the $n$-th Fibonacci number solves 
 * the exact same subproblems repeatedly, leading to exponential time complexity. 
 * By using Memoization (Top-Down Dynamic Programming), we store the result of each subproblem 
 * the first time it is computed. When the same subproblem arises again, we simply look it up 
 * in $O(1)$ time, drastically cutting down redundant work.
 *
 * ## Trade-off: Brute Force vs. Memoization
 * - **Brute Force ($O(2^n)$ Time, $O(n)$ Space):** Very simple to implement but completely 
 * impractical for larger values of $n$ due to the massive, overlapping recursive tree. 
 * - **Memoization ($O(n)$ Time, $O(n)$ Space):** We introduce an auxiliary array (`storage`) 
 * to cache results. This eliminates duplicate calculations, trading a tiny bit of predictable 
 * memory for a massive, game-changing boost in execution speed.
 *
 * ## Complexity
 * - **Time Complexity:** $O(n)$ — Each Fibonacci number from $0$ to $n$ is calculated exactly once. 
 * Subsequent calls for the same value hit the cache and return in $O(1)$ time.
 * - **Space Complexity:** $O(n)$ — Two factors contribute to this: the allocation of the `storage` 
 * array of size $n + 1$, and the maximum depth of the recursive call stack, which reaches $n$.
 *
 * ---
 *
 * ## Dry Run (Example: $n = 4$)
 *
 * 1. **Initialization:**
 * - `fib(4)` is called.
 * - An array `storage` of size 5 ($n+1$) is created and filled with `-1`: `[-1, -1, -1, -1, -1]`.
 * - Calls `helper(4, storage)`.
 *
 * 2. **Execution Tree Flow:**
 * - **`helper(4)`**: Not a base case, `storage[4] == -1`. Calls `helper(3)`.
 * - **`helper(3)`**: Not a base case, `storage[3] == -1`. Calls `helper(2)`.
 * - **`helper(2)`**: Not a base case, `storage[2] == -1`. Calls `helper(1)`.
 * - **`helper(1)`**: Base case! Returns **1**.
 * - `helper(2)` resumes and calls its second branch: `helper(0)`.
 * - **`helper(0)`**: Base case! Returns **0**.
 * - `helper(2)` calculates `ans = 1 + 0 = 1`. Updates `storage[2] = 1`. Returns **1**.
 * - `helper(3)` resumes and calls its second branch: `helper(1)`.
 * - **`helper(1)`**: Base case! Returns **1**.
 * - `helper(3)` calculates `ans = 1 (from helper(2)) + 1 (from helper(1)) = 2`. Updates `storage[3] = 2`. Returns **2**.
 * - `helper(4)` resumes and calls its second branch: `helper(2)`.
 * - **`helper(2)`**: Cache Hit! `storage[2]` is not `-1` (it is `1`). Immediately returns **1** without recalculating.
 * - `helper(4)` calculates `ans = 2 (from helper(3)) + 1 (from helper(2)) = 3`. Updates `storage[4] = 3`. Returns **3**.
 *
 * 3. **Final State of Array:** `[-1, -1, 1, 2, 3]` (Indices 0 and 1 remain un-cached since they trigger immediate base cases).
 * 4. **Output:** `3`
 */
class Solution {
    public int fib(int n) {
        // Base case
        if(n==1 || n==0){
            return n;
        }
        //Initialize the storage array exactly ONCE
        int []storage = new int[n+1];
        Arrays.fill(storage,-1);
        //Call the helper method, passing the shared array
        return helper(n, storage);
    }

    public int helper(int n, int[] storage){
        // Base case
        if (n == 1 || n == 0) {
            return n;
        }
        // memoization : If fib(n) already calculated, return it (O(1) time)
        if (storage[n] != -1) {
            return storage[n];
        }

// Recursive calls (Array is passed down)
        int ans = helper(n - 1, storage) + helper(n - 2, storage);
        //store whatever result you have calculated
        storage[n] = ans;
        return ans;

    }
}

