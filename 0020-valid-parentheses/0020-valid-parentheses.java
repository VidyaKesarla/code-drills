class Solution {
    public boolean isValid(String s) {
        //best data structure to use here is stack and hashmap - why?
        //because it perfectly models the Last-In, First-Out (LIFO) requirement of nested structures
        //steps:
        // Create an empty stack to keep track of open brackets
        //create a hashmap which will pair each opening bracket to its closing bracket
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        //now we will initialise stack to keep track of open brackets
        Stack<Character> stack = new Stack<>();

        //iterate through the string character by character 
        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            if (map.containsKey(ch)){
                //use some junk character if stack is empty
                char topElement = stack.isEmpty() ? '#' : stack.pop();
                if (topElement != map.get(ch)) {
                    return false;
                }
            }
            else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();    
    }
}