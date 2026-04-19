/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        // //create a arraylist 
        // ArrayList<Integer> list = new ArrayList<>();
        // //cretae a string which we will use for output 
        // StringBuilder sb = new StringBuilder();
        // //create a dequeue
        // Queue<TreeNode> q = new LinkedList<TreeNode>();
        // //Add the tree node to a queue
        // q.add(A);
        // while(!q.isEmpty()){
        //     //size of the queue
        //     int size = q.size();
        //     for(int i=0;i<size;i++){
        //         //remove it so that this level is being added to an arraylist
        //         TreeNode first = q.remove();
        //         //finally add the value to the arraylist
        //         list.add(first.val);
        //         //suppose there is no leaf node to this
        //         if(first.left == null && first.val != null){
        //             //create a new node with value -1
        //             TreeNode one = new TreeNode(-1);
        //             q.add(one);
        //         }
        //         else if(first.left !=null){
        //             q.add(first.left);
        //         } 
        //         if(first.right == null && first.val != null){
        //             //create a new node with value -1
        //             TreeNode one = new TreeNode(-1);
        //             q.add(one);
        //         }
        //         else if(first.right !=null){
        //             q.add(first.right);
        //         }
        //     }
        // }
        // return list;
        
        if (root == null) return "null";
        
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        
        while (!q.isEmpty()) {
            TreeNode curr = q.poll();
            if (curr == null) {
                sb.append("null,");
                continue;
            }
            sb.append(curr.val).append(",");
            q.add(curr.left);
            q.add(curr.right);
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("null")) return null;
        
        String[] values = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        
        for (int i = 1; i < values.length; i++) {
            TreeNode parent = q.poll();
            
            // Handle Left Child
            if (!values[i].equals("null")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                parent.left = left;
                q.add(left);
            }
            
            // Handle Right Child
            if (++i < values.length && !values[i].equals("null")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                parent.right = right;
                q.add(right);
            }
        }
        return root;
        
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));