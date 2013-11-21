static final int MAX_N = 100010;
static Integer[] tempSA, sa;
static int[] c, ra;
static int[] lcp, plcp;
static void countingSort(int n, int k) {
	int i, sum, maxi = Math.max(300, n); // up to 255 ASCII chars or length of n
	for (i = 0; i < MAX_N; i++) c[i] = 0; // clear frequency table
	for (i = 0; i < n; i++) // count the frequency of each rank
		c[i + k < n ? ra[i + k] : 0]++;
	for (i = sum = 0; i < maxi; i++) {
		int t = c[i]; c[i] = sum; sum += t;
	}
	for (i = 0; i < n; i++)               // shuffle the suffix array if necessary
		tempSA[c[sa[i] + k < n ? ra[sa[i] + k] : 0]++] = sa[i];
	for (i = 0; i < n; i++)                          // update the suffix array SA
		sa[i] = tempSA[i];
}

static void constructSA(char[] s) { // O(n log(n)) -> n <= 100K
	int i, k, r, n = s.length;
	tempSA = new Integer[n]; sa = new Integer[n];
	ra = new int[n]; int[] tempRA = new int[n];
	c = new int[MAX_N];
	for (i = 0; i < n; i++) ra[i] = s[i];                      // initial rankings
	for (i = 0; i < n; i++) sa[i] = i;          // initial SA: {0, 1, 2, ..., n-1}
	for (k = 1; k < n; k <<= 1) {            // repeat sorting process log n times
		countingSort(n, k);       // actually radix sort: sort based on the second item
		countingSort(n, 0);               // then (stable) sort based on the first item
		tempRA[sa[0]] = r = 0;                  // re-ranking; start from rank r = 0
		for (i = 1; i < n; i++)                         // compare adjacent suffices
			tempRA[sa[i]] =      // if same pair => same rank r; otherwise, increase r
			(ra[sa[i]] == ra[sa[i-1]] && ra[sa[i]+k] == ra[sa[i-1]+k]) ? r : ++r;
		for (i = 0; i < n; i++)                          // update the rank array RA
			ra[i] = tempRA[i];
	} }

static void computeLCP(char[] s) {
	int i, L, n = s.length;
	int[] phi = new int[n];
	lcp = new int[n]; plcp = new int[n];
	phi[sa[0]] = -1; // default value
	for (i = 1; i < n; i++) // compute Phi in O(n)
		phi[sa[i]] = sa[i-1];  // remember which suffix is behind this suffix
	for (i = L = 0; i < n; i++) { // compute Permuted LCP in O(n)
		if (phi[i] == -1) { plcp[i] = 0; continue; } // special case
		while (i + L < n && phi[i] + L < n && s[i + L] == s[phi[i] + L]) L++; // L will be increased max n times
		plcp[i] = L;
		L = Math.max(L-1, 0); // L will be decreased max n times
	}
	for (i = 1; i < n; i++) // compute LCP in O(n)
		lcp[i] = plcp[sa[i]]; // put the permuted LCP back to the correct position
}

static int strncmp(char[] a, int i, char[] b, int j, int n){
	for (int k=0; i+k < a.length && j+k < b.length; k++){
		if (a[i+k] != b[j+k]) return a[i+k] - b[j+k];
	}
	return 0;
}

static int[] stringMatching(char[] s, char[] p) {  // string matching in O(m log n)
	int n = s.length, m = p.length;
	constructSA(s);
	int lo = 0, hi = n-1, mid = lo; // valid matching = [0 .. n-1]
	while (lo < hi) { // find lower bound
		mid = (lo + hi) / 2;
		int res = strncmp(s, sa[mid], p, 0, m); // try to find P in suffix 'mid'
		if (res >= 0) hi = mid;
		else          lo = mid + 1;
	}
	if (strncmp(s,sa[lo], p,0, m) != 0) return new int[]{-1, -1};// not found
	int[] ans = new int[]{ lo, 0} ;

	lo = 0; hi = n - 1; mid = lo;
	while (lo < hi) { // if lower bound is found, find upper bound
		mid = (lo + hi) / 2;
		int res = strncmp(s, sa[mid], p,0, m);
		if (res > 0) hi = mid;
		else         lo = mid + 1;
	}
	if (strncmp(s, sa[hi], p,0, m) != 0) hi--; // special case
	ans[1] = hi;
	return ans;
} // return lower/upper bound as the first/second item of the pair, respectively

static String LRS(char[] s) { // Longest Repeating Subsequence
	int n = s.length;
	constructSA(s);
	computeLCP(s);
	int i, idx = 0, maxLCP = 0;

	for (i = 1; i < n; i++) // O(n)
		if (lcp[i] > maxLCP) {
			maxLCP = lcp[i];
			idx = i;
		}
	return new String(s).substring(sa[idx], sa[idx]+maxLCP);
}

static int owner(int idx,int n,int m) { return (idx < n-m-1) ? 1 : 2; }

static String LCS(String T, String P) { // Longest common subsequence
	int i, idx = 0;

	int m = P.length();
	char[] s = (T + "$" + P + "#").toCharArray(); // append P and '#'
	int n = s.length; // update n
	constructSA(s); // O(n log n)
	computeLCP(s); // O(n)

	int maxLCP = -1;
	for (i = 1; i < n; i++)
		if (lcp[i] > maxLCP && owner(sa[i],n,m) != owner(sa[i-1],n,m)) {  // different owner
			maxLCP = lcp[i];
			idx = i;
		}

	return new String(s).substring(sa[idx], sa[idx] + maxLCP);
}