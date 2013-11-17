int maxFlow(TreeMap<Integer, Integer>[] g, int s, int t) {
  if(s == t) return 0;
  int maxFlow = 0;
  LinkedList<Edge> path = findAugmentingPath(g, s, t);
  while(path != null) {
    int pathCapacity = applyPath(g, path);
    maxFlow += pathCapacity;
    path = findAugmentingPath(g, s, t);
  }
  return maxFlow;
}

LinkedList<Edge> findAugmentingPath(TreeMap<Integer,Integer>[] g, int s, int t) {
  Queue<Integer> Q = new LinkedList<Integer>();
  Q.add(s);
  Edge[] parent = new Edge[g.length];
  Arrays.fill(parent, null);
  while(!Q.isEmpty()) {
    int cur = Q.poll();
    for(Entry<Integer, Integer> e : g[cur].entrySet()) {
      int next = e.getKey();
      int w = e.getValue();
      if(parent[next] == null) {
        Q.add(next);
        parent[next] = new Edge(cur, next, w);
      }
    }
  }
  if(parent[t] == null) return null;
  LinkedList<Edge> path = new LinkedList<Edge>();
  int cur = t;
  while(cur != s) {
    path.add(parent[cur]);
    cur = parent[cur].o;
  }
  return path;
}

int applyPath(TreeMap<Integer, Integer>[] g, LinkedList<Edge> path) {
  int minCapacity = Integer.MAX_VALUE;
  for(Edge e : path)
    minCapacity = Math.min(minCapacity, e.w);
  for(Edge e : path) {
    if(minCapacity == e.w)
      g[e.o].remove(e.d);
    else
      g[e.o].put(e.d, e.w - minCapacity);
    Integer backCapacity = g[e.d].get(e.o);
    if(backCapacity == null)
      g[e.d].put(e.o, minCapacity);
    else
      g[e.d].put(e.o, backCapacity+minCapacity);
  }
  return minCapacity;
}