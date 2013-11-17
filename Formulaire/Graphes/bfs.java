int[] bfs(LinkedList<Integer>[] g)
{
  int[] c = new int[g.length];
  Arrays.fill(c, -1);
  for(int v = 0; v < g.length; v++)
    if(c[v] == -1)
      bfsVisit(g, v, c);
  return c;
}