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

                    

            