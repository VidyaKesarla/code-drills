class Solution {
    public int numIslands(char[][] grid) {
        //count of islands
        int numberOfIslandsCount = 0;
        //total rows, total columns
        int m = grid.length;
        int n = grid[0].length;
        //down, up, right and left - row change and column change
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        //scan every cell in the grid
        for(int r=0;r<m;r++){
            for(int c=0;c<n;c++){
                ///found unvisited land -> new island
                if(grid[r][c] == '1'){
                    //increment island count
                    numberOfIslandsCount++;

                    //then i create a queue to hold cells to process
                    Queue<int[]> queue = new LinkedList<>();
                    //add starting cell to queue
                    queue.offer(new int[]{r,c});

                    //mark as visited immediately
                    grid[r][c] = '0';

                    //do bfs until entire island is sunk
                    while(!queue.isEmpty()){
                        int[] cell = queue.poll();

                        for(int []d: directions){
                            int nr = cell[0] + d[0];
                            int nc = cell[1] + d[1];

                            // check if nbr is inside grid boundaries and is land 
                            if(nr >= 0 && nr < m && nc >= 0 && nc<n && grid[nr][nc] == '1'){        grid[nr][nc] = '0';
                            queue.offer(new int[]{nr,nc});

                            }
                        }
                    }

                }
            }
        }
        return numberOfIslandsCount;
    }
}


