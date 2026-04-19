    class Solution {
    func asteroidCollision(_ asteroids: [Int]) -> [Int] {
        // Use an array as a stack to store the surviving asteroids.
        var stack: [Int] = []

        // Iterate through each asteroid.
        for newAsteroid in asteroids {
            // A flag to determine if the new asteroid survives and should be added to the stack.
            var newAsteroidSurvived = true
            
            // A collision can only happen if the new asteroid is moving left (< 0)
            // and the asteroid on top of the stack is moving right (> 0).
            while !stack.isEmpty && newAsteroid < 0 && stack.last! > 0 {
                let topAsteroid = stack.last!
                
                // Case 1: The asteroid on the stack is larger.
                // The new asteroid explodes.
                if topAsteroid > -newAsteroid {
                    newAsteroidSurvived = false
                    break
                } 
                // Case 2: Both asteroids are the same size.
                // Both explode.
                else if topAsteroid == -newAsteroid {
                    stack.removeLast()
                    newAsteroidSurvived = false
                    break
                } 
                // Case 3: The new asteroid is larger.
                // The asteroid on the stack explodes.
                else {
                    stack.removeLast()
                    // Continue the loop to check the new asteroid against the next one on the stack.
                }
            }
            
            // If the new asteroid survived all its encounters, add it to the stack.
            if newAsteroidSurvived {
                stack.append(newAsteroid)
            }
        }
        
        // The stack contains the final state of the asteroids.
        return stack
    }
}