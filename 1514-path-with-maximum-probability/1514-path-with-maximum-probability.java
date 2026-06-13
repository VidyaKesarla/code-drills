class Solution {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        //we are going to use dijkstra's algorithm adapted for maximum probability instead of shortest / minimum distance

        //Adjacency list, node - > list of (neighbor, edge probability)
        Map<Integer, List<Pair<Integer, Double>>> graph = new HashMap<>();

        for(int i =0;i<edges.length;i++){
            //source
            int u = edges[i][0];
            //destination
            int v = edges[i][1];

            //take the current probability
            double edgeProb = succProb[i];

            /* Equivalent code for below:
            if(!map.containsKey(u)){
                map.put(u, new ArrayList<>());
            }
                map.get(u).add(new Pair<>(v, pathProb));
            */
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(new Pair<>(v, edgeProb));
            graph.computeIfAbsent(v, k -> new ArrayList<>()).add(new Pair<>(u, edgeProb));
        } // Closed the graph-building loop properly here

        //create a max probability array
        double [] maxProb = new double[n];
        maxProb[start_node] = 1.0;

        //Initialize an empty queue queue to store nodes that need to be visited.
        //max heap 
        PriorityQueue<Pair<Double, Integer>> pq = new PriorityQueue<>((a,b) -> -Double.compare(a.getKey(), b.getKey()));

        //Add the starting node start and its probability to the priority queue.
        pq.add(new Pair<>(1.0,start_node));

        while(!pq.isEmpty()){
            //remove cur_node, the node with the highest priority from it
            Pair<Double, Integer> curr = pq.poll();

            double curProb = curr.getKey();
            int currNode = curr.getValue();

            if(currNode == end_node) {
                return curProb;
            }


            // For each neighbor nxt_node of the current node cur_node, calculate the probability of traveling from the starting node to the nxt_node through the current edge cur_node --- nxt_node, and update the maximum probability of nxt_node if necessary. To update the maximum probability, compare the product of the probability with the current node and the probability of the edge cur_node --- nxt_node, with the current maximum probability to the neighbor node. If the product is larger than the maximum probability stored in max_prob[nxt_node], we update the maximum probability max_prob[nxt_node] as their product.

            if(graph.containsKey(currNode)) {
                // Check if the node has been processed
                for( Pair<Integer, Double> neighbor: graph.getOrDefault(currNode, new ArrayList<>())) {
                    int nextNode = neighbor.getKey();
                    double pathProb = neighbor.getValue();
                    if(curProb * pathProb > maxProb[nextNode]) {
                        maxProb[nextNode] = curProb * pathProb;
                        pq.add(new Pair<> (maxProb[nextNode], nextNode));
                    }
                } 
                graph.remove(currNode); //clear the adjacency list by removing the entry
            }
        }
        return 0.0;
    }
}