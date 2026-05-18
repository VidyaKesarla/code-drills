class Solution {
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        
        // Sort strings using a custom comparator
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
        
        // Build the largest number from sorted strings
        if (strs[0].equals("0")) return "0"; // handle case with all zeros
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return sb.toString();
    }
}

// // Note : -
// // - Modify the function or parameters if needed.
// // - Signatures function may vary, adjust parameters if required.

// class Solution {
//     public String largestNumber(int[] nums) {
// //         //we need to convert the integers into a list of strings as we need to compare the concatenated strings
// //         String[] strs =new String[nums.length];
// //         for(int i=0;i<nums.length;i++){
// //             strs[i] = String.valueOf(nums[i]);
// //         }

// //         //define a custom comparator that compares two strings x, y
// //         //         Define a custom comparator that compares two strings, x and y.
// // // Compare the concatenated results: x+y and y+x.
// // // If x+y is larger, then x should come before y.
// // // Otherwise, y should come before x.
// //         Arrays.sort(strs, (a,b) -> (b+a).compareTo(a+b));

// //         //         After sorting, join the array to form the final string.
// // // If the result starts with '0' (which means all numbers were zero), return "0"
// //         if (strs[0].equals("0")) return "0"; // handle case with all zeros
// //         StringBuilder sb = new StringBuilder();
// //         for (String s : strs) {
// //             sb.append(s);
// //         }
// //         return sb.toString();
// // Convert numbers to strings
        

//     }
// }