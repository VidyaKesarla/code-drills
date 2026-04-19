/**
 * LEETCODE 208: Implement Trie (Prefix Tree)
 * * APPROACH: 
 * This is a recursive-style Trie implementation where each Trie object represents 
 * both a Node and the Tree itself. This "Compact Approach" is efficient for 
 * competitive programming due to less boilerplate.
 *
 * DATA STRUCTURE:
 * - Trie[] children: An array of size 26 representing 'a'-'z'.
 * - boolean isEndOfWord: Flag to distinguish between a prefix and a full word.
 */
class Trie {
    private Trie[] children;
    private boolean isEndOfWord;

    /** * INITIALIZATION
     * TC: O(1)
     * SC: O(26) for the root array
     */
    public Trie() {
        this.children = new Trie[26];
        this.isEndOfWord = false;
    }

    /** * INSERTION
     * Logic: Iterate through the word. If the path for a character doesn't 
     * exist, create a new Trie node. Mark the final node as isEndOfWord.
     * * TC: O(L) - where L is word length.
     * SC: O(L) - worst case, L new nodes are created.
     */
    public void insert(String word) {
        Trie node = this;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) {
                node.children[i] = new Trie();
            }
            node = node.children[i];
        }
        node.isEndOfWord = true;
    }

    /** * SEARCH (Full Word)
     * Logic: Use the helper to traverse the path. Node must exist AND 
     * the isEndOfWord flag must be true.
     * * TC: O(L)
     * SC: O(1)
     */
    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }

    /** * STARTS WITH (Prefix Search)
     * Logic: Use the helper to traverse the path. If path exists, return true.
     * * TC: O(L)
     * SC: O(1)
     */
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    /** * HELPER: searchPrefix
     * Navigates the Trie based on the input string.
     * Returns the last node reached, or null if path breaks.
     */
    private Trie searchPrefix(String prefix) {
        Trie node = this;
        for (char c : prefix.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) {
                return null;
            }
            node = node.children[i];
        }
        return node;
    }
}

/**
 * DRY RUN EXAMPLE:
 * 1. insert("app"): 
 * - 'a' -> created at root.children[0]
 * - 'p' -> created at 'a'.children[15]
 * - 'p' -> created at 'p'.children[15], isEndOfWord = true
 * 2. search("ap"): 
 * - searchPrefix finds 'p' node, but isEndOfWord is false -> returns false.
 * 3. startsWith("ap"): 
 * - searchPrefix finds 'p' node -> returns true.
 *
 * TRADE-OFFS:
 * - Pros: Extremely fast lookup (O(L)) and efficient prefix matching.
 * - Cons: High memory usage. Each node has a 26-slot array even if empty.
 * * FOLLOW-UP OPTIMIZATION:
 * - To save space for large alphabets/Unicode, replace Trie[] with 
 * HashMap<Character, Trie>.
 */