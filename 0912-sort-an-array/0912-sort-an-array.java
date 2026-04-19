class Solution {
    public int[] sortArray(int[] nums) {
        //length of the array
        int n = nums.length;
        // 1. Build Max-Heap
        // Start from the last non-leaf node and move up to the root
        //starting from the last parent
        for(int i=n/2-1;i>=0;i--){
            heapify(nums, n, i);
        }

        //Since our first loop turned the array into a Max-Heap, we know for a fact that the biggest number is at nums[0]

        for(int i = n-1;i>0;i--){
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;
            heapify(nums, i, 0);
        }
        return nums;
      
    }

    private void heapify(int [] nums, int n, int i){
        //i'll assume this last parent we are taking as the largest 
        int largest = i;

        //now i'll use the iterative approach to save call stack space instead of recursion
        while(true){
            //let me take this parent's leaf nodes 
            int left = 2*largest + 1;
            int right = 2*largest + 2;

            // FIX 2: currentLargest was never declared. 
            // We initialize it to 'largest' at the start of every check.
            int currentLargest = largest;
            if(left < n && nums[left] > nums[currentLargest]){
                currentLargest = left;
            }
            if(right < n && nums[right] > nums[currentLargest]){
                currentLargest = right;
            }

            // if the largest is no longer the parent, swap it with the largest
            if(currentLargest != largest){
                int swap = nums[largest];
                nums[largest] = nums[currentLargest];
                nums[currentLargest] = swap;
                largest = currentLargest;
            } else {
                break;
            }
        }
    }
}
/**
 * TRACE EXAMPLE: nums = [4, 10, 3, 5, 1], n = 5
 * * PHASE 1: BUILD MAX-HEAP (i = n/2 - 1 down to 0)
 * --------------------------------------------------
 * Starting Array: [4, 10, 3, 5, 1]
 * 1. i = 1 (Value: 10): Children 5, 1. 10 is largest. No change.
 * 2. i = 0 (Value: 4): Children 10, 3. 10 is larger. 
 * - Swap 4 & 10 -> [10, 4, 3, 5, 1]
 * - Heapify index 1: 4 compares with children 5, 1. 5 is larger.
 * - Swap 4 & 5 -> [10, 5, 3, 4, 1]
 * RESULT: [10, 5, 3, 4, 1] (Valid Max-Heap, 10 is at root)
 * * PHASE 2: EXTRACTION/SORT (i = n-1 down to 1)
 * --------------------------------------------------
 * 1. i = 4:
 * - Swap root (10) with nums[4] (1) -> [1, 5, 3, 4 | 10]
 * - Heapify index 0 (size 4): 1 sinks, 5 rises -> [5, 4, 3, 1 | 10]
 * * 2. i = 3:
 * - Swap root (5) with nums[3] (1) -> [1, 4, 3 | 5, 10]
 * - Heapify index 0 (size 3): 1 sinks, 4 rises -> [4, 1, 3 | 5, 10]
 * * 3. i = 2:
 * - Swap root (4) with nums[2] (3) -> [3, 1 | 4, 5, 10]
 * - Heapify index 0 (size 2): 3 is largest. No swap.
 * * 4. i = 1:
 * - Swap root (3) with nums[1] (1) -> [1 | 3, 4, 5, 10]
 * - Only 1 element left in heap. Done.
 * * FINAL SORTED ARRAY: [1, 3, 4, 5, 10]
 */