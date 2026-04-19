class Solution {
    public int fib(int n) {
        // Base case
        if(n==1 || n==0){
            return n;
        }
        //Initialize the storage array exactly ONCE
        int []storage = new int[n+1];
        Arrays.fill(storage,-1);
        //Call the helper method, passing the shared array
        return helper(n, storage);
    }

    public int helper(int n, int[] storage){
        // Base case
        if (n == 1 || n == 0) {
            return n;
        }
        // memoization : If fib(n) already calculated, return it (O(1) time)
        if (storage[n] != -1) {
            return storage[n];
        }

// Recursive calls (Array is passed down)
        int ans = helper(n - 1, storage) + helper(n - 2, storage);
        //store whatever result you have calculated
        storage[n] = ans;
        return ans;

    }
}

