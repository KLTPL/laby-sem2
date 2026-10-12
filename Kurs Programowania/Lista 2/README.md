# Lista 2

## Tasks

| Task    | Description                                     |
| ------- | ----------------------------------------------- |
| `zad_1` | Row of Pascal's triangle (`PascalTriangleRow`), in Java |
| `zad_2` | The same as `zad_1`, in C++                     |

The first argument is the row number `n`, every following argument is an index `k`.
The program prints the `k`-th value of row `n`, or reports that `k` is out of range.

## Building and running

### Java (`zad_1`)

```
javac PascalTriangleRow.java Test.java
java Test 5 0 2 5
```

### C++ (`zad_2`)

```
cmake -B build
cmake --build build
./build/Test 5 0 2 5
```
