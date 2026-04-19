// BRUTE FORCE — O(n²) time, O(1) space
// class SolutionBrute {
//     func longestOnes(_ nums: [Int], _ k: Int) -> Int {
//         var result = 0

//         for left in 0..<nums.count {
//             var zeros = 0
//             for right in left..<nums.count {
//                 if nums[right] == 0 { zeros += 1 }
//                 if zeros > k { break }
//                 result = max(result, right - left + 1)
//             }
//         }
//         return result
//     }
// }

// BRUTE FORCE DRY RUN
// nums = [1, 1, 0, 0, 1], k = 1
//
// left=0:
//   right=0: nums[0]=1, zeros=0, result=1
//   right=1: nums[1]=1, zeros=0, result=2
//   right=2: nums[2]=0, zeros=1, result=3
//   right=3: nums[3]=0, zeros=2, zeros>k → BREAK
//
// left=1:
//   right=1: nums[1]=1, zeros=0, result=3
//   right=2: nums[2]=0, zeros=1, result=3
//   right=3: nums[3]=0, zeros=2, zeros>k → BREAK
//
// left=2:
//   right=2: nums[2]=0, zeros=1, result=3
//   right=3: nums[3]=0, zeros=2, zeros>k → BREAK
//
// left=3:
//   right=3: nums[3]=0, zeros=1, result=3
//   right=4: nums[4]=1, zeros=1, result=3
//
// left=4:
//   right=4: nums[4]=1, zeros=0, result=3
//
// Answer = 3 ✓


// SLIDING WINDOW — O(n) time, O(1) space
class Solution {
    func longestOnes(_ nums: [Int], _ k: Int) -> Int {
        var left = 0
        var zeros = 0
        var result = 0

        for right in 0..<nums.count {
            // step 1: expand right
            if nums[right] == 0 { zeros += 1 }

            // step 2: shrink left if over budget
            while zeros > k {
                if nums[left] == 0 { zeros -= 1 }
                left += 1
            }

            // step 3: record result
            result = max(result, right - left + 1)
        }
        return result
    }
}

// SLIDING WINDOW DRY RUN
// nums = [1, 1, 0, 0, 1], k = 1
//
// right=0: nums[0]=1, zeros=0, left=0, window=[1],     result=1
// right=1: nums[1]=1, zeros=0, left=0, window=[1,1],   result=2
// right=2: nums[2]=0, zeros=1, left=0, window=[1,1,0], result=3
// right=3: nums[3]=0, zeros=2 → SHRINK
//          nums[left=0]=1 → not zero, left=1, zeros still 2
//          nums[left=1]=1 → not zero, left=2, zeros still 2
//          nums[left=2]=0 → IT'S A ZERO, zeros=1, left=3
//          zeros=1, 1>1? NO → stop
//          window=[0], result=3
// right=4: nums[4]=1, zeros=1, left=3, window=[0,1],   result=3
//
// Answer = 3 ✓