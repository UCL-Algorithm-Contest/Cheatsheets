static class Node {
  Node[] next;
  Node fall_node;
  LinkedList<Integer> pattern_ids;
  public Node(int alphabet_len) {
    next = new Node[alphabet_len];
    fall_node = null;
    pattern_ids = null;
  }
}
static int next_id = 0;
static int TrieInsert(Node node, int p[], int alphabet_len) {
  for (int i = 0; i < p.length; i++) {
    if (node.next[p[i]] == null)
      node.next[p[i]] = new Node(alphabet_len);
    node = node.next[p[i]];
  }
  int cur_id;
  if (node.pattern_ids == null) {
    cur_id = next_id++;
    node.pattern_ids = new LinkedList<Integer>();
    node.pattern_ids.add(cur_id);
  } else {
    cur_id = node.pattern_ids.getFirst();
  }
  return cur_id;
  // two identical patterns have the same id
}
static Node BuildTrie(ArrayList<int[]> patterns, int[] ids, int alphabet_len) {
  Node trie_root = new Node(alphabet_len);

  // Insert pattern lines in the trie.
  for (int i = 0; i < patterns.size(); i++)
    ids[i] = TrieInsert(trie_root, patterns.get(i), alphabet_len);

  // Build fall function.
  LinkedList<Node> q = new LinkedList<Node>();
  for (int i = 0; i < alphabet_len; i++)
    if (trie_root.next[i] == null)
      trie_root.next[i] = trie_root;  // Complete the next function for the root.
    else {
      q.add(trie_root.next[i]);
      trie_root.next[i].fall_node = trie_root;
    }
  while (!q.isEmpty()) {
    Node cur = q.poll();
    if (cur.fall_node.pattern_ids != null) {
      if (cur.pattern_ids == null)
        cur.pattern_ids = new LinkedList<Integer>();
      cur.pattern_ids.addAll(cur.fall_node.pattern_ids);
    }
    for (int i = 0; i < alphabet_len; i++)
      if (cur.next[i] != null) {
        q.add(cur.next[i]);
        Node v = cur.fall_node;
        while (v.next[i] == null)
          v = v.fall_node;
        cur.next[i].fall_node = v.next[i];
      }
  }
  return trie_root;
}
static LinkedList<Integer>[] AhoCorasickSearch(Node trie_root, int[] text) {
  LinkedList<Integer>[] match = new LinkedList[text.length];
  Node cur = trie_root;
  for (int i = 0; i < text.length; i++) {
    int ind = text[i];
    while (cur.next[ind] == null) {
      cur = cur.fall_node;
    }
    cur = cur.next[ind];
    match[i] = cur.pattern_ids;
  }
  return match;
}
