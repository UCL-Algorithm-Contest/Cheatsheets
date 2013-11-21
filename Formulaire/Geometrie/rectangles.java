long area(R[] r) {
  // sort y coordinates
  int[] y = new int[2 * r.length];
  int k = 0;
  for(R rect : r) {
    y[k++] = rect.y1;
    y[k++] = rect.y2;
  }
  Arrays.sort(y);
  // build interval tree
  IntervalTree T = new IntervalTree(y);
  // initialize event queue
  PriorityQueue<Event> Q = new PriorityQueue<Event>();
  for(R rectangle : r) {
    Q.add(new Event(rectangle.x1, rectangle));
    Q.add(new Event(rectangle.x2, rectangle));
  }
  long area = 0;
  Event previous = null;
  // loop over all events
  while(!Q.isEmpty()) {
    // poll next event
    Event e = Q.poll();
    if(previous == null) {
      // first vertical line
      T.add(e.r.y1, e.r.y2);
    } else { 
      // found a new vertical line
      // update area by dx * tree measure 
      int dx = e.x - previous.x;
      area += dx * T.measure();
      if(e.x == e.r.x1) {
        // new rectangle, add segment to T
        T.add(e.r.y1, e.r.y2);
      } else {
        // exiting rectangle, remove segment from T
        T.remove(e.r.y1, e.r.y2);
      }
    }
    // update previous
    previous = e;
  }
  return area;
}

class Event implements Comparable<Event> {
  int x;
  R r;
  public Event(int x, R r) {
    this.x = x;
    this.r = r;
  }
  public int compareTo(Event other) {	
    return x - other.x;
  }
}
class R {
  int x1, y1, x2, y2;
  public R(int x1, int y1, int x2, int y2) {
    this.x1 = x1;this.y1 = y1;this.x2 = x2;this.y2 = y2;
  }
}
