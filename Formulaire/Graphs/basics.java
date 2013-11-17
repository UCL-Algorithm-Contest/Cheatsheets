class Edge implements Comparable<Edge> {
  int o, d, w;
  public Edge(int o, int d, int w) {
    this.o = o; this.d = d; this.w = w;
  }
  public int compareTo(Edge o) {
    return w - o.w;
  }
}