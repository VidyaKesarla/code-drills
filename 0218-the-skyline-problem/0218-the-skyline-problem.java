/*
 * ═══════════════════════════════════════════════════════════════════
 * THE SKYLINE PROBLEM — TreeMap Frequency Approach
 * ═══════════════════════════════════════════════════════════════════
 *
 * INTUITION:
 * ──────────
 * At any x-coordinate, we need to know: "What is the tallest active building?"
 *
 * Instead of two separate priority queues (live + past), we use ONE
 * TreeMap<height, count> — a sorted frequency map.
 *
 * Think of it as a LEADERBOARD that:
 *   → Auto-sorts heights in descending order
 *   → Tracks how many buildings at each height are currently "alive"
 *   → The moment a building ends, we decrement its count (paying off "debt")
 *   → If count hits 0, we evict that height — exactly what the `past`
 *     queue was doing, but handled implicitly via counts
 *
 * WHY TreeMap OVER TWO HEAPS (live + past)?
 * ──────────────────────────────────────────
 *   ✔ TreeMap gives O(log n) insert/delete + O(1) max via firstKey()
 *   ✔ No need for a separate `past` queue — count IS the lazy-deletion tracker
 *   ✔ Collections.reverseOrder() keeps the largest height at firstKey() always
 *
 * WHY BETTER THAN BRUTE FORCE?
 * ─────────────────────────────
 *   Brute Force  → Check every x-point against every building → O(n × width)
 *   This Approach→ Event-driven: only process boundary x-values → O(n log n)
 *
 *   ┌─────────────────┬──────────────────────┬──────────────────────┐
 *   │     Aspect      │     Brute Force      │    This Approach     │
 *   ├─────────────────┼──────────────────────┼──────────────────────┤
 *   │ Max height query│ O(n) scan per x-point│ O(1) via firstKey()  │
 *   │ Insert / Remove │ —                    │ O(log n) per event   │
 *   │ Overall TC      │ O(n × width)         │ O(n log n)           │
 *   │ Space           │ O(width)             │ O(n)                 │
 *   └─────────────────┴──────────────────────┴──────────────────────┘
 *
 * ═══════════════════════════════════════════════════════════════════
 * ALGORITHM
 * ═══════════════════════════════════════════════════════════════════
 *
 * Step 1 → Build `events`: for each building [left, right, height]:
 *            Add [left,  -height]  → negative = left edge  (building starts)
 *            Add [right, +height]  → positive = right edge (building ends)
 *
 * Step 2 → Sort `events` by x first; if tied, by height value (ascending)
 *            Handles simultaneous starts/ends correctly (explained below)
 *
 * Step 3 → Initialize TreeMap with {0 → 1} as ground-level sentinel
 *            so firstKey() never throws on an empty map
 *
 * Step 4 → Process each event:
 *            Left edge  (h < 0) → increment count of -h in map
 *            Right edge (h > 0) → decrement count of h; remove if count = 0
 *            Query firstKey() → if changed from prevMax, record skyline point
 *
 * ═══════════════════════════════════════════════════════════════════
 * DRY RUN
 * ═══════════════════════════════════════════════════════════════════
 *
 * Input: [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
 *
 * Events built:
 *   [2,-10], [9,10], [3,-15], [7,15], [5,-12], [12,12],
 *   [15,-10], [20,10], [19,-8], [24,8]
 *
 * Events after sort (by x, then by height value):
 *   [2,-10], [3,-15], [5,-12], [7,15], [9,10], [12,12],
 *   [15,-10], [19,-8], [20,10], [24,8]
 *
 * Processing:
 * ┌──────┬───────┬──────────┬──────────────────────────┬─────────┬─────────────┐
 * │  x   │   h   │  Action  │        heights map       │ curMax  │   result    │
 * ├──────┼───────┼──────────┼──────────────────────────┼─────────┼─────────────┤
 * │  2   │  -10  │ ADD  10  │ {10:1, 0:1}              │  10     │ ✅ [2, 10]  │
 * │  3   │  -15  │ ADD  15  │ {15:1, 10:1, 0:1}        │  15     │ ✅ [3, 15]  │
 * │  5   │  -12  │ ADD  12  │ {15:1, 12:1, 10:1, 0:1}  │  15     │ ❌ no change│
 * │  7   │  +15  │ DEL  15  │ {12:1, 10:1, 0:1}        │  12     │ ✅ [7, 12]  │
 * │  9   │  +10  │ DEL  10  │ {12:1, 0:1}              │  12     │ ❌ no change│
 * │  12  │  +12  │ DEL  12  │ {0:1}                    │   0     │ ✅ [12, 0]  │
 * │  15  │  -10  │ ADD  10  │ {10:1, 0:1}              │  10     │ ✅ [15, 10] │
 * │  19  │  -8   │ ADD   8  │ {10:1, 8:1, 0:1}         │  10     │ ❌ no change│
 * │  20  │  +10  │ DEL  10  │ {8:1, 0:1}               │   8     │ ✅ [20, 8]  │
 * │  24  │  +8   │ DEL   8  │ {0:1}                    │   0     │ ✅ [24, 0]  │
 * └──────┴───────┴──────────┴──────────────────────────┴─────────┴─────────────┘
 *
 * Result: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]] ✅
 *
 * ═══════════════════════════════════════════════════════════════════
 * COMPLEXITY
 * ═══════════════════════════════════════════════════════════════════
 *
 * Time  → O(n log n)
 *   Building events  : O(n)
 *   Sorting events   : O(n log n)  ← dominates
 *   Processing events: O(n log n)  each event does O(log n) TreeMap op
 *
 * Space → O(n)
 *   events list      : O(n)  — 2 entries per building
 *   heights TreeMap  : O(n)  — at most n distinct heights active at once
 *   result list      : O(n)  — at most 2n skyline points
 */
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {

        List<List<Integer>> result = new ArrayList<>();
        // Stores the final skyline key points as [x, height] pairs

        List<int[]> events = new ArrayList<>();
        // Each event is [x_coordinate, height_marker]
        // Negative height  → building STARTS  (left edge)
        // Positive height  → building ENDS    (right edge)

        /*
         * WHY negative for left edge?
         * During sort, if two events share the same x:
         *   → A start(-15) sorts BEFORE an end(+10) because -15 < 10
         *   → This ensures we ADD a building before REMOVING one at the same x
         *   → Prevents false zero-height gaps at building junctions
         *
         *   Timeline visual at x=3:
         *   [-15 processed first] → height rises to 15   ✔
         *   [+10 processed after] → correctly reflects overlap
         */
        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2]});  // left  edge: (x=left,  h=-height)
            events.add(new int[]{b[1],  b[2]});  // right edge: (x=right, h=+height)
        }

        /*
         * Sort by x first; if x is equal, sort by height value (ascending).
         *
         * Case 1 — two LEFT edges at same x:  [-15, -10]
         *   Taller building(-15) processed first → correct peak recorded first
         *
         * Case 2 — LEFT and RIGHT at same x:  [-10, +10]
         *   Left(-10) < Right(+10) → start before end
         *   Prevents a phantom [x, 0] dip between two adjacent buildings
         *   Example: buildings [0,2,3] and [2,4,3] → no dip at x=2
         *
         * Case 3 — two RIGHT edges at same x: [+8, +10]
         *   Shorter removal first → max height stays correct throughout
         */
        events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        /*
         * TreeMap in DESCENDING order → firstKey() always gives current max height.
         * Seeded with {0 → 1} as a ground-level sentinel so firstKey()
         * never throws NoSuchElementException on an empty map.
         *
         *   heights map visual (descending):
         *   ┌────────┬───────┐
         *   │ Height │ Count │  ← firstKey() = top of this table = current max
         *   ├────────┼───────┤
         *   │   15   │   1   │
         *   │   12   │   1   │
         *   │   10   │   2   │  ← two buildings at height 10 active simultaneously
         *   │    0   │   1   │  ← sentinel, always present
         *   └────────┴───────┘
         */
        TreeMap<Integer, Integer> heights = new TreeMap<>(Collections.reverseOrder());
        heights.put(0, 1);

        int prevMax = 0;
        // Tracks the last recorded skyline height to detect changes

        for (int[] event : events) {
            int x = event[0];
            int h = event[1];

            if (h < 0) {
                /*
                 * LEFT EDGE — building starts.
                 * Add its height to the live map (increment count).
                 *
                 *   Before: {10:1, 0:1}
                 *   Event : x=3, h=-15  →  ADD height=15
                 *   After : {15:1, 10:1, 0:1}
                 *              ↑ new tallest building just entered
                 */
                heights.put(-h, heights.getOrDefault(-h, 0) + 1);

            } else {
                /*
                 * RIGHT EDGE — building ends.
                 * Decrement its count. If count reaches 0, evict from map entirely.
                 * This is the implicit "past" queue — no separate structure needed!
                 *
                 *   Before: {15:1, 12:1, 10:2, 0:1}
                 *   Event : x=9, h=+10  →  count=2, decrement → count=1
                 *   After : {15:1, 12:1, 10:1, 0:1}   ← still alive (other building)
                 *
                 *   Before: {12:1, 10:1, 0:1}
                 *   Event : x=12, h=+12 →  count=1, so REMOVE key entirely
                 *   After : {10:1, 0:1}               ← height 12 fully evicted
                 */
                int count = heights.get(h);
                if (count == 1) {
                    heights.remove(h);          // last building at this height is gone
                } else {
                    heights.put(h, count - 1);  // other buildings at same height still alive
                }
            }

            /*
             * Query current max height — O(1) with TreeMap.
             * If it changed from the previously recorded height,
             * we've found a new skyline critical point.
             *
             *   prevMax=10, curMax=15  →  rising edge  → record [x, 15] ✅
             *   prevMax=15, curMax=12  →  falling edge → record [x, 12] ✅
             *   prevMax=12, curMax=12  →  no change    → skip           ❌
             */
            int curMax = heights.firstKey();
            if (curMax != prevMax) {
                result.add(Arrays.asList(x, curMax));
                prevMax = curMax;
            }
        }

        return result;
        // Final skyline: list of [x, height] critical points
    }
}