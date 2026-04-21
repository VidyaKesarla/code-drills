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
// why does this take more space than permutations 1?

//  The short answer is **the HashSet**. In Permutations I, we didn't need to "remember" anything at each level; we just swapped and moved on. In Permutations II, we need a "memory" to stop us from repeating the same mistakes.

// Here is the breakdown of why that creates a larger space footprint:

// ### 1. The "Local Memory" Cost
// In **Permutations I**, your recursive function looks like this:
// * `backtrack(index, nums)`
// * *Space used:* Only the memory for the function call itself (stack frame).

// In **Permutations II**, your function looks like this:
// * `backtrack(index, nums)`
// * `Set<Integer> usedInThisSlot = new HashSet<>();`
// * *Space used:* The stack frame **PLUS** a brand new object in memory (the Set).

// ### 2. Cumulative Space (The Stack)
// Remember that recursion doesn't just run once; it stacks up. If $N=4$:
// * **Level 0** is active and creates a Set (Size $\approx 4$).
// * It calls **Level 1**, which stays active and creates another Set (Size $\approx 3$).
// * It calls **Level 2**, which stays active and creates another Set (Size $\approx 2$).
// * It calls **Level 3**, which stays active and creates another Set (Size $\approx 1$).

// All these Sets exist **simultaneously** in your computer's memory while you are sitting at the base case. 

// ### 3. The Math ($O(N)$ vs $O(N^2)$)
// * **Permutations I:** You have $N$ levels of recursion. Each level uses constant $O(1)$ space for its variables. 
//     * $N \times 1 = \mathbf{O(N)}$
// * **Permutations II:** You have $N$ levels of recursion. But now, each level $i$ uses $O(N-i)$ space to store the unique numbers it has seen so far.
//     * $N + (N-1) + (N-2) \dots + 1 = \frac{N(N+1)}{2} = \mathbf{O(N^2)}$

// ### The "Trade-off"
// You are trading **Space** ($O(N^2)$) for **Time**. 

// By using that extra memory (the Set), you tell the computer: *"Hey, remember that I already tried putting a '1' in this slot. Don't waste time doing it again."* This prevents the algorithm from exploring thousands of redundant paths, making it much faster for inputs with many duplicates, even though it uses a bit more RAM.

// **Is there a way to do it in $O(N)$ space?**
// Yes! If you **sort** the array first and use the `boolean[] used` approach we mentioned earlier, you don't need a HashSet at every level. You only need one single boolean array of size $N$. This brings the space back down to $O(N)$, which is why many people prefer the "Sort + Boolean Array" method for Permutations II in tight memory environments.