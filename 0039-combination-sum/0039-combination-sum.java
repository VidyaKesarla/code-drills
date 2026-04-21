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

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        // Optional: Sorting candidates allows for early "break" instead of "continue"
        // Arrays.sort(candidates); 
        backtrack(0, candidates, target, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, int[] candidates, int target, List<Integer> path, List<List<Integer>> result) {
        // SUCCESS: We hit the target exactly
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // PRUNING: If current candidate is larger than remaining target, move to next
            // If sorted, we could 'break' here. Since not sorted, we 'continue'.
            if (candidates[i] > target) continue; 

            path.add(candidates[i]); // Choose
            
            // EXPLORE: Note we pass 'i' as the start index for the next call.
            // This is what allows us to reuse the number at 'i' again.
            backtrack(i, candidates, target - candidates[i], path, result);
            
            path.remove(path.size() - 1); // Backtrack (Un-choose)
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