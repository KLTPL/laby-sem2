#include "PrimeNumbers.h"
PrimeNumbers::PrimeNumbers(int n) {
  auto [primes, primesAmount] = makePrimesArr(n);
  primes_ = primes;
  primesLength_ = primesAmount;
}

std::pair<bool *, int> PrimeNumbers::calcSieve(int n) {
  bool *sieve = new bool[n + 1];
  for (int i = 0; i < n + 1; i++) {
    sieve[i] = false;
  }

  int count = 0;
  for (int i = 2; i <= n; i++) {
    if (!sieve[i]) {
      count++;

      for (int j = i + i; j <= n; j += i) {
        sieve[j] = true;
      }
    }
  }
  return {sieve, count};
}
std::pair<int *, int> PrimeNumbers::makePrimesArr(int n) {
  auto [sieve, primesAmount] = calcSieve(n);
  int *primes = new int[primesAmount];
  int temp = 0;
  for (int i = 2; i <= n; i++) {
    if (!sieve[i]) {
      primes[temp] = i;
      temp++;
    }
  }
  delete[] sieve;
  return {primes, primesAmount};
}

int PrimeNumbers::getNumber(int m) {
  if (m < 0 || m >= primesLength_) {
    return -1;
  }
  return primes_[m];
}

PrimeNumbers::~PrimeNumbers() { delete[] primes_; }
