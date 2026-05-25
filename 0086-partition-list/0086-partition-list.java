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
    public ListNode partition(ListNode head, int x) {
        //before and after are the two pointers used to create the two list 
        //before_head and after_head are used to save the heads of the two lists
        //all of these are initialised with dummy nodes created
        ListNode before_head = new ListNode(0);
        ListNode after_head = new ListNode(0);
        ListNode before = before_head;
        ListNode after = after_head;

        while(head!=null){
            if(head.val < x){
                //if original value is smaller than x, add it to the before list
                before.next = head;
                before = before.next;
            } else {
                //if original value is greater than or equal to x, add it to the after list
                after.next = head;
                after = after.next;
            }
            //move ahead in original list
            head = head.next;
        }
        //last node of the after list would also be the ending node of the reformed list
         after.next = null;
    //combine these two lists 
    before.next = after_head.next;
    return before_head.next;
    }

   
}
/*
problem wants us to reform the linked list structure such that the elements lesser than a certain value x, come before the elements greater or equal to x. this essentially means in this reformed list, there would be a point in the linked list before which all the elements would be smaller than x and after which all elements would be greater or equal to x. 
this point is called the joint

if we break the reformed list at joint we get two smaller linked lists one with lesser elements and the other with elements greater or equal to x. 

Approach 1: two pointers:

before and after to keep track of the 2 linked lists as pointed above 
we can use these two pointers tto create two separate lists and then these lists could be combined to form the desires reformed list 

1.initialise two pointers before and after with a dummy node 
2. iterate the original linked list, using the head pointer 
3. if the node's value pointed by head is lesser than x the node should be part of the before list
4. move it to the before list 
5. else the node should be part of the after list , move it to the after list 
6. once we are done with all the nodes in original linked list, we would have two lists: before and after. the original list nodes are either part of before or after list depending on its value

// since we traverse the list from left to right: at no point would the order of nodes change relatively in the two lists. another important thing to note: we show oriignal linked list intact. 
However in implementation we remove the nodes from the original list and attach them to before or after list. we dont utilise any additional space. we simply move the nodes from the original list around.
we can discard the dummy node as it was only required to make the implementation easier.

*/

//tC: O(n)
//sc: O(1)