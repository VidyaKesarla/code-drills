
// ============================================================
// LC 239 — Sliding Window Maximum
// ============================================================
//
// ============================================================
// BRUTE FORCE APPROACH
// ============================================================
//
// IDEA: For every window position, scan all k elements for max
//
// func maxSlidingWindow(_ nums: [Int], _ k: Int) -> [Int] {
//     var result = [Int]()
//     for i in 0...(nums.count - k) {
//         var maxVal = Int.min
//         for j in i..<(i + k) {
//             maxVal = max(maxVal, nums[j])
//         }
//         result.append(maxVal)
//     }
//     return result
// }
//
// TIME:  O(nk)
//   - outer loop runs n-k+1 times (one per window)
//   - inner loop scans k elements each time
//   - for n=100000, k=50000 → 5 billion operations → TLE
//
// SPACE: O(1) auxiliary (result array not counted)
//
// WHY IT FAILS:
//   - repeats work already done in previous window
//   - when window slides by 1, we rescan k-1 elements we just saw
//   - no memory of what the previous window's max was
//

// ============================================================
// LC 239 — Sliding Window Maximum
// ============================================================
//
// APPROACH: Monotonic Deque (decreasing order of values)
//
// KEY INSIGHT:
// Deque stores INDICES (not values) in decreasing order of their values
// Front of deque = index of current window maximum
// Two separate rules — never confuse them:
//   BACK removal  → value too small (can never be max while i is in window)
//   FRONT removal → index too old (slid out of window boundary)
//
// TIME:  O(n)
//   - each index is pushed to deque exactly once
//   - each index is popped from deque at most once
//   - total operations across entire loop = O(2n) = O(n)
//
// SPACE: O(k)
//   - deque holds at most k indices at any time
//   - result array is O(n) but that's output, not auxiliary space
//
// ============================================================
// DRY RUN
// nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
//
// i  nums[i]  front-evict?           back-pop?          deque(idx) deque(val)  result
// 0    1      —                      —                  [0]        [1]         —
// 1    3      —                      3>1 → pop idx 0    [1]        [3]         —
// 2   -1      —                      -1<3 → no pop      [1,2]      [3,-1]      [3]   ← window full
// 3   -3      —                      -3<-1 → no pop     [1,2,3]    [3,-1,-3]   [3]
// 4    5      idx1 <= 4-3=1 → evict  5>all → pop 1,2,3  [4]        [5]         [5]
// 5    3      —                      3<5 → no pop       [4,5]      [5,3]       [5]
// 6    6      idx4 <= 6-3=3 → evict  6>3 → pop idx5     [6]        [6]         [6]
//             idx4 <= 3 → yes evict  6<5? no, 6>5 pop4
// 7    7      —                      7>6 → pop idx6     [7]        [7]         [7]
//
// Answer: [3, 3, 5, 5, 6, 7]
//
// INTERESTING EDGE CASE — strictly decreasing [5,4,3,2,1] k=3:
// Back-pop rule NEVER fires (each new value < previous)
// Deque stays full at exactly k elements at all times
// Front eviction fires every step once window is full
// Result = [5,4,3] — leftmost element always wins
// ============================================================
// FOLLOW-UPS
//
// Q1: Why store indices not values?
// A: Values give no location info — can't check if element
//    has slid out of window. Duplicate values make it worse.
//    Indices let you compute deque.front <= i-k precisely.
//
// Q2: At i=4, why is index 1 removed from the front?
// A: Window boundary rule: valid indices = [i-k+1 ... i] = [2...4]
//    Index 1 < 2 → outside window → evict from front
//    This is location disqualification, not value disqualification
//
// Q3: Why loop j in reverse when extending to k-transactions DP?
// A: Prevents using the same transaction twice in one pass
//    Forward loop would let dp[j] use an already-updated dp[j-1]
//    from the same price iteration = same transaction counted twice
//

class Solution {
    // Time: O(n) | Space: O(k)
/*Optimal solution
The deque stores indices (not values) in decreasing order of their values. Rules:
	1.	Before adding index i — remove from the back any index whose value is less than nums[i]. Those can never be max while i is in the window.
	2.	Remove from the front if that index has slid out of the window (deque.front <= i - k).
	3.	The front of the deque is always the index of the current maximum.
*/
    func maxSlidingWindow(_ nums: [Int], _ k: Int) -> [Int] {
        var deque = [Int]()
        var result = [Int]()

        for i in 0..<nums.count{
            // Remove indices outside window
            if !deque.isEmpty && deque.first! <= i-k {
                deque.removeFirst()
            }
            // Remove indices whose values < current (they can never be max)
            while(!deque.isEmpty && nums[deque.last!] < nums[i]){
                deque.removeLast()
            }

            deque.append(i)
            // Start recording once first window is complete
            if (i>=k-1){
                result.append(nums[deque.first!])
            }
        }
        return result
    }
}