// """
// =======================================================================
//   215. Kth Largest Element in an Array
// =======================================================================

// PROBLEM
// -------
// Find the kth largest element in an unsorted array.
// Note: kth largest means kth largest in sorted order, not kth distinct.

// Example: nums = [3, 2, 1, 5, 6, 4], k = 2  →  5

// =======================================================================
//   APPROACH 1 — BRUTE FORCE (Sort)
// =======================================================================

// IDEA
// ----
// Sort the array in descending order, return the element at index k-1.

// DRY RUN  nums = [3, 2, 1, 5, 6, 4],  k = 2
// ---------
//   After sort (desc):  [6, 5, 4, 3, 2, 1]
//                             ^
//                          index 1  →  return 5  ✓

// COMPLEXITY
// ----------
//   Time  : O(n log n)  — cost of sorting
//   Space : O(n)        — merge sort aux space (O(log n) for in-place quicksort)

// """

// def kth_largest_brute(nums, k):
//     nums.sort(reverse=True)
//     return nums[k - 1]


// """
// =======================================================================
//   APPROACH 2 — MIN-HEAP  (Optimal for small k)
// =======================================================================

// IDEA
// ----
// Maintain a min-heap of size exactly k.
//   • If heap size < k  → push freely.
//   • If heap size = k  → only push if val > heap[0] (the current minimum),
//                         then pop the minimum out.

// After all n elements are processed, the heap holds the k largest values
// seen so far. The smallest among them — heap[0] — is the kth largest.

// WHY MIN-HEAP AND NOT MAX-HEAP?
// -------------------------------
// A max-heap would need the full array to extract the kth element.
// A min-heap of size k lets us evict the "weakest contender" instantly
// (it's always at the top), keeping only the k strongest at any time.

// DRY RUN  nums = [3, 2, 1, 5, 6, 4],  k = 2
// ---------
//   heap = []

//   val=3  → size 0 < k=2, push  → heap: [3]
//   val=2  → size 1 < k=2, push  → heap: [2, 3]   (min-heap: 2 on top)
//   val=1  → size=k=2, 1 < heap[0]=2, skip        heap: [2, 3]
//   val=5  → size=k=2, 5 > heap[0]=2, push+pop 2  heap: [3, 5]
//   val=6  → size=k=2, 6 > heap[0]=3, push+pop 3  heap: [5, 6]
//   val=4  → size=k=2, 4 < heap[0]=5, skip        heap: [5, 6]

//   heap[0] = 5  →  return 5  ✓

//   The heap always holds the 2 largest seen so far.
//   Its minimum (top) is by definition the 2nd largest overall.

// COMPLEXITY
// ----------
//   Time  : O(n log k)
//             — n elements processed, each push/pop costs O(log k)
//             — when k << n this is far faster than O(n log n)

//   Space : O(k)
//             — heap never exceeds k elements

// BRUTE FORCE vs MIN-HEAP (summary)
// ----------------------------------
//   ┌─────────────────┬────────────┬─────────┐
//   │ Approach        │ Time       │ Space   │
//   ├─────────────────┼────────────┼─────────┤
//   │ Brute (sort)    │ O(n log n) │ O(n)    │
//   │ Min-Heap        │ O(n log k) │ O(k)    │
//   └─────────────────┴────────────┴─────────┘

//   When k=2 and n=10^6: heap does ~10^6 ops vs sort's ~20*10^6 ops.

// """
// Brute force space scales with the input size. Heap space scales with k, your answer parameter. When k is small (e.g. top 10 from a million items), the heap uses a tiny fraction of what brute force needs.
/*
Min-Heap approach for Kth Largest:
Time: O(n log k)
You process each of the n elements once. For every element, you do a heap push or pop — both are O(log k) because the heap never grows beyond size k. So it’s n × log k total.
Space: O(k)
The heap holds at most k elements at any time. That’s all the extra memory used — no copy of the input, nothing else.


Brute Force (sort the array):
	∙	O(n) — sorting algorithms like merge sort need O(n) auxiliary space, or O(log n) for in-place quicksort. Either way you’re working on the full array.
Min-Heap:
	∙	O(k) — the heap holds at most k elements, and k ≤ n, so this is always at most O(n) but usually much better.

*/
class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        //add k elements to the priority queue
        for (int i=0;i<k;i++){
            pq.add(nums[i]);
        }
        for(int i=k;i<nums.length;i++){
            if(pq.peek() < nums[i]){
                pq.remove();
                pq.add(nums[i]);
            }
        }
        return pq.peek();
    }
}

/*
TC: KlogK + (n-k)logk => O(nlogk)
SC: O(logk)

Tree Height: A binary heap is a complete binary tree which means it is always balanced. For k elements, the height of the tree is approximately 
.
*/