static class UnionFind {
  int[] depth; int[] leader; int[] size;
  public UnionFind(int n) {
    depth = new int[n]; leader = new int[n]; size = new int[n];
    Arrays.fill(depth, 1); Arrays.fill(size, 1);
    for(int i = 0; i < n; i++) leader[i] = i;
  }
  public int find(int a) {
    if(a != leader[a])
      leader[a] = find(leader[a]);
    return leader[a];
  }
  public void union(int a, int b) {
    int leaderA = find(a);
    int leaderB = find(b);
    if(leaderA == leaderB) return;
    if(size[leaderA] > size[leaderB]) {
      union(leaderB, leaderA); return;
    }
    leader[leaderA] = leaderB;
    depth[leaderB] = Math.max(depth[leaderA]+1, depth[leaderB]);
    size[leaderB] += size[leaderA];
  }
}