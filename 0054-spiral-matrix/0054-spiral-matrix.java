class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        int up = 0;
        int down = rows - 1;
        int left = 0;
        int right = columns - 1;
        List<Integer> result = new ArrayList<>();

        while(result.size() < rows * columns){
            for(int col = left;col<=right;col++){
                result.add(matrix[up][col]);
            }
            for(int row = up+1;row<=down;row++){
                result.add(matrix[row][right]);
            }

            if( up != down ) {
                for(int col = right-1;col>=left;col--){
                result.add(matrix[down][col]);
            }
            }

            if(left != right) {
                for(int row = down - 1;row > up;row --){
                result.add(matrix[row][left]);
            }
            }
            left++;
            right--;
            up++;
            down--;
        }
        return result;
    }
}

// Let M be the number of rows and N be the number of columns.

// Time complexity: O(M⋅N). This is because we visit each element once.

// Space complexity: O(1). This is because we don't use other data structures. Remember that we don't include the output array in the space complexity.

