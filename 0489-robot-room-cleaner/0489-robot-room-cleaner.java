/**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 */

 /**
 * 🤖 APPROACH: DFS with Physical Backtracking (The "Blindfolded Traveler")
 * * INTUITION:
 * Since the robot is "blind" and has no map, we treat the starting point as (0, 0).
 * We use Depth-First Search (DFS) to explore as deep as possible into each branch. 
 * Because the robot is a physical agent, "backtracking" in code must be matched by 
 * "backtracking" in the real world. Every time a recursive call finishes, the robot 
 * must physically move back to the previous cell and face its original direction 
 * to keep our virtual coordinate system synchronized.
 *
 * DRY RUN (Example 2x1 Room: [1, 0] | Robot starts at 0,0 facing Up):
 * 1. backtrack(0, 0, Up): 
 * - Marks (0,0) as visited and cleans it.
 * - Loop i=0 (Up): Next is (-1,0). robot.move() -> false (Wall). robot.turnRight().
 * - Loop i=1 (Right): Next is (0,1). robot.move() -> true.
 * a. backtrack(0, 1, Right):
 * - Marks (0,1) as visited and cleans it.
 * - Checks all 4 directions. All are either visited or walls.
 * - Recursion finishes.
 * b. goBack(): Robot turns 180°, moves back to (0,0), turns 180° to face Right again.
 * - Loop i=2,3: Checks Down and Left. Both already visited or walls.
 * 2. Done.
 *
 * COMPLEXITY:
 * - Time: O(N - M), where N is total cells and M is walls. Each reachable cell is visited 
 * exactly once. We perform a constant number of turns (4) and moves per cell.
 * - Space: O(N - M) to store the coordinates of visited cells in a HashSet and to 
 * handle the recursion stack.
 *
 * TRADE-OFFS:
 * - DFS vs BFS: BFS is physically impossible for a single robot because it cannot 
 * "teleport" to the next node in a queue. It must follow a continuous path.
 * - Memory vs Efficiency: We use O(N) memory to avoid cleaning the same cell twice, 
 * prioritizing battery/time efficiency over RAM.
 */

class Solution {
    private static final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private Set<String> visited = new HashSet<>();
    private Robot robot;
    public void cleanRoom(Robot robot) {
        this.robot = robot;
        backTrack(0,0,0);
    }

    public void backTrack(int row, int col, int d) {
        //marking the place visited
        visited.add(row + "," + col);
        //cleaning that place
        robot.clean();

        for(int i =0;i<4;i++){
            int newD = (d+i)%4;
            int newRow = row + directions[newD][0];
            int newCol = col + directions[newD][1];

            if(!visited.contains(newRow + "," + newCol) && robot.move()){
                backTrack(newRow, newCol, newD);
                goBack();
            }
            robot.turnRight();
        }
    }

    private void goBack(){
        robot.turnRight();
        robot.turnRight();
        robot.move();
        robot.turnRight();
        robot.turnRight();
    }


}