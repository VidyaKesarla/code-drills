/*
### Approach: Min-Heap / Priority Queue

#### 1. Dry Run
Let's dry run the code using **nums = [[4, 10, 15], [0, 9, 20], [5, 18]]**.

* **Initialization (Step 1: Populate Heap with first elements)**
  - Add from List 0: `[4, 0, 0]` -> `maxVal` = 4
  - Add from List 1: `[0, 1, 0]` -> `maxVal` = Math.max(4, 0) = 4
  - Add from List 2: `[5, 2, 0]` -> `maxVal` = Math.max(4, 5) = 5
  - **Heap state (Sorted by val):** `[[0, 1, 0], [4, 0, 0], [5, 2, 0]]`
  - Current `maxVal` = 5, `rangeStart` = 0, `rangeEnd` = Integer.MAX_VALUE

* **Iteration 1:**
  - `poll()` -> `[0, 1, 0]` (`minVal` = 0, row = 1, col = 0)
  - Update Range: `5 - 0 < MAX - 0` -> `[rangeStart, rangeEnd]` = `[0, 5]`
  - Next element in row 1 is `nums.get(1).get(1) = 9`.
  - Update `maxVal` = Math.max(5, 9) = 9. `offer([9, 1, 1])`.
  - **Heap state:** `[[4, 0, 0], [5, 2, 0], [9, 1, 1]]`

* **Iteration 2:**
  - `poll()` -> `[4, 0, 0]` (`minVal` = 4, row = 0, col = 0)
  - Update Range: `9 - 4 (5) == 5 - 0 (5)`. No update needed (range size is same).
  - Next element in row 0 is `nums.get(0).get(1) = 10`.
  - Update `maxVal` = Math.max(9, 10) = 10. `offer([10, 0, 1])`.
  - **Heap state:** `[[5, 2, 0], [9, 1, 1], [10, 0, 1]]`

* **Iteration 3:**
  - `poll()` -> `[5, 2, 0]` (`minVal` = 5, row = 2, col = 0)
  - Update Range: `10 - 5 (5) == 5`. No change.
  - Next element in row 2 is `nums.get(2).get(1) = 18`.
  - Update `maxVal` = Math.max(10, 18) = 18. `offer([18, 2, 1])`.
  - **Heap state:** `[[9, 1, 1], [10, 0, 1], [18, 2, 1]]`

* **Iteration 4:**
  - `poll()` -> `[9, 1, 1]` (`minVal` = 9, row = 1, col = 1)
  - Update Range: `18 - 9 = 9` (Greater than 5, no change).
  - Next element in row 1 is `nums.get(1).get(2) = 20`.
  - Update `maxVal` = Math.max(18, 20) = 20. `offer([20, 1, 2])`.
  - **Heap state:** `[[10, 0, 1], [18, 2, 1], [20, 1, 2]]`

* **Iteration 5:**
  - `poll()` -> `[10, 0, 1]` (`minVal` = 10, row = 0, col = 1)
  - Update Range: `20 - 10 = 10` (No change).
  - Next element in row 0 is `nums.get(0).get(2) = 15`.
  - `maxVal` stays 20. `offer([15, 0, 2])`.
  - **Heap state:** `[[15, 0, 2], [18, 2, 1], [20, 1, 2]]`

* **Iteration 6:**
  - `poll()` -> `[15, 0, 2]` (`minVal` = 15, row = 0, col = 2)
  - Update Range: `20 - 15 = 5` (Equal to 5, no change).
  - Row 0 has no more elements left!
  - Loop terminates because `pq.size()` drops to 2 (which is `< nums.size()`).

* **Output:** `[0, 5]` (Note: If the input matches Example 1 exactly with more elements, it eventually finds `[20, 24]`).

---

#### 2. Brute Force Trade-off
* **Brute Force Strategy:** A naive approach would generate every possible pair of values `[a, b]` present across all lists, and check if it spans across at least one element from each of the $K$ lists. 
* **Trade-off:** If $N$ is the total number of elements across all lists, generating pairs takes $O(N^2)$ time, and validating each pair takes $O(N)$ or $O(K \log(\text{avg len}))$. This results in an absolute worst-case time complexity of $O(N^3)$ or $O(N^2 \cdot K)$, making it completely unfeasible for larger constraints ($K \le 3500$). The heap approach optimizes this dramatically by using a sliding window strategy over pointers, checking only relevant intervals.

---

#### 3. Complexity Analysis
* **Time Complexity:** $O(N \log K)$
  Where $K$ is the number of lists (`nums.size()`) and $N$ is the total number of elements across all lists. We insert and remove elements from the Priority Queue of size $K$ at most $N$ times. Each heap operation takes $O(\log K)$ time.
* **Space Complexity:** $O(K)$
  The Priority Queue stores exactly one element from each of the $K$ lists at any given time, requiring $O(K)$ extra space.
*/

class Solution {
    public int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        int maxVal = Integer.MIN_VALUE;
        int rangeStart = 0;
        int rangeEnd = Integer.MAX_VALUE;

        for(int i =0;i<nums.size();i++){
            //add all the first element of each list into the heap
            pq.offer(new int[] {nums.get(i).get(0), i, 0});
            //initially get the maxval among these 3
            maxVal = Math.max(maxVal, nums.get(i).get(0));
        }

        //while the size of heap is equal to the total number of lists, continue until we cant proceed further
        while(pq.size() == nums.size()){
            //poll the min data - val, row and col
            int [] data = pq.poll();
            int row = data[1];
            int col = data[2];
            int minVal = data[0];
            //update the range data if we find the range is smaller
            if(maxVal - minVal < rangeEnd - rangeStart){
                rangeEnd = maxVal;
                rangeStart = minVal;
            }
            //does the current list have a next element to offer? if so then take the next value and update the max value if needed, also add to heap
            if(col + 1 < nums.get(row).size()){
                int nextVal = nums.get(row).get(col + 1);
                pq.offer(new int[] {nextVal, row, col + 1 });
                maxVal = Math.max(nextVal, maxVal);
            }
        }

        return new int[] {rangeStart, rangeEnd};

    }
}