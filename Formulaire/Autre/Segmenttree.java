static class SegmentTree {
  private int[] st, A;
  private int n;
  private int left (int p) { return p << 1; }
  private int right(int p) { return (p << 1) + 1; }

  private void build(int p, int L, int R) {
    if (L == R)
      st[p] = L; // or R
    else {
      int mid = (L + R) / 2;
      build(left(p) , L      , mid);
      build(right(p), mid + 1, R);
      int p1 = st[left(p)], p2 = st[right(p)];
      st[p] = (A[p1] <= A[p2]) ? p1 : p2;
  } }

  private int rmq(int p, int L, int R, int i, int j) { // O(log n)
    if (i >  R || j <  L) return -1;    // outside query  range
    if (i <= L && R <= j) return st[p]; // inside  query  range
    int mid = (L + R) / 2;
    int p1 = rmq(left(p) , L      , mid, i, j);
    int p2 = rmq(right(p), mid + 1, R  , i, j);

    if (p1 == -1) return p2;            // outside query  range
    if (p2 == -1) return p1;
    return (A[p1] <= A[p2]) ? p1 : p2; }

  private int update(int p, int L, int R, int i, int j, int v) {
    if (i > R || j < L)                 // outside update range
      return st[p];
    //if (i <= L && R <= j) // could be lazy here !! Depends on application
    if (L == R) {
      A[i] = v;
      return st[p] = L; // or R
    }
    int mid = (L + R) / 2;
    int p1 = update(left(p) , L      , mid, i, j, v);
    int p2 = update(right(p), mid + 1, R  , i, j, v);
    return st[p] = (A[p1] <= A[p2]) ? p1 : p2;
  }

  public SegmentTree(int[] _A) {
    A = _A; n = A.length;
    st = new int[4 * n];
    for (int i = 0; i < 4 * n; i++) st[i] = 0;
    build(1, 0, n - 1);
  }
  public int rmq(int i, int j) { return rmq(1, 0, n - 1, i, j); }
  public int update_point(int i, int v) {
    return update(1, 0, n - 1, i, i, v); }
  public int update_interval(int i, int j, int v) {
    return update(1, 0, n-1, i, j, v); }
}
