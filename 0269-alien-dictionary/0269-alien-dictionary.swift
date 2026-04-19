/*

/*
 * ============================================================
 * COMPLEXITY ANALYSIS (Time & Space)
 * ============================================================
 * * 1. TIME COMPLEXITY: O(C)
 * Where C is the total number of characters across all words.
 *
 * - Phase A: Building the Graph — O(C)
 * * Initializing: We iterate through every character of every word 
 * once to populate the inDegree map. This is O(C).
 * * Comparisons: We compare adjacent word pairs. In the worst case, 
 * we scan characters until a difference is found. Since we only 
 * look at each character to establish a rule once, this is O(C).
 *
 * - Phase B: Topological Sort (BFS) — O(V + E)
 * * V (Vertices): Unique characters (max 26 in English).
 * * E (Edges): Total rules found (max V^2).
 * * Since V is fixed (26), O(V + E) is essentially O(1).
 *
 * TOTAL TIME: O(C) as scanning the input dominates the sorting phase.
 *
 * 2. SPACE COMPLEXITY: O(1) or O(U)
 * Where U is the number of unique characters (max 26).
 *
 * - Constant Space: The Adjacency List, In-Degree Map, Queue, and 
 * Result String are all bounded by the size of the alphabet (26). 
 * Regardless of whether you have 100 words or 1,000,000 words, 
 * these structures do not grow past 26 entries.
 *
 * - Note on O(C) Space: The `wordArrays` map in this specific Swift 
 * implementation creates character copies of the input, taking O(C) 
 * temporary space. This could be optimized to O(1) by using String.Index.
 *
 * ============================================================
 * SUMMARY TABLE
 * ------------------------------------------------------------
 * | Step                   | Time Complexity | Space Complexity |
 * ------------------------------------------------------------
 * | Iterate words (init)   | O(C)            | O(U) ≈ O(1)      |
 * | Compare pairs (edges)  | O(C)            | O(U^2) ≈ O(1)    |
 * | BFS Sort (Kahn's)      | O(V + E) ≈ O(1) | O(V) ≈ O(1)      |
 * | TOTAL                  | O(C)            | O(1)             |
 * ------------------------------------------------------------
 */
 * ============================================================
 * ALIEN DICTIONARY - TOPOLOGICAL SORT (KAHN'S ALGORITHM)
 * ============================================================
 * * --- TIME COMPLEXITY: O(C) ---
 * C = Total number of characters across all words.
 * 1. Initializing the graph takes O(C) to visit every character.
 * 2. Building edges takes O(C) as we compare adjacent words.
 * 3. BFS takes O(V + E). Since V (vertices) is capped at 26 (alphabet)
 * and E (edges) is capped at V^2, this part is essentially O(1).
 *
 * --- SPACE COMPLEXITY: O(U) or O(1) ---
 * U = Number of unique characters (max 26).
 * 1. Adjacency list and In-Degree map store at most 26 keys.
 * 2. The result string and BFS queue store at most 26 characters.
 * 3. The space is constant relative to the size of the alphabet.
 *
 * --- WHY ADJACENT WORDS ONLY? ---
 * Lexicographical order is transitive. If "A < B" and "B < C", 
 * then "A < C" is already implied. Comparing adjacent pairs 
 * captures all necessary dependency rules efficiently.
 * ============================================================
 */
//
// INTUITION:
// Compare adjacent words to extract character ordering rules,
// then use BFS topological sort to determine the full order.
// If a cycle exists → return "". If invalid prefix → return "".
//
// ------------------------------------------------------------
// DRY RUN: words = ["wrt", "wrf", "er", "ett", "rftt"]
// ------------------------------------------------------------
//

//

//
// STEP 3 — BFS from in-degree 0 nodes:
//
//   Initial queue: [w]
//
//   Pop w → result: [w]
//     w→e: inDegree[e] = 1-1 = 0 → enqueue e
//     queue: [e]
//
//   Pop e → result: [w, e]
//     e→r: inDegree[r] = 1-1 = 0 → enqueue r
//     queue: [r]
//
//   Pop r → result: [w, e, r]
//     r→t: inDegree[t] = 1-1 = 0 → enqueue t
//     queue: [t]
//
//   Pop t → result: [w, e, r, t]
//     t→f: inDegree[f] = 1-1 = 0 → enqueue f
//     queue: [f]
//
//   Pop f → result: [w, e, r, t, f]
//     no neighbors
//     queue: []
//
// STEP 4 — Cycle check:
//   result.count (5) == inDegree.count (5) ✅ no cycle
//
// OUTPUT: "wertf"
//
// ------------------------------------------------------------
// COMPLEXITY:
//   Time  → O(C) where C = total characters across all words
//   Space → O(U) where U = unique characters (max 26)
// ============================================================


// class Solution {
    //the question is , we have been given a set of strings/words which are ordered according to alien dictionary. it is claimed that the strings in words are sorted lexicographically by rules of this new language
    //if this claim is incorrect and the given arrangement of string in words cannot correspond to any order of letter, then we should return ""
    //return a string of unique letters in new alien langauge sorted in lexicographically increasing order
//     func alienOrder(_ words: [String]) -> String {
//         //what are the steps we have to follow?
//         // a common layman can observe this pattern
//         //wrt, wrf => t -> f
//         //wrf, er => w -> e
//         //er, ett => r -> t
//         //ett, rftt => e -> r
//         //these are the orders we have observed
//         // build the graph
//         //we have 4 edges. we should run the topological sort for this
//         //we start with bfs. nodes with in degree 0

//         //process w and then t

//         //process e and then f

//         //process r -> done
//         //handle prefix trap 

//         //count in degrees
//         //bfs/ kahn's algorithm => this is topological sort
//         //check for cycle
//         //graph
//         var adj: [Character: Set<Character>] = [:]
        
//         //indegree array 
//         var inDegree: [Character: Int] = [:]
//         // STEP 1 — Collect all unique characters:
// //   adj      = [w:{}, r:{}, t:{}, f:{}, e:{}]
// //   inDegree = [w:0,  r:0,  t:0,  f:0,  e:0]
//     //build adjacency list for all unique characters
//         for word in words {
//             for c in word {
//                 if adj[c] == nil {
//                     //if there is no entry of this character in the address book then create an empty one for this
//                     adj[c] = Set<Character>()
//                     if inDegree[c] == nil {
//                         inDegree[c] = 0
//                     }
//                 }
//             }
//         }

        

//         //compare adjacent word pairs 
//         //converting the string into array of characters, because in swift we can not index into a string words[i][j] directly like this. once we convert it to array we can
//         let arr = words.map {
//             Array($0)
//         }
//         for i in 0..<arr.count - 1 {
//             let w1 = arr[i]
//             let w2 = arr[i+1]
//             let minLen = min(w1.count, w2.count)

//             //check prefix 
//             if w1.count > w2.count && Array(w1.prefix(minLen)) == w2 {
//                 return "" //invalid
//                 //why its invalid? no dictionary gives us this
//                 /*
//                 example: 
//                 in a real english dictionary if you look up app and apple. app comes first, then apple comes second. always => every dictionary in the world.
//                 //shorter word comes first when one is a prefix of the other
// suppose : words ["apple","app"] apple is listed before app thats backwards. this is illegal / invalid. no valid language can produce this ordering. so we must return ""
//                 */
//             }
            
//             for j in 0..<minLen {
//                 if w1[j] != w2[j] {
//                     let (from, to) = (w1[j], w2[j])
//                     if !(adj[from]?.contains(to) ?? false){
//                         adj[from]?.insert(to)
//                         inDegree[to, default: 0] += 1
//                     }
//                     break
//                 }
//             }
//         }

//         //topological sort: BFS/Kahn's algorithm
//         //first we go through every character in inDegree and keep only the ones with value 0. value 0 means nobody needs to come before this character
//         var queue: [Character] = inDegree.filter {
//             $0.value == 0
//         }.map {
//             $0.key
//         }
//         var result: [Character] = []
//         var head = 0

//         while head < queue.count {
//             let c = queue[head]; head += 1
//             result.append(c)
//             for nbr in adj[c] ?? [] {
//                 inDegree[nbr, default: 0] -= 1
//                 if inDegree[nbr] == 0 {
//                     queue.append(nbr)
//                 }
//             }
//         }

//         if result.count != inDegree.count {
//             return ""
//         }
//         return String(result)

//     }

class Solution {
    func alienOrder(_ words: [String]) -> String {
        // --- DATA STRUCTURES ---
        var adj: [Character: Set<Character>] = [:]
        var inDegree: [Character: Int] = [:]
        
        // --- STEP 1: INITIALIZE ---
        // Goal: Ensure every unique character is a node in our graph.
        for word in words {
            for char in word {
                adj[char] = []
                inDegree[char] = 0
            }
        }
        /* Example State:
           adj      = [w:[], r:[], t:[], f:[], e:[]]
           inDegree = [w:0,  r:0,  t:0,  f:0,  e:0]
        */

        // --- STEP 2: BUILD GRAPH & FIND RULES ---
        let wordArrays = words.map { Array($0) }
        
        for i in 0..<wordArrays.count - 1 {
            let w1 = wordArrays[i]
            let w2 = wordArrays[i+1]
            let minLen = min(w1.count, w2.count)
            
            // Check Prefix Trap: "apple" cannot come before "app"
            if w1.count > w2.count && Array(w1.prefix(minLen)) == w2 {
                return "" 
            }
            
            // Find the first differing character to establish a rule
            for j in 0..<minLen {
                if w1[j] != w2[j] {
                    let from = w1[j], to = w2[j]
                    
                    // If this is a new rule, record the edge and increment in-degree
                    if !adj[from]!.contains(to) {
                        adj[from]!.insert(to)
                        inDegree[to, default: 0] += 1
                    }
                    break // Important: Only the first difference counts!
                }
            }
        }
        /* Example State (after all comparisons):
           Rules found: t->f, w->e, r->t, e->r
           adj      = [w:[e], e:[r], r:[t], t:[f], f:[]]
           inDegree = [w:0,   e:1,   r:1,   t:1,   f:1]
        */

        // --- STEP 3: BFS (KAHN'S ALGORITHM) ---
        // Start with characters that have no dependencies (In-degree 0)
        var queue: [Character] = inDegree.filter { $0.value == 0 }.map { $0.key }
        var result = ""
        var head = 0 
        
        /* Initial Queue: ["w"] */
        
        while head < queue.count {
            let current = queue[head]
            head += 1
            result.append(current)
            
            // For every character that must come AFTER 'current'...
            if let neighbors = adj[current] {
                for neighbor in neighbors {
                    // Decrease their in-degree (dependency satisfied)
                    inDegree[neighbor]! -= 1
                    
                    // If no more dependencies, add to queue
                    if inDegree[neighbor] == 0 {
                        queue.append(neighbor)
                    }
                }
            }
        }
        /* Example Process:
           1. Pop 'w' -> Queue: ['e'] (e's in-degree was 1, now 0)
           2. Pop 'e' -> Queue: ['r'] (r's in-degree was 1, now 0)
           3. Pop 'r' -> Queue: ['t'] (t's in-degree was 1, now 0)
           4. Pop 't' -> Queue: ['f'] (f's in-degree was 1, now 0)
           5. Pop 'f' -> Queue: []
        */

        // --- STEP 4: FINAL VALIDATION ---
        // If result length matches unique char count, we found a valid order.
        // If not, it means there was a circular dependency (e.g., a->b and b->a).
        return result.count == inDegree.count ? result : ""
        
        /* Final Example Output: "wertf" */
    }
}
