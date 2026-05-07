// class Solution {
//     public int jump(int[] nums) {
//         int answer = 0;
//         int currFar = 0;
//         int currEnd = 0;
//         int n = nums.length;

//         for(int i=0;i<n-1;i++){
//             currFar = Math.max(currFar, i + nums[i]);
//             if(i == currEnd){
//                 answer++;
//                 currEnd = currFar;
//             }
//         }
//         return answer;
//     }
// }

/*
## Intuition: The "Window of Opportunity" (Greedy)
The problem asks for the minimum jumps to reach the end. We use a greedy strategy 
by maintaining a "current window" of indices we can reach. 

1. `currFar` is our "scout"—it tells us the farthest possible index we could reach 
   in the *next* jump by exploring every index in our current range.
2. `currEnd` is the boundary of our "current jump." Once we hit this boundary, 
   it means we must have made a jump to reach the best possible spot found by 
   the scout, so we increment `answer` and update the boundary to `currFar`.

---

## Dry Run: nums = [2, 3, 1, 1, 4]
Initial: answer = 0, currFar = 0, currEnd = 0

- i = 0 (val: 2):
    - currFar = max(0, 0 + 2) = 2
    - i == currEnd (0 == 0):
        - answer = 1
        - currEnd = 2 (We've now committed to a jump that covers up to index 2)

- i = 1 (val: 3):
    - currFar = max(2, 1 + 3) = 4
    - i != currEnd (1 != 2): (Still within our first jump's range)

- i = 2 (val: 1):
    - currFar = max(4, 2 + 1) = 4
    - i == currEnd (2 == 2):
        - answer = 2
        - currEnd = 4 (We've reached the end of our first jump's reach, so we jump again)

- i = 3 (val: 1):
    - currFar = max(4, 3 + 1) = 4
    - i != currEnd (3 != 4)

Result: 2

---

## Brute Force Bottleneck
A standard Recursion or DFS/BFS approach would explore every single possible 
jump from every index.
- **Bottleneck:** This results in $O(2^n)$ or $O(n^2)$ with memoization. 
- The greedy approach eliminates the need to backtrack because we only care 
  about the *maximum* reach at any given step.

---

## Edge Cases
1. **Array length 1:** `[7]`. The loop `i < n - 1` (0 < 0) will not execute. 
   Returns `0`. This is correct; you are already at the end.
2. **Maximum jump at the start:** `[10, 1, 1, 1]`. `currFar` will immediately 
   become 10, and at `i=0`, `answer` becomes 1 and `currEnd` becomes 10. 
   The loop finishes without jumping again.
3. **Linear jumps:** `[1, 1, 1, 1]`. `answer` will increment at every single step.

---

## Tradeoffs
- **Greedy vs DP:** This greedy approach is $O(n)$, whereas a DP approach 
  (finding min jumps for each index) is $O(n^2)$. Greedy is significantly 
  faster and uses less memory.
- **Assumption:** This logic assumes you can *always* reach the end. If it 
  were possible to get stuck, you'd need a check to see if `i` ever exceeds `currFar`.

---

## Complexity
- **Time Complexity (TC):** $O(n)$
  We iterate through the array once.
- **Space Complexity (SC):** $O(1)$
  We only store four integer variables.
*/

class Solution {
    public int jump(int[] nums) {
        int answer = 0;
        int currFar = 0;
        int currEnd = 0;
        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            currFar = Math.max(currFar, i + nums[i]);
            if (i == currEnd) {
                answer++;
                currEnd = currFar;
            }
        }
        return answer;
    }
}