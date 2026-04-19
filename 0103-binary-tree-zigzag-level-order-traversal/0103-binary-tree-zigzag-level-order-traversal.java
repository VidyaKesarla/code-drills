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
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        //create an arraylist which you are going to return 
        List<List<Integer>> result = new ArrayList<>();
        //if the given node is null, return an empty result
        if(root == null){
            return result;
        }

        //We will use a deque and a boolean flag

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        boolean reverse = false;

        while(!queue.isEmpty()){
            int levelSize = queue.size();
            ArrayList<Integer> levelNodes = new ArrayList<>();

            for(int i =0;i<levelSize;i++){
                TreeNode node = queue.poll();

                if(reverse){
                    //insert at the beginning for reverse levels
                    levelNodes.add(0,node.val);
                }else{
                    //insert at the end for normal levels
                    levelNodes.add(node.val);
                }

                if(node.left!=null)
                    queue.add(node.left);
                if(node.right != null)
                    queue.add(node.right);
            }

            result.add(levelNodes);
            reverse = !reverse;
            }
            return result;
        }   
    }