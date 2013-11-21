int[] kmpPreprocess(char[] p) {
  int m = p.length;
  int[] b = new int[m+1];
  int i = 0, j = -1; b[0] = -1; // starting values
  while (i < m) { // pre-process the pattern string p
    while (j >= 0 && p[i] != p[j]) j = b[j]; // if different, reset j using b
    i++; j++; // if same, advance both pointers
    b[i] = j;
  }
  return b; }

LinkedList<Integer> kmpSearchAll(char[] s, char[] p) { // text, pattern
  int[] b = kmpPreprocess(p); // back table
  int n = s.length, m = p.length;
  LinkedList<Integer> found = new LinkedList<Integer>();
  int i = 0, j = 0; // starting values
  while (i < n) { // search through string s
    while (j >= 0 && s[i] != p[j]) j = b[j]; // if different, reset j using b
    i++; j++; // if same, advance both pointers
    if (j == m) { // a match found when j == m
      found.add(i-j);
      j = b[j]; // prepare j for the next possible match
    } }
  return found; }

int kmpSearchFirst(char[] s, char[] p) { // text, pattern
  int[] b = kmpPreprocess(p); // back table
  int n = s.length, m = p.length;
  int i = 0, j = 0; // starting values
  while (i < n) { // search through string s
    while (j >= 0 && s[i] != p[j]) j = b[j]; // if different, reset j using b
    i++; j++; // if same, advance both pointers
    if (j == m) { // a match found when j == m
      return i - j;
    } }
  return n - j; }
