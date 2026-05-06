/*
Are we dealing only with lowercase english characters? or the strings can use or contain spaces, symbols, unicode charactersS?

what is the time and space complexity expected?

is it fine if i arrive at a solution which is O(M*n)

because the output expectation is of type boolean then i am not gonna consider multiple valid paths, one is more than enough for find

edge cases: 

s1 ="abc"
s2 = "def"

s3 = "abcde"

return false

if the s3 length is more or less than s1 + s2 length, then immediately return false

if both the s1 and s2 strings are empty then s3 is also empty

one source string is empty: 
s1 = "" , s2 = "abc" , s3 = "abc"
we can return result as true

i can also assume that s1 can come last, it need not be the case the s1 comes at last. s1 ="a", s2 = "b", s3 = "ba"

if there is a scenario where i get overlapping or ambiguous characters, s1 = "aab", s2 ="aac", s3 = "aaaabc"

brute forcE:
uses simple recursion: at every step: you compare the current character of s3 with the current characters of s1 and s2.

recursion: is causing a space complexity of O(M+N) => Stack
TC: O(2^(M+N))

if s1[i] matches s3[k] you move forward in s1 and s3.
if s2[j] matches s3[k] you move forward in s2 and s3

bottleneck : exponential growht: overlapping problems : if s1 and s2 have repeated characters: the recursion tree branches out every single step

tc: O(2^(m+n))
sc: O(m+n)


Optimal approach:
eliminates the redundant work by using a memoisation table or 2d grid. 
instead of recalculating where s1[i] and s2[j] can for s3[i+j] we calculate it once and store it in a table
efficiency: We only visit each state (i,j) exaclty once
Time comp : O(m*n)

*/

class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();

        if(m + n != s3.length()){
            return false;
        }

        boolean[] dp = new boolean[n+1];

        for(int i =0;i<= s1.length(); i++){
            for(int j =0;j<= s2.length(); j++){
                if(i==0 && j==0){
                    dp[j] = true;
                } else if(i==0){
                    dp[j] = dp[j-1] && s2.charAt(j-1) == s3.charAt(i+j-1);
                } else if(j==0){
                    dp[j] = dp[j] && s1.charAt(i-1) == s3.charAt(i+j-1);
                } else {
                    dp[j] = (dp[j-1] && s2.charAt(j-1) == s3.charAt(i+j-1)) || (dp[j] && s1.charAt(i-1) == s3.charAt(i+j-1)) ;
                }
            }
        }
        return dp[s2.length()];    
    }
}