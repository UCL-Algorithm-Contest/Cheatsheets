static Integer[] dfs_num;
static int[] dfs_low, scc_id;
static BitSet visited;
static int dfsNumberCounter;
static Stack<Integer> S;
static void tarjanSCC(LinkedList<Integer>[] g, int u, LinkedList< LinkedList<Integer> > SCCs) {
  dfs_low[u] = dfsNumberCounter;
  dfs_num[u] = dfsNumberCounter++; // dfs_low[u] <= dfs_num[u]
  S.add(u); // stores u in a vector based on order of visitation
  visited.set(u);
  for(int v : g[u]) {
    if(dfs_num[v] == null)
      tarjanSCC(g, v, SCCs);
    if(visited.get(v)) // condition for update
      dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
  }
  if(dfs_low[u] == dfs_num[u]) { // if this is a root (start) of an SCC
    LinkedList<Integer> newSCC = new LinkedList<Integer>();
    int id = SCCs.size();
    for (;;) {
      int v = S.pop(); visited.clear(v);
      newSCC.add(v);
      scc_id[v] = id;
      if(u == v) break;
    }
    SCCs.add(newSCC);
  }
}
static LinkedList<Integer>[] DirectedGraphToDag (LinkedList<Integer>[] g) {
  int n = g.length;
  dfs_num = new Integer[n];
  dfs_low = new int[n];
  scc_id = new int[n];
  visited = new BitSet(n);
  dfsNumberCounter = 0;
  S = new Stack<Integer>();
  LinkedList< LinkedList<Integer> > SCCs = new LinkedList< LinkedList<Integer> >();
  for(int i = 0; i < n; i++)
    if(dfs_num[i] == null)
      tarjanSCC(g, i, SCCs);
  int N = SCCs.size();
  @SuppressWarnings("unchecked")
  LinkedList<Integer>[] G = new LinkedList[N];
  scc_size = new int[N];
  int i = 0;
  for (LinkedList<Integer> SCC : SCCs) {
    G[i] = new LinkedList<Integer>();
    scc_size[i] = SCC.size();
    BitSet reachable = new BitSet(N);
    reachable.set(i);
    for (int u : SCC) {
      for (int v : g[u])
        if (!reachable.get(scc_id[v])) {
          G[i].add(scc_id[v]);
        }
    }
    i++;
  }
  return G;
}
static int[] scc_size; // bonus information
