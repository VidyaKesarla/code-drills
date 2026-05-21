class Solution {
    public int shipWithinDays(int[] weights, int days) {
      int totalLoad = 0;
      int maxLoad = 0;
      //n time complexity 
      for(int weight: weights){
        totalLoad += weight;
        maxLoad = Math.max(maxLoad, weight);
      }  
      int l = maxLoad;
      int r = totalLoad;
//O(logn)
      while(l<r){
        int mid = l + (r-l)/2;
        if (feasible(weights, mid, days)){
            r = mid;
        } else {
            l = mid + 1;
        }
      }
      return l;
    }

    Boolean feasible(int []weights, int c, int days){
        int daysNeeded = 1;
        int currentLoad = 0;
        for(int weight: weights){
            currentLoad = currentLoad + weight;
            if(currentLoad > c){
                daysNeeded++;
                currentLoad = weight;
            }
        }
        return daysNeeded <= days;
    }
}