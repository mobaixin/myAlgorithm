from functools import cache
from math import inf


# 20240506
class Solution:
    # 20240506
    # 741. 摘樱桃
    def cherryPickup(self, grid: list[list[int]]) -> int:
        # 递归，两条路径
        # 时间复杂度 O(n^3)
        # 空间复杂度 O(1)
        n = len(grid)

        # t 为剩余的步数, j 为第一条路在列方向走的步数, k 为第二条路在列方向走的步数
        def dfs(t: int, j: int, k: int) -> int:
            # 不能出界，不能访问 -1 格子
            if j < 0 or k < 0 or t - j < 0 or t - k < 0 or \
               grid[t - j][j] < 0 or grid[t - k][k] < 0:
                return -inf
            if t == 0:  # 此时 j = k = 0
                return grid[0][0]
            val = 0
            if j != k:
                val = grid[t - j][j] + grid[t - k][k]
            else:
                val = grid[t - j][j]
            return max(dfs(t - 1, j, k), dfs(t - 1, j, k - 1), dfs(t - 1, j - 1, k), \
                       dfs(t - 1, j - 1, k - 1)) + val
        
        return max(dfs(2 * n - 2, n - 1, n - 1), 0)
    
    # 20240506
    # 741. 摘樱桃
    def cherryPickup2(self, grid: list[list[int]]) -> int:
        # 递推，两条路径
        # # 时间复杂度 O(n^3)
        # # 空间复杂度 O(n^3)
        # n = len(grid)
        # f = [[[-inf] * (n + 1) for _ in range(n + 1)] for _ in range(n * 2 - 1)]
        # f[0][1][1] = grid[0][0]
        # for t in range(1, n * 2 - 1):
        #     for j in range(max(t - n + 1, 0), min(t + 1, n)):
        #         if grid[t - j][j] < 0:
        #             continue
        #         for k in range(j, min(t + 1, n)):   # 只需要计算 k >= j 的状态
        #             if grid[t - k][k] < 0:
        #                 continue
        #             val = grid[t - j][j] + grid[t - k][k] if j != k else 0
        #             f[t][j + 1][k + 1] = max(f[t - 1][j + 1][k + 1], f[t - 1][j + 1][k], \
        #                                      f[t - 1][j][k + 1], f[t - 1][j][k]) + val
        # return max(f[2 * n - 2][n][n], 0)

        # 递推，空间优化，计算 f[t] 时，只会用到 f[t−1], 倒序计算
        # 时间复杂度 O(n^3)
        # 空间复杂度 O(n^2)
        n = len(grid)
        f = [[-inf] * (n + 1) for _ in range(n + 1)]
        f[1][1] = grid[0][0]
        for t in range(1, n * 2 - 1):
            for j in range(min(t, n - 1), max(t - n, -1), -1):
                for k in range(min(t, n - 1), j - 1, -1):
                    if grid[t - j][j] < 0 or grid[t - k][k] < 0:
                        f[j + 1][k + 1] = -inf
                    else:
                        val = grid[t - j][j] + grid[t - k][k] if j != k else 0
                        f[j + 1][k + 1] = max(f[j + 1][k + 1], f[j + 1][k], f[j][k + 1], f[j][k]) + val
        return max(f[n][n], 0)
    
    # 20240507
    # 1463. 摘樱桃 II
    def cherryPickupII(self, grid: list[list[int]]) -> int:
        # 递归方式
        # 时间复杂度 O(mn^2)
        # 空间复杂度 O(mn^2)
        m, n = len(grid), len(grid[0])

        # i: 当前所在的行，j: 第一条路当前所在的列，k: 第二条路当前所在的列
        @cache
        def dfs(i: int, j: int, k: int) -> int:
            if i == m or j < 0 or j >= n or k < 0 or k >= n:
                return 0
            val = grid[i][j] + (grid[i][k] if j != k else 0)
            return max(dfs(i + 1, j2, k2) for j2 in (j - 1, j, j + 1) for k2 in (k - 1, k, k + 1)) + val
        return dfs(0, 0, n - 1)
    
    # 20240507
    # 1463. 摘樱桃 II
    def cherryPickupII2(self, grid: list[list[int]]) -> int:
        # 三维递推方式
        # 时间复杂度 O(mn^2)
        # 空间复杂度 O(mn^2)
        m, n = len(grid), len(grid[0])
        f = [[[0] * (n + 2) for _ in range(n + 2)] for _ in range(m + 1)]
        for i in range(m - 1, -1, -1):
            for j in range(min(n, i + 1)):
                for k in range(max(j + 1, n - 1 - i), n):   # 只需要计算 k > j 的状态
                    val = grid[i][j] + grid[i][k]
                    f[i][j + 1][k + 1] = max(
                        f[i + 1][j][k], f[i + 1][j][k + 1], f[i + 1][j][k + 2],
                        f[i + 1][j + 1][k], f[i + 1][j + 1][k + 1], f[i + 1][j + 1][k + 2],
                        f[i + 1][j + 2][k], f[i + 1][j + 2][k + 1], f[i + 1][j + 2][k + 2]
                    ) + val
        return f[0][1][n]
    
    # 20240507
    # 1463. 摘樱桃 II
    def cherryPickupII3(self, grid: list[list[int]]) -> int:
        # 三维递推方式，空间优化，在计算 f[i] 时，只会用到 f[i+1]
        # 时间复杂度 O(mn^2)
        # 空间复杂度 O(n^2)
        m, n = len(grid), len(grid[0])
        pre = [[0] * (n + 2) for _ in range(n + 2)]
        cur = [[0] * (n + 2) for _ in range(n + 2)]
        for i in range(m - 1, -1, -1):
            for j in range(min(n, i + 1)):
                for k in range(max(j + 1, n - 1 - i), n):
                    val = grid[i][j] + grid[i][k]
                    cur[j + 1][k + 1] = max(
                        pre[j][k], pre[j][k + 1], pre[j][k + 2],
                        pre[j + 1][k], pre[j + 1][k + 1], pre[j + 1][k + 2],
                        pre[j + 2][k], pre[j + 2][k + 1], pre[j + 2][k + 2]
                    ) + val
            pre, cur = cur, pre # 下一个 i 的 pre 是 cur
        return pre[1][n]



if __name__ == '__main__':
    solution = Solution()
    grid = [[3,1,1],[2,5,1],[1,5,5],[2,1,1]]
    print(solution.cherryPickupII2(grid))
                    

            
