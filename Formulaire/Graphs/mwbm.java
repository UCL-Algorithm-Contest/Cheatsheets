static int[][] cost;
static int n;
static int[] lx, ly;
static int maxMatch;
static boolean[] S, T;
static int[] slack, slackx, prev, xy, yx;

static int[] minHungarian(int[][] M) {
  for(int i = 0; i < M.length; i++)
    for(int j = 0; j < M.length; j++)
      M[i][j] = -M[i][j];
  return maxHungarian(M);
}

static int[] maxHungarian(int[][] M) {
  cost = M;
  n = cost.length;
  slack = new int[n];
  slackx = new int[n];
  prev = new int[n];
  xy = new int[n];
  yx = new int[n];
  maxMatch = 0;
  for(int i = 0; i < n; i++) {
    xy[i] = -1;
    yx[i] = -1;
  }
  initLabels();
  augment();
  int ret = 0;
  int[] assignment = new int[n];
  for(int x = 0; x < n; x++) {
    ret += cost[x][xy[x]];
    assignment[x] = xy[x];
  }
  return assignment;
}

static void initLabels() {
  lx = new int[n];
  ly = new int[n];
  for(int x = 0; x < n; x++)
    for(int y = 0; y < n; y++)
      lx[x] = Math.max(lx[x], cost[x][y]);
}

static void augment() {
  if(maxMatch == n) {return;}
  int x, y, root = 0;
  int[] q = new int[n];
  int wr = 0, rd = 0;
  S = new boolean[n];
  T = new boolean[n];
  for(x = 0; x < n; x++)
    prev[x] = -1;
  for(x = 0; x < n; x++) {
    if(xy[x] == -1) {
      q[wr++] = root = x;
      prev[x] = -2;
      S[x] = true;
      break;
    }
  }
  for(y = 0; y < n; y++) {
    slack[y] = lx[root] + ly[y] - cost[root][y];
    slackx[y] = root;
  }
  while(true) {                        
    while(rd < wr) {
      x = q[rd++];                        
      for(y = 0; y < n; y++) {                 
        if(cost[x][y] == lx[x]+ly[y] && !T[y]) {
          if(yx[y] == -1) {break;}              
          T[y] = true;                    
          q[wr++] = yx[y];                 
          addToTree(yx[y], x);               
        }
      }
      if (y < n) {break;}
    }
    if (y < n) {break;}
    updateLabels();                         
    wr = rd = 0;        
    for (y = 0; y < n; y++) {    
      if (!T[y] &&  slack[y] == 0) {
        if(yx[y] == -1) {
          x = slackx[y];
          break;
        } else {
          T[y] = true;                     
          if(!S[yx[y]]) {
            q[wr++] = yx[y];                  
            addToTree(yx[y], slackx[y]);                             
          }
        }
      }
    }
    if(y < n) {break;}
  }
  if(y < n) {
    maxMatch++;                        
    for(int cx=x, cy=y, ty; cx!=-2; cx=prev[cx], cy=ty){
      ty = xy[cx];
      yx[cy] = cx;
      xy[cx] = cy;
    }
    augment();                            
  }
}

static void updateLabels() {
  int delta = Integer.MAX_VALUE;
  for(int y = 0; y < n; y++)
    if(!T[y])
      delta = Math.min(delta, slack[y]);
  for(int i = 0; i < n; i++) {
    if(S[i]) {lx[i] -= delta;}
    if(T[i]) {ly[i] += delta;}
    if(!T[i]) {slack[i] -= delta;}
  }
}

static void addToTree(int x, int prevx) {
  S[x] = true;
  prev[x] = prevx;
  for(int y = 0; y < n; y++) {
    if(lx[x] + ly[y] - cost[x][y] < slack[y]) {
      slack[y] = lx[x] + ly[y] - cost[x][y];
      slackx[y] = x;
    }
  }
}