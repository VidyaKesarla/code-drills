/**
 * 49. Group Anagrams
 * * Logic:
 * 1. Categorize words by their sorted alphabetical label (e.g., "eat" -> "aet").
 * 2. Use a HashMap to store {SortedLabel: [OriginalWords]}.
 * 3. Grouping occurs naturally because all anagrams share the same SortedLabel.
 * * DRY RUN:
 * Input: strs = ["eat", "tea", "tan"]
 * * Step 1: word = "eat"
 * - Sorted Label = "aet"
 * - Map: {"aet": ["eat"]}
 * * Step 2: word = "tea"
 * - Sorted Label = "aet" (Already exists!)
 * - Map: {"aet": ["eat", "tea"]}
 * * Step 3: word = "tan"
 * - Sorted Label = "ant"
 * - Map: {"aet": ["eat", "tea"], "ant": ["tan"]}
 * * Final Output: [["eat", "tea"], ["tan"]]
 * * TC: O(N * K log K) - N is number of strings, K is max length of string.
 * SC: O(N * K) - Storing all strings in the HashMap.
 */
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : strs) {
            // Create the sorted key (The Label)
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            // If the key doesn't exist, create a new list
            if (!groups.containsKey(key)) {
                groups.put(key, new ArrayList<>());
            }
            
            // Add original word to the corresponding list
            groups.get(key).add(word);
        }

        return new ArrayList<>(groups.values());
    }
}
