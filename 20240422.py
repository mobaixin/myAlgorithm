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
    

    # 20240423
    # 1052.爱生气的书店老板
    def maxSatisfied(self, customers: list[int], grumpy: list[int], minutes: int) -> int:
        # 时间复杂度 O(n)
        # 空间复杂度 O(1)
        n = len(customers)
        if n <= minutes:
            return sum(customers)
        ans = 0
        for i in range(n):
            if grumpy[i] == 0:
                ans += customers[i]
                customers[i] = 0
        
        addNum = sum(customers[0:minutes])
        maxAddNum = addNum
        for i in range(1, n - minutes + 1):
            addNum = addNum - customers[i - 1] + customers[i + minutes - 1]
            maxAddNum = max(maxAddNum, addNum)

        ans += maxAddNum
        return ans
    
    # 20240423
    # 1052.爱生气的书店老板
    def maxSatisfied_2(self, customers: list[int], grumpy: list[int], minutes: int) -> int:
        # 时间复杂度 O(n)
        # 空间复杂度 O(1)
        s = [0, 0]
        max_s1 = 0
        for i, (c, g) in enumerate(zip(customers, grumpy)):
            s[g] += c
            if i < minutes: # 窗口长度不足 minutes
                continue
            max_s1 = max(max_s1, s[1])
            if grumpy[i - minutes + 1]:
                s[1] -= customers[i - minutes + 1]  # 窗口最左边元素离开窗口
        return s[0] + max_s1
