// we can reframe the problem as finding the K 
// the
//   smallest elements from amongst N sorted lists right? We know that the rows are sorted and so are the columns. So, we can treat each row (or column) as a sorted list in itself. Then, the problem just boils down to finding the K 
// th
//  smallest element from amongst these N sorted lists. However, before we get to this problem, lets first talk about a simpler version of the problem which is to find the K 
// th
//  smallest element from amongst 2 sorted lists. This is easy enough to solve since all we need are a pair of pointers which act as indices in the two lists.
//optimised:

// we have N sorted lists instead of just 2. That's what adds to the complexity. We can't really keep N different pointers now, can we? The heap data structure is perfect for this problem since at all times, we want to maintain N different variables with each of them pointing to an element in their corresponding lists. We want to be able to find the minimum amongst these N pointers quickly and then replace that element with the next one in its corresponding list.

// The heap data structure gives us O(1) access to the minimum element and log(N) removal of the minimum element and addition of a new one. We just need to perform this operation K times to get our K 
// th
//   smallest number. It's possible that our matrix has say 100 rows whereas we just need to find out the 5th smallest element. In this case, we can safely discard the rows in the lower part of the matrix i.e. in this case starting from the 6th row to the end of the matrix because the columns are sorted as well. So, we need the min(N,K) rows essentially to find out the answer.



class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int start = matrix[0][0];
        int end = matrix[n-1][n-1];
        while(start < end){
            int mid = start + (end - start) / 2;

            //create a pair
            int[] smallLargePair = {matrix[0][0], matrix[n-1][n-1]};

            int count = this.countLessEqual(matrix, mid, smallLargePair);

            if(count == k)
            return smallLargePair[0];

            if(count < k){
                //search higher
                start = smallLargePair[1];
            } else {
                //search lower
                end = smallLargePair[0];
            }
        }
        return start;
    }

    private int countLessEqual(int matrix[][], int mid, int[] smallLargePair){
        int count = 0;
        int n = matrix.length;
        int row = n-1;
        int col = 0;

        while(row >= 0 && col < n){
            if(matrix[row][col] > mid){ // as matrix[row][col] is bigger than the mid, let's keep track of the
        // smallest number greater than the mid
                smallLargePair[1] = Math.min(smallLargePair[1], matrix[row][col]);
                row--;
            } else {

        // as matrix[row][col] is less than or equal to the mid, let's keep track of the
        // biggest number less than or equal to the mid
                smallLargePair[0] = Math.max(smallLargePair[0], matrix[row][col]);
                count = count + row + 1;
                col++;
            }
        }
        return count;
    }
}

// Time Complexity: O(N×log(Max−Min))

// Let's think about the time complexity in terms of the normal binary search algorithm. For a one-dimensional binary search over an array with N elements, the complexity comes out to be O(log(N)).
// For our scenario, we are kind of defining our binary search space in terms of the minimum and the maximum numbers in the array. Going by this idea, the complexity for our binary search should be O(log(Max−Min)) where Max is the maximum element in the array and likewise, Min is the minimum element.
// However, we update our search space after each iteration. So, even if the maximum element is super large as compared to the remaining elements in the matrix, we will bring down the search space considerably in the next iterations. But, going purely by the extremes for our search space, the complexity of our binary search in search of K 
// th
//   smallest element will be O(log(Max−Min)).
// In each iteration of our binary search approach, we iterate over the matrix trying to determine the size of the left-half as explained before. That takes O(N).
// Thus, the overall time complexity is O(N×log(Max−Min))
// Space Complexity: O(1) since we don't use any additional space for performing our binary search.