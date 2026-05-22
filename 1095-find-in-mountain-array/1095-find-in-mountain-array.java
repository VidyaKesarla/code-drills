/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */
 /**
 * 🏔️ LeetCode 1095: Find in Mountain Array (Triple Binary Search Approach)
 * * ============================================================================
 * 💡 INTUITION:
 * ============================================================================
 * A mountain array strictly increases to a single peak and then strictly decreases.
 * Because it is sorted in two separate segments, a normal linear search is too slow 
 * (O(N)) and violates the strict 100-call API limit.
 * * However, because both halves possess sorted properties, we can utilize 
 * Binary Search three separate times:
 * 1. Find the peak: By comparing a middle element with its immediate neighbor,
 * we can determine if we are on the rising slope or the falling slope,
 * narrowing down the peak index.
 * 2. Search ascending slope: Look for the target from index 0 to peakIndex.
 * 3. Search descending slope: If not found on the left, look for the target
 * from peakIndex + 1 to length - 1 using a reversed comparison.
 * * ============================================================================
 * 🛠️ APPROACH OVERVIEW:
 * ============================================================================
 * 1. Locate Peak: Initialize pointers at 1 and length - 2. If get(mid) < get(mid + 1),
 * the peak is to the right, so move low = mid + 1. Otherwise, high = mid.
 * 2. Left-Side Search: Standard binary search on the increasing segment. Since
 * we want the minimum index, we check this side first. If found, return early.
 * 3. Right-Side Search: Modified binary search on the decreasing segment (reversing
 * the comparison operator). If found, return the index.
 * 4. Fallback: If neither search yields the target, return -1.
 * * ============================================================================
 * 📊 COMPLEXITY ANALYSIS:
 * ============================================================================
 * - Time Complexity (TC): O(log N)
 * We execute exactly 3 binary searches back-to-back. Each binary search takes 
 * O(log N) operations. The total number of API calls stays well below the 100-call 
 * constraint (for N = 10^4, log2(10^4) ≈ 14, making around ~42 calls max).
 * * - Space Complexity (SC): O(1)
 * We only utilize a few primitive variables (low, high, peakIndex, testIndex) 
 * to maintain pointers, consuming constant extra memory space.
 * * ============================================================================
 * ⚖️ TRADE-OFFS & EDGE CASES:
 * ============================================================================
 * - Pros: Guaranteed optimal time complexity; complies with the API call threshold;
 * prioritizes the left side first to naturally return the minimum index.
 * - Cons: Makes redundant API calls if the target is found on the left side but 
 * we already called .get() during the peak extraction phase. (Can be 
 * optimized via a tiny cache/map, but not required to pass).
 * - Edge Cases Handled: Target at peak, target at extreme boundaries (0 or length-1),
 * and target completely absent.
 * * ============================================================================
 * 🏃‍♂️ DRY RUN EXAMPLE:
 * ============================================================================
 * Input: mountainArr = [1, 2, 3, 4, 5, 3, 1], target = 3
 * * Step 1: Find Peak
 * - low = 1, high = 5 -> mid = 3 -> get(3)=4, get(4)=5. 4 < 5, so low = 4
 * - low = 4, high = 5 -> mid = 4 -> get(4)=5, get(5)=3. 5 > 3, so high = 4
 * - Loop ends. peakIndex = 4 (value 5).
 * * Step 2: Search Ascending Segment (low = 0, high = 4)
 * - mid = 2 -> get(2)=3. Since 3 >= target, high = 2.
 * - mid = 1 -> get(1)=2. Since 2 < target, low = 2.
 * - Loop ends. low = 2. get(2) == 3 is True. Returns 2 (Ends early!).
 */
 
class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        //find the length of the mountain array 
        int length = mountainArr.length();

        int low = 1;
        int high = length - 2;

        //find the index of the peak element
        while(low!=high){
            int testIndex = (high + low)/2;
            if(mountainArr.get(testIndex) < mountainArr.get(testIndex+1)){
                low = testIndex + 1;
            } else {
                high = testIndex;
            }
        }
        int peakIndex = low;

        ///search in the increasing part of the array
        low = 0;
        high = peakIndex;
        while(low!=high){
            int testIndex = (high + low)/2;
            if(mountainArr.get(testIndex) < target){
                low = testIndex + 1;
            } else {
                high = testIndex;
            }
        }

        //check if target is present in the strictly increasing part
        if(mountainArr.get(low) == target){
            return low;
        }

        ///search in the decreasing part of the array
        low = peakIndex + 1;
        high = length - 1;
        while(low!=high){
            int testIndex = (high + low)/2;
            if(mountainArr.get(testIndex) > target){
                low = testIndex + 1;
            } else {
                high = testIndex;
            }
        }

        //check if target is present in the strictly decreasing part
        if(mountainArr.get(low) == target){
            return low;
        }

        //target not present in array
        return -1;

    }
}