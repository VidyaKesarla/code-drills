// /*
// Here we can use the approach:
// (prefix[j] - prefix[i])%k == 0

// we can express this in terms of divisor, dividend, remainder

// prefix[j] = Q1 * k + R1 
// prefix[i] = Q2 * k + R2

// so 
// ((Q1-Q2)) * k + (R1-R2)) % k = 0

// Q1-Q2 * k is divisible by k. 
// R1-R2 is also divisible by k

// we can infer => R1,R2 both lie in the range 0,k

// R1-R2 = 0

// prefix[j] % k = prefix[i] % k

// */


// class Solution {
//     public boolean checkSubarraySum(int[] nums, int k) {
//         int prefixMod = 0;
//         //last seen mod lookup
//         HashMap<Integer, Integer> modSeen = new HashMap<>();
//         modSeen.put(0,-1);

//         for(int i=0;i<nums.length;i++){
//             prefixMod = (prefixMod + nums[i]) %k;
//             if(modSeen.containsKey(prefixMod)){
//                 if(i-modSeen.get(prefixMod) > 1){
//                     return true;
//                 } else {
//                     modSeen.put(prefixMod, i);
//                 }
//             }
//         }
//         return false;
//     }
// }

/*
## Intuition
The core idea is based on modular arithmetic: if two different prefix sums have the 
same remainder when divided by `k`, the sum of the elements between those two 
indices must be a multiple of `k`.

Mathematically: 
If `Sum(0...j) % k = R` and `Sum(0...i) % k = R`, 
then `(Sum(0...j) - Sum(0...i)) % k = 0`.
This difference represents the subarray `Sum(i+1...j)`.

---

## Dry Run
Input: `nums = [23, 2, 4, 6, 7]`, `k = 6`
- **Initial:** `modSeen = {0: -1}`, `prefixMod = 0`
- **i = 0 (23):** `prefixMod = 23 % 6 = 5`. `modSeen = {0: -1, 5: 0}`
- **i = 1 (2):** `prefixMod = (5 + 2) % 6 = 1`. `modSeen = {0: -1, 5: 0, 1: 1}`
- **i = 2 (4):** `prefixMod = (1 + 4) % 6 = 5`. 
  - `5` is in `modSeen` at index `0`. 
  - Length check: `2 - 0 = 2`. 
  - `2 > 1` is true. **Return true.**

---

## Brute Force Bottleneck
The brute force approach involves checking every possible subarray `(i, j)`:
1. Iterate `i` from `0` to `n`.
2. Iterate `j` from `i+1` to `n`.
3. Sum elements from `i` to `j` and check `% k == 0`.

**Bottleneck:** The $O(n^2)$ time complexity makes it unfeasible for large 
arrays (e.g., $n = 10^5$). We are re-calculating sums repeatedly without 
leveraging previous computations.

---

## Edge Cases
1. **k is larger than the total sum:** Should return false unless a subarray 
   sum is 0 (which is $0 * k$).
2. **Multiple zeros:** `[0, 0]` should return true (sum is 0, which is $0 * k$).
3. **Subarray at the very beginning:** This is why we initialize `modSeen.put(0, -1)`. 
   If the first two elements sum to `k`, `prefixMod` becomes 0 at `i=1`. 
   `1 - (-1) = 2`, which satisfies the length requirement.
4. **Subarray of length 1:** The problem usually requires a length of at least 2. 
   The logic `i - modSeen.get(prefixMod) > 1` strictly handles this.

---

## Tradeoffs
- **HashMap vs. Array:** We use a `HashMap` to handle cases where `k` is very large 
  (e.g., $10^9$). If `k` was guaranteed to be small (e.g., $k < 10^6$), an 
  integer array `int[] seen = new int[k]` would be faster due to constant time 
  indexing and better cache locality.
- **Space vs. Time:** We sacrifice $O(min(n, k))$ space to achieve $O(n)$ time.

---

## Complexity
- **Time Complexity (TC):** $O(n)$
  We traverse the array exactly once. HashMap operations (put/get) are $O(1)$ on average.
- **Space Complexity (SC):** $O(min(n, k))$
  In the worst case, we store each unique remainder in the Map. The number of 
  entries is limited by the size of the array `n` and the divisor `k`.

---

## Code Correction Note
In your provided code, the `else` block `modSeen.put(prefixMod, i);` should 
**not** execute if the key is already found. If you update the index, you 
shorten the distance for future checks, potentially missing a valid subarray. 
The first occurrence of a remainder is the most "powerful" one for satisfying 
the length > 1 condition.
*/

class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        int prefixMod = 0;
        HashMap<Integer, Integer> modSeen = new HashMap<>();
        modSeen.put(0, -1);

        for (int i = 0; i < nums.length; i++) {
            prefixMod = (prefixMod + nums[i]) % k;
            
            if (modSeen.containsKey(prefixMod)) {
                if (i - modSeen.get(prefixMod) > 1) {
                    return true;
                }
                // Do NOT update the index here. Keep the oldest index.
            } else {
                modSeen.put(prefixMod, i);
            }
        }
        return false;
    }
}