
// 20240429
class Solution {
    // 20240503
    // 1491. 去掉最低工资和最高工资后的工资平均值
    public double average(int[] salary) {
        // 时间复杂度 O(n)
        // 空间复杂度 O(1)
        int n = salary.length;
        int sum_salary = 0;
        int min_salary = Integer.MAX_VALUE;
        int max_salary = Integer.MIN_VALUE;
        for (int s : salary) {
            sum_salary += s;
            min_salary = Math.min(min_salary, s);
            max_salary = Math.max(max_salary, s);
        }
        return (double)(sum_salary - min_salary - max_salary) / (n - 2);
    }
}
