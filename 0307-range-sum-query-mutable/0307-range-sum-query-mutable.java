/*
A very good way to solve this problem is using a flexible data structure: Segment tree:
A segment tree allows us to solve numerous range query problems like finding minimum, maximum, sum, greatest common divisor, least common denominator

It is basically a binary tree in which each node contains aggregate information like min, max, sum for a subrange(i..j) of array as its left and right child hold info for ranges.

Segment tree can be broken down into three following steps:
1. preprocessing step which will build: the segment tree from the given array
2. Update the segment tree when an element is modified
3. Calculate the range sum query using segment tree

*/

class NumArray {
    int[] tree;
    int n;

    public NumArray(int[] nums) {
        //to build the tree lets use a helper function 
        if(nums.length > 0){
            n = nums.length;
            tree = new int[2*n];
            buildTree(nums);
        }
    }

    private void buildTree(int [] nums){
        for(int i=n, j=0;i<2*n;i++,j++){
            tree[i] = nums[j];
        }
        for(int i=n-1; i>0;i--){
            tree[i] = tree[i*2] + tree[i*2 + 1];
        }

    }
    
    public void update(int index, int val) {
        //the index value is given as per the position of that element in the array, now i have to calculate the position at the tree , say if 1 is the index is given of the array , i am going to conclued that the index for the treee would be 1 + 4 = > 5 
        index = index + n; 
        //update the value at the index
        tree[index] = val;

        while(index > 0){
            //let me initialise both the left and right child to index
            int left = index;
            int right = index;
            //if index is even, then we would have to just find the right node
            if(index%2 == 0){
                right = index + 1;
            } else {
                //if index is odd, then we would have to just find the left node
                left = index - 1;
            }
            tree[index/2] = tree[left] + tree[right];
            //keep updating
            index = index / 2;
        }
    }
    
    public int sumRange(int left, int right) {
        left = left + n;
        right = right + n;

        int sum = 0;

        while(left<=right){
            if((left%2)==1){
                sum += tree[left];
                left++;
            } 
            if((right%2) == 0){
                sum += tree[right];
                right--;
            }
            left/=2;
            right/=2;

        }
        return sum;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */