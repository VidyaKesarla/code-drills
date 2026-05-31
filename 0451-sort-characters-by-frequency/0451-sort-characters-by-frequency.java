
// Note : -
// - Modify the function or parameters if needed.
// - Signatures function may vary, adjust parameters if required.

class Solution {
    public String frequencySort(String s) {
        //we need to know how many chars are present in the string
        //edge case:
        if(s == null || s.isEmpty())
        return s;

        //create a sorted array of characters because we cant directly sort a string. we need to convert this to a char array . how will we do it? // by using a in built function: toCharArray
        //another intuition is sort the chars by their numbers so that identical chars are side by side

        char [] chars = s.toCharArray();
        //tc: o(nlogn)
        Arrays.sort(chars);

        //convert identical characters into single group of strings
        List<String> charStrings = new ArrayList<String>();
        StringBuilder currentString = new StringBuilder();
        currentString.append(chars[0]);

        for(int i =1;i<chars.length;i++){
            //condition 1:
            //check if the incoming char is same as the previous one or not
            //if not:
            if(chars[i] != chars[i-1]){
                charStrings.add(currentString.toString());
                currentString = new StringBuilder();
            }
            //else i am just going to append this to currentString 
            currentString.append(chars[i]);
        }
        //convert string builder strings to to String
        charStrings.add(currentString.toString());

        //our comparator: is a,b -> b.length - a.length
        //if a is longer than b then a negative number will be returned 
        //telling the sort algorithm to place a first : otherwise a positive nuumber will be returned: telling it to place a second
        //this results in a longest to shorted sorted list of strings
        //sorting it according to the lenght of identical characters
        Collections.sort(charStrings, (a,b) -> b.length() - a.length());

        //use string builder to build the string to return 
        StringBuilder sb = new StringBuilder();
        for(String str: charStrings)
        sb.append(str);

        return sb.toString();

    }
}
//tc: O(nlogn)
//sc: O(n)