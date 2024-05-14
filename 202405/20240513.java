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

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution);
    }
}
