int n;
double[][] w;
Double[] memo;

double minCostMatching(int paired) {
  if(memo[paired] != null) return memo[paired];
  if(paired == (1 << n) - 1) return 0.0;
  double min = Double.POSITIVE_INFINITY;
  int i = 0;
  while(((paired >> i) & 1) == 1) i++;
  for(int j = i + 1; j < n; j++)
    if(((paired >> j) & 1) == 0)
      min = Math.min(min, w[i][j] + minCostMatching(paired | (1 << i) | (1 << j)));
  memo[paired] = min;
  return min;
}
