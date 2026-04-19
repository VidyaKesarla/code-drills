class Solution {
    func reorderList(_ head: ListNode?) {
        // Guard against empty list or single node
        guard let head = head, head.next != nil else { return }

        // Step 1: Find the middle (Slow/Fast Pointers)
        var slow: ListNode? = head
        var fast: ListNode? = head

        // Use optional chaining '?' instead of '!' to be safe
        while fast?.next != nil && fast?.next?.next != nil {
            slow = slow?.next
            fast = fast?.next?.next
        }

        // Step 2: Reverse the second half
        var prev: ListNode? = nil
        var curr = slow?.next // Start from the node after middle
        slow?.next = nil      // Cut the list into two halves

        while let node = curr {
            let next = node.next
            node.next = prev
            prev = node
            curr = next
        }

        // Step 3: Merge the two halves
        var first: ListNode? = head
        var second: ListNode? = prev // 'prev' is the head of the reversed half

        while second != nil {
            let tmp1 = first?.next
            let tmp2 = second?.next
            
            first?.next = second
            second?.next = tmp1
            
            first = tmp1
            second = tmp2
        }
    }
}