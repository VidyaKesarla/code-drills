
    /* there are three ways to solve this problem;
    1. The Sorting Approach ($O(n \log n)$)
    TC: $O(n \log n)$ due to sorting the unique elements.SC: $O(n)$ to store the Map and the List.
    We use a HashMap to count frequencies, then convert  the entry set into a List and sort it using a custom comparator.

    public List<Integer> topKFrequentSorting(int[] nums, int k) {
    Map<Integer, Integer> countMap = new HashMap<>();
    for (int n : nums) {
        countMap.put(n, countMap.getOrDefault(n, 0) + 1);
    }

    // Convert map keys to a list and sort by their frequency value
    List<Integer> list = new ArrayList<>(countMap.keySet());
    list.sort((a, b) -> countMap.get(b) - countMap.get(a));

    return list.subList(0, k);


/**
 * Approach 2: Min-Heap (Priority Queue)
 * ---------------------------------------------------------
 * 1. Count frequencies using a HashMap: O(n)
 * 2. Maintain a Min-Heap of size 'k':
 * - As we iterate through the unique elements, we add them to the heap.
 * - If the heap size exceeds 'k', we remove (poll) the element with the 
 * lowest frequency.
 * - This ensures that only the 'k' most frequent elements remain in the heap.
 * * Time Complexity: O(n log k)
 * - n: To build the frequency map.
 * - log k: Each heap operation (offer/poll) takes log k time because the 
 * heap never grows larger than k+1.
 * * Space Complexity: O(n + k)
 * - O(n) for the HashMap and O(k) for the Priority Queue.
 */
/*
public int[] topKFrequentHeap(int[] nums, int k) {
    Map<Integer, Integer> countMap = new HashMap<>();
    for (int n : nums) {
        countMap.put(n, countMap.getOrDefault(n, 0) + 1);
    }

    // Min-Heap: smallest frequency at the top
    PriorityQueue<Integer> heap = new PriorityQueue<>(
        (a, b) -> countMap.get(a) - countMap.get(b)
    );

    for (int num : countMap.keySet()) {
        heap.add(num);
        if (heap.size() > k) {
            heap.poll(); // Remove the least frequent element
        }
    }

    // Convert heap to array
    int[] res = new int[k];
    for (int i = 0; i < k; i++) {
        res[i] = heap.poll();
    }
    return res;
}

Step,Processing Element (Freq),Action,"Heap State (Top is ""Exit"")"
1,1 (Freq: 3),Add to heap,[1]
2,2 (Freq: 2),Add to heap,"[2, 1] (2 is at top because 2 < 3)"
3,3 (Freq: 1),Add to heap. Size > k (2)!,"[3, 2, 1] → Kick out 3"
,,Result:,"[2, 1]"
4,4 (Freq: 1),Add to heap. Size > k!,"[4, 2, 1] → Kick out 4"
,,Result:,"[2, 1]"
5,5 (Freq: 1),Add to heap. Size > k!,"[5, 2, 1] → Kick out 5"
,,Result:,"[2, 1]"
}*/

/**
 * Approach 3: Bucket Sort (The Optimal O(n) Solution)
 * ---------------------------------------------------------
 * WHY THIS IS THE BEST APPROACH:
 * 1. MATHEMATICAL SPEED: Unlike Sorting (O(n log n)) or Heaps (O(n log k)), 
 * Bucket Sort does not use comparisons. It achieves O(n) time complexity.
 * 2. EXPLOITS CONSTRAINTS: The maximum frequency of any element is limited 
 * by the size of the input array (n). We use this frequency as an index.
 * 3. EFFICIENCY: By mapping frequencies directly to array indices, we avoid 
 * the logarithmic overhead required by tree-based or sorting structures.
 *
 * COMPLEXITY:
 * - Time: O(n) -> One pass to count, one pass to bucket, one pass to collect.
 * - Space: O(n) -> To store the frequency map and the bucket array.
 */






class Solution {

    
    public int[] topKFrequent(int[] nums, int k) {

        //first create a hashmap which contains the frequency of each element of the array
        Map<Integer,Integer> countMap = new HashMap<>();

        for(int n: nums){
            /*
            When you call map.getOrDefault(key, 0):

            If the key exists: It returns the value currently stored for that key.

                If the key DOES NOT exist: Instead of returning null (which would crash your code if you tried to add 1 to it), it returns the default value you provided (in this case, 0).
            */
            countMap.put(n, countMap.getOrDefault(n,0) + 1);
        }

        //now create an array which will store the list of numbers that appear i times
        List<Integer>[] bucket = new List[nums.length + 1];
        //now for every element in the countMap, i will iterate through and add this element to the frequency list.
        //i am using keySet to get the unique set of elements in the countmap 
        for(int num : countMap.keySet()) {
            int freq = countMap.get(num);
            if(bucket[freq] == null) {
                bucket[freq] = new ArrayList<>();
            }
            bucket[freq].add(num);
        }

        //now to find the top most occuring elements i have to travel backwards from in the bucket array
        int [] res = new int[k];
        //i will also keep a counter to check if we are travelling backwards only k times
        int counter = 0;
        for(int i = bucket.length -1; i>=0 && counter < k; i--){
            if(bucket[i] != null){
                for(int num: bucket[i]){
                    res[counter++] = num;
                    if (counter == k) return res;
                }
            }
        }
        return res;  
    }
}


