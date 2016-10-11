public static void fft(double[] re, double[] im, boolean invert) {
  int count = re.length;
  for (int i = 1, j = 0; i < count; i++) {
    int bit = count >> 1;
    for (; j >= bit; bit >>= 1)
      j -= bit;
    j += bit;
    if (i < j) {
      double temp = re[i];
      re[i] = re[j];
      re[j] = temp;
      temp = im[i];
      im[i] = im[j];
      im[j] = temp;
    }
  }
  for (int len = 2; len <= count; len <<= 1) {
    int halfLen = len >> 1;
    double angle = 2 * Math.PI / len;
    if (invert)
      angle = -angle;
    double wLenA = Math.cos(angle);
    double wLenB = Math.sin(angle);
    for (int i = 0; i < count; i += len) {
      double wA = 1;
      double wB = 0;
      for (int j = 0; j < halfLen; j++) {
        double uA = re[i + j];
        double uB = im[i + j];
        double vA = re[i + j + halfLen] * wA - im[i + j + halfLen] * wB;
        double vB = re[i + j + halfLen] * wB + im[i + j + halfLen] * wA;
        re[i + j] = uA + vA;
        im[i + j] = uB + vB;
        re[i + j + halfLen] = uA - vA;
        im[i + j + halfLen] = uB - vB;
        double nextWA = wA * wLenA - wB * wLenB;
        wB = wA * wLenB + wB * wLenA;
        wA = nextWA;
      }
    }
  }
  if (invert) {
    for (int i = 0; i < count; i++) {
      re[i] /= count;
      im[i] /= count;
    }
  }
}

public static long[] poly_mult(long[] a, long[] b) {
  int resultSize = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
  resultSize = Math.max(resultSize, 1);
  double[] aReal = new double[resultSize];
  double[] aImaginary = new double[resultSize];
  double[] bReal = new double[resultSize];
  double[] bImaginary = new double[resultSize];
  for (int i = 0; i < a.length; i++)
    aReal[i] = a[i];
  for (int i = 0; i < b.length; i++)
    bReal[i] = b[i];
  fft(aReal, aImaginary, false);
  if (a == b) {
    System.arraycopy(aReal, 0, bReal, 0, aReal.length);
    System.arraycopy(aImaginary, 0, bImaginary, 0, aImaginary.length);
  } else
    fft(bReal, bImaginary, false);
  for (int i = 0; i < resultSize; i++) {
    double real = aReal[i] * bReal[i] - aImaginary[i] * bImaginary[i];
    aImaginary[i] = aImaginary[i] * bReal[i] + bImaginary[i] * aReal[i];
    aReal[i] = real;
  }
  fft(aReal, aImaginary, true);
  long[] result = new long[resultSize];
  for (int i = 0; i < resultSize; i++)
    result[i] = Math.round(aReal[i]);
  return result;
}
