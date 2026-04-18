/**
 * Problem: Check if a graph is a valid tree.
 * A tree must:
 * 1. Be fully connected (no isolated components).
 * 2. Be acyclic (no loops).
 * * Optimal: Union-Find (DSU) with Path Compression and Rank.
 * Time: O(N * α(N)) where α is the inverse Ackermann function (nearly O(N)).
 * Space: O(N) for parent and rank arrays.
 */
class Solution {
    private int[] parent;
    private int[] rank;

    public boolean validTree(int n, int[][] edges) {
        // BRUTE/MATHEMATICAL CHECK:
        // A tree with N nodes MUST have exactly N-1 edges.
        // If it has < N-1, it's disconnected. If > N-1, it has a cycle.
        if (edges.length != n - 1) return false;

        // OPTIMAL APPROACH: Union-Find
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int[] edge : edges) {
            // If we try to union two nodes already in the same set, 
            // an extra edge exists -> Cycle detected.
            if (!union(edge[0], edge[1])) return false;
        }

        return true;
    }

    private int find(int x) {
        if (parent[x] == x) return x;
        // Path Compression: directly connect x to its root
        return parent[x] = find(parent[x]);
    }

    private boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return false;

        // Union by Rank: keep the tree flat
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }

    /*
    DRY RUN EXAMPLE:
    Input: n = 4, edges = [[0,1], [2,3], [1,2]]
    
    Initial: parent=[0,1,2,3], rank=[0,0,0,0]
    
    1. Edge [0,1]:
       find(0)=0, find(1)=1. roots 0 != 1.
       union(0,1) -> parent[1]=0, rank[0]=1.
       State: parent=[0,0,2,3], rank=[1,0,0,0]
       
    2. Edge [2,3]:
       find(2)=2, find(3)=3. roots 2 != 3.
       union(2,3) -> parent[3]=2, rank[2]=1.
       State: parent=[0,0,2,2], rank=[1,0,1,0]
       
    3. Edge [1,2]:
       find(1): parent[1]=0 -> parent[0]=0. Root is 0.
       find(2): parent[2]=2. Root is 2.
       roots 0 != 2.
       union(0,2) -> rank[0]==rank[2], so parent[2]=0, rank[0]=2.
       State: parent=[0,0,0,2], rank=[2,0,1,0]
       
    Result: true (All edges processed, no false returned).
    */
}