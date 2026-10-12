# Lista 1

## Tasks

| Task    | Description                                             |
| ------- | ------------------------------------------------------- |
| `zad_1` | Prime numbers up to `n` (sieve of Eratosthenes), in Java |
| `zad_2` | The same as `zad_1`, in C++                             |

The first argument is `n`, every following argument is an index `m`.
The program prints the `m`-th prime number not greater than `n` (counted from 0),
or reports that `m` is out of range.

## Building and running

### Java (`zad_1`)

```
javac PrimeNumbers.java Test.java
java Test 100 0 5 10
```

### C++ (`zad_2`)

```
cmake -B build
cmake --build build
./build/Test 100 0 5 10
```
