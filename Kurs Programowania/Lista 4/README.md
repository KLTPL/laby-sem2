# Lista 4

## Tasks

Row `n` of Pascal's triangle displayed in a window, implemented three ways.

| Variant                | Description                                                       |
| ---------------------- | ----------------------------------------------------------------- |
| `Test.java`            | Swing window, rows computed by `PascalTriangle`                   |
| `trojkatpascala/`      | JavaFX application built with Maven                               |
| `cpp/`                 | C++ program computing the row, called from Java (`cpp/Pascal3.java`) |

## Building and running

### Swing

```
javac PascalTriangle.java Test.java
java Test 5
```

### JavaFX (Maven)

```
cd trojkatpascala
mvn clean javafx:run -Djavafx.args="6"
```

### C++ called from Java

Run from this directory, `Pascal3` starts `./cpp/program`.

```
g++ cpp/main.cpp cpp/PascalTriangleRow.cpp -o cpp/program
javac cpp/Pascal3.java
java cpp.Pascal3 7
```
