/*
# Intuition
The goal is to find two lines that form a container with the most water. 
Since **Area = (Width) * (Minimum Height)**, and width decreases as we move pointers 
inward, we must prioritize keeping the taller lines.

# Approach
1. **Initialize**: `left` at 0, `right` at the last index.
2. **Calculate**: Determine the current area using the shorter of the two heights.
3. **Move**: Move the pointer that points to the **shorter** line.
4. **Result**: Return the maximum area recorded.

# Complexity
- **Time complexity**: $O(n)$ — Single pass through the array.
- **Space complexity**: $O(1)$ — Only a few integer variables used.
*/

class Solution {
    func maxArea(_ height: [Int]) -> Int {
        var left = 0
        var right = height.count - 1
        var maxWater = 0

        // while(left < right){
        //     let width = right - left
        //     // The height of the water is limited by the shorter lin
        //     let heigh = min(height[right], height[left])

        //     // Update the record if the current area is larger
        //     let area = width * heigh
        //     maxWater = max(maxWater, area)
        //     // DECISION: Move the pointer with the shorter height.
        //     // If we move the taller one, the area can only decrease (width shrinks, 
        //     // and height is still limited by the original shorter one).
        //     if(height[left] < height[right]){
        //         left += 1
        //     } else {
        //         right -= 1
        //     }

        // }

        while left < right {
    let h = min(height[left], height[right])
    maxWater = max(maxWater, (right - left) * h)
    
    // Skip lines that are shorter than or equal to the current h
    while left < right && height[left] <= h {
        left += 1
    }
    while left < right && height[right] <= h {
        right -= 1
    }
}
        return maxWater
    }
}
// 1. Brute Force (Why it fails)We would check every possible pair of lines.Time Complexity: $O(n^2)$.Problem: For $n = 10^5$, this results in $10^{10}$ operations, leading to Time Limit Exceeded (TLE).
// If you keep the shorter pointer and move the taller one, the height of your container can never increase (it's limited by that original short pointer), but your width will definitely decrease. Thus, the area would only ever get smaller. 
// The only way to find a bigger area is to abandon the shorter wall and hope to find a taller one.