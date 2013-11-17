static double[] bellmanFord(LinkedList<Edge> gt, int v, int n) {
  double[] dist = new double[n];
  Arrays.fill(dist, Double.POSITIVE_INFINITY);
  dist[v] = 0;
  for(int i=0; i < n-1; i++)
    for(Edge e : gt)
      if(dist[e.o] + e.w < dist[e.d])
        dist[e.d] = dist[e.o] + e.w;
  for(Edge e : gt)
    if(dist[e.o] + e.w < dist[e.d])
      return null;
  return dist;
}

static double[] spfa (LinkedList<Edge>[] g, int s) {
  int n = g.length;
  double[] dist = new double[n];
  Arrays.fill(dist, Double.POSITIVE_INFINITY);
  Queue<Integer> q = new LinkedList<Integer>();
  BitSet inQueue = new BitSet(n);
  int[] timesIn = new int[n];
  dist[s] = 0;
  q.add(s);
  inQueue.set(s);
  timesIn[s]++;
  while (!q.isEmpty()) {
    int cur = q.poll(); inQueue.clear(cur);
    for (Edge next : g[cur]) {
      int v = next.d, w = next.w;
      if (dist[cur] + w < dist[v]) {
        dist[v] = dist[cur] + w;
        if (!inQueue.get(v)) {
          q.add(v);
          inQueue.set(v);
          timesIn[v]++;
          if (timesIn[v] >= n) {
            return null; // Infinite loop
          }
        }
      }
    }
  }
  return dist;
}