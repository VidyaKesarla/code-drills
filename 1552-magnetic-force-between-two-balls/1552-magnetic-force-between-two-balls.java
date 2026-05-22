class Solution {

    private boolean canPlaceBalls(int x, int[] position, int m){
        int prevBallPos = position[0];
        int ballsPlaced = 1;

        for(int i =1;i<position.length && ballsPlaced < m; i++){
            int currPos = position[i];

            if(currPos - prevBallPos >= x){
                ballsPlaced = ballsPlaced + 1;
                prevBallPos = currPos;
            }
        }
        return ballsPlaced == m;
    }

    public int maxDistance(int[] position, int m) {
        //basically we have to return the required force or distance
        int answer = 0;
        int n = position.length;

        Arrays.sort(position);

        //initialise the search space
        int low = 1;
        int high = (int) Math.ceil(position[n-1]/(m-1.0));
        while(low<=high){
            int mid = low + (high - low)/2;
            if(canPlaceBalls(mid, position, m)){
                answer = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
         return answer;
    }
   
}