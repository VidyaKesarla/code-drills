class Solution {
    // TIME  : O(n²) - we check every cell in the n×n matrix
    // SPACE : O(n)  - visited array of size n + recursion stack depth of n
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;         // total number of cities
        int provinces = 0;                  // answer counter
        boolean[] visited = new boolean[n]; // O(n) space for visited array

        // O(n) outer loop - tries every city as potential province starter
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {              // found a city not yet in any province
                provinces++;                // this city starts a NEW province
                dfs(isConnected, visited, i); // explore ALL cities in this province
            }
        }
        return provinces;
    }

    // TIME  : O(n) per call - scans entire row of current city
    // SPACE : O(n) - recursion stack can go n levels deep in worst case
    public void dfs(int[][] connect, boolean[] visited, int city) {
        visited[city] = true;               // mark current city as explored

        // O(n) - check every possible neighbor j of current city
        for (int j = 0; j < connect.length; j++) {
            if (!visited[j]                 // neighbor not explored yet
                && connect[city][j] == 1) { // AND directly connected to city
                dfs(connect, visited, j);   // explore neighbor → stack grows here
            }
        }
        // when loop ends, all cities reachable from 'city' are marked visited
    }
}

/*
 * ═══════════════════════════════════════════════
 *         COMPLEXITY ANALYSIS
 * ═══════════════════════════════════════════════
 *
 * TIME COMPLEXITY : O(n²)
 * ─────────────────────────────────────────────
 *  • Outer loop          → runs n times
 *  • Each dfs call       → scans 1 full row = n cells
 *  • Every city is       → visited EXACTLY once
 *  • Total work          → n cities × n columns = n²
 *  • Even if dfs is called multiple times,
 *    total cells visited across ALL dfs calls = n²
 *    (each cell checked at most once due to visited[])
 *
 * SPACE COMPLEXITY : O(n)
 * ─────────────────────────────────────────────
 *  • visited[]  array    → O(n)
 *  • Recursion stack     → O(n) worst case
 *    Worst case: all cities in one chain
 *    0→1→2→3→...→n-1 → stack depth = n
 *  • Input matrix        → not counted (given)
 *  • Total               → O(n) + O(n) = O(n)
 *
 * WORST CASE (stack depth):
 *  [1,1,0,0]
 *  [1,1,1,0]   →  0→1→2→3  (chain, depth = n)
 *  [0,1,1,1]
 *  [0,0,1,1]
 *
 * BEST CASE:
 *  Identity matrix       → n isolated cities
 *  No dfs goes deep      → stack depth = 1
 *
 * ═══════════════════════════════════════════════
 *         DRY RUN
 * ═══════════════════════════════════════════════
 *
 * isConnected = [        cities: 0, 1, 2
 *   [1, 1, 0],
 *   [1, 1, 0],
 *   [0, 0, 1]
 * ]
 *
 * INITIAL STATE:
 * ┌─────────────────────────────┐
 * │ visited   = [F, F, F]       │
 * │ provinces = 0               │
 * └─────────────────────────────┘
 *
 * ─────────────────────────────────────────────
 * OUTER LOOP i=0
 * ─────────────────────────────────────────────
 * visited[0] = F → NEW PROVINCE!
 * provinces = 1
 * call dfs(city=0)
 *
 *   dfs(city=0)
 *   ├── visited[0] = true   → visited = [T, F, F]
 *   ├── j=0: visited[0]=T   → SKIP
 *   ├── j=1: visited[1]=F AND connect[0][1]=1 → call dfs(city=1)
 *   │     dfs(city=1)
 *   │     ├── visited[1] = true  → visited = [T, T, F]
 *   │     ├── j=0: visited[0]=T  → SKIP
 *   │     ├── j=1: visited[1]=T  → SKIP
 *   │     ├── j=2: connect[1][2]=0 → SKIP
 *   │     └── return ← back to dfs(0)
 *   └── j=2: connect[0][2]=0 → SKIP
 *   return ← back to main loop
 *
 * STATE AFTER i=0:
 * ┌─────────────────────────────┐
 * │ visited   = [T, T, F]       │
 * │ provinces = 1               │
 * └─────────────────────────────┘
 *
 * ─────────────────────────────────────────────
 * OUTER LOOP i=1
 * ─────────────────────────────────────────────
 * visited[1] = T → SKIP (already in province 1)
 *
 * ─────────────────────────────────────────────
 * OUTER LOOP i=2
 * ─────────────────────────────────────────────
 * visited[2] = F → NEW PROVINCE!
 * provinces = 2
 * call dfs(city=2)
 *
 *   dfs(city=2)
 *   ├── visited[2] = true   → visited = [T, T, T]
 *   ├── j=0: connect[2][0]=0 → SKIP
 *   ├── j=1: connect[2][1]=0 → SKIP
 *   ├── j=2: visited[2]=T    → SKIP
 *   └── return ← back to main loop
 *
 * STATE AFTER i=2:
 * ┌─────────────────────────────┐
 * │ visited   = [T, T, T]       │
 * │ provinces = 2               │
 * └─────────────────────────────┘
 *
 * ─────────────────────────────────────────────
 * CALL STACK VISUAL
 * ─────────────────────────────────────────────
 *
 * findCircleNum()
 *  │
 *  ├── i=0 → unvisited → provinces=1
 *  │    └── dfs(0)
 *  │         ├── mark 0 visited
 *  │         └── dfs(1)         ← 0 connected to 1
 *  │              └── mark 1 visited
 *  │                  └── (no new neighbors)
 *  │
 *  ├── i=1 → already visited → SKIP
 *  │
 *  └── i=2 → unvisited → provinces=2
 *       └── dfs(2)
 *            └── mark 2 visited
 *                └── (no new neighbors)
 *
 * ─────────────────────────────────────────────
 * FINAL ANSWER = 2 ✅
 * ─────────────────────────────────────────────
 *
 * Province 1 → cities {0, 1}   (directly connected)
 * Province 2 → cities {2}      (isolated)
 ┌──────────────────┬─────────────────────┬────────┬────────────┐
│ dfs(city)        │ j                   │ value  │ action     │
├──────────────────┼─────────────────────┼────────┼────────────┤
│ dfs(0) → row 0   │ j=0 connect[0][0]   │   1    │ visited→SKIP│
│                  │ j=1 connect[0][1]   │   1    │ dfs(1) ✅  │
│                  │ j=2 connect[0][2]   │   0    │ SKIP       │
├──────────────────┼─────────────────────┼────────┼────────────┤
│ dfs(1) → row 1   │ j=0 connect[1][0]   │   1    │ visited→SKIP│
│                  │ j=1 connect[1][1]   │   1    │ visited→SKIP│
│                  │ j=2 connect[1][2]   │   0    │ SKIP       │
├──────────────────┼─────────────────────┼────────┼────────────┤
│ dfs(2) → row 2   │ j=0 connect[2][0]   │   0    │ SKIP       │
│                  │ j=1 connect[2][1]   │   0    │ SKIP       │
│                  │ j=2 connect[2][2]   │   1    │ visited→SKIP│
└──────────────────┴─────────────────────┴────────┴────────────┘
 */