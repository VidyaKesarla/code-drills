/**
 * # Intuition
 * When the input array contains duplicates, the standard swapping logic will produce redundant 
 * permutations. To avoid this, we must ensure that for any specific position (index), we only 
 * process a unique value once. If we've already "tried" a '1' at the current slot, trying another 
 * '1' at the same slot would lead to an identical branch of possibilities.
 *
 * # Approach
 * 1. **Pointer Strategy**: Same as Permutations I, use an 'index' to track the current slot.
 * 2. **Local Tracking (The Key)**: In each `backtrack` call, initialize a `HashSet<Integer>`. 
 * This set tracks values already placed in the *current* slot for this specific recursion level.
 * 3. **Duplicate Check**:
 * - For each `i` from `index` to `length-1`, check if `nums[i]` exists in the `HashSet`.
 * - If it exists, skip it (`continue`).
 * - If not, add it to the set and proceed with the swap/recursion.
 * 4. **No Sorting Needed**: Because we use a `HashSet` to check for seen values at each level, 
 * this swap method does not require the input array to be sorted beforehand.
 *
 * # Complexity
 * - Time complexity: O(N * N!)
 * The total number of unique permutations is at most N!. For each, we perform O(N) work 
 * to copy the result.
 * - Space complexity: O(N^2)
 * The recursion stack is O(N). Additionally, at each of those N levels, we may store a 
 * HashSet containing up to N elements.
 *
 * # Dry Run Example (nums = [1, 1, 2])
 * - solve(0): i=0, val=1. HashSet={1}. Swap(0,0) -> [1, 1, 2].
 * - solve(1): i=1, val=1. HashSet={1}. Swap(1,1) -> [1, 1, 2].
 * - solve(2): i=2, val=2. HashSet={2}. Swap(2,2) -> [1, 1, 2] -> SNAPSHOT [1, 1, 2]
 * - solve(1) Backtrack: i=2, val=2. HashSet={1, 2}. Swap(2,1) -> [1, 2, 1].
 * - solve(2): i=2, val=1. HashSet={1}. Swap(2,2) -> [1, 2, 1] -> SNAPSHOT [1, 2, 1]
 * - solve(0) Backtrack: i=1, val=1. **HashSet already contains 1!** -> SKIP.
 * - solve(0) Backtrack: i=2, val=2. HashSet={1, 2}. Swap(2,0) -> [2, 1, 1]...
 *
 * # Why it's better than Brute Force
 * This approach prunes the recursion tree early. Instead of generating all duplicates and 
 * filtering them at the end, it never explores duplicate branches in the first place, 
 * saving significant time and memory.
 *
 * # Follow-up Q&A
 * Q: Can we do this without HashSet to save space?
 * A: Yes, by sorting the array first and using a `boolean[] used` array. That allows 
 * checking `if(i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;`.
 * Q: Does the HashSet need to be cleared?
 * A: No, because a new HashSet is created locally within each recursive function call's 
 * activation record on the stack.
 */

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, result);
        return result;
    }

    private void backtrack(int index, int[] nums, List<List<Integer>> result) {
        if (index == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }

        // Only keep track of values used at THIS position (index) in the recursion tree
        Set<Integer> usedInThisSlot = new HashSet<>();

        for (int i = index; i < nums.length; i++) {
            // If we have already tried this value in the current 'index' slot, skip it
            if (usedInThisSlot.contains(nums[i])) {
                continue;
            }

            usedInThisSlot.add(nums[i]);
            
            swap(i, index, nums);
            backtrack(index + 1, nums, result);
            swap(i, index, nums); // Backtrack
        }
    }

    private void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

