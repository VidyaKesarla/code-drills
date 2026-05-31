
// Note : -
// - Modify the function or parameters if needed.
// - Signatures function may vary, adjust parameters if required.

class Solution {
    public String frequencySort(String s) {
        //we need to know how many chars are present in the string
        //edge case:
        if(s == null || s.isEmpty())
        return s;

        //create a sorted array of characters because we cant directly sort a string. we need to convert this to a char array . how will we do it? // by using a in built function: toCharArray
        //another intuition is sort the chars by their numbers so that identical chars are side by side

        char [] chars = s.toCharArray();
        //tc: o(nlogn)
        Arrays.sort(chars);

        //convert identical characters into single group of strings
        List<String> charStrings = new ArrayList<String>();
        StringBuilder currentString = new StringBuilder();
        currentString.append(chars[0]);

        for(int i =1;i<chars.length;i++){
            //condition 1:
            //check if the incoming char is same as the previous one or not
            //if not:
            if(chars[i] != chars[i-1]){
                charStrings.add(currentString.toString());
                currentString = new StringBuilder();
            }
            //else i am just going to append this to currentString 
            currentString.append(chars[i]);
        }
        //convert string builder strings to to String
        charStrings.add(currentString.toString());

        //our comparator: is a,b -> b.length - a.length
        //if a is longer than b then a negative number will be returned 
        //telling the sort algorithm to place a first : otherwise a positive nuumber will be returned: telling it to place a second
        //this results in a longest to shorted sorted list of strings
        //sorting it according to the lenght of identical characters
        Collections.sort(charStrings, (a,b) -> b.length() - a.length());

        //use string builder to build the string to return 
        StringBuilder sb = new StringBuilder();
        for(String str: charStrings)
        sb.append(str);

        return sb.toString();

    }
}
//tc: O(nlogn)
//sc: O(n)

// ### 💡 Intuition & Technical Breakdown

// This solution uses a **sorting-heavy** approach. By sorting the characters initially, identical characters are clumped together. This allows us to easily group them into distinct string segments and then perform a secondary sort on those segments based on their length (frequency).

// ---

// ### ⏱️ Complexity Analysis

// * **Time Complexity (TC):** $O(N \log N)$
//   * `Arrays.sort(chars)` takes $O(N \log N)$ where $N$ is the length of the string.
//   * Grouping the characters into `charStrings` takes a single linear pass: $O(N)$.
//   * `Collections.sort(charStrings)` takes $O(K \log K)$ where $K$ is the number of unique characters. In the worst-case scenario where all characters are unique ($K = N$), this step takes $O(N \log N)$.
//   * Reassembling the final string takes $O(N)$.

// * **Space Complexity (SC):** $O(N)$
//   * Storing the characters in the `chars` array requires $O(N)$ space.
//   * The `charStrings` list and `StringBuilder` allocations scale linearly with the input length, requiring $O(N)$ auxiliary space.

// ---

// ### 🔍 Detailed Dry Run (`s = "tree"`)

// 1. **Initial Sort:** * `chars` array becomes `['e', 'e', 'r', 't']`.

// 2. **Grouping Pass:**
//    * `i = 1`: `chars[1]` ('e') matches `chars[0]`. `currentString` becomes `"ee"`.
//    * `i = 2`: `chars[2]` ('r') does not match. `"ee"` is added to `charStrings`. `currentString` resets to `"r"`.
//    * `i = 3`: `chars[3]` ('t') does not match. `"r"` is added to `charStrings`. `currentString` resets to `"t"`.
//    * **Post-loop:** The remaining `"t"` is appended.
//    * **Resulting List:** `["ee", "r", "t"]`

// 3. **Custom Sort & Assembly:**
//    * Sorting the list by string length keeps `"ee"` at the front: `["ee", "r", "t"]`.
//    * `StringBuilder` stitches them back together.
//    * **Output:** `"eert"` (or `"eetr"`)

// ---

// ### ⚖️ Brute Force Trade-offs
// * **Pros:** Extremely intuitive to implement, avoids map/frequency-array overhead, and ensures stable clumping of identical elements early on.
// * **Cons:** Dual sorting passes introduce an $O(N \log N)$ bottleneck. While efficient enough to pass easily, it can be optimized to optimal $O(N)$ linear time using a frequency bucket array.