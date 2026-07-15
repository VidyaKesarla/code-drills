class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        //lets create a map to store the results of the subproblems
        HashMap<Integer, List<String>> dp = new HashMap<>();

        //iterate from the end of the string to the beginning
        for(int startIdx = s.length(); startIdx >= 0; startIdx--){
            //list to store valid sentences starting from startIdx
            List<String> validSentences = new ArrayList<>();

            for(int endIdx = startIdx; endIdx < s.length(); endIdx++){
                String currentWord = s.substring(startIdx, endIdx + 1);

                if(wordDict.contains(currentWord)){
                    //if its the last word, add it as a valid sentence
                    if(endIdx == s.length() - 1){
                        validSentences.add(currentWord);
                    } else {
                        //if its not the last word - append it to each sentence formed by remaining substring
                        List<String> sentencesFromNextIndex = dp.get(endIdx + 1);
                        for (String sentence : sentencesFromNextIndex) {
                            validSentences.add(currentWord + " " + sentence);
                        }
                    }
                }
            }
            dp.put(startIdx, validSentences);
        }
        return dp.getOrDefault(0, new ArrayList<>());
    }
}