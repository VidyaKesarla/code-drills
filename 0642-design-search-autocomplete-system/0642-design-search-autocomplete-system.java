/*
What is a trie?
It is a data structure that can be used to efficiently search for strings
we can also call it as a prefix tree used to efficiently store and retrieve keys in a dataset of strings
If we are not familiar with tries: there is some link which we can use to understand it

TRIE => TREE where each node is labeled.
We label each node with a character
Path from root to any node represents the string that is build by the nodes on the path
What it is=> complete tree management object
Main job => track single root node and expose public function
methods => insert(), search() and startsWith()
Quantity => only one instance is initialised to manage a dictionary

TRIENODE:
What it is=> A trienode is a single block/unit within the tree
Main job => it is to store links to children and an end-of-word flag
Quantity => thousands can exist inside a single tree
Root => empty string 

*/

class TrieNode {
    Map<Character, TrieNode> children;
    //sentences map will be used to count the number of times each sentence was typed
    Map<String, Integer> sentences;

    //initialise it
    public TrieNode(){
        children = new HashMap<>();
        sentences = new HashMap<>();
    }
}

class AutocompleteSystem {
    //trie ds is a very great option whenever we want to search in a collection of strings=> that is especialy if you are searching one character at a time

    //if we put all sentences in a trie => we can walk through every sentence simultaneously with each ccall to input

    TrieNode root;
    TrieNode currNode;
    TrieNode dead;
    StringBuilder currSentence;

    //Calling addToTrie(sentence, count) means that sentence was typed count times and we will update our trie to reflect it
    private void addToTrie(String sentence, int count){
        TrieNode node = root;
        for(char c: sentence.toCharArray()){
            if(!node.children.containsKey(c)){
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c);
            //each trie node has a hashmap which holds all sentences that have the current path as a prefix. As we need to return the sentences that have been typed the most, we need to map each sentence to its count
            node.sentences.put(sentence, node.sentences.getOrDefault(sentence, 0) + count);
        }
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        for(int i =0;i<sentences.length;i++){
            addToTrie(sentences[i], times[i]);
        }
        currSentence = new StringBuilder();
        currNode = root;
        dead = new TrieNode();
    }
    //calling this input function means we are typing some character, calling it repeatedly means we are typing out a sentence. currSentence is a class attribute which represents the current sentence we are typing. along with this let us also keep a class attribute currNode that represents the current node in trie we are located at.
    //whenever we start typing a new sentence we set currNode = root
    public List<String> input(char c) {
        //3 possibilities for input func:

        //1. we have finished typing current sentence. 
        /*
        Add currSentence as a string to trie using addToTrie func and reset our class variables. 
        empty currSentence
        set currNode = root, return an empty List
        */
        if(c == '#'){
            addToTrie(currSentence.toString(), 1);
            currSentence.setLength(0);
            currNode = root;
            return new ArrayList<String>();
        }

        //3. if current char c is not #, c is not a child of currNode.
        //there are no existing sentences that have the current sentence we aRE typing as a prefix. we just need to add c to currSentence and return an empty list
        currSentence.append(c);
        if(!currNode.children.containsKey(c)){
            currNode = dead;
            return new ArrayList<String>();
        }


        //2. if current char c is not #, c is a child of currNode.
        //there are some existing sentences that have the current sentence we are typing as a prefix. first lets add this current char c to currSentence. next walk to the childnode by doing currNode = currNode.children[c]. fetch the sentences that have the current sentence as a prefix. we store them in hashmap currNode.sentences with the mapping sentence: count. finally sort these sentences according to their count and return the top 3 sentences according to the criteria

        //as sorting is expensive, we can use heap for the same

        currNode = currNode.children.get(c);
        //initialise heap with a custom comparator -> worse sentences need to be removed first. 
        //we iterate over sentences and push each one onto the heap
        //when heap size exceeds 3 we pop from it 
        //after handling all sentences the 3 best sentences will remain in the heap.
        PriorityQueue<String> heap = new PriorityQueue<>((a,b) -> {
            //first hot sentence
            int hotA = currNode.sentences.get(a);
            int hotB = currNode.sentences.get(b);
            //
            if(hotA == hotB){
                return b.compareTo(a);
            }
            return hotA - hotB;
        });

        for(String sentence: currNode.sentences.keySet()){
            heap.add(sentence);
            if(heap.size() > 3){
                heap.remove();
            }
        }

        List<String> ans = new ArrayList<>();
        while(!heap.isEmpty()){
            ans.add(heap.remove());
        }

        Collections.reverse(ans);
        return ans;
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */