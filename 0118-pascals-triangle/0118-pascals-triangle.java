class Solution {
    public List<List<Integer>> generate(int numRows) {
        //lets create a pascal triangle which is of list,list integer type
        List<List<Integer>> pascalT = new ArrayList<>();

        if (numRows == 0){
            return pascalT;
        }


        List<Integer> firstRow = new ArrayList<>();
        firstRow.add(1);
        pascalT.add(firstRow);

        //i number of rows and j number of colums

        for(int i=1;i<numRows;i++){
            //get the previous row
            List<Integer> prevRow = pascalT.get(i - 1);
            List<Integer> currRow = new ArrayList<>();
            //j index 0
            currRow.add(1);
            for(int j=1;j<i;j++){
                currRow.add(prevRow.get(j-1) + prevRow.get(j));
            }
            currRow.add(1);
            pascalT.add(currRow);
        }

        return pascalT;
    }
}

/* TC: O(n^2) Quadratic time complexity. 
SC: O(n^2) 

Time Complexity: $O(n^2)$, where $n$ is numRows. You are visiting every element in the triangle exactly once.Space Complexity: $O(n^2)$ to store the result. If we don't count the output list, the auxiliary space is $O(1)$ (just pointers and indices).
public List<List<Integer>> generate(int numRows) {
        //lets create a pascal triangle which is of list,list integer type
        List<List<Integer>> pascalT = new ArrayList<>();

        if (numRows == 0){
            return pascalT;
        }


        List<Integer> firstRow = new ArrayList<>();
        firstRow.add(1);
        pascalT.add(firstRow);

        //i number of rows and j number of colums

        for(int i=1;i<numRows;i++){
            //get the previous row
            List<Integer> prevRow = pascalT.get(i - 1);
            List<Integer> currRow = new ArrayList<>();
            //j index 0
            currRow.add(1);
            for(int j=1;j<i;j++){
                currRow.add(prevRow.get(j-1) + prevRow.get(j));
            }
            currRow.add(1);
            pascalT.add(currRow);
        }

        return pascalT;
    }*/

    /*Second MOST OPTIMAL approach 
    class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();
        long current = 1; // Use long to prevent overflow during calculation
        
        for (int j = 0; j <= rowIndex; j++) {
            row.add((int) current);
            // Calculate next term using the formula
            current = current * (rowIndex - j) / (j + 1);
        }
        
        return row;
    }
}

2. The "Ultimate" Optimization: $O(n)$ Time & $O(1)$ Extra SpaceUsing the Binomial Coefficient formula, we can calculate any element in a row based on the previous element. This is the fastest way to get a specific row because it eliminates the need to calculate all previous rows.The formula for the next element is:$$Element_{j} = Element_{j-1} \times \frac{n - (j - 1)}{j}$$

Time Complexity: $O(n)$ — Only one loop! This is a massive improvement over $O(n^2)$.Space Complexity: $O(1)$ — Excluding the output list, we only use one variable (current).


    }
    */