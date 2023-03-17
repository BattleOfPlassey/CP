import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Random;

public class CP3 {
    public static void main(String[] args) {

        // String one = "Abhishek";
        // String two = "his";
        // System.out.println(strStr(one, two));
        int[] arr = { 2, 3, 4, 7, 11 };
        int k = 7;
        // System.out.println(findKthPositive(arr, k));
    }


    public int countSubstrings(String s) {
        int count= 0;
        for(int i = 0; i< s.length(); i++){
            count+=countSubstringsUtil(s,i,i);
            count+=countSubstringsUtil(s,i,i+1);
        }
        return count;
    }

    
    private int countSubstringsUtil(String s, int i, int i2) {
        int count = 0;
        while(i>=0 && i2 < s.length() && s.charAt(i)==s.charAt(i2)){
            count++;
            i--;
            i2++;
        }
        return count;
    }


    public String longestPalindromeLCS(String s) {
        String ans = "";
        int resLen = 0;

        for(int i = 0; i< s.length(); i++){
            int left = i;
            int right = i;
            while(left >=0 && right <s.length() && s.charAt(left) == s.charAt(right)){
                // if the new windows size is greter then old one the
                if(right - left +1 > resLen){
                    resLen = right - left+1;
                    ans = s.substring(left, right+1);
                }
                left--;
                right++;
            }
            left = i;
            right= i+1;
            while(left >=0 && right <s.length() && s.charAt(left) == s.charAt(right)){
                // if the new windows size is greter then old one the
                if(right - left +1 > resLen){
                    resLen = right - left+1;
                    ans = s.substring(left, right+1);
                }
                left--;
                right++;
            }
        }

        return ans;
        
    }
    

    static class  Node{
        Node[] links = new Node[26];
        boolean flag = false;

        boolean containsKey(char c){
            return (links[c-'a']!=null);
        }

        void put(char ch, Node node){
            links[ch-'a'] = node;
        }

        Node get(char ch){
            return links[ch-'a'];
        }
        void setEnd(){
            flag = true;
        }

        boolean isEnd(){
            return flag;
        }

    }


    class Trie {
        Node root;
    public Trie() {
        root = new Node();
    }
    
    public void insert(String word) {
        Node node = root; // for iterating purpose
        for(int i = 0; i< word.length(); i++){
            if(!node.containsKey(word.charAt(i))){
                node.put(word.charAt(i), new Node());
            }
            // GOTO next node
            node = node.get(word.charAt(i));
        }
        node.setEnd();
    }
    
    public boolean search(String word) {
        Node node = root;
        for(int i = 0; i< word.length(); i++){
            if(!node.containsKey(word.charAt(i))){
                return false;
            }
            node = node.get(word.charAt(i));
        }
        return node.isEnd();
    }
    
    public boolean startsWith(String prefix) {
        Node node = root;
        for(int i = 0; i< prefix.length(); i++){
            if(!node.containsKey(prefix.charAt(i))){
                return false;
            }
            node = node.get(prefix.charAt(i));
        }
        return true;
    }
}


    public TreeNode buildTreePost(int[] inorder, int[] postorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreePostHelper(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, map);
    }

    private TreeNode buildTreePostHelper(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart,
            int postEnd, Map<Integer, Integer> map) {
        if (inEnd < inStart || postEnd < postStart) {
            return null;
        }

        TreeNode root = new TreeNode(postorder[postEnd]);

        int indexRootInOrder = map.get(postorder[postEnd]);

        int diffInIndexandEnd = indexRootInOrder - inStart;

        root.left = buildTreePostHelper(inorder, inStart, indexRootInOrder - 1, postorder, postStart,
                postStart + diffInIndexandEnd - 1, map);

        root.right = buildTreePostHelper(inorder, indexRootInOrder + 1, inEnd, postorder, postStart + diffInIndexandEnd,
                postEnd - 1, map);

        return root;
    }

    private TreeNode buildTreePostHelper2(int[] inorder, int is, int ie, int[] postorder, int ps, int pe,
            Map<Integer, Integer> map) {
        if (ps > pe || is > ie) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[pe]);

        int inRoot = map.get(postorder[pe]);

        int numsLeft = inRoot - is;
        
        root.left = buildTreePostHelper2(inorder, is, inRoot -1, postorder, ps, ps+numsLeft-1, map);

        root.right = buildTreePostHelper2(inorder, inRoot+1, ie, postorder, ps+numsLeft, pe-1, map);

