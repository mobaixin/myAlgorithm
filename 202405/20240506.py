from math import inf


# 20240506
class Solution:
    # 20240506
    # 741. 摘樱桃
    def cherryPickup(self, grid: list[list[int]]) -> int:
        # 时间复杂度 O(n^3)
        # 空间复杂度 O(1)
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
        n = len(grid)
        return max(dfs(2 * n - 2, n - 1, n - 1), 0)
            
