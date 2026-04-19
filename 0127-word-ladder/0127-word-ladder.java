/**
 * TOPIC: Breadth-First Search (BFS) - Shortest Path in an Unweighted Graph
 * * THE 12-YEAR-OLD ANALOGY:
 * Imagine a dark forest. You are looking for a treasure (endWord).
 * Instead of running 5 miles in one direction (DFS), you take 1 step in EVERY 
 * possible direction, then from those spots, you take another step (BFS). 
 * The moment you bump into the treasure, you KNOW it's the shortest path.
 *
 * THE TRAPS TO AVOID:
 * 1. The Loop Trap: Always remove words from the Set once visited (Burn the Bridge).
 * 2. The Speed Trap: Use a HashSet for O(1) lookups. Never search a List directly.
 * 3. The Memory Trap: Use a char[] for "surgery" instead of creating many substrings.
 */
 /*
DRY RUN EXAMPLE:
Input: begin="HIT", end="COG", list=["HOT", "DOT", "DOG", "COG"]

Round 1 (Dist 1): 
   - Pull "HIT" -> Try letters -> Find "HOT"
   - Queue: ["HOT"], Set: {"DOT", "DOG", "COG"}
Round 2 (Dist 2): 
   - Pull "HOT" -> Try letters -> Find "DOT"
   - Queue: ["DOT"], Set: {"DOG", "COG"}
Round 3 (Dist 3): 
   - Pull "DOT" -> Try letters -> Find "DOG"
   - Queue: ["DOG"], Set: {"COG"}
Round 4 (Dist 4): 
   - Pull "DOG" -> Try letters -> Find "COG"
   - Queue: ["COG"], Set: {}
Round 5 (Dist 5): 
   - Pull "COG" -> MATCH! Return 5.

COMPLEXITY:
Time: O(M^2 * N) -> M = word length, N = total words in list.
Space: O(M * N) -> To store the Set and Queue.
*/

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //i convert the array of string of words to hashset so that i can contain only the unique elements and also look up time is very less. if this word in the list => instant
        Set<String> wordSet = new HashSet<>(wordList);
        //If target is not in the list no path exists
        if(!wordSet.contains(endWord))
        return 0;
        //we will search using queue -> algorithm used is breadth first search
        Queue<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        int distance = 1;

        while(!queue.isEmpty()){
            int size = queue.size();
            //process all words at the current level
            for(int i = 0;i<size;i++){
                String currentWord = queue.poll();
                //edge case
                if (currentWord.equals(endWord))
                return distance; 
                //try changing each character of the word
                char[] chars = currentWord.toCharArray();
                for(int j=0;j<chars.length;j++){
                    char originalChar = chars[j];
                    for(char c = 'a';c<='z';c++){
                        if (c == originalChar)
                        continue;
                        chars[j] = c;
                        String nextWord = String.valueOf(chars);
                        if (wordSet.contains(nextWord)){
                            queue.add(nextWord);
                            //mark as visited 
                            wordSet.remove(nextWord);
                        }
                    }
                    //restore for next position
                    chars[j] = originalChar;
                }
        }
        distance++;


    }
    return 0;
    }
}



