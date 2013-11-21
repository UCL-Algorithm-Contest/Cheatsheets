static int n; // V
static int m; // vertex on the left subset of V
static LinkedList<Integer>[] g;
static int[] match;
static BitSet visited;

private static int Aug(int l) {
  if (visited.get(l)) return 0;
  visited.set(l);

  for (int right : g[l]) {
    if (match[right] == -1 || Aug(match[right]) == 1) {
      match[right] = l;
      return 1; // we found one matching
    }
  }

  return 0; // no matching
}

static int mcbm () {
  int MCBM = 0;
  match = new int[n];
  for (int i = 0; i < n; i++) {
    match[i] = -1;
  }
  for (int l = 0; l < m; l++) {
    visited = new BitSet(n);
    MCBM += Aug(l);
  }
  return MCBM;
}
