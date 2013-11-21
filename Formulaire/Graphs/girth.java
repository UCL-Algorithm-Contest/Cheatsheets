int girth(LinkedList<Integer>[] g) {
  int girth = Integer.MAX_VALUE;
  for(int v = 0; v < g.length; v++) {
    girth = Math.min(girth, checkFromV(v, g));
  }
  return girth;
}

int checkFromV(int v, LinkedList<Integer>[] g) {
  int[] parent = new int[g.length];
  Arrays.fill(parent, -1);
  int[] d = new int[g.length];
  Arrays.fill(d, Integer.MAX_VALUE);
  Queue<Integer> Q = new LinkedList<Integer>();
  Q.add(v);
  d[v] = 0;
  while(!Q.isEmpty()) {
    int cur = Q.poll();
    for(int u : g[cur]) {
      if(u != parent[cur]) {
        if(d[u] == Integer.MAX_VALUE) {
          parent[u] = cur;
          d[u] = d[cur] + 1;
          Q.add(u);
        } else {
          return d[cur] + d[u] + 1;
        }
      }
    }
  }
  return Integer.MAX_VALUE;
}

