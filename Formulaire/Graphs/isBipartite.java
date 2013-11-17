boolean isBipartite(LinkedList<Integer>[] g)
{
  int[] d = bfs(g);
  for(int u = 0; u < g.length; u++)
    for(Integer v: g[u])
      if((d[u]%2)!=(d[v]%2)) return false;
  return true;
}