class MedianFinder {
    private PriorityQueue<Integer> low;
    private PriorityQueue<Integer> high;

    public MedianFinder() {
        //maxHeap : stores the smaller half of the elements 
        low = new PriorityQueue<> (Collections.reverseOrder());
        //minHeap : stores the larger half of the elements
        high = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        //add the first element in the array to the maxHeap
        low.offer(num);

        //balance the right heap by adding the largest element from low to high heap
        high.offer(low.poll());

        // Step 3: Maintain size property (low.size >= high.size)
        if(low.size() < high.size()){
            low.offer(high.poll());
        }
    }
    
    public double findMedian() {
        if(low.size() > high.size()){
            return (double) low.peek();
        } else {
            return (low.peek() + high.peek())/2.0;
        }
        
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */

 /*
 public class Solution {
    public ArrayList<Integer> solve(ArrayList<Integer> A) {
        ArrayList<Integer> ans = new ArrayList<>();
        PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder()); // we are creating MAXPQ (for store the smaller value present in Array)
        PriorityQueue<Integer> right = new PriorityQueue<>(); // we are creating Default MINPQ (for store the greater value present in Array)
        
        for(int i = 0; i < A.size(); i++) { // we are iterating input ArrayList
            if(left.size() == right.size()) { // if both PQ are empty we should add value in right first and then remove from right and add that value in left to maintain smaller value in left PQ
                // add appropriate value should get added on left
                // add value to left via right
                right.add(A.get(i));
                left.add(right.remove());
                ans.add(left.peek()); // add value in answer ArrayList by getting peek value in left PQ                
            } else {
                // add appropriate value should get added on right
                // add value to left via left
                left.add(A.get(i));
                right.add(left.remove());
                ans.add(left.peek()); // add value in answer ArrayList by getting peek value in left PQ                
            }
        }
        return ans;
    }
}*/