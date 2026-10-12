#include "PascalTriangleRow.hpp"
PascalTriangleRow::PascalTriangleRow(long long n) {
    this->n = n;
    row = new long long[n + 1];
}
PascalTriangleRow::~PascalTriangleRow() {
    delete[] row;
}
void PascalTriangleRow::fill() {
    row[0] = 1;
    for (long long i = 1; i <= n; i++) {
        row[i] = row[i - 1] * (n - i + 1) / i;
    }
}
