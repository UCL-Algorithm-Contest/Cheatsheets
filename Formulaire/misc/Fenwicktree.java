static class FenwickTree {
  private int[] ft;
  private int LSOne(int S) { return (S & (-S)); }
  public FenwickTree(int n) { // ignore index 0
    ft = new int[n+1];
    for (int i = 0; i <= n; i++) ft[n] = 0;
  }
  public int rsq(int b) { // returns RSQ(1, b)        PRE 1 <= b <= n
    int sum = 0; for (; b > 0; b -= LSOne(b)) sum += ft[b];
    return sum;
  }
  public int rsq(int a, int b) { // returns RSQ(a, b) PRE 1 <= a,b <= n
    return rsq(b) - (a == 1 ? 0 : rsq(a - 1));
  }
  void adjust(int k, int v) { // n = ft.size() - 1    PRE 1 <= k <= n
    for (; k < ft.length; k += LSOne(k)) ft[k] += v;
  }
}
