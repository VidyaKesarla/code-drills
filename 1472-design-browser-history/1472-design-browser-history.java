class BrowserHistory {
    ArrayList<String> visitedURLs;
    int currURL, lastURL;

    public BrowserHistory(String homepage) {
        visitedURLs = new ArrayList<String>(Arrays.asList(homepage));
        currURL = 0;
        lastURL = 0;
    }
    
    public void visit(String url) {
        currURL += 1;
        if(visitedURLs.size() > currURL){
            visitedURLs.set(currURL, url);
        } else {
            visitedURLs.add(url);
        }
        lastURL = currURL;
    }
    
    public String back(int steps) {
        currURL = Math.max(0, currURL - steps);
        return visitedURLs.get(currURL);
    }
    
    public String forward(int steps) {
        currURL = Math.min(lastURL, currURL + steps);
        return visitedURLs.get(currURL);
    }
}

/**
 * Your BrowserHistory object will be instantiated and called as such:
 * BrowserHistory obj = new BrowserHistory(homepage);
 * obj.visit(url);
 * String param_2 = obj.back(steps);
 * String param_3 = obj.forward(steps);
 */


//  class BrowserHistory {
//     private Stack<String> history, future;
//     private String current;

//     public BrowserHistory(String homepage){
//         history = new Stack<String>();
//         future = new Stack<String>();
//         current = homepage;
//     }

//     public void visit(String url){
//         history.push(current);
//         current = url;

//         future = new Stack<String>();
//     }

//     public String back(int steps){
//         while(steps > 0 && !history.isEmpty()){
//             future.push(current);
//             current = history.pop();
//             steps--;
//         }
//         return current;
//     }

//     public String forward(int steps){
//         while(steps > 0 && !future.isEmpty()){
//             history.push(current);
//             current = future.pop();
//             steps--;
//         }
//         return current;
        
//     }
//  }