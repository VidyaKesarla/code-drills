class Solution {
    public int numDecodings(String s) {
        //initialise a dp array
        int[] dp = new int[s.length() +1];
        dp[0] = 1;

        //if the first char in the string is 0 => 0 doesnt have a single digit to decode
        dp[1] = s.charAt(0) == '0' ? 0 : 1;

        for(int i=2;i<dp.length;i++){
            //check if single digit decode is posible
            if(s.charAt(i-1) != '0'){
                dp[i] = dp[i-1];
            }
            //check if successful two digit decode is possible
            int twoDigit = Integer.valueOf(s.substring(i-2,i)); // gives me the integer value from susbstring 
            if(twoDigit >= 10 && twoDigit <= 26){
                dp[i] = dp[i] + dp[i-2];
            }
        }
        return dp[s.length()];
    }
}