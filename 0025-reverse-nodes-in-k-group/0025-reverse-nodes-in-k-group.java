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

    public ListNode reverseLinkedList(ListNode head, int k) {
        ListNode new_head = null;
        ListNode ptr = head;

        while(k>0){
            ListNode next_node = ptr.next;
            ptr.next = new_head;
            new_head = ptr;
            ptr = next_node;
            k--;
        }
        return new_head;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode ptr = head;
        ListNode kTail = null;
        ListNode new_head = null;
        
        while(ptr != null) {
        int count = 0;
        ptr = head;
        while(count < k && ptr != null){
            ptr = ptr.next;
            count++;
        }

        if(count == k){
            ListNode reverseHead = this.reverseLinkedList(head, k);
            if (new_head == null) {
                new_head = reverseHead;
            }
            if(kTail != null){
                kTail.next = reverseHead;
            }
            kTail = head;
            head = ptr;
        }
        }
        if (kTail != null){
            kTail.next = head;
        }
        return new_head == null ? head : new_head;
        
    }
}