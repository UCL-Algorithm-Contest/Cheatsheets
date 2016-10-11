static int n;
static LinkedList<Integer>[] g;
static Integer[] match;
static int INF;
static int[] dist;
static BitSet left;

static boolean BFS () {
  Queue<Integer> q = new LinkedList<Integer>();
  dist = new int[n];
  for (int u = 0; u < n; u++) {
    if (left.get(u)) {
      if (match[u] == null) {
        dist[u] = 0;
        q.add(u);
      } else
        dist[u] = INF;
    }
  }
  int found = INF;
  while (!q.isEmpty()) {
    int u = q.poll();
    if (dist[u] < found) {
      for (int v : g[u]) {
        if (match[v] == null) {
          if (found == INF)
            found = dist[u] + 1;
        } else if (dist[match[v]] == INF) {
          dist[match[v]] = dist[u] + 1;
          q.add(match[v]);
        }
      }
    }
  }
  return found != INF;
}

static boolean DFS (Integer u) {
  if (u != null) {
    for (int v : g[u]) {
      if (match[v] == null || dist[match[v]] == dist[u] + 1)
        if (DFS(match[v])) {
          match[v] = u;
          match[u] = v;
          return true;
        }
    }
    dist[u] = INF;
    return false;
  }
  return true;
}

static void left_right () {
  BitSet vis = new BitSet(n);
  left = new BitSet(n);
  Queue<Integer> q = new LinkedList<Integer>();
  for (int i = 0; i < n; i++) {
    if (vis.get(i)) continue;
    vis.set(i);
    left.set(i);
    q.add(i);
    while (!q.isEmpty()) {
      int cur = q.poll();
      for (int next : g[cur]) {
        if (!vis.get(next)) {
          vis.set(next);
          if (!left.get(cur))
            left.set(next);
          q.add(next);
        }
      }
    }
  }
}

static int hopcroftKarp () {
  left_right();
  INF = n+1;
  match = new Integer[n];
  int MCBM = 0;
  while (BFS())
    for (int u = 0; u < n; u++)
      if (left.get(u) && match[u] == null)
        if (DFS(u))
          MCBM++;
  return MCBM;
}
