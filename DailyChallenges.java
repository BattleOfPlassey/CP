import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Queue;

class DailyChallenges {

    // fab 9
    // https://leetcode.com/problems/as-far-from-land-as-possible/
    class MaxDistanceClass {
        int row;
        int col;
        int step;

        MaxDistanceClass(int row, int col, int step) {
            this.row = row;
            this.col = col;
            this.step = step;
        }
    }

    public int maxDistance(int[][] grid) {
        int ans = Integer.MIN_VALUE;

        int n = grid.length;
        int m = grid[0].length;

        int[][] vis = new int[n][m];
        int[][] distance = new int[n][m];

        Queue<MaxDistanceClass> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    q.add(new MaxDistanceClass(i, j, 0));
                    vis[i][j] = 1;
                } else {
                    vis[i][j] = 0;
                }
            }
        }

        while (!q.isEmpty()) {
            int r = q.peek().row;
            int c = q.peek().col;
            int s = q.peek().step;
            distance[r][c] = s;
            q.remove();
            int[] delrow = { -1, 0, 1, 0 };
            int[] delcol = { 0, 1, 0, -1 };

            for (int i = 0; i < 4; i++) {
                int newrow = delrow[i] + r;
                int newcol = delcol[i] + c;

                if (newrow >= 0 && newcol >= 0 && newrow < n &&
                        newcol < m && vis[newrow][newcol] == 0) {
                    q.add(new MaxDistanceClass(newrow, newcol, s + 1));
                    vis[newrow][newcol] = 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, distance[i][j]);
            }
        }
        return ans;
    }

    // Fab 10
    // https://leetcode.com/problems/shortest-path-with-alternating-colors/
    // 1129. Shortest Path with Alternating Colors
    static class SATNode {
        int node;
        int steps;
        int preColor;

        SATNode(int a, int b, int c) {
            this.node = a;
            this.steps = b;
            this.preColor = c;
        }
    }

    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        // create adj list of the following matrix
        Map<Integer, List<List<Integer>>> adj = new HashMap<>();

        for (int[] re : redEdges) {
            adj.computeIfAbsent(re[0], k -> new ArrayList<List<Integer>>()).add(Arrays.asList(re[1], 0));
        }

        for (int[] be : blueEdges) {
            adj.computeIfAbsent(be[0], k -> new ArrayList<List<Integer>>()).add(Arrays.asList(be[1], 1));
        }

        int[] answer = new int[n];
        Arrays.fill(answer, -1);
        boolean[][] visited = new boolean[n][2];

        Queue<int[]> q = new LinkedList<>();

        visited[0][0] = visited[0][1] = true;
        q.offer(new int[] { 0, 0, -1 });
        answer[0] = 0;
        while (!q.isEmpty()) {
            int[] element = q.poll();

            int node = element[0];
            int steps = element[1];
            int prevColor = element[2];

            if (!adj.containsKey(node)) {
                continue;
            }

            for (List<Integer> nei : adj.get(node)) {
                int neighbor = nei.get(0);
                int color = nei.get(1);

                if (!visited[neighbor][color] && color != prevColor) {
                    if (answer[neighbor] == -1) {
                        answer[neighbor] = 1 + steps;
                    }
                    visited[neighbor][color] = true;
                    q.offer(new int[] { neighbor, 1 + steps, color });
                }
            }

        }

        return answer;
    }

    // Feb 11
    // https://leetcode.com/problems/minimum-fuel-cost-to-report-to-the-capital/description/
    // 2477. Minimum Fuel Cost to Report to the Capital
    long minFuel = 0;

    public long minimumFuelCost(int[][] roads, int seats) {
        // convert this roads to adj list
        int n = roads.length + 1;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            adj.add(new ArrayList<>());
        }
        int[] indegree = new int[n];

        for (int[] cur : roads) {
            adj.get(cur[0]).add(cur[1]);
            adj.get(cur[1]).add(cur[0]);
            indegree[cur[0]]++;
            indegree[cur[1]]++;
        }

        return minimumFuelCostBFS(adj, seats, indegree, n);

    }

    private long minimumFuelCostBFS(ArrayList<ArrayList<Integer>> adj, int seats, int[] indegree, int n) {

        Queue<Integer> q = new LinkedList<>();

        for (int i = 1; i < n; i++) {
            if (indegree[i] == 1) {
                q.offer(i);
            }
        }

        int[] representative = new int[n];
        Arrays.fill(representative, 1);
        long minFeul = 0;

        while (!q.isEmpty()) {
            int node = q.peek();
            q.poll();

            minFeul += Math.ceil((double) representative[node] / seats);

            for (int neighbor : adj.get(node)) {
                indegree[neighbor]--;
                representative[neighbor] += representative[node];
                if (indegree[neighbor] == 1 && neighbor != 0) {
                    q.offer(neighbor);
                }
            }
        }
        return minFeul;

    }

    // Feb 14
    // https://leetcode.com/problems/add-binary/
    // 67. Add Binary
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();

        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0; // to count the carry bits

        while (i >= 0 || j >= 0) {
            // make a temp vairbale sum which will store the sum of 2 value at each
            // iteration
            int sum = carry;

            // pick the last of string a and convert it into int
            if (i >= 0) {
                sum = sum + a.charAt(i) - '0';
            }

            if (j >= 0) {
                sum = sum + b.charAt(j) - '0';
            }

            sb.append(sum % 2); // store if the num of ith and jth one is 2 to store 0 at that index and more
            // the next 1 to the carry.
            carry = sum / 2;

            i--;
            j--;
        }
        // if something is left in carry
        if (carry != 0) {
            sb.append(carry);
        }
        // return the revse of the string as we are iterating from the back and storing
        // it in the sequence manner
        return sb.reverse().toString();

    }

    // https://leetcode.com/problems/add-to-array-form-of-integer/description/
    // 15 Feb 2023
    public static List<Integer> addToArrayForm(int[] num, int k) {
        List<Integer> list = new ArrayList<>();

        int curr = k;
        int n = num.length;
        while (--n >= 0 || curr > 0) {
            if (n >= 0) {
                curr = curr + num[n];
            }
            list.add(curr % 10);
            curr = curr / 10;
        }

        Collections.reverse(list);
        return list;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // Feb 17

    Integer pre = null;
    Integer res = Integer.MAX_VALUE;

    public int minDiffInBST(TreeNode root) {

        return getminDiffInBST(root);
    }

    private int getminDiffInBST(TreeNode root) {
        if (root == null) {
            return 0;
        }
        getminDiffInBST(root.left);
        if (pre != null) {
            // If we have iterated the 1st ele in in order and we are at the root node of
            // the left mosr tree
            // We will store the difference in root.val and pre
            res = Math.min(res, root.val - pre);
        }
        // starting me pre will be none so the current node which will be the lastmost
        // last one in case of inorder whill be the pre
        pre = root.val;
        getminDiffInBST(root.right);

        return res;
    }

    // day 19
    // 103. Binary Tree Zigzag Level Order Traversal
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();

        // Performing level order traversal
        zzloHelper(root, ans, 0);
        return ans;

    }

    public static void zzloHelper(TreeNode root, List<List<Integer>> res, int level) {

        if (root == null) {
            return;
        }

        // check if the new Arraylist is presnet for the current level
        if (level >= res.size()) {
            // means here is no arraylist for the current level
            // so create new arrayList for this level
            res.add(level, new ArrayList<>());
        }

        // we want zig zag so what we can do is reverse the every odd level
        if (level % 2 == 0) {
            // even level
            res.get(level).add(root.val);
        } else {
            // odd level
            // here waht we are doing is add new new element of that level to 0th position
            // so all next will
            // shifted
            res.get(level).add(0, root.val);
        }

        // goto left and right and increae the level
        zzloHelper(root.left, res, level + 1);
        zzloHelper(root.right, res, level + 1);

    }

    // 35. Search Insert Position
    // 20 feb
    public int searchInsert(int[] nums, int target) {
        // iTerate in the nums suppose the current number is greater then or equal to
        // the target means that positionwould be the postion of out target so return
        // that if not found in the nums means it element is greater then all the
        // lements in the nums it would be at the last position after the last so return
        // nums.length
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        // element not found
        return nums.length;
    }

    // 21 feb 2023
    // 540. Single Element in a Sorted Array
    public int singleNonDuplicate(int[] nums) {
        int xor = 0;
        for (int i = 0; i < nums.length; i++) {
            xor = xor ^ nums[i];
        }
        return xor;
    }

    // 22 Feb
    // https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/
    public int shipWithinDays(int[] weights, int D) {
        /*
         * Find the least possible capacity of ship. It will be maximum of -> the
         * largest item or the weight on one ship if the weight is evenly distributed on
         * all the ships i.e. (sum_of_all_items)/(total_ships)
         */
        int heaviestItem = weights[0];
        int weightSum = 0;
        for (int x : weights) {
            heaviestItem = Math.max(heaviestItem, x);
            weightSum += x;
        }
        int avgWeightOnShip = (int) weightSum / D;
        // Minimum required weight capacity of a ship
        int minWeight = Math.max(heaviestItem, avgWeightOnShip);

        // Start from minimum possible size to maximum possible
        for (int i = minWeight; i <= weights.length * minWeight; i++) {
            int[] ship = new int[D];
            int index = 0;
            // Fill all the ships
            for (int j = 0; j < ship.length; j++) {
                // Try to fit as many items as possible
                while (index < weights.length && ship[j] + weights[index] < i) {
                    ship[j] += weights[index];
                    index++;
                }
            }
            if (index == weights.length)
                return i - 1;
        }
        return 0;
    }

    static class Project implements Comparable<Project> {
        int capital, profit;

        public Project(int c, int p) {
            this.capital = c;
            this.profit = p;
        }

        public int compareTo(Project project) {
            return capital - project.capital;
        }
    }

    public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;

        Project[] projects = new Project[n];
        for (int i = 0; i < n; i++) {
            projects[i] = new Project(capital[i], profits[i]);
        }
        Arrays.sort(projects);
        // Maintianing max heap
        // default is min heap so we have to reverse thee order using collection reverse
        PriorityQueue<Integer> pq = new PriorityQueue<>(n, Collections.reverseOrder());

        int ptr = 0;
        for (int i = 0; i < k; i++) {
            while (ptr < n && projects[ptr].capital <= w) {
                pq.add(projects[ptr++].profit);
            }
            if (pq.isEmpty()) {
                break;
            }
            w += pq.poll();
        }

        return w;
    }

    // Feb 24
    // https://leetcode.com/problems/minimize-deviation-in-array/description/
    public int minimumDeviation(int[] nums) {
        // First create a priority Queue
        // and implemented a custom comperator, which help us in making this priority
        // queue act like a max heap
        // concept of max heap states that, highest value should lie at the front of the
        // queue & lowest value at the back of the queue
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // so we apply the custom comperator over here

        int min = Integer.MAX_VALUE; // creating minimum variable
        for (int i : nums) { // add these values in priority queue
            if (i % 2 == 1)
                i *= 2; // if value is odd mult. by 2 & make it even
            min = Math.min(min, i); // find the minimum
            pq.add(i);
        } // now we have the value in the priority queue, where we have convert all the
          // odd's into even

        // let's find the maximum of all the value
        int diff = Integer.MAX_VALUE;
        while (pq.peek() % 2 == 0) {
            int max = pq.remove(); // max will be at top of the queue
            diff = Math.min(diff, max - min); // find the difference
            min = Math.min(max / 2, min);// minimum can change because, new value we are getting by half max can be
            // lower then current minimum
            pq.add(max / 2); // add that in the queue
        }

        return Math.min(diff, pq.peek() - min);
    }

    // Feb 26
    // https://leetcode.com/problems/edit-distance/

    public int minDistance(String word1, String word2) {
        /*
         * // Get the lcs
         * suppose we have
         * horse and ros so lcs of horse and ros is rs which of length 2
         * so total deletion in horse will be len(horse) - len(lcs) = 5 - 3 = 2
         * and total inserting in horse will be len(ros) - len(lcs) = 3 -2 = 1
         */
        return word1.length() + word2.length() - 2 * lcs(word1, word2);
    }

    private int lcs(String s1, String s2) {
        int dp[][] = new int[s1.length()][s2.length()];
        for (int[] d : dp) {
            Arrays.fill(d, -1);
        }
        return lcsmemo(s1, s2, s1.length() - 1, s2.length() - 1, dp);
    }

    private int lcsmemo(String s1, String s2, int ind1, int ind2, int[][] dp) {
        if (ind1 < 0 || ind2 < 0) {
            return 0;
        }
        if (dp[ind1][ind2] != -1) {
            return dp[ind1][ind2];
        }
        if (s1.charAt(ind1) == s2.charAt(ind2)) {
            return dp[ind1][ind2] = 1 + lcsmemo(s1, s2, ind1 - 1, ind2 - 2, dp);
        }
        return dp[ind1][ind2] = Math.max(lcsmemo(s1, s2, ind1 - 1, ind2, dp), lcsmemo(s1, s2, ind1, ind2 - 1, dp));
    }

}
