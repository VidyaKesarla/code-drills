/**
 * LEETCODE: Apply Substitutions (BRUTE FORCE)
 * ------------------------------------------------------------
 * APPROACH: Repeated String Replacement
 * 1. While the text still contains the '%' character:
 * 2. Loop through every [key, value] pair in the replacements list.
 * 3. Use the built-in `.replacingOccurrences` to swap %key% for its value.
 *
 * --- WHY THIS FAILS / LIMITATIONS ---
 * 1. TIME LIMIT EXCEEDED (TLE): 
 * - Replacing a string requires a full scan of the text O(N).
 * - Doing this for M replacements in a loop that runs K times (for nesting) 
 * leads to O(K * N * M). On large strings, this is extremely slow.
 * 2. ORDERING ISSUES: 
 * - If you replace "%APP%" before "%APPLE%", you might break the larger tag.
 * 3. INFINITE LOOPS: 
 * - If %A% contains %B% and %B% contains %A%, this will loop forever.
 *
 * --- BRUTE FORCE CODE ---
 */

// class Solution {
//     func applySubstitutionsBruteForce(_ replacements: [[String]], _ text: String) -> String {
//         var result = text
        
//         // Keep replacing as long as there is a potential tag left
//         while result.contains("%") {
//             let previousResult = result
            
//             for pair in replacements {
//                 let key = "%\(pair[0])%"
//                 let value = pair[1]
                
//                 // Scan the entire string and replace all instances of this key
//                 result = result.replacingOccurrences(of: key, with: value)
//             }
            
//             // Safety break: If no changes were made in a full pass, stop 
//             // (e.g., if a % is present but doesn't match any key)
//             if result == previousResult {
//                 break
//             }
//         }
        
//         return result
//     }
// }

/**


 * LEETCODE: Apply Substitutions

 * * APPROACH: Recursive DFS with Memoization
 * 1. Convert text to a Character Array for O(1) index access.
 * 2. Use a HashMap (mapping) to store key-value pairs.
 * 3. Use a second HashMap (memo) to cache already-resolved tags.
 * 4. Use a 'Two-Pointer' logic inside a while loop to find patterns like %VAR%.
 * APPROACH: Recursive DFS with Memoization.
 * 1. Store replacements in a Dictionary for O(1) lookup.
 * 2. Scan string; when '%' is hit, find the closing '%' to extract the KEY.
 * 3. Recursively resolve the KEY (in case its value has more tags).
 * 4. Use a MEMO dictionary to store resolved tags and avoid duplicate work.
 *
 * --- VISUALIZATION DRY RUN ---
 * Input: text = "Hi %A%!", replacements = [["A", "Go %B%"], ["B", "Home"]]
 *
 * | Step | Pointer (i) | Current Char | Action / Logic                       | Result String   |
 * |------|-------------|--------------|--------------------------------------|-----------------|
 * | 1    | 0           | 'H'          | Normal char, append                  | "H"             |
 * | 2    | 1           | 'i'          | Normal char, append                  | "Hi"            |
 * | 3    | 2           | ' '          | Normal char, append                  | "Hi "           |
 * | 4    | 3           | '%'          | Start Tag. Find next '%' at index 5  | (Waiting...)    |
 * | 5    | [RECURSE]    | --           | Solving Key "A" ("Go %B%")           | (Waiting...)    |
 * | 6    | [RECURSE]    | --           | Solving Key "B" ("Home")             | "Home"          |
 * | 7    | [MEMO]       | --           | Save "B" -> "Home"                   | --              |
 * | 8    | [BACK TO A]  | --           | "Go " + "Home"                       | "Go Home"       |
 * | 9    | [MEMO]       | --           | Save "A" -> "Go Home"                | --              |
 * | 10   | 6 (Jump)     | '!'          | Append resolved "A" + remaining '!'  | "Hi Go Home!"   |
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY: O(N + M)
 * - N is the length of the final expanded string.
 * - M is the number of replacements.
 * SPACE COMPLEXITY: O(N + M)
 * - O(M) for the mapping/memo dictionaries.
 * - O(N) for the recursion stack and character array.
 */

class Solution {
    //the mapping that you see
    var mapping = [String: String]()
    //memoisation map to store repeated subproblems
    var memo = [String: String]()
    func applySubstitutions(_ replacements: [[String]], _ text: String) -> String {
        //creating a dictionary with key and value pairs basically a map to refer to find the value for the key. it just takes O(1) for looking up
        for pair in replacements {
            mapping[pair[0]] = pair[1]
        }
        //convert string to char array
        return resolve(Array(text))
    }

    private func resolve(_ chars: [Character]) -> String {
        var result = ""
        var i = 0
        
        while i < chars.count {
            if chars[i] == "%" {
                var j = i + 1
                while j < chars.count && chars[j] != "%"{
                    j += 1
                }
                //extract the word between the percents % ... %
                let key = String(chars[(i+1)..<j])

                //recursive resolution with memoisation - check if we have solved this tag before(memoisation) if this value is already present in memo
                if let resolvedValue = memo[key]{
                    //if already present then i just add resolved value to the result
                    result += resolvedValue
                } else if let rawValue = mapping[key]{
                    //recurse to handle nested tags inside the value
                    let resolved = resolve(Array(rawValue))
                    memo[key] = resolved
                    result += resolved
                }
                //now that we have already found out value for the key and added to the result, we just need to go to the next index after %
                i = j + 1
            } else {
                //if its just a normal character(alphabets) add it to the result
                result.append(chars[i])
                i += 1
            }
        }
        return result
    }
}
