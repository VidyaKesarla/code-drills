class Solution {
    public int trap(int[] height) {
        //take two pointerS:
        int left = 0;
        int right = height.length - 1;
        int maxLeft = 0;
        int maxRight = 0;

        //declare sum resultant
        int sum = 0;

        while (left < right){
            if(height[left]<height[right]){
                if(height[left] >= maxLeft){
                    maxLeft = height[left];
                } else {
                    sum += maxLeft - height[left];
                }
                left++;
            } else {
                if(height[right] >= maxRight){
                    maxRight = height[right];
                } else {
                    sum += maxRight - height[right];
                }
                right--;
            }
        }
        return sum;  
    }
}