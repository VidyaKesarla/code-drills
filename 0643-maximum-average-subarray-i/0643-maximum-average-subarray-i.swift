class Solution {
    func findMaxAverage(_ nums: [Int], _ k: Int) -> Double {
        var sum = 0, maxSum = Int.min
        for r in 0..<nums.count {
            sum += nums[r]
            if r >= k { 
                sum = sum - nums[r-k]
            }
            if r >= k - 1 {
                maxSum = max(maxSum, sum)
            }
        }
        return Double(maxSum)/Double(k) // returning the sum divided by k
    }
}

//Time complexity is O(n) -> one pass through the array
//Space complexity: O(1) -> only three variables which are sum, maxSum, r