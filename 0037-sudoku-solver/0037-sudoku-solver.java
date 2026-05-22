class Solution {

    int n = 3;
    //row or col or box size
    int N = n*n;

    //create 2d arrays rows, cols, boxes, to store if that particular col or row or box has that particular value
    int[][] rows = new int[N][N+1];
    int[][] cols = new int[N][N+1];
    int[][] boxes = new int[N][N+1];

    //a new board to copy the existing board and backtrack
    char [][] board;

    //is the board solved?
    boolean sudokuSolved = false;

    //create a function to check if i can actually place the new number d in that particular index (row, col)

    public boolean couldPlace(int d, int row, int col){
        int id = (row/n)*n + col/n;
        return rows[row][d] + cols[col][d] + boxes[id][d] == 0;
    }

    //create a function placeNumber to place a number at that particular row and col in the new board instance
    public void placeNumber(int d, int row, int col){
        int id = (row/n)*n + col/n;
        rows[row][d]++;
        cols[col][d]++;
        boxes[id][d]++;
        board[row][col] = (char) (d+'0');
    }

    //create a func removeNumber that wont lead to any solution
    public void removeNumber(int d, int row, int col){
        int id = (row/n)*n + col/n;
        rows[row][d]--;
        cols[col][d]--;
        boxes[id][d]--;
        board[row][col] = '.';
    }

    //create a function where we think of calling backtrack function in recursion to continue to place numbers till the moment we have a solution
    public void placeNextNumbers(int row, int col){
        if ((col == N-1) && (row == N - 1)) {
            //we are in the last cell so we have the solution
            sudokuSolved = true;
        } else {
            if (col == N-1) 
            backTrack(row+1,0);
            else
            backTrack(row,col+1);
        }
    }

    //create a generic backTrack function 
    public void backTrack(int row, int col){
        if(board[row][col] == '.'){
            //if the cell is empty, i will iterate through all the 9 numbers 
            for(int d =1;d<10;d++){
                if(couldPlace(d,row, col)){
                    placeNumber(d, row, col);
                    placeNextNumbers(row, col);

                    if(!sudokuSolved)
                    removeNumber(d, row, col);
                }
            }
        } else {
            placeNextNumbers(row, col);
        }
    }

    public void solveSudoku(char[][] board) {
        this.board = board;

        for(int i =0;i<N;i++){
            for(int j =0;j<N;j++){
                char num = board[i][j];
                if(num!= '.'){
                    int d = Character.getNumericValue(num);
                    placeNumber(d, i, j);
                }
        }
    }
    backTrack(0,0);
}


}