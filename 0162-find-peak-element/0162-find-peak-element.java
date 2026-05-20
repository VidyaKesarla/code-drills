class Solution {
    //iterative binary search
    public int findPeakElement(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        while(l < r){
            //find middle
            int mid = (l + r ) / 2;
            if(nums[mid] > nums[mid+1]){
                //we are on a descending slope, is my right neighbor bigger? peak is on the right go right
                r = mid;
            } else {
                //is my right neighbor smaller? peak is on the left go left
                l = mid + 1;
            }
        }
        return l;
    }
}