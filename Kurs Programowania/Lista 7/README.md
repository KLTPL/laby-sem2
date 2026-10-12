# Lista 7

## Tasks

Generic binary search tree for `Integer`, `Double`, `String` and a custom `Product` type.

| Directory | Description                                                              |
| --------- | ------------------------------------------------------------------------ |
| `cpp/`    | C++ template `BinaryTree<T>` with an interactive console menu            |
| `java/`   | Java `BinaryTree<T>` served over a socket, with a JavaFX client drawing the tree |

`Product` is entered as `<ID> <Name> <Price>` and ordered by its ID.

## Building and running

### C++

```
cd cpp
g++ src/main.cpp -o tree_app
./tree_app
```

Pick a tree type, then use `insert`, `search`, `delete`, `draw` and `back`.

### Java

JavaFX SDK 17.0.10 is required in `java/javafx-sdk-17.0.10/` (see the root `README.md`).
Run the commands from `java/`.

```
javac -d out --module-path javafx-sdk-17.0.10/lib --add-modules javafx.controls src/*.java
```

Start the server (port 8080), then one or more clients in separate terminals:

```
java -cp out --module-path javafx-sdk-17.0.10/lib --add-modules javafx.controls TreeServer
java -cp out --module-path javafx-sdk-17.0.10/lib --add-modules javafx.controls TreeClient
```

The clients share the same trees on the server.

### Documentation

```
javadoc -d docs/javadoc --module-path javafx-sdk-17.0.10/lib --add-modules javafx.controls src/*.java
doxygen Doxyfile
```
