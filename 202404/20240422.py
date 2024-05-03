from bisect import bisect_left
from collections import defaultdict, deque
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
    
    # 20240424
    # 2385. 感染二叉树需要的总时间
    def amountOfTime(self, root, start: int) -> int:
        # 时间复杂度 O(n)
        # 空间复杂度 O(n)
        # 记录边 + BFS
        time = 0
        edgeList = [[] for _ in range(100001)]
        q = deque()
        q.append(root)
        while q:
            node = q.popleft()
            if node.left:
                edgeList[node.val].append(node.left.val)
                edgeList[node.left.val].append(node.val)
                q.append(node.left)
            if node.right:
                edgeList[node.val].append(node.right.val)
                edgeList[node.right.val].append(node.val)
                q.append(node.right)
        
        visited = [False] * 100001
        q.append(start)
        size = 1
        while q:
            nodeVal = q.popleft()
            visited[nodeVal] = True
            if size == 0:
                size = len(q)
                time += 1
            else:
                size -= 1
            for sonVal in edgeList[nodeVal]:
                if not visited[sonVal]:
                    q.append(sonVal)
        return time
    
    # 20240424
    # 2385. 感染二叉树需要的总时间
    def amountOfTime_2(self, root, start: int) -> int:
        # 时间复杂度 O(n)
        # 空间复杂度 O(n)
        # 记录父节点 + DFS
        fa = {}
        start_node = None
        
        def dfs(node, from_):
            if node is None:
                return
            fa[node] = from_    # 记录每个节点的父节点
            if node.val == start:   # 找到 start
                nonlocal start_node
                start_node = node
            dfs(node.left, node)
            dfs(node.right, node)
        dfs(root, None)

        def maxDepth(node, from_):
            if node is None:
                return -1   # start 的深度是 0
            return max(maxDepth(x, node) for x in (node.left, node.right, fa[node]) if x != from_) + 1
        return maxDepth(start_node, start_node)


    # 20240425
    # 2739. 总行驶距离
    def distanceTraveled(self, mainTank: int, additionalTank: int) -> int:
        # 时间复杂度 O(mainTank)
        # 空间复杂度 O(1)
        ans = 0
        while mainTank >= 5:
            mainTank -= 5
            ans += 50
            if additionalTank:
                additionalTank -= 1
                mainTank += 1
        return ans + mainTank * 10
    
    # 20240425
    # 2739. 总行驶距离
    def distanceTraveled_2(self, mainTank: int, additionalTank: int) -> int:
        # 时间复杂度 O(1)
        # 空间复杂度 O(1)
        addTank = min((mainTank - 1) // 4, additionalTank)
        return (mainTank +addTank) * 10
    
    # 20240426
    # 1146. 快照数组
    class SnapshotArray:
        def __init__(self, length: int):
            self.cur_snap_id = 0
            self.history = defaultdict(list)    # 每个 index 的历史修改记录

        def set(self, index: int, val: int) -> None:
            self.history[index].append((self.cur_snap_id, val))

        def snap(self) -> int:
            self.cur_snap_id += 1
            return self.cur_snap_id - 1

        def get(self, index: int, snap_id: int) -> int:
            # 找快照编号 <= snap_id 的最后一次修改记录
            # 等价于找快照编号 >= snap_id+1 的第一个修改记录，它的上一个就是答案
            j = bisect_left(self.history[index], (snap_id + 1, )) - 1
            return self.history[index][j][1] if j >= 0 else 0
    
    # 20240427
    # 2639. 查询网格图中每一列的宽度
    def findColumnWidth(self, grid: list[list[int]]) -> list[int]:
        # 时间复杂度 O(mnlogU), U 为 grid[i][j] 的绝对值的最大值
        # 空间复杂度 O(1)
        m = len(grid)
        n = len(grid[0])
        ans = [0] * n
        for i in range(m):
            for j in range(n):
                ans[j] = max(ans[j], len(str(grid[i][j])))
        return ans
    
    def findColumnWidth2(self, grid: list[list[int]]) -> list[int]:
        # 时间复杂度 O(mnlogU), U 为 grid[i][j] 的绝对值的最大值
        # 空间复杂度 O(1)
        return [max(len(str(x)) for x in col) for col in zip(*grid)]
    
    def findColumnWidth3(self, grid: list[list[int]]) -> list[int]:
        # 时间复杂度 O(mnlogU), U 为 grid[i][j] 的绝对值的最大值
        # 空间复杂度 O(1)
        ans = [0] * len(grid[0])
        for j, col in enumerate(zip(*grid)):
            for x in col:
                x_len = int(x <= 0)
                x = abs(x)
                while x:
                    x_len += 1
                    x //= 10
                ans[j] = max(ans[j], x_len)
        return ans
    
    def findColumnWidth4(self, grid: list[list[int]]) -> list[int]:
        # 只需要对每一列的最小值或最大值求长度
        # 时间复杂度 O(n(m + logU)), U 为 grid[i][j] 的绝对值的最大值
        # 空间复杂度 O(1)
        return [len(str(max(max(col), -10 * min(col)))) for col in zip(*grid)]
    
    # 20240428
    # 1017. 负二进制转换
    def baseNeg2(self, n: int) -> str:
        if n == 0 or n == 1:
            return str(n)
        res = []
        while n:
            remainder = n & 1
            res.append(str(remainder))
            n -= remainder
            n //= -2
        return ''.join(res[::-1])

if __name__ == "__main__":
    solution = Solution()
    print(solution.findColumnWidth4([[-15,1,3],[15,7,12],[5,6,-2]]))
