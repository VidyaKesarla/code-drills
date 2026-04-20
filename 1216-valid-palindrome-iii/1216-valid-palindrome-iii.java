class Solution {
    public boolean isValidPalindrome(String s, int k) {
        int dp [] = new int[s.length()];

        int temp;
        int prev;
        for(int i = s.length() - 2;i>=0;i--){
            prev = 0;
            for(int j = i+1;j<s.length();j++){
                temp = dp[j];

                if (s.charAt(i) == s.charAt(j)){
                    dp[j] = prev;
                } else {
                    dp[j] = 1 + Math.min(dp[j], dp[j-1]);  
                }
                prev = temp;
            }
        }
        return dp[s.length()-1] <=k;
    }
}