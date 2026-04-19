/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     public var val: Int
 *     public var next: ListNode?
 *     public init() { self.val = 0; self.next = nil; }
 *     public init(_ val: Int) { self.val = val; self.next = nil; }
 *     public init(_ val: Int, _ next: ListNode?) { self.val = val; self.next = next; }
 * }
 */
class Solution {
    func removeNthFromEnd(_ head: ListNode?, _ n: Int) -> ListNode? {
    let dummy = ListNode(0)
    dummy.next = head
    var fast: ListNode? = dummy
    var slow: ListNode? = dummy

    // Move fast n+1 steps ahead
    for _ in 0...n {
        fast = fast?.next
    }

    // Move both until fast hits end
    while fast != nil {
        fast = fast?.next
        slow = slow?.next
    }

    // slow is now just before the node to delete
    slow?.next = slow?.next?.next
    return dummy.next
}

}