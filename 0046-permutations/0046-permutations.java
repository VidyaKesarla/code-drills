class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0,nums, result);
        return result;
    }

    private void backtrack(int index, int[] nums, List<List<Integer>> result){
        if(index == nums.length){
            List<Integer> subArrayList = new ArrayList<>();
            for(int num: nums){
                subArrayList.add(num);
            }
            result.add(new ArrayList<>(subArrayList));
            return;
        }


        for(int i=index;i<nums.length;i++){
        swap(i, index, nums);
        backtrack(index+1, nums, result);
        swap(i, index, nums);
        }
    }

    private void swap(int i, int j, int[] nums){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }



    
}