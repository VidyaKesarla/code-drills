class Solution {
    public int countNumbersWithUniqueDigits(int n) {
        if(n==0)
        return 1;

        //for n=1 = > there are 0-9 unique single digit numbers. 10 total => this holds the running grand total of all unique numbers found so far
        int totalUniqueCount = 10;

        //tracks the number of unique combinations for the current digit length: this stores only the number of unique combinations possible for exact length
        int currentLengthChoices = 9;

        //tracks how many unique digits are left to choose from - represents how many unused digits are left to pick from the next slot 
        int availableDigits = 9;

        for(int i =2;i<=n;i++){
            currentLengthChoices = currentLengthChoices * availableDigits;
            totalUniqueCount = totalUniqueCount + currentLengthChoices;
            availableDigits--;
        }

        return totalUniqueCount;
    }
}