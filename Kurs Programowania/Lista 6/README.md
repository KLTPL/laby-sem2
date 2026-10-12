# Lista 6

## Tasks

Multithreaded simulation in Swing.

- A grid of fields, each one driven by its own thread that changes its colour periodically.
- Fields on the edges communicate with the opposite edge (torus topology).
- Clicking a field pauses and resumes its thread.
- Rows and columns can be added while the simulation is running.

Sources are in `src/`, generated documentation is in `docs/javadoc/`.

## Building and running

Run the commands from this directory.

```
javac -d out src/*.java
java -cp out Main
```

### Documentation

```
javadoc -d docs/javadoc src/*.java
doxygen Doxyfile
```
