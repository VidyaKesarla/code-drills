/**
 * APPROACH: Modified Bellman-Ford (Layered BFS Logic)
 * * Why this over standard algorithms?
 * 1. Bellman-Ford: Naturally handles the "At most K stops" constraint by 
 * running exactly K+1 iterations. Each iteration represents taking 1 more flight.
 * 2. BFS: Could work similarly, but requires tracking the minimum cost at each 
 * "level" to avoid unnecessary work.
 * 3. Dijkstra: Normally the fastest for cheapest paths, but it's "greedy." 
 * It might find a cheap path that exceeds K stops and ignore a more 
 * expensive path that fits the K limit. To fix this, Dijkstra needs a 
 * complex state: (cost, city, stops_remaining).
 *
 * --- DRY RUN EXAMPLE ---
 * n = 3, flights = [[0,1,100], [1,2,100], [0,2,500]], src = 0, dst = 2, k = 1
 * * Initial: prices = [0, inf, inf]
 * * Round 1 (i=0, 1 flight max):
 * - Flight [0,1,100]: prices[0] is 0 -> temp[1] = 100
 * - Flight [1,2,100]: prices[1] is inf -> Skip (prevents using 2 flights in 1 round)
 * - Flight [0,2,500]: prices[0] is 0 -> temp[2] = 500
 * Result: prices = [0, 100, 500]
 * * Round 2 (i=1, 2 flights max):
 * - Flight [0,1,100]: 0 + 100 not < 100 -> No change
 * - Flight [1,2,100]: prices[1] is 100 -> 100 + 100 < 500? YES -> temp[2] = 200
 * - Flight [0,2,500]: 0 + 500 not < 200 -> No change
 * Result: prices = [0, 100, 200]
 * * Final Answer: prices[2] = 200
 */

  //we can't find this using dijsktra: as it for unweighted graph and shortest path
         // here shortest path means shortest weighted path bellmanford :  min sum of weights
         //prices: this is a map. We create an array to store the cheapest price found so far to reach every city. we fill it with Int.max because initially we havent discovered any cities yet
class Solution {
    //bellman ford algorithm
    func findCheapestPrice(_ n: Int, _ flights: [[Int]], _ src: Int, _ dst: Int, _ k: Int) -> Int {
    var prices = [Int](repeating: Int.max, count: n)
    prices[src] = 0
    
    // Iterate K + 1 times (each iteration = 1 flight taken)
    for _ in 0...k {
        var tempPrices = prices // Snapshot of previous iteration
        
        for flight in flights {
            let u = flight[0], v = flight[1], price = flight[2]
            
            // If the start city 'u' was reached in a previous round
            if prices[u] != Int.max {
                if prices[u] + price < tempPrices[v] {
                    tempPrices[v] = prices[u] + price
                }
            }
        }
        prices = tempPrices // Finalize this round's discoveries
    }
    
    return prices[dst] == Int.max ? -1 : prices[dst]
}
}


/**
 * APPROACH: Modified Bellman-Ford (The "Shortest Path for the Paranoid")
 *
 * 1. THE CORE PHILOSOPHY: "RELAXATION"
 * The algorithm relies on Relaxation. Imagine you think a bus costs $50. Someone says: 
 * "Take a train for $10, then a shuttle for $20." You just "relaxed" your estimate to $30. 
 * Bellman-Ford does this for every single edge, over and over.
 *
 * 2. HOW IT WORKS (Standard Version)
 * - Initialize: Set source distance to 0, all others to Infinity.
 * - The Loop: Repeat V - 1 times (where V is the number of cities).
 * - Process: For every edge (u -> v), if dist[u] + cost < dist[v], update dist[v].
 * - Negative Cycle Check: One more loop to see if prices still drop (indicates an infinite loop).
 *
 * 3. WHY MODIFIED FOR "K STOPS"?
 * - Iteration Limit: We run only K + 1 times. Each loop represents taking exactly one 
 * more "hop." At most K stops means at most K + 1 flights.
 * - tempPrices Array: Standard Bellman-Ford updates 'in-place.' Here, we use a 
 * snapshot (tempPrices) to ensure we only process exactly ONE flight per iteration, 
 * preventing us from accidentally skipping ahead and taking multiple stops in one round.
 *
 * 4. BELLMAN-FORD VS. DIJKSTRA
 * | Feature          | Dijkstra (Greedy)        | Bellman-Ford (Iterative)     |
 * |------------------|--------------------------|------------------------------|
 * | Strategy         | Picks nearest node       | Checks every single edge     |
 * | Speed            | Faster: O(E log V)       | Slower: O(V * E)             |
 * | Negative Weights | Fails (gets stuck)       | Handles them perfectly       |
 * | Best Use Case    | Maps, GPS, General paths | Edge-limited paths (K-Stops) |
 *
 * TIME COMPLEXITY: O(K * E) where E is number of flights.
 * SPACE COMPLEXITY: O(N) to store prices.
 */

