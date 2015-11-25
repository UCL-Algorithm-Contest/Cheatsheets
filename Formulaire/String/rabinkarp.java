static final long MOD  = 2147483647;
static final long BASE = 2;

static int RabinKarp(int[] p, int[] s) {
  if (s.length < p.length) return -1;
  int m = p.length, n = s.length;
  long phash = 0;
  long hash = 0;
  long exp = 1;
  for (int i = m-1; i >= 0; i--) {
    hash  = (hash  + ((s[i]*exp) % MOD)) % MOD;
    phash = (phash + ((p[i]*exp) % MOD)) % MOD;
    if (i > 0) exp = (exp * BASE) % MOD;
  }
  if (hash == phash) return 0;

  for (int i = m; i < n; i++) {
    // subtract top number
    hash = (hash + MOD - ((s[i-m]*exp) % MOD)) % MOD;
    // shift hash
    hash = (hash * BASE) % MOD;
    // add new number
    hash = (hash + s[i]) % MOD;
    if (hash == phash) return i-m+1;
  }
  return -1;
}
