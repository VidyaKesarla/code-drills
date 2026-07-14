class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        for(int row = 1;row<triangle.size();row++){
            for(int col = 0;col<=row;col++){
                int smallest = Integer.MAX_VALUE;
                if(col > 0){
                    smallest = triangle.get(row - 1).get(col - 1);
                }
                if(col < row){
                    smallest = Math.min(smallest, triangle.get(row - 1).get(col));
                }
                int path = smallest + triangle.get(row).get(col);
                triangle.get(row).set(col, path);
            }
        }
        return Collections.min(triangle.get(triangle.size() - 1));
    }
}