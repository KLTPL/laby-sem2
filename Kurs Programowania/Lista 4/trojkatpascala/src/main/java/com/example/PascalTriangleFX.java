package com.example;
public class PascalTriangleFX {
    int[] row;
    public PascalTriangleFX(int n) {
        row = new int[n+1];
        fill(n);
    }
    public int[] fill(int n) {
        row[0] = 1;
        for (int i = 1; i <= n; i++) {
            row[i] = (int) ((long) row[i-1] * (n - i + 1) / i);
        }
        return row;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int num : row) {
            sb.append(num).append("   ");
        }
        return sb.toString();
}
}