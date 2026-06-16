// class Solution {
//     public int minCostConnectPoints(int[][] points) {
//         //Choosing kruskal's algorithm
//         //It is a greedy algorithm, it builds the MST by making the locally optimal choice at each step 
//         //pick cheapest edge -> add is no cycle
//         //pick next cheapest edge - > add if no cycle
//         //repeat until all points are connected
//         //without sorted order, this guarantee it breaks

//         //hence we need a priority queue(minheap) to help us choose the cheapest/smallest always
//         if(points == null || points.length == 0){
//             return 0;
//         }
//         int size = points.length;
//         //this is the comparator that tells the priority queue how to order edges
//         //return calue -> negative => x comes before y. zero => x and y are equal. positive => y comes before x
//         //smaller cost alwys bubbles to the top making it a min heap 
//         PriorityQueue<Edge> pq = new PriorityQueue<>((x,y) -> x.cost - y.cost);

//         UnionFind uf = new UnionFind(size);
//         //why do we need union find? mainly to check -> does this edge create a cycle?

//         //lets use two for loops to calculate the distance between two coordinates
//         for(int i =0;i<size;i++){
//             int [] coordinate1 = points[i];
//             for(int j =i+1;j<size;j++){
//                 int [] coordinate2 = points[j];
//                 //calculate the distance between 2 coordinates
//                 int cost = Math.abs(coordinate1[0] - coordinate2[0]) + Math.abs(coordinate1[1] - coordinate2[1]);
//                 Edge edge = new Edge(i,j, cost);
//                 //add this edge to priority queue
//                 pq.add(edge);
//             }
//         }

//         int result = 0;
//         int countOfEdgesforMST = size -1;

//         while(!pq.isEmpty() && countOfEdgesforMST > 0){
//             Edge edge = pq.poll();
//             if(!uf.connected(edge.point1, edge.point2)){
//                 uf.union(edge.point1, edge.point2);
//                 result = result + edge.cost;
//                 countOfEdgesforMST--;
//             }
//         }
//         return result;
//     }

//     class Edge {
//         int point1;
//         int point2;
//         int cost;

//         Edge(int point1, int point2, int cost){
//             this.point1 = point1;
//             this.point2 = point2;
//             this.cost = cost;
//         }
//     }


//     class UnionFind {
//         int [] root;
//         int [] rank;
//         //constructor
//         public UnionFind(int size){
//             //create two arrays rank and root 
//             root = new int[size];
//             rank = new int[size];
//             //initialise their values: 
//             for(int i =0;i<size;i++){
//                 root[i] = i;
//                 rank[i] = 1;
//             }
//         }

//         //two functions 
//         public int find(int x){
//             if(x == root[x])
//             return x;

//             return root[x] = find(root[x]);
//         }

//         public void union(int x, int y){
//             int rootX = find(x);
//             int rootY = find(y);

//             if(rootX != rootY){
//                 if(rank[rootX] > rank[rootY]){
//                     root[rootY] = rootX;
//                 } else if (rank[rootX] < rank[rootY]){
//                     root[rootX] = rootY;
//                 } else {
//                     root[rootY] = rootX;
//                     rank[rootX] += 1;
//                 }
//             }
//         }

//         //we use the following function for finding if two points are conncted
//         public boolean connected(int x, int y){
//             return find(x) == find(y);
//         }
//     }



// }


class Solution {

    public int minCostConnectPoints(int[][] points){
        //lets take the length of the array given 
        int n = points.length;
        int mstCost = 0;
        int edgesUsed = 0;

        //boolean array to track nodes which are visited
        boolean[] inMST = new boolean[n];

        //create a minDistance array 
        int[] minDist = new int[n];
        minDist[0] = 0;

        for(int i =1;i<n;i++){
            minDist[i] = Integer.MAX_VALUE;
        }

        while(edgesUsed < n){
            int currMinEdge = Integer.MAX_VALUE;
            int currNode = -1;

            //lets pick least weight node which is not in MST
            for(int node = 0;node < n;node++){
                if(!inMST[node] && currMinEdge > minDist[node]){
                    currMinEdge = minDist[node];
                    currNode = node;
                }
            }

            mstCost = mstCost + currMinEdge;
            edgesUsed++;
            inMST[currNode] = true;

            //update adjacent nodes of current node
            for(int nextNode =0;nextNode < n;nextNode++){
                int weight = Math.abs(points[currNode][0] - points[nextNode][0]) + Math.abs(points[currNode][1] - points[nextNode][1]);

                if(!inMST[nextNode] && minDist[nextNode] > weight){
                        minDist[nextNode] = weight;
                }
            }
        }

        return mstCost;


    }




}

