// class Solution {
//     public int shortestSubarray(int[] nums, int k) {
//         //array length
//         int n = nums.length;
//         //initialise shortest subarray to this length
//         int shortestSubarrayLength = Integer.MAX_VALUE;
//         //take prefix sum and initialise it to 0
//         long cumulativeSum = 0;

//         //create a prefix sum heap which will return the lowwest on top of it
//         PriorityQueue<Pair<Long, Integer>> prefixSumHeap = new PriorityQueue<>(
//             (a, b) -> Long.compare(a.getKey(), b.getKey())
//         );


//         for(int i =0;i<n;i++){
//             //i calculate the cumulative sum by adding each number
//             cumulativeSum += nums[i];
//             if(cumulativeSum >= k){
//                 //if i find that the cumulative sum is greater than or equal to k then i calculate the shortestsubarraylength
//                 shortestSubarrayLength = Math.min(
//                     shortestSubarrayLength,
//                     i + 1
//                 );
//             }
//             while(!prefixSumHeap.isEmpty() && cumulativeSum - prefixSumHeap.peek().getKey() >= k){
//             // Update shortest subarray length
//                 shortestSubarrayLength = Math.min(
//                     shortestSubarrayLength,
//                     i - prefixSumHeap.poll().getValue()
//                 );

//                 // Add current cumulative sum and index to heap
//             prefixSumHeap.offer(new Pair<>(cumulativeSum, i));
//         }
//         }

        


        

//         // Return -1 if no valid subarray found
//         return shortestSubarrayLength == Integer.MAX_VALUE
//             ? -1
//             : shortestSubarrayLength;

//     }
// }

import java.util.*;

class Solution {
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] P = new long[n + 1];
        for (int i = 0; i < n; i++) {
            P[i + 1] = P[i] + nums[i];
        }
        
        int result = n + 1;
        Deque<Integer> deque = new ArrayDeque<>();
        
        for (int i = 0; i < P.length; i++) {
            while (!deque.isEmpty() && P[i] - P[deque.peekFirst()] >= k) {
                result = Math.min(result, i - deque.pollFirst());
            }
            while (!deque.isEmpty() && P[i] <= P[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        
        return result <= n ? result : -1;
    }
}