        return root;
    }

    public TreeNode buildTreePreorderInorder(int[] preorder, int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        int inStart = 0;
        int inEnd = inorder.length - 1;
        int prestart = 0;
        int preEnd = preorder.length - 1;

        return buildTreePreorderInorderHelper(preorder, prestart, preEnd, inorder, inStart, inEnd, map);
    }

    private TreeNode buildTreePreorderInorderHelper(int[] preorder, int prestart, int preEnd, int[] inorder,
            int inStart, int inEnd, Map<Integer, Integer> map) {

        if (prestart > preEnd || inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[prestart]);
        int indexOfRoot = map.get(root.val);

        int numsBetweenRoot = indexOfRoot - inStart;

        root.left = buildTreePreorderInorderHelper(preorder, prestart + 1, preEnd + numsBetweenRoot, inorder, inStart,
                indexOfRoot - 1, map);

        root.right = buildTreePreorderInorderHelper(preorder, prestart + numsBetweenRoot + 1, preEnd, inorder,
                indexOfRoot + 1, inEnd, map);

        return root;
    }

    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode node = q.poll();

            if (node != null) {
                q.add(node.left);
                q.add(node.right);
            } else {
                while (!q.isEmpty()) {
                    if (q.poll() != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, LinkedList<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String word = new String(chars);

            if (!map.containsKey(word)) {
                map.put(word, new LinkedList<String>());
            }
            map.get(word).add(word);
        }
        return new LinkedList<>(map.values());
    }

    public int sumNumbers(TreeNode root) {
        return snhelper(root, 0);
    }

    private int snhelper(TreeNode root, int ans) {
        if (root == null) {
            return 0;
        }
        ans = ans * 10 + root.val;
        if (root.left == null && root.right == null) {
            return ans;
        }
        return snhelper(root.left, ans) + snhelper(root.right, ans);
    }

    public int characterReplacement(String s, int k) {
        int size = s.length();

        int max_count = 0;
        int length = 0;
        int start = 0;
        int[] char_count = new int[26];
        for (int end = 0; end < size; end++) {
            // add current one to char count
            char_count[s.charAt(end) - 'A']++;

            // get the current char count
            int current_char_count = char_count[s.charAt(end) - 'A'];
            max_count = Math.max(max_count, current_char_count);

            while ((end - start) - max_count + 1 > k) {
                char_count[s.charAt(start) - 'A']--;
                start++;
            }
            length = Math.max(length, end - start + 1);

        }
        return length;
    }

    public boolean isSymmetricIterative(TreeNode root) {
        if (root == null) {
            return true;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root.left);
        q.add(root.right);
        while (!q.isEmpty()) {
            TreeNode left = q.poll();
            TreeNode right = q.poll();

            if (left == null && right == null) {
                continue;
            }
            if ((left == null && right != null) || (left != null && right == null)) {
                return false;
            }
            if (left.val != right.val) {
                return false;
            }
            q.add(left.left);
            q.add(right.right);
            q.add(left.right);
            q.add(right.left);
        }
        return true;
    }

    public boolean isSymmetric(TreeNode root) {
        return isSymmetricCall(root.left, root.right);
    }

    private boolean isSymmetricCall(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if ((left == null && right != null) || (left != null && right == null)) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        return isSymmetricCall(left.left, right.right) && isSymmetricCall(left.right, right.left);
    }

    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return new TreeNode(head.val);
        }

        ListNode slow = head;
        ListNode fast = head.next.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        TreeNode res = new TreeNode(slow.next.val); // Thi is the middle of ll
        ListNode righthalf = slow.next.next; // seperating right side
        slow.next = null; // detach left side
        res.left = sortedListToBST(head); // run same for left side
        res.right = sortedListToBST(righthalf); // for right side
        return res;

    }

    // https://leetcode.com/problems/linked-list-random-node/description/
    static class SolutionLLRN {
        ArrayList<Integer> list = new ArrayList<>();

        public SolutionLLRN(ListNode head) {
            while (head != null) {
                list.add(head.val);
                head = head.next;
            }
        }

        public int getRandom() {
            double random = Math.random() * list.size();
            return list.get((int) random);
        }
    }

    static class SolutionLLRN2 {
        ListNode head;

        public SolutionLLRN2(ListNode head) {
            this.head = head;
        }

        public int getRandom() {
            int result = 1;
            ListNode node = head;
            Random rand = new Random();
            for (int i = 1; node != null; i++) {
                if (rand.nextInt(i) == i - 1) {
                    result = node.val;
                }
                node = node.next;
            }

            return result;
        }
    }

    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> set = new HashSet<>();
        int left = 0;
        int right = 0;
        int maxLen = 0;

        while (right < s.length()) {
            if (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            } else {
                set.add(s.charAt(right));
                right++;
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }
        return maxLen;
    }

    public int lengthOfLongestSubstringM2(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0;
        int right = 0;
        int maxLen = 0;

        while (right < s.length()) {
            if (map.containsKey(s.charAt(right))) {
                left = Math.max(left, map.get(s.charAt(right)) + 1);
            }
            map.put(s.charAt(right), right);
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }

        return maxLen;
    }

    static void printAllDuplicates(String s) {
        /*
         * Given a string of characters from a to z. Print the duplicate
         * characters(which are occurring
         * more than once) in the given string with their occurrences count.
         */
        if (s.length() == 0 || s == null) {
            System.out.println(0);
        }
        HashMap<Character, Integer> map = new HashMap<>(26);
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        }
    }

    static void printAllDuplicatesM2(String s) {
        /*
         * Given a string of characters from a to z. Print the duplicate
         * characters(which are occurring
         * more than once) in the given string with their occurrences count.
         */
        // using aray
        int[] char_count = new int[26];

        for (int i = 0; i < s.length(); i++) {
            char_count[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (char_count[i] > 1) {
                System.out.println("Char: " + (char) (i + 'a') + " Count: " + char_count[i]);
            }
        }

    }

    String printSequence(String S) {
        // code here
        String[] numpad = { "2", "22", "222", "3", "33", "333",
                "4", "44", "444", "5", "55", "555",
                "6", "66", "666", "7", "77", "777",
                "7777", "8", "88", "888", "9", "99",
                "999", "9999" };

        return getStringSequence(S, numpad);
    }

    private String getStringSequence(String input, String[] numpad) {
        StringBuilder sb = new StringBuilder();
        int len = input.length();

        for (int i = 0; i < len; i++) {
            if (input.charAt(i) == ' ') {
                sb.append("0");
            } else {
                sb.append(numpad[input.charAt(i) - 'A']);
            }
        }

        return sb.toString();
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode detectCycle(ListNode head) {
        // BruteForce using Set
        HashSet<ListNode> map = new HashSet<>();
        ListNode tmp = head;
        while (tmp != null) {
            if (map.contains(tmp)) {
                return tmp;
            } else {
                map.add(tmp);
            }
            tmp = tmp.next;
        }
        return null;
    }

    public ListNode detectCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode entry = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) { // we found that , there is a cycle
                while (slow != entry) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return entry;
            }
        }
        return null;
    }

    int minEatingSpeed2(int[] piles, int h) {
        int left = 1;
        int right = 1000000000;

        while (left <= right) { // performing binary search
            int mid = left + (right - left) / 2; // doing in that way, to handle overflow instead of left + right / 2
            // if koko can eat, mid bananas per hour in less then or equals to h time
            if (canEatInTime(piles, mid, h))
                right = mid - 1; // means decrement our right pointer to optimise better solution
            else
                left = mid + 1; // if not true, increment left pointer
        }
        return left; // bcz left pointer hold our optimize k, at the end of BS
    }

    boolean canEatInTime(int piles[], int k, int h) {

        int hours = 0; // track count of hours
        for (int pile : piles) {
            // performing claculation, take an example
            // k = 4
            // pile = 10

            // pile / k => 10 / 4 = 2
            // pile % k => 2, so we need to have one more hour to eat remaining bananas left
            // over as remainder
            // hours = 3;
            int div = pile / k;
            hours += div;
            if (pile % k != 0)
                hours++; // if remainder value is not 0, we need to have an extra hour
        }
        return hours <= h;
    }

    public int minEatingSpeed(int[] piles, int H) {
        int l = 1, r = 1000000000;
        while (l < r) {
            int m = (l + r) / 2, total = 0;
            for (int p : piles)
                total += (p + m - 1) / m;
            if (total > H)
                l = m + 1;
            else
                r = m;
        }
        return l;
    }

    public long minimumTime(int[] time, int totalTrips) {
        // Initialize the search space as the range of possible minimum times required
        // for all buses to complete at least totalTrips trips.
        // The minimum time required for each bus to complete totalTrips trips is the
        // minimum time taken by any bus multiplied by totalTrips, and the maximum time
        // required is the sum of times taken by all buses multiplied by totalTrips.
        long l = 1;
        long r = Arrays.stream(time).min().getAsInt() * (long) totalTrips;

        // Use binary search to find the minimum time required to complete totalTrips
        // trips.
        while (l < r) {
            // Calculate the mid-point time value.
            final long m = (l + r) / 2;

            // Calculate the total number of trips completed for all buses at the mid-point
            // time value.
            long numTrips = numTrips(time, m);

            // If the total number of trips completed is greater than or equal to
            // totalTrips, the current mid-point time is a valid candidate for the minimum
            // time required.
            // Therefore, update the search space to the lower half of the current range.
            if (numTrips >= totalTrips)
                r = m;
            // If the total number of trips completed is less than totalTrips, the current
            // mid-point time is too high, and we need to increase the minimum time
            // required.
            // Therefore, update the search space to the upper half of the current range.
            else
                l = m + 1;
        }

        // Return the minimum time required to complete totalTrips trips.
        return l;
    }

    // Helper function to calculate the total number of trips completed by all buses
    // at any given time.
    private long numTrips(int[] time, long m) {
        // Calculate the total number of trips completed for each bus at the mid-point
        // time value.
        // The total number of trips completed for a bus is the floor division of m by
        // the time taken by the bus.
        // We then sum up the total number of trips completed for all buses.
        return Arrays.stream(time).asLongStream().reduce(0L, (subtotal, t) -> subtotal + m / t);
    }

    public static int findKthPositive(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            System.out.println("mid: " + mid);
            if (arr[mid] - mid - 1 < k) {
                System.out.println("arr[mid]-mid-1: " + (arr[mid] - mid - 1));
                left = mid + 1;
                System.out.println("left = mid+1");
                System.out.println("left: " + left);
            } else {
                right = mid - 1;
                System.out.println("right = mid -1");
                System.out.println("right:" + right);
            }
        }
        System.out.println("left: " + left);
        return left + k;
    }

    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n == 1)
            return 0;

        Map<Integer, List<Integer>> indices = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indices.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        Queue<Integer> storeIndex = new LinkedList<>();
        boolean[] visited = new boolean[n];
        storeIndex.offer(0);
        visited[0] = true;
        int steps = 0;

        while (!storeIndex.isEmpty()) {
            int size = storeIndex.size();
            while (size-- > 0) {
                int currIndex = storeIndex.poll();
                if (currIndex == n - 1)
                    return steps;

                List<Integer> jumpNextIndices = indices.get(arr[currIndex]);
                jumpNextIndices.add(currIndex - 1);
                jumpNextIndices.add(currIndex + 1);
                for (int jumpNextIndex : jumpNextIndices) {
                    if (jumpNextIndex >= 0 && jumpNextIndex < n && !visited[jumpNextIndex]) {
                        storeIndex.offer(jumpNextIndex);
                        visited[jumpNextIndex] = true;
                    }
                }
                jumpNextIndices.clear();
            }
            steps++;
        }
        return -1;

    }

    public long countSubarraysMethod1(int[] nums, int minK, int maxK) {
        long leftMostMin = -1;
        long leftMin = -1;
        long rightMax = -1;
        long totalSubArray = 0;

        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] < minK || nums[i] > maxK) {
                leftMostMin = i;
            }
            if (nums[i] == minK) {
                leftMin = i;
            }
            if (nums[i] == maxK) {
                rightMax = i;
            }
            totalSubArray += Math.max(0L, Math.min(leftMin, rightMax) - leftMostMin);
        }
        return totalSubArray;

    }

    public static int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        if (needle.length() > haystack.length()) {
            return -1;
        }
        int i = 0;
        int j = needle.length();
        while (j <= haystack.length()) {
            // System.out.println(i +" i");
            if (needle.equals(haystack.substring(i, j))) {
                // System.out.println("i j " + i + " " + j + " ");
                return i;
            }
            i++;
            j++;
        }
        return -1;
    }

    public static int compressM2(char[] chars) {

        int indexAns = 0;
        int index = 0;
        while (index < chars.length) {
            char currchar = chars[index];
            int count = 0;
            while (index < chars.length && chars[index] == currchar) {
                index++;
                count++;
            }
            chars[indexAns++] = currchar;
            if (count != 1) {
                for (char ch : Integer.toString((count)).toCharArray()) {
                    chars[indexAns++] = ch;
                }
            }
        }

        return indexAns;
    }

    public static int compress(char[] chars) {
        int i = 0;
        int res = 0;
        while (i < chars.length) {
            int groupLength = 1;
            while (i + groupLength < chars.length && chars[i] == chars[i + 1]) {
                groupLength++;
            }
            chars[res++] = chars[i];
            if (groupLength > 1) {
                for (char c : Integer.toString(groupLength).toCharArray())
                    ;

                chars[res++] = c;
            }
            i += groupLength;
        }

        return res;
    }
}
