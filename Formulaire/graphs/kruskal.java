double kruskal(LinkedList<Edge> g, int n) {
  Collections.sort(g);
  UnionFind uf = new UnionFind(n);
  double w = 0;
  int c = 0;
  for(Edge e: g) {
    if(c == n-1) return w;
    if(uf.find(e.o) != uf.find(e.d)) {
      w+=e.w;
      c++;
      uf.union(e.o, e.d);
    }
  }
  return w;
}