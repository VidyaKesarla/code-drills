class Solution {
    int [][] strg;
    //no of ways to reach a cell ways(i,j) = ways(i-1,j)(left) + ways(i,j-1)(top)
    public int uniquePaths(int m, int n) {
        strg = new int[m][n];
        for (int[] row : strg) {
    Arrays.fill(row, -1);
}
        return ways(m-1, n-1);
    }

    int ways(int i, int j){
        if(i<0 || j<0){
            return 0;
        }
        if(i==0 && j==0){
            return 1;
        }

        if(strg[i][j] != -1){
            return strg[i][j];
        }

        strg[i][j] = ways(i-1,j) + ways(i,j-1);
        return strg[i][j];
    }
}

//call stack space is not getting involved. tc: O(N*M) SC: O(N*M)
//     int solve(int n, int m){
//         int [] [] dp = new int[n][m];
//         for(int i=0;i<n;i++){
//             for(int j=0;j<m;j++){
//                 if(i==0 && j==0){
//                     dp[i][j] = 1;
//                 } else {
//                     dp[i][j] = dp[i-1][j] + dp[i][j-1];
//                 }
//             }
//         }
//         return dp[n-1][m-1];
//     }
// }

//memoisation is called top to bottom approach (recursive)
/*
this takes call stack space
we can avoid it in iterative approach

*/

//tabulation/ bottom up approach - iterative





