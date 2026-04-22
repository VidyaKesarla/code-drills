class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        if (word.length() > m * n) return false;

        // Count frequencies to ensure word is even possible
        int[] counts = new int[128];
        for (char[] row : board) for (char c : row) counts[c]++;
        for (char c : word.toCharArray()) if (--counts[c] < 0) return false;

        // Optimization: Start from the rarer end of the word
        if (counts[word.charAt(0)] > counts[word.charAt(word.length() - 1)]) {
            word = new StringBuilder(word).reverse().toString();
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, i, j, word, 0)) return true;
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String word, int k) {
        if (k == word.length()) return true;
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(k)) return false;

        board[i][j] ^= 256; // Bitwise visit mark
        boolean found = dfs(board, i+1, j, word, k+1) || dfs(board, i-1, j, word, k+1) ||
                        dfs(board, i, j+1, word, k+1) || dfs(board, i, j-1, word, k+1);
        board[i][j] ^= 256;
        return found;
    }
}