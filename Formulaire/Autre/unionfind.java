static int[] depth;
static int[] leader;
static int[] size;
static int getLeader (int a) {
  if(a != leader[a])
    leader[a] = getLeader(leader[a]);
  return leader[a];
}
static void merge (int a, int b) {
  int leaderA = getLeader(a);
  int leaderB = getLeader(b);
  if(leaderA == leaderB)
    return;
  if(size[leaderA] > size[leaderB]) {
    leader[leaderB] = leaderA;
    depth[leaderA] = Math.max(depth[leaderA], depth[leaderB]+1);
    size[leaderA] += size[leaderB];
  }
  else {
    leader[leaderA] = leaderB;
    depth[leaderB] = Math.max(depth[leaderA]+1, depth[leaderB]);
    size[leaderB] += size[leaderA];
  }
}
