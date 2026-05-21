class Solution {

    private int minimumSubarraysRequired(int[]nums, int maxSumAllowed){
        int currentSum = 0;
        int splitsRequired = 0;

        for(int element: nums){
            //add elemetns only if the sum doesnt exceed maxSumAllowed
            if(currentSum + element <= maxSumAllowed){
                currentSum += element;
            } else {
                //if the element addition makes sum more than maxSumAllowed increment the splits requied and reset sum
                currentSum = element;
                splitsRequired++;
            }
        }
        //return the number of subarrays
        return splitsRequired + 1;
    }


    public int splitArray(int[] nums, int k) {
        //here i will assume that there is a number X which is the minimum largest subarray sum with m subarrays
        //first i will make sure that this number X is greateer than or equal to the max number in the array


        //start from the 0th id, and keep adding elements to it, only if they do not make the sum greater than X


        //If adding the element would make the sum greate than X then we have to split the subarray here, so we will increment the splitsReqd count 


        //take a sum variable which will store the current sum
        int sum = 0;
        //take the max Element and initialise it to Integer max 
        int maxElement = Integer.MIN_VALUE;

        for(int element: nums){
            sum = sum + element;
            maxElement = Math.max(maxElement, element);
        }
        //defining left and right boundary for binary search
        int left = maxElement;
        int right = sum;

        int minimumLargestSplitSum = 0;

        while(left<=right){
            //find the mid value or X that we guess
            int maxSumAllowed = left + (right - left)/2;
            //find the minimum number of splits, if its less than or equal to k, move towards left, smaller values
            if(minimumSubarraysRequired(nums, maxSumAllowed) <= k) {
                right = maxSumAllowed - 1;
                minimumLargestSplitSum = maxSumAllowed;
            } else {
                left = maxSumAllowed + 1;
            }
        }

        return minimumLargestSplitSum;
    }
}

/*
### 🧠 Intuition: Dynamic Programming vs. Binary Search on Answer

When faced with a "Minimize the Maximum" or "Maximize the Minimum" problem pattern, 
our minds typically jump to either Dynamic Programming or Binary Search on Answer. 
Here is how they stack up against each other, along with a deep-dive dry run.

---

### 📊 Algorithmic Trade-offs & Approaches

1. Brute Force (Backtracking / Recursion)
- Intuition: Try every possible combination of placing k-1 dividers between the elements 
  of the array and find the configuration that yields the minimum possible maximum subarray sum.
- Trade-off: Excessive redundant computations. It explores a massive combination tree, 
  making it practically useless (O((N-1)C(K-1))) for arrays larger than 20–30 elements.

2. Dynamic Programming (DP)
- Intuition: Break the problem down into smaller subproblems. Let DP[i][j] represent the 
  minimized largest sum of splitting the first 'i' elements into 'j' subarrays. To find DP[i][j], 
  we look at all possible split points 'p' (where j-1 <= p < i) and take the minimum of the 
  maximum between the previous subproblem and our new partition:
  DP[i][j] = min_p( max(DP[p][j-1], sum(nums[p+1...i])) )
- Trade-off: Highly structured and deterministic, but it handles the constraints linearly over 
  subproblems. It requires extra memory to preserve states (O(N * K) space) and takes O(N^2 * K) time.

3. Binary Search (The Optimized Approach)
- Intuition: Instead of trying to figure out *where* to place the splits, we guess the *target 
  maximum subarray sum* (X). 
  We know the absolute tightest upper bound for a single subarray cannot be smaller than the 
  largest element in the array (maxElement), because a single element cannot be split. Similarly, 
  the absolute maximum possible sum is the sum of the entire array (sum). 

  Because this search space is strictly sorted [maxElement ... sum], and the feasibility behaves 
  monotonically (if a max sum of X works, any capacity X+1 will also work), we can binary search 
  for the answer! For every middle value, we use a greedy approach to see if we can partition the 
  array into <= k groups without exceeding that middle value.

---

### ⚙️ Complexity Comparison

| Approach          | Time Complexity                   | Space Complexity | Pros / Cons                                  |
| :---------------- | :-------------------------------- | :--------------- | :------------------------------------------- |
| Brute Force       | O((N-1)C(K-1))                    | O(N)             | TLE for even modest constraints.             |
| DP                | O(N^2 * K)                        | O(N * K)         | Reliable, but slow and memory-heavy.        |
| Binary Search     | O(N * log(sum - maxElement))      | O(1)             | Fast, memory efficient, needs monotonicity.  |

---

### 🏃‍♂️ Step-by-Step Dry Run of Binary Search

Let's trace the optimized binary search code using Example 1: nums = [7, 2, 5, 10, 8] and k = 2.

1. Initialization
   - sum = 7 + 2 + 5 + 10 + 8 = 32
   - maxElement = max(7, 2, 5, 10, 8) = 10
   - Initial search range: left = 10, right = 32

2. Search Loops

   * Iteration 1
     - maxSumAllowed = 10 + (32 - 10)/2 = 21
     - Check minimumSubarraysRequired(nums, 21):
       - Elements 7, 2, 5 easily fit -> currentSum = 14
       - Next element 10: 14 + 10 = 24 > 21 -> Split! splitsRequired = 1, currentSum = 10
       - Next element 8: 10 + 8 = 18 <= 21 -> currentSum = 18
       - Returns splitsRequired + 1 = 2 subarrays.
     - Result: Since 2 <= k, 21 is a valid threshold. Record minimumLargestSplitSum = 21, 
       search lower half: right = 20.

   * Iteration 2
     - maxSumAllowed = 10 + (20 - 10)/2 = 15
     - Check minimumSubarraysRequired(nums, 15):
       - 7, 2, 5 fit -> currentSum = 14
       - 10: 14 + 10 = 24 > 15 -> Split! splitsRequired = 1, currentSum = 10
       - 8: 10 + 8 = 18 > 15 -> Split! splitsRequired = 2, currentSum = 8
       - Returns splitsRequired + 1 = 3 subarrays.
     - Result: Since 3 > k, 15 is too small. Search upper half: left = 16.

   * Iteration 3
     - maxSumAllowed = 16 + (20 - 16)/2 = 18
     - Check minimumSubarraysRequired(nums, 18):
       - 7, 2, 5 fit -> currentSum = 14
       - 10: 14 + 10 = 24 > 18 -> Split! splitsRequired = 1, currentSum = 10
       - 8: 10 + 8 = 18 <= 18 -> currentSum = 18
       - Returns splitsRequired + 1 = 2 subarrays.
     - Result: Since 2 <= k, 18 is valid. Record minimumLargestSplitSum = 18, 
       search lower half: right = 17.

   * Iteration 4
     - maxSumAllowed = 16 + (17 - 16)/2 = 16
     - Check minimumSubarraysRequired(nums, 16):
       - 7, 2, 5 fit -> currentSum = 14
       - 10: 14 + 10 = 24 > 16 -> Split! splitsRequired = 1, currentSum = 10
       - 8: 10 + 8 = 18 > 16 -> Split! splitsRequired = 2, currentSum = 8
       - Returns 3 subarrays. (Invalid, 3 > k). Search upper half: left = 17.

   * Iteration 5
     - maxSumAllowed = 17 + (17 - 17)/2 = 17
     - Check minimumSubarraysRequired(nums, 17):
       - Returns 3 subarrays because 10 and 8 still force splits. (Invalid, 3 > k). 
       - Search upper half: left = 18.

3. Termination
   The loop ends because left (18) > right (17). 
   The function correctly returns our tracked optimal state: minimumLargestSplitSum = 18.
*/