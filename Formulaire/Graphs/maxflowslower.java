// Ford-Fulkerson (with slow DFS)
static int pcap;
static int maxflowFF(int[][] g, int source, int sink) {
  int flow = 0;
  pcap = Integer.MAX_VALUE;
  while(augmentDFS(g, source, sink, new boolean[g.length])) {
    flow += pcap;
    pcap = Integer.MAX_VALUE;
  }
  return flow;
}
		
static boolean augmentDFS(int[][] g, int cur, int sink, boolean[] done) {
  if(cur == sink) return true;
  if(done[cur]) return false;
  done[cur] = true;
  for(int v = 0; v < g.length; v++) {
    if(g[cur][v] > 0) {
      pcap = Math.min(pcap, g[cur][v]);
      if(augmentDFS(g, v, sink, done)) {
        g[cur][v] -= pcap;
        g[v][cur] += pcap;
        return true;
      }
    }
  }
  return false;
}

// Edmonds-Karp (with slow BFS)
static int maxflowEK(int[][] g, int source, int sink) {
  int flow = 0;
  int pcap;
  while((pcap = augmentBFS(g, source, sink)) != -1)
    flow += pcap;
  return flow;
}

static int augmentBFS(int[][] g, int source, int sink) {
  Queue<Integer> Q = new LinkedList<Integer>();
  Integer[] p = new Integer[g.length];
  int[] pcap = new int[g.length];
  pcap[source] = Integer.MAX_VALUE;
  p[source] = -1;
  Q.add(source);
  while(p[sink] == null && !Q.isEmpty()) {
    int cur = Q.poll();
    for(int v = 0; v < g.length; v++) {
      if(g[cur][v] > 0 && p[v] == null) {
        p[v] = cur;
        pcap[v] = Math.min(pcap[cur], g[cur][v]);
        Q.add(v);
      }
    }
  }
  if(p[sink] == null) return -1;
  // update residual graph
  int cur = sink;
  while(cur != source) {
    g[p[cur]][cur] -= pcap[sink];
    g[cur][p[cur]] += pcap[sink];
    cur = p[cur];
  }
  return pcap[sink];
}