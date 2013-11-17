Stack<Integer> toposort; // add stack to global variables
/* ... */
void dfs(LinkedList<Integer>[] g) {
  /* ... */
  toposort = new Stack<Integer>();
  for(int v = 0; v < g.length; v++) { /* ... */ }
}

void dfsVisit(LinkedList<Integer>[] g, int v,int[] label) {
  /* ... */
  toposort.push(v); // push vertex when closing it
  label[v] = CLOSED;
}