import java.util.*;
import java.util.LinkedList;

class SlidingWindow {

	// Max Sum Subarray of size K
	static long maximumSumSubarray(int K, ArrayList<Integer> arr, int N) {
		// code here
		long max = Integer.MIN_VALUE;
		long sum = 0;
		int start = 0;
		int end = 0;
		// cant use for loop cause start is depended on the condition
		// it not required to move start after each for loop
		while (end < N) {
			sum = sum + arr.get(end);
			// Achieving the window size
			if ((end - start + 1) < K) {
				end++;
			}
			// At this point windows size is acheived
			else if ((end - start + 1) == K) {
				max = Math.max(sum, max);
				sum = sum - arr.get(start);
				start++;
				end++;
			}
		}
		return max;
	}

	// First negative integer in every window of size k
	public long[] printFirstNegativeInteger(long A[], int N, int K) {
		long[] ans = new long[N];
		int start = 0;
		int end = 0;
		int i = 0;
		Queue<Long> q = new LinkedList<Long>();
		while (end < N) {
			if (A[end] < 0) {
				q.add(A[end]);
			}

			// achieve the window size
			if ((end - start + 1) < K) {
				end++;
			}
			// if winodws size is acheived
			else if ((end - start + 1) == K) {
				// suppose there is not negative in the window size then add 0
				if (q.isEmpty()) {
					ans[i++] = 0;
				} else {
					// List as at list 1 number in it
					ans[i++] = q.peek();

					// Now sliding the window
					// What here we have done is ,
					// for name of calculations we have just added the negative number but we havent
					// done anythis with number which are not non negative
					// This will not give error
					// But, what if the ith ele is added to the list
					// and we have to move on
					if (A[start] == q.peek()) {
						q.poll();
					}

				}
				start++;
				end++;
			}

		}

		return ans;
	}

	public static int searchOwn(String pat, String txt) {
		int ans = 0;
		int K = pat.length();
		int size = txt.length();

		Map<String, Integer> map = new HashMap<>();

		for (int i = 0; i < K; i++) {

			if (map.containsKey(pat.charAt(i))) {
				System.out.println("Yes");
			}else{
				System.out.println("No");
			}

			if (map.containsKey(pat.charAt(i))) {
				int val = map.get(i) + 1;
				System.out.println("YESSS");
				map.put(String.valueOf(pat.charAt(i)), val);
			}
			
			map.put(String.valueOf(pat.charAt(i)), 1);
		}
		System.out.println(map);

		int count  = map.size();
		int start = 0;
		int end = 0;

		while(end < size){
			if(map.containsKey(String.valueOf(txt.charAt(end)))){
				count--;
			}

			// acheive the window size
			if((end - start +1)< K){
				end++;
			}
			// Windows size is achieved
			else if ((end - start +1) == K){
				if(count == 0){
					ans++;
				}
				if(map.containsKey(String.valueOf(txt.charAt(start)))){
					count++;
				}
			}
			start++;
			end++;
		}

		return ans;
	}
	static public int search(String pat, String txt) {
        char[] p = new char[256];
        char[] s = new char[256];
        int k = pat.length();
        int cnt = 0;
        for(int i = 0;i<k;i++){
            p[pat.charAt(i)]++;
        }
        for(int i = 0;i<k-1;i++){
			s[txt.charAt(i)]++;
        }
        for(int i = k-1,j=0;i<txt.length();i++,j++){
            s[txt.charAt(i)]++;
            if(Arrays.equals(p,s)) cnt++;
            s[txt.charAt(j)]--;
            
        }
        return cnt;
    }

	public static void main(String[] args) {
		System.out.println("Henlo World");

		// System.out.println(search("kkkkk", "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"));
	}
}
