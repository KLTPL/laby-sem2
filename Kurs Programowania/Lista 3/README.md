# Lista 3

## Tasks

| Task    | Description                                                        |
| ------- | ------------------------------------------------------------------ |
| `zad_1` | Figure hierarchy (`Shape`, `Figure` and concrete figures) in Java  |
| `zad_2` | The same hierarchy in C++                                          |
| `zad_3` | Figures as enum constants implementing area and perimeter, in Java |
| `zad_4` | The same enum-based approach, in C++                               |

Supported figures: circle, square, rectangle, diamond, pentagon, hexagon.
Each figure reports its area and perimeter.

## Usage

Arguments are a figure token followed by its numeric parameters, repeated any number of times.

| Token | Figure     | Parameters                                          |
| ----- | ---------- | --------------------------------------------------- |
| `c`   | circle     | `r`                                                 |
| `p`   | pentagon   | `side`                                              |
| `h`   | hexagon    | `side`                                              |
| `q`   | quadrangle | `side angle`, `sideA sideB 90` or `s1 s2 s3 s4 angle` |

A quadrangle becomes a square, rectangle or diamond depending on the sides and the angle.

## Building and running

Run the commands from inside a task directory.

### Java (`zad_1`, `zad_3`)

```
javac geometry/*.java FigureTest.java
java FigureTest c 5 q 4 90 h 3
```

`zad_3` has no `geometry/` package:

```
javac Figure.java FigureTest.java
java FigureTest c 5 q 4 90 h 3
```

### C++ (`zad_2`, `zad_4`)

```
cmake -B build
cmake --build build
./build/FigureTest c 5 q 4 90 h 3
```
