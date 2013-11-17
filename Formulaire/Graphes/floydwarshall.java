double[][] floydWarshall(double[][] A)
{
  int n = A.length;
  for(int k = 0; k < n; k++)
    for(int v = 0; v < n; v++)
      for(int u = 0; u < n; u++)
        A[v][u] = Math.min(A[v][u], A[v][k] + A[k][u]);
        //or:
        A[v][u] = min(A[v][u], max(A[v][k], A[k][u])); //minimax
        A[v][u] = max(A[v][u], min(A[v][k], A[k][u])); //maximin
        A[v][u] = max(A[v][u], A[v][k] * A[k][u]); //safest path (A contains probability)
  return A;
}