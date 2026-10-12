# Lista 5

## Tasks

Simple graphic editor in JavaFX.

- Draw circles, rectangles and polygons from the `Shapes` menu.
- Select, move, scale (mouse wheel) and rotate (`Shift` + mouse wheel) shapes.
- Save and load the drawing with `File`.

Sources are in `src/`, generated documentation is in `docs/javadoc/`.

## Building and running

JavaFX SDK 17.0.10 is required in `javafx-sdk-17.0.10/` (see the root `README.md`).
Run the commands from this directory.

```
javac -d out --module-path javafx-sdk-17.0.10/lib --add-modules javafx.controls src/*.java
java -cp out --module-path javafx-sdk-17.0.10/lib --add-modules javafx.controls MainApp
```

### Documentation

```
javadoc -d docs/javadoc --module-path javafx-sdk-17.0.10/lib --add-modules javafx.controls src/*.java
doxygen Doxyfile
```
