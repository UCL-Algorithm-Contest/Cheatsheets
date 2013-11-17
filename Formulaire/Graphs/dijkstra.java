double[] dijkstra(LinkedList<Edge>[] g, int v) {
  double[] d = new double[g.length];
  Arrays.fill(d, Double.POSITIVE_INFINITY);
  d[v] = 0;
  PriorityQueue<Edge> PQ = new PriorityQueue<Edge>();
  for(Edge e : g[v])
    PQ.add(e);
  while(!PQ.isEmpty()) {
    Edge minE = PQ.poll();
    if(d[minE.dest] == Double.POSITIVE_INFINITY) {
      d[minE.dest] = minE.w;
      for(Edge e : g[minE.dest])
        if(d[e.dest] == Double.POSITIVE_INFINITY)
          PQ.add(new Edge(e.orig, e.dest, e.w + d[e.orig]));
    }
  }
  return d;
}