class PrimeNumbers {
  int[] primes;

  public PrimeNumbers(int n) {
    primes = makePrimesArr(n);
  }

  private record SieveResult(boolean[] sieve, int primesAmount) {
  }

  private SieveResult calcSieve(int n) {
    boolean[] sieve = new boolean[n + 1];

    int count = 0;
    for (int i = 2; i <= n; i++) {
      if (!sieve[i]) {
        count++;

        for (int j = i + i; j <= n; j += i) {
          sieve[j] = true;
        }
      }
    }
    return new SieveResult(sieve, count);

  }

  private int[] makePrimesArr(int n) {
    SieveResult result = calcSieve(n);
    int[] primes = new int[result.primesAmount];
    int temp = 0;
    for (int i = 2; i <= n; i++) {
      if (!result.sieve[i]) {
        primes[temp] = i;
        temp++;
      }
    }
    return primes;
  }

  public int getNumber(int m) {
    if (m < 0 || m >= primes.length) {
      return -1;
    }
    return primes[m];

  }
}
