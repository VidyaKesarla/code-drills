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

/*
 * ----------------------------------------------------------------------------------
 * LEETCODE 84: LARGEST RECTANGLE IN HISTOGRAM
 * ----------------------------------------------------------------------------------
 *
 * 1. BRUTE FORCE VS. MONOTONIC STACK TRADE-OFF:
 * - BrUTE FORCE O(N^2) Time, O(1) Space:
 * We consider every pair of bars as boundaries or expand outwards from each bar 
 * to find its left/right limits. This triggers massive redundant scanning, leading 
 * to Time Limit Exceeded (TLE) on large datasets.
 * - MONOTONIC STACK O(N) Time, O(N) Space:
 * We trade linear memory to entirely eliminate redundant scans. By keeping track of 
 * indices with strictly increasing heights, we instantly know the boundary limits 
 * for every bar when it gets popped. Each element is pushed and popped exactly once.
 *
 * 2. WHY DO WE NEED THIS DATA STRUCTURE (ArrayDeque)?
 * - The stack acts as a memory log of "unresolved" bars. For any popped bar, its max 
 * rectangle is strictly bounded by:
 * a) First smaller bar to its right: The current index 'i' causing the pop.
 * b) First smaller bar to its left: The index right beneath it in the stack.
 * - We use 'ArrayDeque' instead of 'java.util.Stack' because the legacy Stack class 
 * is synchronized, adding unnecessary lock overhead in single-threaded execution.
 *
 * 3. COMPLEXITY ANALYSIS:
 * - Time Complexity (TC): O(N). Each index is pushed once and popped at most once. 
 * The inner while loop runs at most N times across the entire execution.
 * - Space Complexity (SC): O(N). In the worst case (monotonically increasing array), 
 * the stack grows linearly up to N + 1 elements.
 *
 * 4. CRUCIAL EDGE CASES HANDLED:
 * - Single element array (e.g., [4]): Directly skips the loop, evaluates in phase 2 
 * as 4 * (1 - (-1) - 1) = 4.
 * - Monotonically increasing/decreasing arrays: The base index '-1' ensures safe width 
 * calculation from the absolute left wall.
 * - All elements identical (e.g., [5, 5, 5]): The '>=' operator handles duplicate 
 * heights correctly by popping them sequentially and capturing the correct area.
 * - Empty Array ([]): Gracefully returns 0 without executing logic.
 *
 * 5. DETAILED DRY RUN: heights = [6, 7, 5, 2, 4, 5, 9, 3]
 * Initial State: stack = [-1], length = 8, maxArea = 0
 *
 * PHASE 1: LINEAR SCAN (FOR LOOP)
 * ===================================================================================
 * i = 0 | H = 6 | stack = [-1]       | 6 >= stack.peek() False -> Push 0. Stack: [-1, 0]
 * i = 1 | H = 7 | stack = [-1, 0]    | 7 >= 6 False            -> Push 1. Stack: [-1, 0, 1]
 * i = 2 | H = 5 | stack = [-1, 0, 1] | POP TRIGGERED! (7 >= 5)
 * • Pop index 1 (H=7): width = 2 - 0 - 1 = 1  -> Area = 7 * 1 = 7.  maxArea = 7
 * • Pop index 0 (H=6): width = 2 - (-1) - 1 = 2 -> Area = 6 * 2 = 12. maxArea = 12
 * • Push 2. Stack: [-1, 2]
 * i = 3 | H = 2 | stack = [-1, 2]    | POP TRIGGERED! (5 >= 2)
 * • Pop index 2 (H=5): width = 3 - (-1) - 1 = 3 -> Area = 5 * 3 = 15. maxArea = 15
 * • Push 3. Stack: [-1, 3]
 * i = 4 | H = 4 | stack = [-1, 3]    | 4 >= 2 False            -> Push 4. Stack: [-1, 3, 4]
 * i = 5 | H = 5 | stack = [-1, 3, 4] | 5 >= 4 False            -> Push 5. Stack: [-1, 3, 4, 5]
 * i = 6 | H = 9 | stack = [-1, 3, 4] | 9 >= 5 False            -> Push 6. Stack: [-1, 3, 4, 5, 6]
 * i = 7 | H = 3 | stack = [..., 6]   | POP TRIGGERED! (9 >= 3)
 * • Pop index 6 (H=9): width = 7 - 5 - 1 = 1  -> Area = 9 * 1 = 9.  maxArea = 15
 * • Pop index 5 (H=5): width = 7 - 4 - 1 = 2  -> Area = 5 * 2 = 10. maxArea = 15
 * • Pop index 4 (H=4): width = 7 - 3 - 1 = 3  -> Area = 4 * 3 = 12. maxArea = 15
 * • Push 7. Stack: [-1, 3, 7]
 *
 * PHASE 2: CLEARING REMAINING ELEMENTS (WHILE LOOP)
 * ===================================================================================
 * Remaining Stack: [-1, 3, 7], right boundary defaults to total length = 8
 * • Pop index 7 (H=3): width = 8 - 3 - 1 = 4  -> Area = 3 * 4 = 12. maxArea = 15
 * • Pop index 3 (H=2): width = 8 - (-1) - 1 = 8 -> Area = 2 * 8 = 16. maxArea = 16 (FINAL)
 * ----------------------------------------------------------------------------------
 */
