class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int currentSum = 0;
        int minLength = Integer.MAX_VALUE;

        for(int right = 0; right < nums.length; right++){
            //right pointer would just expand the window
            currentSum += nums[right];

            while(currentSum >= target){
                //whenever the current window sum it meets the target, then i am going to shrink it from the left to find a smaller length;
                minLength = Math.min(minLength, right - left + 1);
                currentSum = currentSum - nums[left];
                left++;
            }
        }

        return (minLength == Integer.MAX_VALUE ) ? 0 : minLength;
    }
}


/*
there is an inner while loop inside another for loop, isn't the time complexity O(n 
2
 )? The reason it is still O(n) is because the right pointer right can move n times and the left pointer left can move also n times in total. The inner loop is not running n times for each iteration of the outer loop. A sliding window guarantees a maximum of 2n window iterations. This is what is referred to as amortized analysis - even though the worst case for an iteration inside the for loop is O(n), it averages out to O(1) when you consider the entire runtime of the algorithm.

*
/


/*
We are not using any extra space other than a few integer variables:left, right, sumOfCurrentWindow, and res, which takes up constant space each.
*/