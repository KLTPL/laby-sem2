#include "PrimeNumbers.h"
#include <iostream>
#include <string>

int main(int argc, char *argv[]) {
  if (argc < 2) {
    return 0;
  }

  int n;
  try {
    n = std::stoi(argv[1]);
    if (n < 2) {
      std::cout << argv[1] << " - Nieprawidłowy zakres\n";
      return 0;
    }
  } catch (...) {
    std::cout << argv[1] << " - nieprawidłowa dana\n";
    return 0;
  }

  PrimeNumbers primesGenerator(n);

  for (int i = 2; i < argc; i++) {
    try {
      int m = std::stoi(argv[i]);
      int res = primesGenerator.getNumber(m);

      if (res >= 2) {
        std::cout << m << " - " << res << "\n";
      } else {
        std::cout << m << " - liczba spoza zakresu\n";
      }
    } catch (...) {
      std::cout << argv[i] << " - nieprawidłowa dana\n";
    }
  }

  return 0;
}
