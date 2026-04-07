class Solution {
    func findOrder(_ numCourses: Int, _ prerequisites: [[Int]]) -> [Int] {
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
        var result = [Int]()
        var head = 0

        while head < queue.count {
            let current = queue[head]
            head += 1
            result.append(current)


            for neighbor in adj[current] {
                inDegree[neighbor] -= 1

                if inDegree[neighbor] == 0 {
                    queue.append(neighbor)
                }
            }
        }

        return result.count == numCourses ? result : []
    }
}