class Solution {
//     While window is valid:
//   1. Is this the smallest valid window so far? → record it
//   2. Remove the leftmost character from have
//   3. Did that removal break a required character's count? → formed--
//   4. Move left forward
//   5. Loop again — if still valid, keep shrinking

//     Time: O(|s| + |t|)
//   — right pointer moves forward n steps total
//   — left pointer moves forward at most n steps total
//   — building need map = O(|t|)

// Space: O(|s| + |t|)
//   — need map: at most |t| unique chars
//   — have map: at most |s| unique chars

    func minWindow(_ s: String, _ t: String) -> String {
        guard !t.isEmpty && s.count >= t.count else {
            return ""
        }
        let sArr = Array(s)
        //frequency maps
        // what t requires. Built once. Never changes.
        var need = [Character: Int]()
        // what the current window contains. Changes as window slides.
        var have = [Character: Int]()

        for c in t {
            need[c, default: 0] += 1
        }

        let required = need.count 
        var formed = 0

        var left = 0
        var minLen = Int.max
        var minLeft = 0

        for right in 0..<sArr.count {
            let c = sArr[right]
            
            have[c, default: 0] += 1
        if let needed = need[c], have[c] == needed {
            formed += 1
        }
        // Window valid — shrink from left
        while formed == required {
            // Record minimum
            if right - left + 1 < minLen {
                minLen = right - left + 1
                minLeft = left
            }

            // Remove leftmost char
            let lc = sArr[left]
            have[lc]! -= 1
            if let needed = need[lc], have[lc]! < needed {
                formed -= 1
            }
            left += 1
        }
        }
        return minLen == Int.max ? "" :
        String(sArr[minLeft..<(minLeft + minLen)])

    }
}