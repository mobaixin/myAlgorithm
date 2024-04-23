from functools import cache


class Solution:
    # 20240422
    # 377.组合总和 IV
    def combinationSum4(self, nums: list[int], target: int) -> int:
        # 时间复杂度 O(target * n)
        # 空间复杂度 O(target)
        @cache
        def dfs(i):
            if i == 0:
                return 1
            return sum(dfs(i - x) for x in nums if x <= i)
        return dfs(target)