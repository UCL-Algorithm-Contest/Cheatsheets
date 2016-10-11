int maxflowEK(TreeMap<Integer, Integer>[] g, int source, int sink) {
  int flow = 0;
  int pcap;
  while((pcap = augmentBFS(g, source, sink)) != -1) {
    flow += pcap;
  }
  return flow;
}

	
int augmentBFS(TreeMap<Integer, Integer>[] g, int source, int sink) {
  // initialize bfs
  Queue<Integer> Q = new LinkedList<Integer>();
  Integer[] p = new Integer[g.length];
  int[] pcap = new int[g.length];
  pcap[source] = Integer.MAX_VALUE;
  p[source] = -1;
  Q.add(source);
  // compute path
  while(p[sink] == null && !Q.isEmpty()) {
    int u = Q.poll(); 
    for(Entry<Integer, Integer> e : g[u].entrySet()) {
      int v = e.getKey();
      if(e.getValue() > 0 && p[v] == null) {
        p[v] = u;
        pcap[v] = Math.min(pcap[u], e.getValue());
        Q.add(v);
      }
    }
  }
  if(p[sink] == null) return -1;
  // update graph
  int cur = sink;
  while(cur != source) {
    int prev = p[cur];
    int cap = g[prev].get(cur);
    g[prev].put(cur, cap - pcap[sink]);
    Integer backcap = g[cur].get(prev);
    g[cur].put(prev, backcap == null? pcap[sink] : backcap + pcap[sink]);
    cur = prev;
  }
  return pcap[sink];
}

