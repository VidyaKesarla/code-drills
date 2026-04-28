// ### Approach & Complexity
// 1. Iterate through the tokens.
// 2. If it's a number: Push it onto the stack.
// 3. If it's an operator: 
//    - Pop the top element as 'b' (second operand).
//    - Pop the next element as 'a' (first operand).
//    - Apply the operation (a op b) and push the result back.
// 4. Result: The last remaining element in the stack is the answer.

// * Time Complexity: O(n) — Single pass through the tokens.
// * Space Complexity: O(n) — In the worst case, the stack stores all operands.

// ### Dry Run (Example: ["4", "13", "5", "/", "+"])
// | Token | Action     | Stack (Top at right) | Math          |
// | :---  | :---       | :---                 | :---          |
// | "4"   | Push       | [4]                  | -             |
// | "13"  | Push       | [4, 13]              | -             |
// | "5"   | Push       | [4, 13, 5]           | -             |
// | "/"   | Pop 5, 13  | [4, 2]               | 13 / 5 = 2    |
// | "+"   | Pop 2, 4   | [6]                  | 4 + 2 = 6     |
// Final Result: 6

// ### Trade-offs: Stack<Integer> vs. int[] Array
// - Stack<Integer>: Standard, readable, but involves "autoboxing" overhead 
//   (converting int to Integer objects).
// - int[] as Stack: Significantly faster and more memory-efficient. By using 
//   a pointer (top), we avoid object allocation and operate directly on primitives.

// ### Java Implementation (Optimal Array-Stack)

//brute force approach 
/*
one might consider whenever we scan the array, and we look for an operator, performing calculation with two preceding numbers, replace those three elements with result and repeating . trade off: this is highly inefficient: everytime we try to collapse a calculation, you have to shift the array or rescan: leading to O(n^2 time complexity)
*/
class Solution {
    public int evalRPN(String[] tokens) {
        Stack <Integer> stack = new Stack<>();
        for(String s: tokens){
            if (isOperator(s)){
                int b = stack.pop();
                int a = stack.pop();
                switch(s){
                    case "+": stack.push(a+b);
                    break;
                    case "-": stack.push(a-b);
                    break;
                    case "*": stack.push(a*b);
                    break;
                    case "/": stack.push(a/b);
                    break;
                }
            } else {
                stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }

    private boolean isOperator(String s){
        return s.equals("*") || s.equals("+") || s.equals("-") || s.equals("/");
    }
}

