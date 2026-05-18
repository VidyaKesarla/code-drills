import java.util.ArrayList;
import java.util.List;
/*
 * Time Complexity: O(N)
 * - Binary search takes O(log N) to find the insertion index.
 * - However, ArrayList.add(index, element) is O(N) because it requires shifting 
 * subsequent elements in memory.
 * - The final merging pass is O(N) as we iterate through all intervals once.
 * - Overall, the linear operations dominate the logarithmic search.
 *
 * Space Complexity: O(N)
 * - We utilize an intermediate ArrayList to facilitate the insertion, which 
 * takes O(N) space.
 * - The result list and final array also occupy O(N) space to store the 
 * merged intervals.
 */

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        List<int[]> list = new ArrayList<>();
        for (int[] interval : intervals) {
            list.add(interval);
        }

        // 1. Binary Search to find the insertion position (based on start time)
        int left = 0;
        int right = n - 1;
        int target = newInterval[0];

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (intervals[mid][0] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // 2. Insert the new interval at the identified position
        // In Java ArrayList, this is an O(N) operation due to element shifting
        list.add(left, newInterval);

        // 3. Handle Merging (Linear Pass)
        List<int[]> res = new ArrayList<>();
        for (int[] current : list) {
            // If res is empty or no overlap, add current interval
            if (res.isEmpty() || res.get(res.size() - 1)[1] < current[0]) {
                res.add(current);
            } else {
                // Overlap detected: merge with the last interval in res
                int[] last = res.get(res.size() - 1);
                last[1] = Math.max(last[1], current[1]);
            }
        }

        return res.toArray(new int[res.size()][]);
    }
}


// //brute force:
// /*
// Append the newInterval to the end of the intervals,ignoring the order,
//  and then i sort the entire list based on the start times, this will ensure that overlapping intervals they sit next to each other,
//   and then the third step would be iterate through the sorted list and merge any intervals that overlap.

// Create a new list 'combined' which will add the newInterval to intervals 
// sort combined by the first element of each interval 
// initialise an empty list called merged 

// for each current interval in combined 
// if the merged array is empty , or if the curren interval does not overlap with the last interval in merged, (current.start > last.merged.end simple add current to merged)

// if there is an overlap merge them by updating the end of the last interval in meged to be the max of both

// last_merg.ed = max(last_merged.end, current.end)

// tc: O(Nlog n)

// adding the interval = O(1)
// sorting the array = O(NlOGN) where n is the number of total intervals
// merge. pass (We use a single for loop that starts at the first interval and ends at the last one) = O(N) 
// sc: O(N) ( we are creatingn a new list to store the merged intervals)