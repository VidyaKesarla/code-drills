class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        //this is the result array which will hold our answer, it will be a 2D array
        List<List<Integer>> result = new ArrayList<>();
        //start the recursive process: we pass the result first, a fresh empty list for the first path, the input numbers, 0 because we start at the first element
        backtrack(result, new ArrayList<>(), nums, 0);
        //once recursion is finished, return the final collection of subsets
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> currentSubset, int [] nums, int start){
        //every time we enter this function,the current path we are on is already a valid subset, we obviously must create a new arraylist copy everytime, a new subset
        result.add(new ArrayList<>(currentSubset));

        for(int i=start; i<nums.length;i++){
            currentSubset.add(nums[i]);
            backtrack(result, currentSubset, nums, i+1);
            currentSubset.remove(currentSubset.size() - 1);
        }
    }
}

// Imagine currentSubset is a physical basket.Level 0: Basket is []. Save it.Loop $i=0$: Put 1 in the basket. Basket is [1].Recurse to Level 1: Save [1].Loop $i=1$: Put 2 in. Basket is [1, 2].Recurse to Level 2: Save [1, 2].Loop $i=2$: Put 3 in. Basket is [1, 2, 3].Recurse: Save [1, 2, 3]. Loop $i=3$ (ends).Backtrack: Take 3 out. Basket is [1, 2]. Loop $i=2$ ends.Backtrack: Take 2 out. Basket is [1].Loop $i=2$: Put 3 in. Basket is [1, 3].Recurse: Save [1, 3]. Loop $i=3$ (ends).Backtrack: Take 3 out. Basket is [1]. Loop $i=2$ ends.Backtrack: Take 1 out. Basket is [].Loop $i=1$: Put 2 in. Basket is [2].(Process repeats for paths starting with 2...)
