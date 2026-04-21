/**
 * # Intuition
 * This problem adds two key constraints to Combination Sum I: 
 * 1. Each number can be used only **once**.
 * 2. The input array contains **duplicates**, but the output must be unique.
 * To solve this, we move the pointer forward (`i + 1`) to prevent reuse, and use sorting
 * combined with a skip-logic check to prevent duplicate combinations at the same level.
 *
 * # Approach
 * 1. **Sorting**: Mandatory. It groups duplicate values together (`[1, 1, 2]`), allowing us
 * to easily identify and skip redundant branches.
 * 2. **Skip Logic**: `if (i > start && candidates[i] == candidates[i-1]) continue;`
 * This ensures that at any specific position in our combination, we only try a unique
 * value once. If there are two '1's, we only start a branch with the first '1'.
 * 3. **Pointer Movement**: Pass `i + 1` to the recursive call. This ensures we never 
 * re-pick the same physical element.
 * 4. **Pruning**: Since the array is sorted, if `candidates[i] > target`, we `break` 
 * because all subsequent numbers will also be too large.
 *
 * # Complexity
 * - Time complexity: O(2^N)
 * In the worst case, every element is either included or excluded.
 * - Space complexity: O(N)
 * The recursion stack depth is at most N.
 */

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        // 1. Sort to handle duplicates and enable pruning
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
            // 2. SKIP DUPLICATE VALUES at the same level
            // i > start ensures we don't skip a number when it's the first one being 
            // considered for the current slot.
            if (i > start && candidates[i] == candidates[i - 1]) continue;

            // 3. PRUNING
            if (candidates[i] > target) break;

            path.add(candidates[i]);
            // 4. FORWARD ONLY: Move to i + 1 (no reuse)
            backtrack(i + 1, candidates, target - candidates[i], path, result);
            path.remove(path.size() - 1);
        }
    }
}

/**
 * # RECURSION STACK & INDEX TRACE (candidates = [1, 1, 2], target = 3)
 * | Call Stack             | target | i | Action        | Path    | Note                      |
 * |------------------------|--------|---|---------------|---------|---------------------------|
 * | solve(start=0, tar=3)  | 3      | 0 | pick 1 (idx 0)| [1]     | 1st slot: tried '1'       |
 * | solve(start=1, tar=2)  | 2      | 1 | pick 1 (idx 1)| [1,1]   | 2nd slot: tried '1'       |
 * | solve(start=2, tar=1)  | 1      | 2 | pick 2 (idx 2)| [1,1,2] | tar < 0 (Return)          |
 * | (Backtrack to tar=2)   | 2      | 2 | pick 2 (idx 2)| [1,2]   | 2-2=0! SNAPSHOT: [1,2]    |
 * | (Backtrack to tar=3)   | 3      | 1 | **SKIP** | [ ]     | i=1, start=0. 1 == 1!     |
 * |                        |        |   |               |         | Prevents repeating [1, 2] |
 * | solve(start=0, tar=3)  | 3      | 2 | pick 2 (idx 2)| [2]     | 1st slot: tried '2'       |
 *
 * # Follow-up Q&A
 * Q: Why "i > start" and not "i > 0"?
 * A: "i > start" means "is this the first time this loop is looking at a number for THIS slot?" 
 * If we used "i > 0", we could never pick the second '1' in [1, 1, 2], effectively 
 * breaking the ability to form [1, 1].
 *
 * Q: How does this differ from Combination Sum I?
 * A: I allows reuse (`backtrack(i, ...)`) and has distinct inputs. 
 * II forbids reuse (`backtrack(i + 1, ...)`) and handles duplicates with sorting + skipping.
 */