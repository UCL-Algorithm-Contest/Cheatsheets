static long modpow (long a, long n, long m) {
  if (n == 0) {
    return 1 % m;
  }
  if (n % 2 == 0) {
    long demi = modpow(a, n/2, m);
    return (demi * demi) % m;
  } else {
    return (modpow(a, n-1, m) * a) % m;
  }
}
static long modular_sqrt(long a, long p) {
  /*
     Solve the congruence of the form:
     x^2 = a (mod p)
     And returns x. Note that p - x is also a root.

     0 is returned is no square root exists for
     these a and p.
     */
  /*
     The Tonelli-Shanks algorithm is used (except
     for some simple cases in which the solution
     is known from an identity). This algorithm
     runs in polynomial time (unless the
     generalized Riemann hypothesis is false).
     */
  // Simple cases
  if (legendre_symbol(a, p) != 1) {
    return 0;
  } else if (a == 0) {
    return 0;
  } else if (p == 2) {
    return a;
  } else if (p % 4 == 3) {
    return modpow(a, (p + 1) / 4, p);
  }

  /* Partition p-1 to s * 2^e for an odd s (i.e.
     reduce all the powers of 2 from p-1)
     */
  long s = p - 1;
  long e = 0;
  while (s % 2 == 0) {
    s /= 2;
    e += 1;
  }

  /* Find some 'n' with a legendre symbol n|p = -1.
     Shouldn't take long.*/
  long n = 2;
  while (legendre_symbol(n, p) != -1) {
    n += 1;
  }

  /* x is a guess of the square root that gets better
   * with each iteration.
   * b is the "fudge factor" - by how much we're off
   * with the guess. The invariant x^2 = ab (mod p)
   * is maintained throughout the loop.
   * g is used for successive powers of n to update
   * both a and b
   * r is the exponent - decreases with each update
   */
  long x = modpow(a, (s + 1) / 2, p);
  long b = modpow(a, s, p);
  long g = modpow(n, s, p);
  long r = e;

  for (;;) {
    long t = b;
    long m = 0;
    for (m = 0; m < r; m++) {
      if (t == 1) {
        break;
      }
      t = (t * t) % p;
    }

    if (m == 0) {
      return x;
    }

    long pow2 = 1;
    for (int i = 0; i < r-m-1; i++) { pow2 *= 2; }
    long gs = modpow(g, pow2, p);
    g = (gs * gs) % p;
    x = (x * gs) % p;
    b = (b * g) % p;
    r = m;
  }
}


static long legendre_symbol1(long a, long p) {
  // p is prime and a is rel. prime to b
  long ls = modpow(a, (p - 1) / 2, p);
  return ls == p - 1 ? -1 : ls;
}
static long legendre_symbol(long a, long b) {
  // b is odd and rel. prime to a
  a %= b;
  if (a == 0) {
    return 0;
  }
  int exp2 = 0;
  while (a % 2 == 0) {
    a /= 2;
    exp2++;
  }
  int cur = 1;
  if (exp2 % 2 == 1 && (b % 8 == 3 || b % 8 == 5)) {
    cur *= -1;
  }
  if (a < 0) {
    if (b % 4 == 3) {
      cur *= -1;
    }
    a *= -1;
  }
  if (a == 1) {
    return cur;
  }
  if (a % 4 == 3 && b % 4 == 3) {
    cur *= -1;
  }
  return cur * legendre_symbol(b, a);
}
