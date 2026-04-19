/*
 * Problem: Decode String (k[encoded_string])
 * Approach: Two-Stack (Iterative)
 * * Logic:
 * We use a stack to manage nested contexts. When we hit '[', we "save" our progress 
 * (the string built so far and the multiplier) and start fresh for the inner content. 
 * When we hit ']', we "pop" the last saved state, multiply the current inner string, 
 * and append it to the outer string we saved.
 */

/*
 * --- DRY RUN ---
 * Input: "2[a3[b]]"
 * * 1. char: '2' -> currentNum = (0 * 10) + 2 = 2
 * 2. char: '[' -> stack.append(("", 2)), currentString = "", currentNum = 0
 * 3. char: 'a' -> currentString = "a"
 * 4. char: '3' -> currentNum = (0 * 10) + 3 = 3
 * 5. char: '[' -> stack.append(("a", 3)), currentString = "", currentNum = 0
 * 6. char: 'b' -> currentString = "b"
 * 7. char: ']' -> 
 * - Pop: (prevString: "a", repeatCount: 3)
 * - currentString = "a" + ("b" * 3) = "abbb"
 * 8. char: ']' -> 
 * - Pop: (prevString: "", repeatCount: 2)
 * - currentString = "" + ("abbb" * 2) = "abbbabbb"
 * * Final Result: "abbbabbb"
 */

/*
 * --- COMPLEXITY ANALYSIS ---
 *
 * Time Complexity: O(Max_K * N)
 * - We iterate through the string of length N once.
 * - However, we are building a resulting string. In the worst case (deeply nested 
 * or large multipliers), the time spent is proportional to the total number 
 * of characters in the final decoded string.
 *
 * Space Complexity: O(M + K)
 * - O(M) where M is the number of nested brackets (depth of the stack).
 * - O(K) for the space taken by the output string being constructed.
 */

class Solution {
    func decodeString(_ s: String) -> String {
        var countStack: [Int] = [] //stores repeat counts
        var stringStack: [String] = [] //stores build strings
        var currentStr = ""
        var currentNum = 0

        for char in s {
            if char.isNumber {
                currentNum = currentNum * 10 + Int(String(char))!
            } else if char == "[" {
                //save current state of string and repeating counts in two different stacks
                countStack.append(currentNum)
                stringStack.append(currentStr)
                currentNum = 0
                currentStr = ""
            } else if char == "]"{
                //pop and combine
                let repeatCount = countStack.removeLast()
                let prevStr = stringStack.removeLast()
                currentStr = prevStr + String(repeating: currentStr, count: repeatCount)
            } else {
                //regular character
                currentStr.append(char)
            }
        }
        return currentStr
    }
}


/*
FeatureIterative (Stack)Recursive (DFS)MemoryUses Heap memory for the Array.Uses Stack memory for function calls.ReadabilityClearer for simple loops.Very natural for "nested" problems (trees/brackets).RiskVirtually no risk of crash.Could hit a Stack Overflow if brackets are nested 10,000+ deep.
*/