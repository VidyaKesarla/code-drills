/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        // Guard clause for empty input
        if (lists == null || lists.length == 0) return null;
        // Initialize Min-Heap based on node values
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((node1, node2) -> node1.val - node2.val);

    // Add the head of each non-empty list
        for(int i=0;i<lists.length;i++){
            if(lists[i] != null) {
            minHeap.add(lists[i]);}
        }

        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;

        while(minHeap.size() != 0){
            //take the minimum element from minHeap ie // Get the smallest node
            ListNode minNode = minHeap.poll();
            //add to new linked list
            temp.next = minNode;
            //increment the pointer
            temp = temp.next;
            //increment the pointer in minHeap
            minNode = minNode.next;

            // If there's a next node in that list, add it to the heap
            if(minNode != null){
                minHeap.add(minNode);
            }
        }
        return dummy.next;
    }
}

/*

Time Complexity: $O(N \log k)$, where $N$ is the total number of nodes across all lists and $k$ is the number of linked lists. Each insertion and extraction from the heap takes $O(\log k)$.Space Complexity: $O(k)$ to store the nodes in the priority queue.

*/

/*
Why this works so wellEfficiency: Instead of merging lists one by one (which could lead to $O(N \cdot k)$), the heap acts as a "tournament," allowing you to find the next smallest value in logarithmic time.Memory: You aren't creating new nodes; you're simply rearranging the next pointers of the existing nodes, which is very memory-efficient.*/


/*

/*
/*
---------------------------------------------------------
📊 VISUALIZATION (Example: List1: 1->4, List2: 1->3, List3: 2)
---------------------------------------------------------
Step | Heap Content (Lounge) | Action               | Merged List
-----|-----------------------|----------------------|------------
Start| [1a, 1b, 2]           | Initial heads added  | Empty
1    | [1b, 2, 4]            | Poll 1a, add next (4)| 1
2    | [2, 3, 4]             | Poll 1b, add next (3)| 1 -> 1
3    | [3, 4]                | Poll 2, (List3 empty)| 1 -> 1 -> 2
---------------------------------------------------------

⏱️ COMPLEXITY ANALYSIS:
- Time: O(N log k) 
  where N is total nodes and k is number of lists. 
  Each of the N nodes is added/removed from the heap once (log k).
  
- Space: O(k) 
  The heap stores at most one node from each of the k lists.
---------------------------------------------------------
*/