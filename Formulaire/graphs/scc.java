int[] scc(LinkedList<Integer>[] g) {
  // compute the reverse graph
  LinkedList<Integer>[] gt = transpose(g);
  // compute ordering
  dfs(gt);
  // !! last position will contain the number of scc's
  int[] scc = new int[g.length + 1];
  Arrays.fill(scc, -1);
  int nbComponents = 0;
  // simulate bfs loop but in toposort ordering
  while(!toposort.isEmpty()) {
    int v = toposort.pop();
    if(scc[v] == -1) {
      nbComponents++;
      bfsVisit(g, v, scc);
    }
  }
  scc[g.length] = nbComponents;
  return scc;
}