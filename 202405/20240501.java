import java.util.Arrays;

// 20240501
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

    // 20240504
    // 1235. 规划兼职工作
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        // 时间复杂度 O(nlogn)
        // 空间复杂度 O(n)
        int n = startTime.length;
        int[][] jobs = new int[n][];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, (a, b) -> a[1] - b[1]);   // 按照结束时间排序

        int[] f = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int j = search(jobs, i, jobs[i][1]);
            f[i + 1] = Math.max(f[i], f[j + 1] + jobs[i][2]);
        }
        return f[n];
    }
    // 返回 endTime <= upper 的最大下标
    public int search(int[][] jobs, int right, int upper) {
        int left = -1;
        while (left + 1 < right) {
            int mid = (left + right) >> 1;
            if (jobs[mid][1] <= upper) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 20240505
    // 1652. 拆炸弹
    public int[] decrypt(int[] code, int k) {
        // 时间复杂度 O(n)
        // 空间复杂度 O(1)
        int n = code.length;
        int[] ans = new int[n];
        int r = k > 0 ? k + 1 : n;
        k = Math.abs(k);
        int sum_tmp = 0;
        for (int i = r - k; i < r; i++) {
            sum_tmp += code[i]; // ans[0]
        }
        for (int i = 0; i < n; i++) {
            ans[i] = sum_tmp;
            sum_tmp = sum_tmp - code[(r - k) % n] + code[r % n];
            r++;
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution);
    }
}
