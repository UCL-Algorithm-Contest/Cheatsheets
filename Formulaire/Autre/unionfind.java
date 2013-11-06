static int[] depth;
static int[] leader;
static boolean isLeader (int a) {
	return leader[a] == a;
}
static int getLeader (int a) {
	int parent = leader[a];
	if (!isLeader(parent)) {
		leader[a] = getLeader(parent);
	}
	return leader[a];
}
static void merge (int a, int b) {
	if (!isLeader(a)) {
		merge(getLeader(a), b);
	} else if (!isLeader(b)) {
		merge(a, getLeader(b));
	} else if (depth[a] > depth[b]) {
		merge(b,a);
	} else {
		leader[a] = b;
		depth[b] = Math.max(depth[b], depth[a] + 1);
	}
}
