/*
💡 Binary Search on the Answer: Detailed Breakdown

🚀 Approach:
Instead of guessing the eating speed K, we observe that if Koko can finish all bananas 
at speed X, she can also finish them at any speed > X. This monotonicity allows 
us to binary search for the minimum speed in the range [1, max(piles)].

📊 Brute Force vs. Optimal:
- Brute Force: Check K=1, K=2, K=3... until it works. Time: O(N * M)
- Binary Search: Bisect the speed range. Time: O(N * log M)
(N = number of piles, M = max pile size)

🖍️ Dry Run: Example 3
Input: piles = [30, 11, 23, 4, 20], h = 6
Initial Range: low = 1, high = 30

1. Mid = 15: Hours = 2+1+2+1+2 = 8. (8 > 6). Too slow! Update low = 16.
2. Mid = 23: Hours = 2+1+1+1+1 = 6. (6 <= 6). Works! Update high = 23.
3. Mid = 19: Hours = 2+1+2+1+2 = 8. (8 > 6). Too slow! Update low = 20.
4. Mid = 21: Hours = 2+1+2+1+1 = 7. (7 > 6). Too slow! Update low = 22.
5. Mid = 22: Hours = 2+1+2+1+1 = 7. (7 > 6). Too slow! Update low = 23.
6. End: low meets high at 23.

⚙️ Complexity Analysis:
- Time Complexity (TC): O(N log M) 
- Space Complexity (SC): O(1)

📝 Implementation Detail:
We use the integer ceiling trick (a + k - 1) / k to avoid floating-point 
precision issues and overhead from Double/ceil().
*/

class Solution {
    func minEatingSpeed(_ piles: [Int], _ h: Int) -> Int {
        var low = 1
        var high = piles.max() ?? 1000000000
        
        while low < high {
            let mid = low + (high - low) / 2
            
            if canFinish(piles, h, mid) {
                // Current speed works, try slower to find the minimum
                high = mid
            } else {
                // Too slow, must increase speed
                low = mid + 1
            }
        }
        return low
    }
    
    private func canFinish(_ piles: [Int], _ h: Int, _ k: Int) -> Bool {
        var totalHours = 0
        for pile in piles {
            // Equivalent to ceil(Double(pile) / Double(k))
            totalHours += (pile + k - 1) / k
        }
        return totalHours <= h
    }
}