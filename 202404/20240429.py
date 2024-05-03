from math import inf, min, max


# 20240429
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
