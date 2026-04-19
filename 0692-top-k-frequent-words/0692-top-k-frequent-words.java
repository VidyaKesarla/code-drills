class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        //lets create a hashmap which will store frequencies or counts of the words
        Map<String, Integer> counts = new HashMap<>();
        for(String word: words){
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }

    /*
    The Comparator: * If frequencies are different: counts.get(a) - counts.get(b) makes it a min-heap for frequency (lowest frequency is at the top).

If frequencies are the same: b.compareTo(a) flips the alphabetical order for the heap. This ensures that if we have "apple" and "banana" with the same frequency, "banana" stays at the top to be removed first, leaving "apple" in our final list.

The pq.poll(): By keeping the heap size at k, we ensure we only perform operations on a small data structure, keeping the complexity at O(Nlogk).

The Reverse: Since the heap gives us the "top k" but in ascending order of importance, we reverse the final list to meet the "highest to lowest" requirement.

    */

        PriorityQueue<String> pq = new PriorityQueue<>((a,b) -> counts.get(a).equals(counts.get(b)) ? b.compareTo(a) : counts.get(a) - counts.get(b));


// 3. Maintain only k elements in the heap
        for (String word : counts.keySet()) {
            pq.offer(word);
            if (pq.size() > k) {
                pq.poll();
            }
        }
// 4. Build the result list (it will be in reverse order)
        List<String> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(pq.poll());
        }
// 5. Reverse to get descending frequency
        Collections.reverse(result);
        return result;



        
    }
}