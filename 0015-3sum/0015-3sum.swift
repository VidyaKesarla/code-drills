// ============================================================
// PROBLEM: 3Sum
// Given an array, find all unique triplets that sum to zero.
//
// INTUITION:
// After sorting, fix one element and use two pointers to find
// pairs summing to its negation. Sorting makes duplicate-skipping
// easy by letting us compare adjacent elements.
//
// APPROACH:
// 1. Sort the array
// 2. Fix index i, use left = i+1 and right = n-1
// 3. Move pointers based on sum:
//    sum == 0 → record, skip duplicates, move both pointers
//    sum < 0  → left++  (need larger sum)
//    sum > 0  → right-- (need smaller sum)
// 4. Early exit: if nums[i] > 0, impossible to reach sum = 0
//
// WHY WE SKIP DUPLICATES:
// ─────────────────────────────────────────────────────────────
// Suppose sorted array has [..., -1, -1, ...] at positions i=1, i=2
// Both would produce the SAME triplets since they're equal values.
// We skip i=2 (duplicate of i=1) to avoid pushing the same triplet
// into result again.
//
// Same logic for left and right pointers:
// After recording a triplet [-1, 0, 1], if nums[left+1] == nums[left],
// moving left by 1 would just re-examine the same pair → duplicate triplet.
// So we skip ahead past all equal values before moving inward.
//
// In short: skipping duplicates ensures every triplet in result is UNIQUE.
//
// TIME COMPLEXITY:  O(n²)
//   - Sorting        → O(n log n)
//   - Outer for loop → O(n)
//   - Inner while    → O(n) per iteration
//   - Total          → O(n log n) + O(n²) = O(n²)
//
// SPACE COMPLEXITY: O(1) auxiliary
//   - Only pointer variables used (left, right, sum)
//   - Output array is not counted as auxiliary space
//
// ─────────────────────────────────────────────────────────────
// DRY RUN
// Input:       [-1, 0, 1, 2, -1, -4]
// After sort:  [-4, -1, -1,  0,  1,  2]
//               idx: 0   1   2   3   4  5
//
// ── i=0, nums[i]=-4 ──────────────────────────────────────────
//   left=1, right=5 → -4+(-1)+2 = -3 < 0 → left++
//   left=2, right=5 → -4+(-1)+2 = -3 < 0 → left++
//   left=3, right=5 → -4+  0 +2 = -2 < 0 → left++
//   left=4, right=5 → -4+  1 +2 = -1 < 0 → left++
//   left=5 == right → STOP. No triplet.
//
// ── i=1, nums[i]=-1 ──────────────────────────────────────────
//   left=2, right=5 → -1+(-1)+2 = 0 ✅ → save [-1,-1,2]
//     skip left dup?  nums[2]==nums[3]? -1==0? No
//     skip right dup? nums[5]==nums[4]?  2==1? No
//     left=3, right=4
//   left=3, right=4 → -1+  0 +1 = 0 ✅ → save [-1,0,1]
//     skip left dup?  nums[3]==nums[4]? 0==1? No
//     skip right dup? nums[4]==nums[3]? 1==0? No
//     left=4, right=3
//   left(4) >= right(3) → STOP. Found: [-1,-1,2], [-1,0,1]
//
// ── i=2, nums[i]=-1 ──────────────────────────────────────────
//   nums[2]==nums[1] → -1==-1 → SKIP (duplicate of i=1)
//
// ── i=3, nums[i]=0 ───────────────────────────────────────────
//   left=4, right=5 → 0+1+2 = 3 > 0 → right--
//   left(4) == right(4) → STOP. No triplet.
//
// ── i=4, nums[i]=1 ───────────────────────────────────────────
//   nums[4]=1 > 0 → BREAK (can't reach sum=0 with larger values)
//
// RESULT: [[-1,-1,2], [-1,0,1]]
// ============================================================

class Solution {
    func threeSum(_ nums: [Int]) -> [[Int]] {
        let nums = nums.sorted()
        let n = nums.count
        var result = [[Int]]()

        for i in 0..<n {
            // Early exit: if smallest remaining is positive, sum can never be 0
            if nums[i] > 0 { break }

            // Skip duplicate values for i to avoid duplicate triplets in result
            if i > 0 && nums[i] == nums[i-1] {
                continue
            }

            var left = i + 1
            var right = n - 1

            while left < right {
                let sum = nums[i] + nums[left] + nums[right]

                if sum == 0 {
                    result.append([nums[i], nums[left], nums[right]])

                    // Skip duplicate values on left to avoid recording same triplet again
                    while left < right && nums[left] == nums[left + 1] {
                        left += 1
                    }
                    // Skip duplicate values on right to avoid recording same triplet again
                    while left < right && nums[right] == nums[right - 1] {
                        right -= 1
                    }
                    left += 1
                    right -= 1

                } else if sum < 0 {
                    left += 1  // Need a larger value to increase sum
                } else {
                    right -= 1 // Need a smaller value to decrease sum
                }
            }
        }
        return result
    }
}
