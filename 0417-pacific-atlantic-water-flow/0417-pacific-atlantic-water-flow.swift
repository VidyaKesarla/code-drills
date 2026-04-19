/**
 * 417. Pacific Atlantic Water Flow
 *
 * --- APPROACH: REVERSE REACHABILITY (DFS) ---
 * Instead of checking if each cell can reach the oceans, we start from the oceans 
 * and see which cells they can reach.
 *
 * 1. Initialize two 2D boolean arrays: `canReachPacific` and `canReachAtlantic`.
 * 2. Start DFS from the borders:
 * - Pacific: Top row and Left column.
 * - Atlantic: Bottom row and Right column.
 * 3. During DFS, move "uphill": Only move to a neighbor if its height >= current cell.
 * 4. The intersection: Any cell marked 'true' in both matrices can flow to both oceans.
 *
 * --- COMPLEXITY ANALYSIS ---
 * Time Complexity: O(M * N)
 * - Each cell is visited at most twice (once for Pacific, once for Atlantic).
 * - M is the number of rows, N is the number of columns.
 *
 * Space Complexity: O(M * N)
 * - We maintain two reachability matrices of size M * N.
 * - The recursion stack for DFS can go up to O(M * N) in the worst case.
 */

 /*
 * 💡 THE INTUITION: FROM "ROLLING MARBLES" TO "RISING TIDES"
 *
 * Most people struggle with this problem initially because they try to simulate 
 * water flowing DOWN from every single cell. This "Top-Down" approach is 
 * redundant and slow. Here is why "Reverse Flow" is the key:
 *
 * 1. AVOID THE REDUNDANCY TRAP
 * If you start from every cell (M x N starting points), you traverse the 
 * same paths thousands of times. If a peak is in the center, you calculate 
 * its path, then move to the neighbor and re-calculate almost the exact 
 * same path. This leads to a brutal O((M x N)^2) complexity.
 *
 * 2. FLIP THE LOGIC: SOURCE VS. SINK
 * Instead of treating oceans as Sinks (ends), treat them as Sources (starts).
 * - Reverse Flow: Start from the edges and move UPHILL.
 * - By running a search from the Pacific and Atlantic edges separately, 
 * you only visit each cell a constant number of times.
 *
 * 3. CONNECTIVITY > PATHFINDING
 * Searching "downhill" is a tricky pathfinding problem. Searching "uphill" 
 * turns this into a simple Connectivity Problem (like Number of Islands):
 * - The Question: "What is the contiguous landmass connected to this 
 * ocean that stays at this height or higher?"
 * - The Solution: Use two visited arrays. Any cell marked True in BOTH 
 * is your answer.
 *
 * COMPLEXITY:
 * - Time: O(M x N) — Each cell is visited once per ocean search.
 * - Space: O(M x N) — For the reachability matrices and recursion stack.
 */

class Solution {
    func pacificAtlantic(_ heights: [[Int]]) -> [[Int]] {
        guard !heights.isEmpty else { return [] }
        let rows = heights.count
        let cols = heights[0].count

        var canReachPacific = Array(repeating: Array(repeating: false, count: cols), count: rows)
        var canReachAtlantic = Array(repeating: Array(repeating: false, count: cols), count: rows)

        // Start DFS from horizontal borders
        for c in 0..<cols {
            dfs(0, c, &canReachPacific, heights[0][c], heights) // Top edge => Pacific
            dfs(rows - 1, c, &canReachAtlantic, heights[rows - 1][c], heights) // Bottom edge => Atlantic
        }

        // Start DFS from vertical borders
        for r in 0..<rows {
            dfs(r, 0, &canReachPacific, heights[r][0], heights) // Left edge => Pacific
            dfs(r, cols - 1, &canReachAtlantic, heights[r][cols - 1], heights) // Right edge => Atlantic
        }

        var result = [[Int]]()
        for r in 0..<rows {
            for c in 0..<cols {
                if canReachPacific[r][c] && canReachAtlantic[r][c] {
                    result.append([r, c])
                }
            }
        }
        return result
    }

    private func dfs(_ r: Int, _ c: Int, _ reachable: inout [[Bool]], _ prevHeight: Int, _ heights: [[Int]]) {
        let rows = heights.count
        let cols = heights[0].count

        // Base cases: Out of bounds, height is lower (not uphill), or already visited
        if r < 0 || c < 0 || r >= rows || c >= cols || heights[r][c] < prevHeight || reachable[r][c] {
            return
        }

        reachable[r][c] = true

        dfs(r + 1, c, &reachable, heights[r][c], heights)
        dfs(r - 1, c, &reachable, heights[r][c], heights)
        dfs(r, c + 1, &reachable, heights[r][c], heights)
        dfs(r, c - 1, &reachable, heights[r][c], heights)
    }
}