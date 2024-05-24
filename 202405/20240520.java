import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 20240520
class Solution {
    // 20240522
    // 2225. 找出输掉零场或一场比赛的玩家
    public List<List<Integer>> findWinners(int[][] matches) {
        // 时间复杂度 O(nlogn)
        // 空间复杂度 O(n)
        int n = matches.length;
        Map<Integer, Integer> lossCount = new HashMap<>();
        for (int[] m : matches) {
            if (!lossCount.containsKey(m[0])) {
                lossCount.put(m[0], 0);
            }
            lossCount.merge(m[1], 1, Integer::sum);
        }

        List<List<Integer>> ans = Arrays.asList(new ArrayList<>(), new ArrayList<>());
        for (Map.Entry<Integer, Integer> e : lossCount.entrySet()) {
            int cnt = e.getValue();
            if (cnt < 2) {
                ans.get(cnt).add(e.getKey());
            }
        }

        Collections.sort(ans.get(0));
        Collections.sort(ans.get(1));
        return ans;
    }

    // 20240524
    // 1673. 找出最具竞争力的子序列s
    public int[] mostCompetitive(int[] nums, int k) {
        // 时间复杂度 O(n)
        // 空间复杂度 O(n)
        int n = nums.length;
        int[] st = new int[k];
        int m = 0;  // 栈的大小
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            while (m > 0 && x < st[m - 1] && m + n - i > k) {
                m--;
            }
            if (m < k) {
                st[m++] = x;
            }
        }
        return st;
    }
    
}