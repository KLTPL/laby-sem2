#include "PascalTriangleRow.hpp"
PascalTriangleRow::PascalTriangleRow(int n) {
  row_ = createRow(n);
  n_ = n;
}
PascalTriangleRow::~PascalTriangleRow() { delete[] row_; }

int *PascalTriangleRow::createRow(int n) {
  int *res = new int[n + 1];
  res[0] = 1;

  for (int k = 1; k <= n; k++) {
    res[k] = res[k - 1] * (n - k + 1) / k;
  }
  return res;
}

int PascalTriangleRow::getVal(int k) {
  if (k < 0 || k >= n_ + 1) {
    return -1;
  }
  return row_[k];
}
