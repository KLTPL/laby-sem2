class PascalTriangleRow {
  int[] row;

  public PascalTriangleRow(int n) {
    row = createRow(n);
  }

  private int[] createRow(int n) {
    int[] res = new int[n + 1];
    res[0] = 1;

    for (int k = 1; k <= n; k++) {
      res[k] = res[k - 1] * (n - k + 1) / k;
    }
    return res;
  }

  public int getVal(int k) {
    if (k < 0 || k >= row.length) {
      return -1;
    }
    return row[k];
  }
}
