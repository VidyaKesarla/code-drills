class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        //first i add all the frequencies of each barcode in the hashmap
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int code: barcodes){
            map.put(code, map.getOrDefault(code, 0) + 1);
        }

        //create a maxHeap ->which will contain heap based on the frequency
        PriorityQueue <Integer> maxHeap = new PriorityQueue<>((a,b)-> map.get(b) - map.get(a));
        maxHeap.addAll(map.keySet());

        //we need a result array which will contain the elements shuffled
        int[] result = new int[barcodes.length];
        int index = 0;

        //we should be processing only two elements in the heap at a time
        while(maxHeap.size() >= 2){
            //poll first two codes
            int code1 = maxHeap.poll();
            int code2 = maxHeap.poll();

            result[index++] = code1;
            result[index++] = code2;

            //decrement the frequencies of the codes 
            map.put(code1, map.get(code1) - 1);
            map.put(code2, map.get(code2) - 1);

            if(map.get(code1) > 0){
                maxHeap.add(code1);
            }

            if(map.get(code2) > 0){
                maxHeap.add(code2);
            }
        }

            if(!maxHeap.isEmpty()){
                //add remaining code to result arr
                result[index] = maxHeap.poll();
            }
        return result;
    }
}

/*
 * PROBLEM: Rearrange barcodes such that no two adjacent barcodes are the same.
 * It is guaranteed a valid answer always exists.
 *
 * ─────────────────────────────────────────────
 * INTUITION
 * ─────────────────────────────────────────────
 * The only way two identical barcodes end up adjacent is if one value
 * dominates and we place it carelessly. So the safest move at every step
 * is: place the TWO most frequent remaining barcodes side by side.
 *
 * Why does this work?
 * - The most frequent value is the "most dangerous" — it's most likely
 *   to collide with itself.
 * - By always pairing it with the second most frequent, we spread it out
 *   as fast as possible while filling the array.
 * - A max-heap automates "who's most frequent right now?" after every
 *   placement, without re-sorting from scratch each round.
 *
 * Think of it like dealing cards: always deal from the two biggest piles
 * alternately — neither pile ever stacks on top of itself.
 *
 * ─────────────────────────────────────────────
 * DATA STRUCTURES — why both?
 * ─────────────────────────────────────────────
 * HashMap  → value → count   ("barcode 1 has freq 3")
 * Max-Heap → count → value   ("the highest-freq barcode right now is 1")
 *
 * They serve opposite lookup directions. Neither can replace the other:
 * - Heap alone: can't decrement or re-check frequencies in O(1)
 * - HashMap alone: can't find the top-2 without scanning everything each round
 *
 * ─────────────────────────────────────────────
 * DRY RUN  —  input: [1, 1, 1, 2, 2, 3]
 * ─────────────────────────────────────────────
 * Step 0 — build freq map:
 *   freqMap = {1:3, 2:2, 3:1}
 *   heap    = [1(3), 2(2), 3(1)]   ← sorted by freq descending
 *
 * Step 1 — poll code1=1, code2=2
 *   result  = [1, 2, _, _, _, _]
 *   freqMap = {1:2, 2:1, 3:1}
 *   heap    = [1(2), 2(1), 3(1)]   ← re-insert both (freq > 0)
 *
 * Step 2 — poll code1=1, code2=2
 *   result  = [1, 2, 1, 2, _, _]
 *   freqMap = {1:1, 2:0, 3:1}
 *   heap    = [1(1), 3(1)]          ← 2 dropped (freq == 0)
 *
 * Step 3 — poll code1=1, code2=3
 *   result  = [1, 2, 1, 2, 1, 3]  ✓
 *   freqMap = {1:0, 2:0, 3:0}
 *   heap    = []                    ← both dropped
 *
 * ─────────────────────────────────────────────
 * BRUTE FORCE vs THIS APPROACH
 * ─────────────────────────────────────────────
 *
 * Brute Force:
 *   Generate all permutations, check each for adjacent duplicates.
 *   Time:  O(n!)  — completely infeasible for large n
 *   Space: O(n)
 *
 * Sorting-based greedy (no heap):
 *   Re-sort the frequency map after every round to find top-2.
 *   Time:  O(n * k log k)  where k = unique barcode count
 *   Space: O(k)
 *   Works, but redundantly re-sorts unchanged entries every round.
 *
 * This approach (max-heap):
 *   Heap maintains order incrementally — only the two touched entries
 *   are re-inserted, costing O(log k) each.
 *   Time:  O(n log k)  — n placements, each O(log k) heap ops
 *   Space: O(k)        — heap + freqMap both store k unique values
 *
 * ─────────────────────────────────────────────
 * COMPLEXITY SUMMARY
 * ─────────────────────────────────────────────
 * Time:  O(n log k)  where n = barcodes.length, k = unique barcodes
 *        In the worst case k = n, so O(n log n)
 * Space: O(k)        for the heap and frequency map
 */

