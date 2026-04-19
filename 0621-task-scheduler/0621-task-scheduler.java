import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] counts = new int[26];
        
        for(char t : tasks){
            counts[t - 'A']++;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); 
        for(int count : counts){
            if(count > 0) {
                maxHeap.add(count);
            }
        }

        int time = 0;
        Queue<int[]> coolingLine = new LinkedList<>();

        /* =====================================================================================
           EXAMPLE ITERATION TRACE (WITH ALPHABETS)
           Tasks: ["A", "A", "A", "B", "B", "C"], n = 2
           
           Initial State: maxHeap = [A:3, B:2, C:1] 
                          coolingLine = []
                          time = 0
           
           --- TIME = 1 ---
           1. maxHeap pops 'A' (count 3). 'A' has 2 left to run.
           2. Add 'A' to coolingLine to wait for cooldown: {task: A(2), available_time: 1 + 2 = 3}
           3. Front of coolingLine isn't ready.
           State: maxHeap = [B:2, C:1]
                  coolingLine = [{A(2) available at T=3}]
           
           --- TIME = 2 ---
           1. maxHeap pops 'B' (count 2). 'B' has 1 left to run.
           2. Add 'B' to coolingLine: {task: B(1), available_time: 2 + 2 = 4}
           3. Front of coolingLine ('A') is available at T=3. Current time is 2. (Not ready)
           State: maxHeap = [C:1]
                  coolingLine = [{A(2) at T=3}, {B(1) at T=4}]
           
           --- TIME = 3 ---
           1. maxHeap pops 'C' (count 1). 'C' has 0 left to run. (Task C is completely finished!)
           2. Front of coolingLine ('A') is available at T=3. Current time is 3. It's ready!
           3. Pop 'A' from coolingLine and push its remaining count (2) back into maxHeap.
           State: maxHeap = [A:2]
                  coolingLine = [{B(1) at T=4}]
           
           (At T=4, the maxHeap will pop 'A' again, its count will drop to 1, and it will go 
           right back to the end of the cooling line. The cycle continues!)
           ===================================================================================== */
           

        while(!maxHeap.isEmpty() || !coolingLine.isEmpty()){
            time++; 

            if(!maxHeap.isEmpty()){
                int left = maxHeap.poll() - 1;
                
                if(left > 0) {
                    coolingLine.add(new int[]{left, time + n});
                }
            }

            if(!coolingLine.isEmpty() && coolingLine.peek()[1] == time) {
                maxHeap.add(coolingLine.poll()[0]);
            }
        }
        
        return time; 
    }
}