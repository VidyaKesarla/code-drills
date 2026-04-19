class Trie {
//     children is an array of 26 Trie references (null-initialized), one slot per lowercase letter ('a' at index 0, 'b' at 1, etc.).

// isEndOfWord tracks if the node represents a complete word's end (false by default).
    private Trie [] children = new Trie[26];
    private boolean isEndOfWord = false;
    public Trie() {
        // Empty constructor; fields auto-initialize to null/false. Root node starts with no children
    }
    
    public void insert(String word) {
        //this means we are starting with root
        Trie node = this;
        for (char c: word.toCharArray()){
            //lets map a to 0, b to 1, c to 2 and so on
            int i = c - 'a';
            if(node.children[i] == null){
                //lets create the child if its missing
                node.children[i] = new Trie();
            }
            //traverse to the child
            node = node.children[i];
        }
        //mark end of word
        node.isEndOfWord = true;
    }
    
    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }
    
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    private Trie searchPrefix(String prefix){
        //this means we are starting with root
        Trie node = this;
        for (char c: prefix.toCharArray()){
            //lets map a to 0, b to 1, c to 2 and so on
            int i = c - 'a';
            if(node.children[i] == null){
                return null;
            }
            //traverse to the child
            node = node.children[i];
        }
        return node;

    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */