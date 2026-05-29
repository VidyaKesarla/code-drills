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
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
 //optimised approach for this problem is to use recursion + convert linked list to array list. trade off in space, TC;: O(n) SC: O(n)
class Solution {
    //lets create a global variable array list 
    private List<Integer> values;

    public Solution() {
        this.values = new ArrayList<Integer>();
    }
    //convert the linked list to array 
    private void mapListToValues(ListNode head){
        while(head!=null){
            //added values to array
            this.values.add(head.val);
            head = head.next;
        }
    }

    private TreeNode convertListToBST(int left, int right){
        //invalid case
        if(left > right){
            return null;
        }

        //middle element forms the root
        int mid = (left + right)/2;
        TreeNode node = new TreeNode(this.values.get(mid));

        //base case
        if(left == right)
        return node;
        //recursively form bst on the two halves
        node.left = convertListToBST(left, mid - 1);
        node.right = convertListToBST(mid+1, right);
        return node;
    }

    public TreeNode sortedListToBST(ListNode head) {
        //form an array out of the given linked list and then use the array to form the BST
        this.mapListToValues(head);
        return convertListToBST(0, this.values.size() - 1);
    }
}

/**
 * APPROACH: Array Conversion + Binary Search Tree Split
 * * 1. BRUTE FORCE APPROACH (Slow/Fast Pointer):
 * - Find the middle element using slow and fast pointers to make it the root node.
 * - Recursively do this for the left and right halves of the linked list.
 * - Time Complexity: O(N log N) -> Finding the middle takes O(N) at each of the log N levels.
 * - Space Complexity: O(log N) -> Recursion stack.
 * * 2. THIS APPROACH (Array Trade-off):
 * - We flat out convert the Linked List to an ArrayList to gain O(1) random access memory lookups.
 * - Once in an array, we find the middle element instantly and build the tree.
 * - Time Complexity: O(N) -> O(N) to populate the array + O(N) to build the tree.
 * - Space Complexity: O(N) -> Trade-off: Storing elements explicitly inside the ArrayList.
 * * 3. ULTIMATE OPTIMAL (In-order Simulation - For reference):
 * - We can achieve O(N) Time and O(log N) Space by building the tree bottom-up using 
 * the list's sorted nature to simulate an In-order traversal while advancing a global head pointer.
 * * --- DRY RUN ---
 * Input List: [-10, -3, 0, 5, 9]
 * 1. mapListToValues() converts it to ArrayList: values = [-10, -3, 0, 5, 9] (size = 5)
 * 2. convertListToBST(0, 4):
 * - mid = (0 + 4) / 2 = 2. Root created with value 0.
 * - Left child calls convertListToBST(0, 1):
 * - mid = (0 + 1) / 2 = 0. Node created with value -10.
 * - Left child (0, -1) -> null.
 * - Right child (1, 1) -> Base case returns Node(-3).
 * - Right child calls convertListToBST(3, 4):
 * - mid = (3 + 4) / 2 = 3. Node created with value 5.
 * - Left child (3, 2) -> null.
 * - Right child (4, 4) -> Base case returns Node(9).
 * * Final Balanced Tree Structure:
 * 0
 * / \
 * -10   5
 * \    \
 * -3    9
 */