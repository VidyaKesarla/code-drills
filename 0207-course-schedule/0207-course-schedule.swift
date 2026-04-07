/**
 * LEETCODE 207: Course Schedule (Topological Sort / Kahn's Algorithm)
 * -------------------------------------------------------------------
 * ANALOGY:
 * - LOCKERS (adj): Locker 'i' contains a list of courses that "unlock" 
 * once you finish course 'i'.
 * - BOSSES (inDegree): If inDegree[5] is 3, it means course 5 has 3 "bosses" 
 * (prerequisites) you must defeat before you can take it.
 *
 * --- VISUALIZATION DRY RUN ---
 * Input: numCourses = 3, prerequisites = [[1,0], [2,1]] (0 -> 1 -> 2)
 * * | Step | Queue | Current (Poll) | completedCount | Neighbors (Bosses Defeated) | New inDegree |
 * |------|-------|----------------|----------------|---------------------------|--------------|
 * | Init | [0]   | --             | 0              | Course 0 has 0 bosses     | [0:0, 1:1, 2:1] |
 * | 1    | []    | 0              | 1              | Open Locker 0 -> Defeat 1 | [1:0, 2:1]   |
 * | 2    | [1]   | --             | 1              | 1's bosses = 0 -> Add to Q| [1:0, 2:1]   |
 * | 3    | []    | 1              | 2              | Open Locker 1 -> Defeat 1 | [2:0]        |
 * | 4    | [2]   | --             | 2              | 2's bosses = 0 -> Add to Q| [2:0]        |
 * | 5    | []    | 2              | 3              | Done!                     | --           |
 * * Result: completedCount (3) == numCourses (3) -> TRUE
 * -------------------------------------------------------------------

 */
class Solution {
/**
 * COMPLEXITY ANALYSIS:
 * -------------------------------------------------------------------
 * TIME COMPLEXITY: O(V + E)
 * - Building the Graph: We iterate through the prerequisites list once 
 * to populate the adj list and inDegree array. O(E)
 * - Initializing the Queue: We iterate through all numCourses once 
 * to find those with an in-degree of 0. O(V)
 * - Processing the BFS: 
 * * Each course is added to/removed from the queue once. O(V)
 * * Each edge (neighbor) is visited exactly once. O(E)
 * - Total: O(V + E) + O(V) + O(E) = O(V + E)
 *
 * SPACE COMPLEXITY: O(V + E)
 * - Adjacency List (adj): Stores every prerequisite edge. O(V + E)
 * - In-Degree Array: An integer array of size numCourses. O(V)
 * - Queue: In the worst case, holds all courses. O(V)
 * - Total: O(V + E) + O(V) + O(V) = O(V + E)
 *
 * -------------------------------------------------------------------
 * SWIFT INTERVIEW TIP: Array Optimization
 * Using a 'head' pointer instead of queue.removeFirst() is crucial.
 * - queue.removeFirst() is O(n) in Swift (shifts all elements).
 * - This would degrade total Time Complexity to O(V² + E).
 * - Using a 'head' pointer maintains O(1) dequeue and O(V + E) total time.
 */
    func canFinish(_ numCourses: Int, _ prerequisites: [[Int]]) -> Bool {
        //set up my data structure: adjacency matrix/list 
        //Array(repeating:count:)	An initializer that creates an array of a specific size and fills it with a default value.
        var adj = Array(repeating: [Int](), count: numCourses)
        //create an indegree array
        var inDegree = Array(repeating: 0, count: numCourses)

        //building the graph: we use tuple like decomposition in the for in loop
        for edge in prerequisites {
            let course = edge[0]
            let prereq = edge[1]
            adj[prereq].append(course)
            inDegree[course] += 1
        }

        //we initialise queue with an array as swift does not have built in queue type, if massive data set then use deque
        var queue = [Int]()
        for i in 0..<numCourses {
            if inDegree[i] == 0 {
                queue.append(i)
            }
        }

        //process the queue, use a pointer for queue which is head which will help us simulate the queue.popFirst() and then initialise a var which has completed course count so far
        var completedCount = 0
        var head = 0

        while head < queue.count {
            let current = queue[head]
            head += 1
            completedCount += 1

            for neighbor in adj[current] {
                inDegree[neighbor] -= 1

                if inDegree[neighbor] == 0 {
                    queue.append(neighbor)
                }
            }
        }

        return completedCount == numCourses

    }
}