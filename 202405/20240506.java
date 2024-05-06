import java.util.Arrays;

// 20240506
class Solution {
    // 20240506
    // 741. 摘樱桃
    public int cherryPickup(int[][] grid) {
        // 递归，两条路径
        // 时间复杂度 O(n^3)
        // 空间复杂度 O(n^3)
        int n = grid.length;
        int[][][] memo = new int[n * 2 - 1][n][n];
        for (int[][] m : memo) {
            for (int[] r : m) {
                Arrays.fill(r, -1); // -1 表示没有计算过
            }
        }
        return Math.max(dfs(n * 2 - 2, n - 1, n - 1, grid, memo), 0);
    }
    private int dfs(int t, int j, int k, int[][] grid, int[][][] memo) {
        // 不能出界
        if (j < 0 || k < 0 || t - j < 0 || t - k < 0 || grid[t - j][j] < 0 || grid[t - k][k] < 0) {
            return Integer.MIN_VALUE;
        }
        if (t == 0){    // 此时 j = k = 0
            return grid[0][0];
        }
        if (memo[t][j][k] != -1){   // 之前计算过
            return memo[t][j][k];
        }
        int val = grid[t - j][j] + (j != k ? grid[t - k][k] : 0);
        int res = Math.max(
            Math.max(dfs(t - 1, j, k, grid, memo), dfs(t - 1, j, k - 1, grid, memo)),
            Math.max(dfs(t - 1, j - 1, k, grid, memo), dfs(t - 1, j - 1, k - 1, grid, memo))
        ) + val;
        memo[t][j][k] = res;    // 记忆化
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
    }
}
