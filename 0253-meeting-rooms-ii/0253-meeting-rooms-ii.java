class Solution {
    public int minMeetingRooms(int[][] intervals) {
        
        //sort the array based on start time
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[]a, int[]b){
                if(a[0]<=b[0]){
                    //Return -1 (or any negative): Means a should come before b
                    return -1;
                } else {
                    //Return 1 (or any positive): Means a should come after b
                    return 1;
                }
            }
        });
        //take the end times and sort in a min Heap
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> (a[1] - b[1]));
        //add first meeting interval to minHeap [0,30]
        minHeap.add(intervals[0]);
        for(int i=1;i<intervals.length;i++){
            //the current meeting in the heap with the lowest end time
            int [] current = minHeap.peek();
            //compare current meeting's end time with upcoming meeting start time 
            if(current[1] <= intervals[i][0]){
                minHeap.poll();
            }
            minHeap.add(intervals[i]);
        }
        //number of meeting rooms 
        return minHeap.size();

    }
}
/*

Time Complexity: $O(N \log N)$ because we sort once ($O(N \log N)$) and iterate through $N$ meetings with heap operations ($O(\log N)$ each).Space Complexity: $O(N)$ for the heap in the worst case (where all meetings overlap). */

/*

To visualize this, we first have to apply your Step 1: Sort by Start Time.

Original: [[3,6], [3,10], [2,4], [7,8], [5,9]]
Sorted: [[2,4], [3,6], [3,10], [5,9], [7,8]]

Now, let's trace the Min-Heap (which tracks when rooms become free).


/*
     * Example Trace: [[3,6], [3,10], [2,4], [7,8], [5,9]]
     * 1. Sort by start: [[2,4], [3,6], [3,10], [5,9], [7,8]]
     * * Iteration Log:
     * +------+----------+-----------------------+----------------+-------+
     * | Step | Meeting  | Action                | Heap (End Tms) | Rooms |
     * +------+----------+-----------------------+----------------+-------+
     * | 1    | [2, 4]   | Add 4                 | [4]            |   1   |
     * | 2    | [3, 6]   | 3 < 4 (New Room)      | [4, 6]         |   2   |
     * | 3    | [3, 10]  | 3 < 4 (New Room)      | [4, 6, 10]     |   3   |
     * | 4    | [5, 9]   | 5 >= 4 (Reuse Room 1) | [6, 9, 10]     |   3   |
     * | 5    | [7, 8]   | 7 >= 6 (Reuse Room 2) | [8, 9, 10]     |   3   |
     * +------+----------+-----------------------+----------------+-------+


     Final Output: 3

     The "Office Floor" Visualization
Imagine three rooms (A, B, and C) at your office in Bengaluru:

Time 2:00 — Meeting [2,4] arrives. Room A is occupied until 4:00.

Time 3:00 — Meeting [3,6] arrives. Room A is still busy (until 4). Room B is occupied until 6:00.

Time 3:00 — Meeting [3,10] arrives. Rooms A & B are busy. Room C is occupied until 10:00.

Time 5:00 — Meeting [5,9] arrives.

Check Rooms: Room A became free at 4:00. Success! * The meeting takes Room A. Now Room A is occupied until 9:00.

Time 7:00 — Meeting [7,8] arrives.

Check Rooms: Room B became free at 6:00. Success!

The meeting takes Room B. Now Room B is occupied until 8:00.
     */

