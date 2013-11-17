double[][] floydWarshall(double[][] A)
{
  int n = A.length;
  for(int k = 0; k < n; k++)
    for(int v = 0; v < n; v++)
      for(int u = 0; u < n; u++)
        A[v][u] = Math.min(A[v][u], A[v][k] + A[k][u]);
  return A;
}