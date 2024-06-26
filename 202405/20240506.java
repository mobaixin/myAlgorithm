import java.util.Arrays;

import javax.swing.text.AbstractDocument.LeafElement;

import org.ietf.jgss.GSSException;

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

    // 20240507
    // 1463. 摘樱桃 II
    public int cherryPickupII2(int[][] grid) {
        // 三维递推方式
        // 时间复杂度 O(mn^2)
        // 空间复杂度 O(mn^2)
        int m = grid.length;
        int n = grid[0].length;
        int[][][] f = new int[m + 1][n + 2][n + 2];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < Math.min(n, i + 1); j++) {
                for (int k = Math.max(j + 1, n - 1 - i); k < n; k++) {  // 只需要计算 k > j 的状态
                    int val = grid[i][j] + grid[i][k];
                    f[i][j + 1][k + 1] = max(
                        f[i + 1][j][k], f[i + 1][j][k + 1], f[i + 1][j][k + 2],
                        f[i + 1][j + 1][k], f[i + 1][j + 1][k + 1], f[i + 1][j + 1][k + 2],
                        f[i + 1][j + 2][k], f[i + 1][j + 2][k + 1], f[i + 1][j + 2][k + 2]
                    ) + val;
                }
            }
        }
        return f[0][1][n];
    }

    // 20240507
    // 1463. 摘樱桃 II
    public int cherryPickupII3(int[][] grid) {
        // 二维递推方式，空间优化，计算 f[i] 时，只会用到 f[i + 1]
        // 时间复杂度 O(mn^2)
        // 空间复杂度 O(n^2)
        int m = grid.length, n = grid[0].length;
        int[][] pre = new int[n + 2][n + 2];
        int[][] cur = new int[n + 2][n + 2];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < Math.min(n, i + 1); j++) {
                for (int k = Math.max(j + 1, n - 1 - i); k < n; k++) {  // 只需要计算 k > j 的状态
                    int val = grid[i][j] + grid[i][k];
                    cur[j + 1][k + 1] = max(
                        pre[j][k], pre[j][k + 1], pre[j][k + 2],
                        pre[j + 1][k], pre[j + 1][k + 1], pre[j + 1][k + 2],
                        pre[j + 2][k], pre[j + 2][k + 1], pre[j + 2][k + 2]
                    ) + val;
                }
            }
            int[][] tmp = pre;
            pre = cur;  // 下一个 i 的 pre 是 cur
            cur = tmp;
        }
        return pre[1][n];
    }
    private int max(int x, int... y) {
        for (int v : y) {
            x = Math.max(x, v);
        }
        return x;
    }

    // 20240508
    // 2079. 给植物浇水
    public int wateringPlants(int[] plants, int capacity) {
        // 时间复杂度 O(n)
        // 空间复杂度 O(1)
        int n = plants.length;
        int totalSteps = 0;
        int curCapacity = capacity;
        for (int i = 0; i < n; i++) {
            if (curCapacity < plants[i]) {
                totalSteps += 2 * i;
                curCapacity = capacity;
            }
            totalSteps += 1;
            curCapacity -= plants[i];
        }
        return totalSteps;
    }

    // 20240509
    // 2105. 给植物浇水 II
    public int minimumRefill(int[] plants, int capacityA, int capacityB) {
        // 时间复杂度 O(n)
        // 空间复杂度 O(1)
        int ans = 0;
        int a = capacityA;
        int b = capacityB;
        int i = 0;
        int j = plants.length - 1;
        while (i < j) {
            // Alice 给植物 i 浇水
            if (a < plants[i]) {
                // 没有足够的水，重新灌满水罐
                ans++;
                a = capacityA;
            }
            a -= plants[i++];
            // Bob 给植物 j 浇水
            if (b < plants[j]) {
                // 没有足够的水，重新灌满水罐
                ans++;
                b = capacityB;
            }
            b -= plants[j--];
        }
        // Alice 和 Bob 到达同一株植物，那么当前水罐中水更多的人会给这株植物浇水
        if (i == j && Math.max(a, b) < plants[i]) {
            // 没有足够的水，重新灌满水罐
            ans++;
        }
        return ans;
    }

    // 20240510
    // 2960. 统计已测试设备
    public int countTestedDevices(int[] batteryPercentages) {
        // 时间复杂度 O(n)
        // 空间复杂度 O(1)
        int dec = 0;
        for (int x : batteryPercentages) {
            if (x > dec) {
                dec++;
            }
        }
        return dec;
    }

    // 20240511
    // 2391. 收集垃圾的最少总时间
    public int garbageCollection(String[] garbage, int[] travel) {
        // 时间复杂度 O(n + L)
        // 空间复杂度 O(1)
        int ans = 0;
        for (String g : garbage) {
            ans += g.length();
        }
        for (int t : travel) {
            ans += t * 3;
        }
        for (char c : new char[]{'M', 'P', 'G'}) {
            for (int i = garbage.length - 1; i > 0 && garbage[i].indexOf(c) < 0; i--) {
                ans -= travel[i - 1];   // 没有垃圾 c，多跑了
            }
        }
        return ans;
    }
 
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] grid = {{3,1,1},{2,5,1},{1,5,5},{2,1,1}};
        System.out.println(solution.cherryPickupII3(grid));
    }
}