/*
==========================================================
DRY RUN — Leetcode Example 1
==========================================================

INPUT:
     c=0 c=1 c=2 c=3 c=4
r=0 [ 1   1   1   1   0 ]
r=1 [ 1   1   0   1   0 ]
r=2 [ 1   1   0   0   0 ]
r=3 [ 0   0   0   0   0 ]

m=4, n=5, count=0

----------------------------------------------------------
OUTER LOOP: r=0, c=0 → grid[0][0]='1' → count=1
→ enqueue [0,0], mark '0'

Queue: [[0,0]]
Grid:
0  1  1  1  0
1  1  0  1  0
1  1  0  0  0
0  0  0  0  0
----------------------------------------------------------

POLL [0,0]:
  DOWN  [1,0]='1' ✅ → sink, enqueue
  UP    [-1,0]    ❌ → out of bounds
  RIGHT [0,1]='1' ✅ → sink, enqueue
  LEFT  [0,-1]    ❌ → out of bounds
Queue: [[1,0],[0,1]]
Grid:
0  0  1  1  0
0  1  0  1  0
1  1  0  0  0
0  0  0  0  0

POLL [1,0]:
  DOWN  [2,0]='1' ✅ → sink, enqueue
  UP    [0,0]='0' ❌ → already sunk
  RIGHT [1,1]='1' ✅ → sink, enqueue
  LEFT  [1,-1]    ❌ → out of bounds
Queue: [[0,1],[2,0],[1,1]]
Grid:
0  0  1  1  0
0  0  0  1  0
0  1  0  0  0
0  0  0  0  0

POLL [0,1]:
  DOWN  [1,1]='0' ❌ → already sunk
  UP    [-1,1]    ❌ → out of bounds
  RIGHT [0,2]='1' ✅ → sink, enqueue
  LEFT  [0,0]='0' ❌ → already sunk
Queue: [[2,0],[1,1],[0,2]]
Grid:
0  0  0  1  0
0  0  0  1  0
0  1  0  0  0
0  0  0  0  0

POLL [2,0]:
  DOWN  [3,0]='0' ❌ → water
  UP    [1,0]='0' ❌ → already sunk
  RIGHT [2,1]='1' ✅ → sink, enqueue
  LEFT  [2,-1]    ❌ → out of bounds
Queue: [[1,1],[0,2],[2,1]]
Grid:
0  0  0  1  0
0  0  0  1  0
0  0  0  0  0
0  0  0  0  0

POLL [1,1]: all neighbors '0' → nothing added
Queue: [[0,2],[2,1]]

POLL [0,2]:
  RIGHT [0,3]='1' ✅ → sink, enqueue
  others all '0' or out of bounds
Queue: [[2,1],[0,3]]
Grid:
0  0  0  0  0
0  0  0  1  0
0  0  0  0  0
0  0  0  0  0

POLL [2,1]: all neighbors '0' → nothing added
Queue: [[0,3]]

POLL [0,3]:
  DOWN [1,3]='1' ✅ → sink, enqueue
  others all '0' or out of bounds
Queue: [[1,3]]
Grid:
0  0  0  0  0
0  0  0  0  0
0  0  0  0  0
0  0  0  0  0

POLL [1,3]: all neighbors '0' → nothing added
Queue: [] ← EMPTY → BFS DONE!

----------------------------------------------------------
RESUME OUTER LOOP:
All remaining cells = '0' → no more islands found
----------------------------------------------------------
FINAL ANSWER: count = 1 ✅

==========================================================
BFS WAVE VISUALIZATION
==========================================================

Wave 0: start at [0,0]
*  .  .  .  .
.  .  .  .  .
.  .  .  .  .
.  .  .  .  .

Wave 1: spread to [1,0],[0,1]
*  *  .  .  .
*  .  .  .  .
.  .  .  .  .
.  .  .  .  .

Wave 2: spread to [2,0],[1,1],[0,2]
*  *  *  .  .
*  *  .  .  .
*  .  .  .  .
.  .  .  .  .

Wave 3: spread to [2,1],[0,3]
*  *  *  *  .
*  *  .  *  .
*  *  .  .  .
.  .  .  .  .

Wave 4: spread to [1,3]
*  *  *  *  .
*  *  .  *  .
*  *  .  .  .
.  .  .  .  .

Wave 5: no new cells → BFS ends
*  *  *  *  .
*  *  .  *  .
*  *  .  .  .
.  .  .  .  .
← entire island sunk! ✅

==========================================================
DIRECTIONS EXPLAINED
==========================================================

For any cell [r,c], the for loop runs 4 times:

        UP
      [r-1,c]
         ↑
LEFT  ←[r,c]→  RIGHT
[r,c-1]        [r,c+1]
         ↓
      [r+1,c]
        DOWN

d={1,0}  → nr=r+1, nc=c   → DOWN
d={-1,0} → nr=r-1, nc=c   → UP
d={0,1}  → nr=r,   nc=c+1 → RIGHT
d={0,-1} → nr=r,   nc=c-1 → LEFT

All 4 neighbors checked every time! ✅

==========================================================
TIME & SPACE COMPLEXITY
==========================================================

TIME COMPLEXITY: O(m × n)
─────────────────────────
→ Outer loop visits ALL cells       = O(m × n)
→ Each cell added to queue ONCE     = O(m × n)
→ Each cell marked '0' immediately
  so NEVER visited twice!
→ Total = O(m × n) ✅

SPACE COMPLEXITY: O(min(m, n))
──────────────────────────────
→ Only the QUEUE takes extra space
→ BFS expands like a wave/frontier
→ Queue holds frontier cells only,
  NOT the entire island at once!

Worst case queue size at each wave:
Wave 0: 1 cell
Wave 1: 2 cells
Wave 2: 3 cells  ← max = min(m,n)
Wave 3: 3 cells
Wave 4: 2 cells
Wave 5: 1 cell

→ Max queue size = min(m,n) ✅
→ NOT O(m×n) because BFS never
  holds all cells simultaneously!

WHY BFS BEATS DFS ON SPACE:
─────────────────────────────
DFS goes DEEP → call stack grows to O(m×n)
BFS goes WIDE → queue stays at O(min(m,n))

╔══════════════════════════════════════════╗
║  BFS  → Time: O(m×n) Space: O(min(m,n)) ║
║  DFS  → Time: O(m×n) Space: O(m×n)      ║
║  BFS wins on Space! ✅                   ║
╚══════════════════════════════════════════╝
==========================================================
*/