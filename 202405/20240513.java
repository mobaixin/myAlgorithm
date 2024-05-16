import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// 20240513
class Solution {
    // 20240514
    // 2244. 完成所有任务需要的最少轮数
    public int minimumRounds(int[] tasks) {
        // 时间复杂度 O(n)
        // 空间复杂度 O(n)
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int t : tasks) {
            cnt.merge(t, 1, Integer::sum);
        }
        int ans = 0;
        for (int c : cnt.values()) {
            if (c == 1) {
                return -1;
            }
            ans += (c + 2) / 3;
        }
        return ans;
    }

    // 20240515
    // 2589. 完成所有任务的最少时间
    public int findMinimumTime(int[][] tasks) {
        // 时间复杂度 O(nlogn + nU), U = max(endi)
        // 空间复杂度 O(U)
        int n = tasks.length;
        Arrays.sort(tasks, (a, b) -> a[1] - b[1]);
        int ans = 0;
        int mx = tasks[n - 1][1];
        boolean[] run = new boolean[mx + 1];
        for (int[] t : tasks) {
            int start = t[0];
            int end = t[1];
            int d = t[2];
            for (int i = start; i <= end; i++) {
                if (run[i]) {
                    d--;    // 去掉运行中的时间点
                }
            }
            for (int i = end; d > 0; i--) { // 剩余的 d 填充区间后缀
                if (!run[i]) {
                    run[i] = true;  // 运行
                    d--;
                    ans++;
                }
            }
        }
        return ans;
    }

    // 20240516
    // 1953. 你可以工作的最大周数
    public long numberOfWeeks(int[] milestones) {
        // 时间复杂度 O(n)
        // 空间复杂度 O(1)
        long s = 0;
        int m = 0;
        for (int x : milestones) {
            s += x;
            m = Math.max(x, m);
        }
        return m > s - m + 1 ? (s - m) * 2 + 1 : s;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution);
    }
}
