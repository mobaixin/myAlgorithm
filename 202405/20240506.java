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

    // 20240506
    // 741. 摘樱桃
    public int cherryPickup2(int[][] grid) {
        // 递推，两条路径
        // 时间复杂度 O(n^3)
        // 空间复杂度 O(n^3)
        int n = grid.length;
        int[][][] f = new int[n * 2 - 1][n + 1][n + 1];
        for (int[][] m : f) {
            for (int[] r : m) {
                Arrays.fill(r, Integer.MIN_VALUE);
            }
        }
        f[0][1][1] = grid[0][0];
        for (int t = 1; t < n * 2 - 1; t++) {
            for (int j = Math.max(t - n + 1, 0); j <= Math.min(t, n - 1); j++) {
                if (grid[t - j][j] < 0) {
                    continue;
                }
                for (int k = j; k <= Math.min(t, n - 1); k++) { // 只需要计算 k >= j 的状态
                    if (grid[t - k][k] < 0) {
                        continue;
                    }
                    int val = grid[t - j][j] + (j != k ? grid[t - k][k] : 0);
                    f[t][j + 1][k + 1] = Math.max(
                        Math.max(f[t - 1][j + 1][k + 1], f[t - 1][j + 1][k]),
                        Math.max(f[t - 1][j][k + 1], f[t - 1][j][k])
                    ) + val;
                }
            }
        }
        return Math.max(f[n * 2 - 2][n][n], 0);
    }

    // 20240506
    // 741. 摘樱桃
    public int cherryPickup3(int[][] grid) {
        // 递推，空间优化，在计算 f[t] 时，只会用到 f[t - 1]，倒序计算
        // 时间复杂度 O(n^3)
        // 空间复杂度 O(n^2)
        int n = grid.length;
        int[][] f = new int[n + 1][n + 1];
        for (int[] r : f) {
            Arrays.fill(r, Integer.MIN_VALUE);
        }
        f[1][1] = grid[0][0];
        for (int t = 1; t < n * 2 - 1; t++) {
            for (int j = Math.min(t, n - 1); j >= Math.max(t - n + 1, 0); j--) {
                for (int k = Math.min(t, n - 1); k >= j; k--) {
                    if (grid[t - j][j] < 0 || grid[t - k][k] < 0) {
                        f[j + 1][k + 1] = Integer.MIN_VALUE;
                    } else {
                        int val = grid[t - j][j] + (j != k ? grid[t - k][k] : 0);
                        f[j + 1][k + 1] = Math.max(
                            Math.max(f[j + 1][k + 1], f[j + 1][k]),
                            Math.max(f[j][k + 1], f[j][k])
                        ) + val;
                    }
                }
            }
        }
        return Math.max(f[n][n], 0);
    }

    // 20240507
    // 1463. 摘樱桃 II
    public int cherryPickupII(int[][] grid) {
        // 递归方式
        // 时间复杂度 O(mn^2)
        // 空间复杂度 O(mn^2)
        int m = grid.length;
        int n = grid[0].length;
        int[][][] memo = new int[m][n][n];
        for (int[][] me : memo) {
            for (int[] r : me) {
                Arrays.fill(r, -1); // -1 表示没有计算过
            }
        }
        return dfsII(0, 0, n - 1, grid, memo);
    }
    private int dfsII(int i, int j, int k, int[][] grid, int[][][] memo) {
        int m = grid.length;
        int n = grid[0].length;
        if (i == m || j < 0 || j >= n || k < 0 || k >= n) {
            return 0;
        }
        if (memo[i][j][k] != -1) {  // 之前计算过
            return memo[i][j][k];
        }
        int res = 0;
        int val = grid[i][j] + (j != k ? grid[i][k] : 0);
        for (int j2 = j - 1; j2 <= j + 1; j2++) {
            for (int k2 = k - 1; k2 <= k + 1; k2++) {
                res = Math.max(res, dfsII(i + 1, j2, k2, grid, memo));
            }
        }
        res += val;
        memo[i][j][k] = res;
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
    }
}
