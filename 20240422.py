from functools import cache

# 20240422
class Solution:
    # 20240422
    # 377.组合总和 IV
    def combinationSum4(self, nums: list[int], target: int) -> int:
        # # 递归
        # # 时间复杂度 O(target * n)
        # # 空间复杂度 O(target)
        # @cache
        # def dfs(i):
        #     if i == 0:
        #         return 1
        #     return sum(dfs(i - x) for x in nums if x <= i)
        # return dfs(target)
    
        # 一维递推
        f = [1] + [0] * target
        for i in range(1, target + 1):
            f[i] = sum(f[i - x] for x in nums if x <= i)
        return f[target]
