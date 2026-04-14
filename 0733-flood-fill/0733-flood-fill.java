class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
         //get the original color of the starting pixel
        int startColor = image[sr][sc];

        //edge case: if the starting pixel is already the target color, return immediately to avoid an infinite recursion loop 
        if (startColor != color){
            dfs(image, sr, sc, startColor, color);
        }
        return image;
    }

    private void dfs(int[][] image, int r, int c, int startColor, int newColor){
        //check boundary conditions:
        if(r<0 || r>= image.length || c<0 || c>=image[0].length){
            return;
        }

        //check color condition:
        //does this pixel match the original color we want to change?
        if (image[r][c] != startColor){
            return;
        }

        //update the pixel color
        image[r][c] = newColor;

        //recurse in 4 directions
        dfs(image, r+1, c, startColor, newColor);
        dfs(image, r-1, c, startColor, newColor);
        dfs(image, r, c+1, startColor, newColor);
        dfs(image, r, c-1, startColor, newColor);
    }
}