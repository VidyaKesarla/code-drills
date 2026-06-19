class Solution {
    public String simplifyPath(String path) {
        //lets initialise a stack 
        Stack<String> stack = new Stack <String>();
        //split the string using / as delimiter. aim: we have a valid path given as input string, we have to shorten it, which means whatever we have between 2 / is either a directory name or a special character and we have to process them accordinly
        String[] components = path.split("/");

        //now lets process one component at a time

        for(String directory: components){

            if(directory.equals(".") || directory.isEmpty()){
                //do nothing
                continue;
            } else if (directory.equals("..")) {
                //we need to process it. this just means go one level up in current directory path, so we will pop an entry from stack if its not empty
                if(!stack.isEmpty()){
                    stack.pop();
                }
            } else {
                //if component we are processing right now is not one of the special characters, looks like its a legitimate directory lets add it to the stack
                stack.add(directory);
            }

        }


        //stitch together all directry names together
        StringBuilder result = new StringBuilder();
        for(String dir: stack){
            result.append("/");
            result.append(dir);
        }
        //if the result length is greater than 0 return this as a string or we will jus return /
        return result.length() > 0 ? result.toString() : "/" ;
    }
}