/**
 * # RECURSION STACK & INDEX TRACE (nums = [1, 1, 2])
 * | Call Stack       | index | i | val | Action       | Array State | Event/Note              |
 * |------------------|-------|---|-----|--------------|-------------|-------------------------|
 * | 1. solve(0)      | 0     | 0 | 1   | swap(0, 0)   | [1, 1, 2]   | Set0: {1}               |
 * | 2. solve(1)      | 1     | 1 | 1   | swap(1, 1)   | [1, 1, 2]   | Set1: {1}               |
 * | 3. solve(2)      | 2     | 2 | 2   | swap(2, 2)   | [1, 1, 2]   | SNAPSHOT: [1, 1, 2]     |
 * | (Backtrack 2)    | 1     | 2 | 2   | swap(2, 1)   | [1, 2, 1]   | Set1: {1, 2}            |
 * | 4. solve(2)      | 2     | 2 | 1   | swap(2, 2)   | [1, 2, 1]   | SNAPSHOT: [1, 2, 1]     |
 * | (Backtrack 1)    | 0     | 1 | 1   | SKIP         | [1, 1, 2]   | 1 already in Set0!      |
 * | 5. solve(0)      | 0     | 2 | 2   | swap(2, 0)   | [2, 1, 1]   | Set0: {1, 2}            |
 * | 6. solve(1)      | 1     | 1 | 1   | swap(1, 1)   | [2, 1, 1]   | Set1: {1}               |
 * | 7. solve(2)      | 2     | 2 | 1   | swap(2, 2)   | [2, 1, 1]   | SNAPSHOT: [2, 1, 1]     |
 * | (Backtrack 6)    | 1     | 2 | 1   | SKIP         | [2, 1, 1]   | 1 already in Set1!      |
 */

 /**
 * # DERIVATION OF COMPLEXITY
 * * ## 1. Time Complexity: O(N * N!)
 * Time complexity is calculated as: (Total Recursive Nodes) x (Work per Node).
 * * - **Total Nodes (N!)**: In a permutation tree, the number of leaf nodes (final permutations) 
 * is exactly N! (N factorial). For the first slot we have N choices, the second N-1, etc.
 * - **Work per Base Case (N)**: When we reach the base case (index == nums.length), we must 
 * iterate through the array to convert it into a List: `for(int num : nums) { list.add(num); }`.
 * This is an O(N) operation.
 * - **Total**: Since we perform this O(N) copy for every one of the N! permutations, 
 * the total time is O(N * N!).
 * * ## 2. Space Complexity: O(N) or O(N^2)
 * We focus on auxiliary space (extra memory used by the algorithm, excluding the output).
 * * - **Recursion Stack (O(N))**: The maximum depth of the recursion tree is N. 
 * The computer must store at most N function calls on the stack at any one time.
 * - **HashSet Storage (O(N^2)) [Permutations II only]**: 
 * In Permutations II, we create a HashSet at every level of the recursion.
 * - Level 0: HashSet of size N
 * - Level 1: HashSet of size N-1
 * - Summing N + (N-1) + ... + 1 results in O(N^2) total space across the stack.
 * * ## Summary Table
 * | Problem          | Time Complexity | Space Complexity | Key Factor                       |
 * |------------------|-----------------|------------------|----------------------------------|
 * | Permutations I   | O(N * N!)       | O(N)             | N! permutations * O(N) copy      |
 * | Permutations II  | O(N * N!)       | O(N^2)           | Stack depth * Local HashSet size |
 */
/**
 * # WHY PERMUTATIONS II USES MORE SPACE THAN PERMUTATIONS I
 * * The increase from O(N) to O(N^2) space complexity is due to the "Local Memory" 
 * required to track duplicates at each level of the recursion.
 * * ## 1. The HashSet Overhead
 * - In **Permutations I**, each recursive call only stores a few primitive variables 
 * (index, i). This is O(1) space per frame, leading to O(N) total stack space.
 * - In **Permutations II**, every single recursive call initializes a `new HashSet<>()`. 
 * This set stays in memory as long as that specific recursive branch is active.
 * * ## 2. Cumulative Memory on the Stack
 * Because recursion "stacks" frames, these HashSets exist simultaneously:
 * - Level 0 (index 0) creates a Set to track values for the 1st slot (~N elements).
 * - Level 1 (index 1) creates a Set to track values for the 2nd slot (~N-1 elements).
 * - ...and so on, down to the base case.
 * * ## 3. The Mathematical Divergence
 * - **Permutations I**: (N levels) * (O(1) variables) = **O(N)**.
 * - **Permutations II**: N + (N-1) + (N-2) + ... + 1 = N(N+1)/2 = **O(N^2)**.
 * * ## 4. The Trade-off: Space vs. Time
 * We are trading **Space** (O(N^2) memory) for **Time** (Efficiency). 
 * By using the HashSet, we "remember" which values we've already tried at the current 
 * position. This allows us to "prune" the recursion tree, preventing the algorithm 
 * from wasting time exploring thousands of identical duplicate paths.
 * * ## 5. Can we get back to O(N)?
 * Yes. If memory is a constraint, we can **Sort** the array first and use a 
 * single global `boolean[] used` array. This avoids creating HashSets at every 
 * level and brings auxiliary space complexity back down to **O(N)**.
 */