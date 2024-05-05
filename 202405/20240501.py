from bisect import bisect_left
from math import inf


# 20240501
class Solution:
    # 20240503
    # 1491. 去掉最低工资和最高工资后的工资平均值
    def average(self, salary: list[int]) -> float:
        # 时间复杂度 O(n)
        # 空间复杂度 O(1)
        n = len(salary)
        sum_salary = 0
        min_salary = inf
        max_salary = -1
        for s in salary:
            sum_salary += s
            min_salary = min(min_salary, s)
            max_salary = max(max_salary, s)
        return (sum_salary - min_salary - max_salary) / (n - 2)
    
    # 20240504
    # 1235. 规划兼职工作
    def jobScheduling(self, startTime: list[int], endTime: list[int], profit: list[int]) -> int:
        # 时间复杂度 O(nlogn), n 为 startTime 的长度
        # 空间复杂度 O(n)
        n = len(startTime)
        jobs = sorted(zip(endTime, startTime, profit))  # 按照结束时间排序
        f = [0] * (n + 1)
        for i, (_, st, p) in enumerate(jobs):
            j = bisect_left(jobs, (st + 1,), hi=i)  # hi=i 表示二分上界为 i (默认为 n)
            # 状态转移中, 为什么是 j 不是 j+1: 上面算的是 > st, -1 后得到 <= st, 但由于还要 +1, 抵消了
            f[i + 1] = max(f[i], f[j] + p)
        return f[-1]
    
    # 20240505
    # 1652. 拆炸弹
    def decrypt(self, code: list[int], k: int) -> list[int]:
        # 时间复杂度 O(n)
        # 空间复杂度 O(1)
        n = len(code)
        ans = [0] * n
        if k == 0:
            return ans
        elif k < 0:
            sum_tmp = sum(code[k:])
            ans[0] = sum_tmp
            for i in range(1, n):
                sum_tmp = sum_tmp - code[i - k - 1] + code[i - 1]
                ans[i] = sum_tmp
        else:
            sum_tmp = sum(code[1:k+1])
            ans[0] = sum_tmp
            for i in range(1, n):
                sum_tmp = sum_tmp - code[i] + code[(i + k) % n]
                ans[i] = sum_tmp
        return ans

if __name__ == '__main__':
    solution = Solution()