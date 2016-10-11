int[] p;

int minCostFlow(TreeMap<Integer, Edge>[] g, int s, int t) {
  int mincost = 0;
  while(spfa(g, s) != null && p[t] != -1) {
    // compute path capacity
    int cur = t;
    int pcap = Integer.MAX_VALUE;
    while(cur != s) {
      int prev = p[cur];
      pcap = Math.min(pcap, g[prev].get(cur).cap);
      cur = prev;
    }
    // update graph
    cur = t;
    int pcost = 0;
    while(cur != s) {
      int prev = p[cur];
      Edge epath = g[prev].get(cur);
      pcost += epath.cost * pcap;
      // update current edge
      if(epath.cap == pcap) g[prev].remove(cur);
      else epath.cap -= pcap;
      // update reverse edge
      Edge eback = g[cur].get(prev);
      if(eback != null) eback.cap += pcap;
      else g[cur].put(prev, new Edge(pcap, -epath.cost))
      cur = prev;
    }
    mincost += pcost;
  }
  return mincost;
}
