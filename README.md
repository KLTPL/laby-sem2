# Laby Sem 2

My repo for programming tasks from 2nd semester of Algorithmic Computer Science @ WUST.

## Subjects

```
laby-sem2/
├── README.md
└── Kurs Programowania/    # Programming Course
    ├── Lista 0/           # Java basics
    ├── Lista 1/ ... 2/    # Prime numbers, Pascal's triangle (Java and C++)
    ├── Lista 3/           # Geometric figures (Java and C++)
    ├── Lista 4/           # Pascal's triangle with GUI, Java calling C++
    ├── Lista 5/           # Graphic editor (JavaFX)
    ├── Lista 6/           # Multithreaded simulation (Swing)
    └── Lista 7/           # Generic binary tree, client-server (Java and C++)
```

## Conventions

Each list has its own `README.md` with the task descriptions and build commands.

| Language | Toolchain                                |
| -------- | ---------------------------------------- |
| Java     | `javac` / `java`, `mvn` (Lista 4)        |
| C++      | `g++` / `cmake`                          |

Lists 5 and 7 use JavaFX 17.0.10, which is not stored in the repository.
Download the SDK from <https://gluonhq.com/products/javafx/> and unpack it as
`javafx-sdk-17.0.10/` next to `src/`.

Build artifacts, compiled executables, generated Doxygen output and the JavaFX SDK are git-ignored.
