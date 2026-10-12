#pragma once

#include <utility>

class PrimeNumbers {
private:
  int *primes_;
  int primesLength_;

  std::pair<bool *, int> calcSieve(int n);
  std::pair<int *, int> makePrimesArr(int n);

public:
  PrimeNumbers(int n);

  int getNumber(int m);

  ~PrimeNumbers();
};
