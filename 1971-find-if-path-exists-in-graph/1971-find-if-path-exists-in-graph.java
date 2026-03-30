class Solution {
    class DSU {
        int [] parent;
        int [] rank;

        public DSU(int n){
            parent = new int[n];
            rank = new int[n];
            for(int i=0;i<n;i++){
                parent[i] = i;
                rank[i] = i;
            }
        }

        public int find(int i){
            if(parent[i] == i){
                return i;
            }
            return parent[i] = find(parent[i]);
        }

        public void union(int i,int j){
            int rootI = find(i);
            int rootJ = find(j);

            if(rootI != rootJ){
                //attach the shorter tree to taller tree
                if(rank[rootI] > rank[rootJ]){
                    parent[rootJ] = rootI;
                } else if(rank[rootI] < rank[rootJ]) {
                    parent[rootI] = rootJ;
                } else {
                    parent[rootI] = rootJ;
                    rank[rootJ]++;
                }
            }
        }
    }



    public boolean validPath(int n, int[][] edges, int source, int destination) {
        //edge case: source is already destination
        if(source == destination)
        return true;

        DSU dsu = new DSU(n);

        // process all edges to build sets
        for(int [] edge: edges){
            dsu.union(edge[0], edge[1]);
        }

        //check if both nodes belong to the same root component
        return dsu.find(source) == dsu.find(destination);
    }
}

/*
For a graph with $V$ vertices (nodes) and $E$ edges, the complexity of the Disjoint Set Union (DSU) approach with Path Compression and Union by Rank is as follows:1. Time ComplexityThe total time complexity is $O(V + E \cdot \alpha(V))$.Initialization: $O(V)$ to create the parent and rank arrays.Processing Edges: We iterate through $E$ edges. For each edge, we perform a find and a union operation.The $\alpha(V)$ Factor: This represents the Inverse Ackermann Function. It is an extremely slow-growing function. For all practical values of $V$ (even if $V$ is the number of atoms in the universe), $\alpha(V)$ is less than 5.Final Check: $O(\alpha(V))$ to perform two final find operations for the source and destination.In short: Because $\alpha(V)$ is effectively a constant, the complexity is nearly linear, or $O(E)$.2. Space ComplexityThe space complexity is $O(V)$.Parent Array: We need an array of size $V$ to store the parent of each node ($O(V)$).Rank Array: We need an array of size $V$ to store the heights/ranks for optimization ($O(V)$).Recursion Stack: With path compression, the trees stay very flat, so the recursive stack for the find method is negligible, but technically $O(\log V)$ in the worst-case before the tree is flattened.

Method,Time Complexity,Space Complexity,Notes
DSU,O(E⋅α(V)),O(V),Best for connectivity and multiple queries.
BFS,O(V+E),O(V+E),Requires building an adjacency list first.
DFS,O(V+E),O(V+E),Risk of StackOverflow in Java for very deep graphs.

*/