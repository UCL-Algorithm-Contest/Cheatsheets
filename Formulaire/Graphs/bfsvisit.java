int[] bfsVisit(LinkedList<Integer>[] g, int v, int c[]) { //c is for connected components only
  Queue<Integer> Q = new LinkedList<Integer>();
  Q.add(v);
  int[] d = new int[g.length];
  c[v]=v; //for connected components
  Arrays.fill(d, Integer.MAX_VALUE);
  // set distance to origin to 0
  d[v] = 0;
  while(!Q.isEmpty()) {
    int cur = Q.poll();
    // go over all neighbors of cur
    for(int u : g[cur]) {
      // if u is unvisited
      if(d[u] == Integer.MAX_VALUE) { //or c[u] == -1 if we calculate connected components 
      	c[u] = v; //for connected components
        Q.add(u);
        // set the distance from v to u
        d[u] = d[cur] + 1;
      }
    }
  }
  return d;
}