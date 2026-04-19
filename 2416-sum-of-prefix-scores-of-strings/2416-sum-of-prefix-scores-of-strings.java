class Solution {

//best appraoch  - using trie -? 
/*
most efficient time complexity, specifically O(L), where L is the total length of all strings.
main challenge resolved by: counting shared prefixes—in a single, linear pass over the input data
*/

    private class TrieNode {
        TrieNode [] children;
        int count;
        public TrieNode() {
            children = new TrieNode[26];
            count = 0;
        }
    }

    //to insert a word into the trie incrementing the count for every node/prefix
    private void insert(TrieNode root, String word){
        TrieNode current = root;
        //convert the array to char array
        for(char c: word.toCharArray()){
            int index = c - 'a';
            if (current.children[index] == null){
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
            current.count++; //increment the score or count for this prefix
        }
    }

    //helper function: what does this do? 
    /*
    Traverse the Trie using the word and sum the 'count' located at each node
    along the path which  represents the scores of all prefixes
    */

    private long getPrefixScoreSum(TrieNode root, String word){
        TrieNode current = root;
        long score = 0;
        for(char c: word.toCharArray()){
        int index = c - 'a';
        current = current.children[index];
        // score of the prefix ending at current is current.count
        score += current.count;
        }
        return score;
    }


    public int[] sumPrefixScores(String[] words) {
        // Words of size n consisting of non empty strings
        /*
        score of a string term as the
         number of strings words[i] such that term is a prefix of words[i]
        */
        //create a trienode
        TrieNode root = new TrieNode();
        //find the length of the string
        int n = words.length;
        for (String word : words) {
            insert(root, word);
        }
        int[] answer = new int[n];
        for(int i =0;i<n;i++){
            answer[i] = (int)getPrefixScoreSum(root, words[i]);
        }
        return answer;
    }

}