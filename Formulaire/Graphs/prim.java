double prim(LinkedList<Edge>[] g) {
  boolean[] inTree = new boolean[g.length];
  PriorityQueue<Edge> PQ = new PriorityQueue<Edge>();
  // add 0 to the tree and initialize the priority queue
  inTree[0] = true;
  for(Edge e : g[0]) PQ.add(e);
  double weight = 0;
  int size = 1;
  while(size != g.length) {
    // poll the minimum weight edge in PQ
    Edge minE = PQ.poll();
    // if its endpoint in not in the tree, add it
    if(!inTree[minE.d]) {
      // add edge minE to the MST
      inTree[minE.d] = true;
      weight += minE.w; 
      size++;
      // add edge leading to new endpoints to the PQ
      for(Edge e : g[minE.d])
        if(!inTree[e.d]) PQ.add(e);
    }
  }
  return weight;
}