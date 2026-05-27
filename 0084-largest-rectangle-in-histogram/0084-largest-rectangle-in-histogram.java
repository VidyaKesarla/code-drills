class Solution {
    public int largestRectangleArea(int[] heights) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;
        stack.push(-1);
        int length = heights.length;
        for(int i=0;i<length;i++){
            while(stack.peek() != -1 && (heights[stack.peek()] >= heights[i])){
                int currentHeight = heights[stack.pop()];
                int currentWidth = i - stack.peek() - 1;
                maxArea = Math.max(maxArea, currentHeight * currentWidth);
            }
            stack.push(i);
        }
        while(stack.peek() != -1){
                int currentHeight = heights[stack.pop()];
                int currentWidth = length - stack.peek() - 1;
                maxArea = Math.max(maxArea, currentHeight * currentWidth);
            }
        return maxArea;
    }
}