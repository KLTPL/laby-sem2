#include "PascalTriangleRow.hpp"
#include <iostream>

int main(int argc, char *argv[]) {
  if (argc < 2) {
    return 0;
  }
  int n;
  try {
    n = std::stoi(argv[1]);
    if (n < 0) {
      std::cout << argv[1] << " - Nieprawidłowy numer wiersza\n";
      return 0;
    }
  } catch (...) {
    std::cout << argv[1] << " - nieprawidłowa dana\n";
    return 0;
  }
  PascalTriangleRow rowGenerator(n);

  for (int i = 2; i < argc; i++) {
    try {
      int k = std::stoi(argv[i]);
      int res = rowGenerator.getVal(k);

      if (res != -1) {
        std::cout << k << " - " << res << "\n";
      } else {
        std::cout << k << " - nieprawidłowy zakres\n";
      }
    } catch (...) {
      std::cout << argv[i] << " - nieprawidłowa dana\n";
    }
  }
}
