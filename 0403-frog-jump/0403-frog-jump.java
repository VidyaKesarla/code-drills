class Solution {
    public boolean dp[][] = new boolean[2001][2001];
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    public boolean canCross(int[] stones) {
        dp[0][0] = true;
        int n = stones.length;

        for(int i =0;i<n;i++){
            map.put(stones[i], i);
        }

        for(int index = 0;index<=n;index++){
            for(int prevJump =0; prevJump<=n;prevJump++){
                if(dp[index][prevJump]){
                    if(map.containsKey(stones[index] + prevJump)){
                        dp[map.get(stones[index] + prevJump)][prevJump] = true;
                    }
                    if(map.containsKey(stones[index] + prevJump + 1)){
                        dp[map.get(stones[index] + prevJump + 1)][prevJump + 1] = true;
                    }
                    if(map.containsKey(stones[index] + prevJump - 1)){
                        dp[map.get(stones[index] + prevJump - 1)][prevJump - 1] = true;
                    }
                }
            }
        }

        for(int i =0;i<=n;i++){
            if(dp[n-1][i]){
                return true;
            }
        }

        return false;
    }
}