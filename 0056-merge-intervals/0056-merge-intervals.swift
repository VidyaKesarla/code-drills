/**
 * Approach: Sort and Merge
 * 1. Sort the intervals by their start times. This ensures that any potential 
 * overlaps are with the most recently added interval in our result list.
 * 2. Iterate through the sorted intervals:
 * - If the current interval starts after the last merged interval ends, 
 * there is no overlap. Add it to the result.
 * - If the current interval starts before or at the last merged interval's end, 
 * they overlap. Merge them by updating the end time to the maximum of both.
 *
 * Complexity:
 * - Time: O(n log n) due to sorting, followed by an O(n) linear scan.
 * - Space: O(n) to store the result (or O(log n) if excluding output space).
 *
 * Dry Run Example: [[1,3], [2,6], [8,10]]
 * - Sorted: [[1,3], [2,6], [8,10]]
 * - Start with [[1,3]]
 * - [2,6] overlaps with [1,3] (2 <= 3) -> Merge to [[1,6]]
 * - [8,10] does not overlap with [1,6] (8 > 6) -> Result: [[1,6], [8,10]]
 */
class Solution {
    func merge(_ intervals: [[Int]]) -> [[Int]] {
        //first check if the array is empty, if empty: return empty array
        guard !intervals.isEmpty else { return [] }

        //sort intervals according to start time =>. { $0[0] < $1[0] } - we are processing intervals in chronological order
        let sortedIntervals = intervals.sorted { $0[0] < $1[0] }

        //next step -> initialise the result with first interval
        var merged: [[Int]] = [sortedIntervals[0]]

    //iterate and compare
        for i in 1..<sortedIntervals.count {
            let current = sortedIntervals[i]
            let currentStart = current[0]
            let currentEnd = current[1]

            let lastIndex = merged.count - 1
            let lastEnd = merged[lastIndex][1]


            //the only comparison we have to do is: in loop we access merged[lastIndex], we are checking against the most recently added interval we only ever need to compare the current start with previous end
            if currentStart <= lastEnd {
                merged[lastIndex][1] = max(lastEnd, currentEnd)
            } else {
                merged.append(current)
            }
        }
        return merged
    }
}
//TC: O(nlogn)
//SC: O(n)