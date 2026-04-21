/**
 * # Intuition
 * The goal is to generate all possible orderings of a distinct set of integers. 
 * Instead of building a new list from scratch and checking for duplicates (which is slow), 
 * we can view the problem as "filling slots." By swapping elements in the original array, 
 * we can systematically place every available number into the current slot and move on to 
 * the next one, ensuring we cover every combination without extra search overhead.
 *
 * # Approach
 * 1. **Pointer Strategy**: Use an 'index' to track which position in the array we are currently deciding.
 * 2. **Swapping**: For the current 'index', loop through all elements from 'i = index' to 'nums.length - 1'.
 * - Swap nums[i] with nums[index] to "choose" that number for the current slot.
 * - Recurse: Call backtrack(index + 1) to fill the remaining slots.
 * - Backtrack: Swap nums[i] and nums[index] back to restore the array for the next loop iteration.
 * 3. **Base Case**: When 'index == nums.length', the array represents a unique permutation. 
 * At this point, we take a "snapshot" by converting the array into a list and adding it to the result.
 *
 * # Complexity
 * - Time complexity: O(N * N!)
 * There are N! permutations. For each, we perform O(N) work to copy the array into the final result list.
 * - Space complexity: O(N)
 * This is the depth of the recursion stack. (Excluding the space required to store the N! permutations).
 *
 * # Dry Run Example (nums = [1, 2, 3])
 * - solve(0): i=0, swap(0,0) -> [1, 2, 3]
 * - solve(1): i=1, swap(1,1) -> [1, 2, 3]
 * - solve(2): i=2, swap(2,2) -> [1, 2, 3] -> snapshot [1, 2, 3]
 * - solve(1) backtrack: i=2, swap(2,1) -> [1, 3, 2]
 * - solve(2): i=2, swap(2,2) -> [1, 3, 2] -> snapshot [1, 3, 2]
 * - solve(0) backtrack: i=1, swap(1,0) -> [2, 1, 3] ... (repeat for all branches)
 *
 * # Why it's better than Brute Force
 * Standard brute force often uses `list.contains()`, which is O(N), making the total time O(N^2 * N!).
 * This swap-based approach is O(N * N!) and uses O(1) auxiliary space (excluding recursion stack)
 * because it doesn't require a 'used' boolean array or a Set to track visited numbers.
 *
 * # Follow-up Q&A
 * Q: What if there are duplicates?
 * A: You would need a HashSet inside the loop at each level to ensure you don't swap the same value 
 * into the same index twice.
 * Q: Why copy the array at the base case?
 * A: Because the original array is modified during backtracking. If you don't take a snapshot, 
 * all entries in your result would eventually point to the same final state of the array.
 */

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, result);
        return result;
    }

    private void backtrack(int index, int[] nums, List<List<Integer>> result) {
        // Base case: All positions filled
        if (index == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }

        for (int i = index; i < nums.length; i++) {
            swap(i, index, nums);           // Choice: Put nums[i] at position 'index'
            backtrack(index + 1, nums, result); // Explore: Fill the next slot
            swap(i, index, nums);           // Undo: Restore array for next iteration
        }
    }

    private void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}


/**
 * # RECURSION STACK & INDEX TRACE (nums = [1, 2, 3])
 * * | Call Stack       | index | i | Action       | Array State | Event/Note          |
 * |------------------|-------|---|--------------|-------------|---------------------|
 * | 1. solve(0)      | 0     | 0 | swap(0, 0)   | [1, 2, 3]   | Fixed '1' at pos 0  |
 * | 2. solve(1)      | 1     | 1 | swap(1, 1)   | [1, 2, 3]   | Fixed '2' at pos 1  |
 * | 3. solve(2)      | 2     | 2 | swap(2, 2)   | [1, 2, 3]   | Fixed '3' at pos 2  |
 * | 4. solve(3)      | 3     | - | Base Case    | [1, 2, 3]   | SNAPSHOT: [1, 2, 3] |
 * | (Backtrack 2)    | 1     | 2 | swap(2, 1)   | [1, 3, 2]   | Swap '3' into pos 1 |
 * | 5. solve(2)      | 2     | 2 | swap(2, 2)   | [1, 3, 2]   | Fixed '2' at pos 2  |
 * | 6. solve(3)      | 3     | - | Base Case    | [1, 3, 2]   | SNAPSHOT: [1, 3, 2] |
 * | (Backtrack 0)    | 0     | 1 | swap(1, 0)   | [2, 1, 3]   | Swap '2' into pos 0 |
 * | 7. solve(1)      | 1     | 1 | swap(1, 1)   | [2, 1, 3]   | Fixed '1' at pos 1  |
 * | 8. solve(2)      | 2     | 2 | swap(2, 2)   | [2, 1, 3]   | Fixed '3' at pos 2  |
 * | 9. solve(3)      | 3     | - | Base Case    | [2, 1, 3]   | SNAPSHOT: [2, 1, 3] |
 * | (Backtrack 7)    | 1     | 2 | swap(2, 1)   | [2, 3, 1]   | Swap '3' into pos 1 |
 * | 10. solve(2)     | 2     | 2 | swap(2, 2)   | [2, 3, 1]   | Fixed '1' at pos 2  |
 * | 11. solve(3)     | 3     | - | Base Case    | [2, 3, 1]   | SNAPSHOT: [2, 3, 1] |
 * | (Backtrack 0)    | 0     | 2 | swap(2, 0)   | [3, 2, 1]   | Swap '3' into pos 0 |
 * | 12. solve(1)     | 1     | 1 | swap(1, 1)   | [3, 2, 1]   | Fixed '2' at pos 1  |
 * | 13. solve(2)     | 2     | 2 | swap(2, 2)   | [3, 2, 1]   | Fixed '1' at pos 2  |
 * | 14. solve(3)     | 3     | - | Base Case    | [3, 2, 1]   | SNAPSHOT: [3, 2, 1] |
 * | (Backtrack 12)   | 1     | 2 | swap(2, 1)   | [3, 1, 2]   | Swap '1' into pos 1 |
 * | 15. solve(2)     | 2     | 2 | swap(2, 2)   | [3, 1, 2]   | Fixed '2' at pos 2  |
 * | 16. solve(3)     | 3     | - | Base Case    | [3, 1, 2]   | SNAPSHOT: [3, 1, 2] |
 */

