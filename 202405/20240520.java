

// 20240520
class Solution {
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