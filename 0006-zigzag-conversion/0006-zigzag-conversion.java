class Solution {
    public String convert(String s, int numRows) {
        if(numRows == 1){
            return s;
        }

        StringBuilder answer = new StringBuilder();
        int n = s.length();
        int charsInSection = (numRows - 1) * 2;
        
        for(int currRow = 0;currRow < numRows;currRow++){
            int id = currRow;

            while(id < n){
                answer.append(s.charAt(id));
                //current row is not first or last row
                if (currRow != 0 && currRow != numRows - 1){
                    int charsInBetween = charsInSection - 2 * currRow;
                    int secondIndex = id + charsInBetween;

                    if(secondIndex < n) {
                        answer.append(s.charAt(secondIndex));
                    }

                }

                id += charsInSection;
            }
        }
        return answer.toString();
    }
}