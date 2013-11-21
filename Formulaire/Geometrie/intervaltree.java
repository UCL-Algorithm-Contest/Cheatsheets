class IntervalTree {
  Node root;
  public IntervalTree(int[] x) {
    root = new Node();
    buildTree(root, 0, x.length - 1, x);
  }
  public int measure() {
    return root.measure;
  }
  public void buildTree(Node node, int i, int j, int[] x) {
    if(j - i == 1) {
      node.l = x[i];
      node.r = x[j];
      node.m = -1;
    } else {
      node.l = x[i];
      node.r = x[j];
      int mid = (i + j) / 2;
      Node left = new Node();
      buildTree(left, i, mid, x);
      Node right = new Node();
      buildTree(right, mid, j, x);
      node.m = x[mid];
      node.left = left;
      left.parent = node;
      node.right = right;
      right.parent = node;
    }
  }
  public void remove(int x1, int x2) {
    remove(root, x1, x2);
  }
  private void remove(Node node, int x1, int x2) {
    if(node.l == x1 && node.r == x2) {
      node.count = Math.max(0, node.count - 1);
      if(node.left == null || node.right == null) {
        node.measure = node.count == 0 ? 0 : node.measure;
      } else {
        node.measure = node.count == 0 ? node.left.measure + node.right.measure : node.measure;										
      }
   } else {
      // go down the three to delete new interval
      int mid = node.m;
      if(x1 < mid && mid < x2) { 
        // split
        remove(node.left, x1, mid);
        remove(node.right, mid, x2);
      } else if(node.l <= x1 && x2 <= mid) { 
        // contained on left
        remove(node.left, x1, x2);
      } else { 
        // contained on right
        remove(node.right, x1, x2);
      }
      // update measures when going up
      if(node.count == 0) {
        node.measure = node.left.measure + node.right.measure;
      }
    }
  }
  public void add(int x1, int x2) {
    add(root, x1, x2);
  }
  private void add(Node node, int x1, int x2) {
    if(node.l == x1 && node.r == x2) {
      node.measure = x2 - x1;
      node.count++;
    } else {
      // go down the three to add new interval
      int mid = node.m;
      if(x1 < mid && mid < x2) { 
        // split
        add(node.left, x1, mid);
        add(node.right, mid, x2);
      } else if(node.l <= x1 && x2 <= mid) { 
        // contained on left
        add(node.left, x1, x2);
      } else { 
        // contained on right
        add(node.right, x1, x2);
      }
      // update measures when going up
      if(node.count == 0) {
        node.measure = node.left.measure + node.right.measure;
      }
    }
  }
  public class Node {
    int l, r, m;
    int count, measure;
    Node left, right, parent;
  }
}
