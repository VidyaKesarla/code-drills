/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }

    /**
 * 🟢 BRUTE FORCE APPROACH: Reversing using an Auxiliary Stack
 * * LOGIC:
 * A Stack follows the Last-In-First-Out (LIFO) principle. We can leverage this 
 * by pushing all node values onto a stack and then popping them back into 
 * the nodes in reverse order.
 * * -------------------------------------------------------------------------
 * 🏃 DRY RUN: (Input: 1 -> 2 -> 3)
 * -------------------------------------------------------------------------
 * 1. PUSH PHASE:
 * - temp at Node(1): push(1). Stack: [1]
 * - temp at Node(2): push(2). Stack: [1, 2]
 * - temp at Node(3): push(3). Stack: [1, 2, 3]
 * - temp becomes null. Loop 1 ends.
 * * 2. THE RESET:
 * - temp = head; (Crucial step! Moves the pointer back to Node(1)).
 * * 3. POP PHASE:
 * - temp at Node(1): pop() -> 3. Node(1).val becomes 3. temp moves to Node(2).
 * - temp at Node(2): pop() -> 2. Node(2).val becomes 2. temp moves to Node(3).
 * - temp at Node(3): pop() -> 1. Node(3).val becomes 1. temp moves to null.
 * - Loop 2 ends.
 * * Final List State: 3 -> 2 -> 1
 * -------------------------------------------------------------------------
 * 📊 COMPLEXITY ANALYSIS:
 * * Time Complexity: O(n)
 * We traverse the list twice: once to fill the stack and once to update 
 * values. O(n) + O(n) = O(2n), which simplifies to O(n).
 * * Space Complexity: O(n)
 * We use an auxiliary Stack to store all 'n' values from the linked list.
 * -------------------------------------------------------------------------
 */

import java.util.Stack;

    // public ListNode reverseList(ListNode head) {
    //     if (head == null) return null;

    //     Stack<Integer> stack = new Stack<>();
    //     ListNode temp = head;

    //     // Step 1: Push all values onto the stack
    //     while (temp != null) {
    //         stack.push(temp.val);
    //         temp = temp.next;
    //     }

    //     // Step 2: Reset pointer to head to overwrite data
    //     temp = head; 
    //     while (temp != null) {
    //         temp.val = stack.pop();
    //         temp = temp.next;
    //     }

    //     return head;
    // }

class Solution {
    public ListNode reverseList(ListNode head){
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            // Step 1: Keep track of the rest of the list
            ListNode nextTemp = curr.next;
            
            // Step 2: Flip the pointer to point backward
            curr.next = prev;
            
            // Step 3: Shift the 'prev' and 'curr' pointers forward
            prev = curr;
            curr = nextTemp;
        }

        // 'prev' ends up at the last node, which is our new head
        return prev;
    }
}
// Time: $O(n)$ — One pass through the list.Space: $O(1)$ — No extra data structures, just three pointers.