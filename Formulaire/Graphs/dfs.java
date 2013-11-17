int UNVISITED = 0, OPEN = 1, CLOSED = 2;
boolean cycle; // true iff there is a cycle

void dfsVisit(LinkedList<Integer>[] g, int v,int[] label) {
  label[v] = OPEN;
  for(int u : g[v]) {
    if(label[u] == UNVISITED)
      dfsVisit(g, u, label);	
    if(label[u] == OPEN)
      cycle = true;
  }
  label[v] = CLOSED;
}

void dfs(LinkedList<Integer>[] g) {
  int[] label = new int[g.length];
  Arrays.fill(label, UNVISITED);
  cycle = false;
  for(int v = 0; v < g.length; v++)
    if(label[v] == UNVISITED)
      dfsVisit(g, v, label);
}