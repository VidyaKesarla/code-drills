/**
 * # RECURSION STACK & INDEX TRACE (candidates = [2, 3, 6, 7], target = 7)
 * | Call Stack             | Target | i | Action        | Path      | Event/Note                |
 * |------------------------|--------|---|---------------|-----------|---------------------------|
 * | 1. solve(start=0, 7)   | 7      | 0 | pick 2        | [2]       | Try index 0 again         |
 * | 2. solve(start=0, 5)   | 5      | 0 | pick 2        | [2, 2]    | Try index 0 again         |
 * | 3. solve(start=0, 3)   | 3      | 0 | pick 2        | [2, 2, 2] | 7 - 2 - 2 - 2 = 1         |
 * | 4. solve(start=0, 1)   | 1      | 0 | pick 2        | [2,2,2,2] | 1-2 = -1 (STOP/Return)    |
 * | (Backtrack to 1)       | 1      | 1 | pick 3        | [2,2,2,3] | 1-3 = -2 (STOP/Return)    |
 * | (Backtrack to 3)       | 3      | 1 | pick 3        | [2, 2, 3] | 3-3 = 0! SNAPSHOT [2,2,3] |
 * | (Backtrack to 5)       | 5      | 1 | pick 3        | [2, 3]    | 5-3 = 2                   |
 * | 5. solve(start=1, 2)   | 2      | 1 | pick 3        | [2, 3, 3] | 2-3 = -1 (STOP/Return)    |
 * | 6. solve(start=1, 2)   | 2      | 2 | pick 6        | [2, 3, 6] | 2-6 = -4 (STOP/Return)    |
 * | (Backtrack to 7)       | 7      | 1 | pick 3        | [3]       | Skip 2 forever now        |
 * | 7. solve(start=1, 4)   | 4      | 1 | pick 3        | [3, 3]    | 4-3 = 1                   |
 * | 8. solve(start=1, 1)   | 1      | 2 | pick 6        | [3, 3, 6] | 1-6 = -5 (STOP/Return)    |
 * | (Backtrack to 7)       | 7      | 2 | pick 6        | [6]       | 7-6 = 1                   |
 * | 9. solve(start=2, 1)   | 1      | 2 | pick 6        | [6, 6]    | 1-6 = -5 (STOP/Return)    |
 * | (Backtrack to 7)       | 7      | 3 | pick 7        | [7]       | 7-7 = 0! SNAPSHOT [7]     |
 */

/**
 * # COMPARISON: BRUTE FORCE VS. OPTIMAL (PRUNING)
 * * ## 1. Brute Force (Standard Backtracking)
 * In the basic approach, the algorithm makes a recursive call for every candidate 
 * in the loop, regardless of its value. It relies on the base case `if (target < 0)` 
 * to stop the recursion.
 * - **Mechanism**: Uses `continue` or simply hits the negative base case.
 * - **Efficiency**: Explores many "dead-end" branches (e.g., if target is 1 and 
 * candidate is 100, it still calls the function).
 * * ## 2. Optimal Approach (Sorting + Pruning)
 * By sorting the `candidates` array first, we can implement "Pruning"—cutting off 
 * branches of the recursion tree before they are even created.
 * - **Mechanism**: If `candidates[i] > target`, we **break** the loop. 
 * - **Why it works**: Since the array is sorted, if `candidates[i]` is already too 
 * large to fit the remaining target, every number after it (`i+1, i+2...`) is 
 * guaranteed to be even larger and thus also invalid.
 * * ## Comparison Table
 * | Feature             | Brute Force Backtracking | Optimal (Sorted + Pruning)       |
 * |---------------------|--------------------------|----------------------------------|
 * | **Pre-processing** | None                     | Arrays.sort(candidates) O(NlogN) |
 * | **Loop Exit** | Completes all iterations | `break` early when value > target|
 * | **Recursion Depth** | Hits target < 0          | Never calls if target < value    |
 * | **Performance** | Standard                 | Significantly faster for large T |
 * * ## Visualizing Pruning (Target = 5, Candidates = [2, 3, 10, 15])
 * - **Brute**: Tries 2, Tries 3, **Calls 10 (fails)**, **Calls 15 (fails)**.
 * - **Optimal**: Tries 2, Tries 3, Sees 10 > 5 -> **BREAKS**. (Never even looks at 15).
 * * ## Complexity Note
 * While the worst-case Big O remains the same, pruning drastically reduces the 
 * *average-case* number of recursive calls, often by 50-80% in practical test cases.
 */

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        
        // Sorting is the prerequisite for the 'break' optimization (Pruning)
        Arrays.sort(candidates); 
        
        backtrack(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int[] candidates, int target, List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // OPTIMAL PRUNING:
            // Because candidates are sorted, if this one is too big, all following are too big.
            if (candidates[i] > target) break; 

            path.add(candidates[i]);
            // Stay at index 'i' to allow unlimited reuse
            backtrack(i, candidates, target - candidates[i], path, result);
            path.remove(path.size() - 1);
        }
    }
}

/**
 * # COMPLEXITY DERIVATION
 * - **Time Complexity: O(2^N)** or more accurately **O(N^(T/M))**
 * N is the number of candidates, T is target, M is the minimal value. 
 * The height of the tree is T/M. At each node, we have N choices.
 * - **Space Complexity: O(T/M)**
 * The maximum depth of the recursion stack is the target divided by the 
 * smallest candidate (e.g., target 10, smallest 2 -> depth 5).
 *
 * # WHY NO DUPLICATES?
 * By passing `i` as the `start` for the next recursive call, we ensure the 
 * algorithm can either:
 * 1. Stay at the same number (Reuse).
 * 2. Move to the next number (Forward).
 * It can NEVER go back to `i-1`. This "one-way street" logic is the 
 * mathematical way to ensure unique combinations.
 */