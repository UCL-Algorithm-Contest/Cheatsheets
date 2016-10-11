TreeMap<Integer,Edge>[] preprocess(TreeMap<Integer,Edge>[] g) {
  TreeMap<Integer, Edge>[] h = 
  new TreeMap[2*g.length];
  for(int v = 0; v < h.length; v++)
    h[v] = new TreeMap<Integer, Edge>();
  for(int v = 0; v < g.length; v++) {
    for(Entry<Integer,Edge> entry:g[v].entrySet()) {
      int u = entry.getKey();
      Edge e = entry.getValue();
      h[2*v+1].put(2*u, e);
    }
    h[2*v].put(2*v+1, new Edge(Integer.MAX_VALUE,0));
  }
  return h;
}
