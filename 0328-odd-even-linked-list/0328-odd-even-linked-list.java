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
     /*
     * =========================================================================
     * ANALYSIS & EXPLANATION
     * =========================================================================
     * * TIME COMPLEXITY (TC): O(n)
     * - We traverse the entire linked list exactly once. The loop advances 
     * through the nodes by pairs, processing all 'n' elements linearly.
     * * SPACE COMPLEXITY (SC): O(1)
     * - The reordering is done entirely in-place by mutating the existing `.next` 
     * pointers. No extra data structures or copies of nodes are created.
     * * BRUTE FORCE TRADE-OFF:
     * - A naive brute force approach would allocate memory for two entirely new 
     * lists (one for odd indices, one for even), copy the values, and join them. 
     * While conceptually simpler and less prone to pointer errors, it sacrifices 
     * memory efficiency by scaling to O(n) auxiliary space. The in-place 
     * strategy used here optimizes memory down to absolute efficiency.
     * * INTUITION:
     * - Since the original list elements alternate between odd and even positions 
     * (1st is odd, 2nd is even, 3rd is odd...), we can untangle them into two 
     * simultaneous, interleaved paths. By pointing an odd node directly to the 
     * node past its even neighbor, we isolate the odds. We do the same for evens. 
     * By keeping a dedicated reference to the start of the even list (`evenHead`), 
     * we can seamlessly append it to the end of the odd list once processing concludes.
     * * =========================================================================
     * STEP-BY-STEP DRY RUN
     * =========================================================================
     * Input: head = [1, 2, 3, 4, 5]
     * * [Initialization]:
     * - odd = node(1)
     * - even = node(2)
     * - evenHead = node(2)
     * * [Iteration 1]:
     * - Condition check: even != null && even.next != null (node(2) and node(3) exist) -> True
     * 1. odd.next = even.next  => node(1) now points to node(3)
     * 2. odd = odd.next        => odd pointer moves forward to node(3)
     * 3. even.next = odd.next  => node(2) now points to node(4)
     * 4. even = odd.next       => even pointer moves forward to node(4)
     * - Current Structure: [1 -> 3], [2 -> 4 -> 5]
     * * [Iteration 2]:
     * - Condition check: even != null && even.next != null (node(4) and node(5) exist) -> True
     * 1. odd.next = even.next  => node(3) now points to node(5)
     * 2. odd = odd.next        => odd pointer moves forward to node(5)
     * 3. even.next = odd.next  => node(4) now points to null (since node(5).next is null)
     * 4. even = odd.next       => even pointer moves forward to null
     * - Current Structure: [1 -> 3 -> 5], [2 -> 4 -> null]
     * * [Loop Termination]:
     * - Condition check: even != null (even is null) -> False. Loop breaks.
     * * [Final Stitching]:
     * - odd.next = evenHead      => node(5) connects to node(2)
     * * Resulting Output: [1 -> 3 -> 5 -> 2 -> 4 -> null]
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null){
            return null;
        }
        ListNode even = head.next;
        ListNode evenHead = even;
        ListNode odd = head;
        while(even != null && even.next != null){
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = odd.next;  
        }
        odd.next = evenHead;
        return head;
    }
}


   