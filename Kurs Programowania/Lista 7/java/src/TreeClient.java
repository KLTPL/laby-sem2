import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The JavaFX graphical client for the Binary Tree Server.
 * Connects to the server, sends commands, parses the serialized tree structure,
 * and visually renders the tree using geometric shapes.
 */
public class TreeClient extends Application {

  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 8080;

  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;

  // UI Components
  private Pane treeDrawingPane;
  private Label statusLabel;
  private ComboBox<String> typeComboBox;
  private TextField inputField;

  // Helper variable for parsing the serialized string
  private int parseIndex = 0;

  /**
   * Temporary class used locally by the client to rebuild the tree structure for
   * drawing.
   */
  private static class UINode {
    String value;
    UINode left, right;

    UINode(String value) {
      this.value = value;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Graphical Binary Tree Client");

    // Top Control Panel
    HBox topPanel = new HBox(10);
    topPanel.setPadding(new Insets(10));
    topPanel.setAlignment(Pos.CENTER_LEFT);

    typeComboBox = new ComboBox<>();
    typeComboBox.getItems().addAll("Integer", "Double", "String", "Product");
    typeComboBox.setValue("Integer");

    typeComboBox.setOnAction(e -> {
      changeTreeType();
      if (typeComboBox.getValue().equals("Product")) {
        inputField.setPromptText("Format: ID-Name-Price (e.g. 105-Laptop-999.99)");
      } else {
        inputField.setPromptText("Enter value...");
      }
    });

    inputField = new TextField();
    inputField.setPromptText("Enter value...");
    inputField.setPrefWidth(320);

    Button insertBtn = new Button("Insert");
    insertBtn.setOnAction(e -> executeCommand("INSERT", inputField.getText()));

    Button deleteBtn = new Button("Delete");
    deleteBtn.setOnAction(e -> executeCommand("DELETE", inputField.getText()));

    Button searchBtn = new Button("Search");
    searchBtn.setOnAction(e -> executeSearch(inputField.getText()));

    Button drawBtn = new Button("Draw / Refresh");
    drawBtn.setOnAction(e -> executeCommand("DRAW", ""));

    topPanel.getChildren().addAll(
        new Label("Type:"), typeComboBox,
        inputField, insertBtn, deleteBtn, searchBtn, drawBtn);

    // Center Drawing Area
    treeDrawingPane = new Pane();
    treeDrawingPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc;");
    ScrollPane scrollPane = new ScrollPane(treeDrawingPane);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);

    // Bottom Status Bar
    statusLabel = new Label("Connecting to server...");
    statusLabel.setPadding(new Insets(5));
    VBox bottomPanel = new VBox(statusLabel);
    bottomPanel.setStyle("-fx-background-color: #e0e0e0;");

    // Main Layout
    BorderPane root = new BorderPane();
    root.setTop(topPanel);
    root.setCenter(scrollPane);
    root.setBottom(bottomPanel);

    Scene scene = new Scene(root, 900, 700);
    primaryStage.setScene(scene);
    primaryStage.setOnCloseRequest(e -> disconnect());
    primaryStage.show();

    connectToServer();
  }

  private void connectToServer() {
    try {
      socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      Platform.runLater(() -> statusLabel.setText("Connected to server at " + SERVER_ADDRESS + ":" + SERVER_PORT));
      changeTreeType();

    } catch (IOException e) {
      Platform.runLater(() -> statusLabel.setText("Connection Error: " + e.getMessage()));
    }
  }

  private void changeTreeType() {
    String selectedType = typeComboBox.getValue();
    String response = sendRequest("SET_TYPE " + selectedType);
    Platform.runLater(() -> {
      statusLabel.setText(response.trim());
      executeCommand("DRAW", "");
    });
  }

  private void executeCommand(String command, String value) {
    if (!command.equals("DRAW") && value.trim().isEmpty()) {
      statusLabel.setText("Please enter a value first.");
      return;
    }

    String request = command + (value.isEmpty() ? "" : " " + value);
    String response = sendRequest(request).trim();

    if (response.startsWith("Error")) {
      Platform.runLater(() -> statusLabel.setText(response));
    } else {
      Platform.runLater(() -> {
        renderTreeGraphics(response);
        statusLabel.setText("Command '" + command + "' executed successfully.");
        inputField.clear();
      });
    }
  }

  private void executeSearch(String value) {
    if (value.trim().isEmpty()) {
      statusLabel.setText("Please enter a value to search.");
      return;
    }
    String response = sendRequest("SEARCH " + value);
    Platform.runLater(() -> statusLabel.setText(response.trim()));
  }

  private String sendRequest(String request) {
    if (out == null || in == null)
      return "Error: Not connected.";
    try {
      out.println(request);
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        if (line.equals("<<END>>"))
          break;
        sb.append(line);
      }
      return sb.toString();
    } catch (IOException e) {
      return "Error communicating with server: " + e.getMessage();
    }
  }

  /**
   * Clears the drawing pane, parses the serialized string, and visually draws the
   * tree.
   *
   * @param serializedData The string from the server (e.g., "5(3()())(8()())").
   */
  private void renderTreeGraphics(String serializedData) {
    treeDrawingPane.getChildren().clear();
    if (serializedData == null || serializedData.isEmpty() || serializedData.equals("()")) {
      return;
    }

    parseIndex = 0;
    UINode rootUI = parseTreeString(serializedData);

    double startX = treeDrawingPane.getWidth() > 0 ? treeDrawingPane.getWidth() / 2 : 450;
    double startY = 50;
    double initialHorizontalGap = 200;

    drawUINode(rootUI, startX, startY, initialHorizontalGap);
  }

  /**
   * Recursive descent parser to convert the serialized string back into a tree
   * structure.
   *
   * @param data The serialized string.
   * @return The root UINode of the parsed tree.
   */
  private UINode parseTreeString(String data) {
    if (parseIndex >= data.length() || data.charAt(parseIndex) == ')') {
      return null;
    }

    if (data.startsWith("()", parseIndex)) {
      parseIndex += 2;
      return null;
    }

    int start = parseIndex;
    while (parseIndex < data.length() && data.charAt(parseIndex) != '(' && data.charAt(parseIndex) != ')') {
      parseIndex++;
    }
    String value = data.substring(start, parseIndex);
    UINode node = new UINode(value);

    if (parseIndex < data.length() && data.charAt(parseIndex) == '(') {
      parseIndex++;
      node.left = parseTreeString(data);
      parseIndex++;
    }

    if (parseIndex < data.length() && data.charAt(parseIndex) == '(') {
      parseIndex++;
      node.right = parseTreeString(data);
      parseIndex++;
    }

    return node;
  }

  /**
   * Recursively draws nodes and connection lines on the JavaFX Pane.
   *
   * @param node The current node to draw.
   * @param x    The X coordinate of this node.
   * @param y    The Y coordinate of this node.
   * @param hGap The horizontal gap to its children (decreases recursively to
   *             prevent overlap).
   */
  private void drawUINode(UINode node, double x, double y, double hGap) {
    if (node == null)
      return;

    double vGap = 60;

    if (node.left != null) {
      Line leftLine = new Line(x, y, x - hGap, y + vGap);
      treeDrawingPane.getChildren().add(leftLine);
      drawUINode(node.left, x - hGap, y + vGap, hGap / 1.8);
    }

    if (node.right != null) {
      Line rightLine = new Line(x, y, x + hGap, y + vGap);
      treeDrawingPane.getChildren().add(rightLine);
      drawUINode(node.right, x + hGap, y + vGap, hGap / 1.8);
    }

    Circle circle = new Circle(x, y, 20);
    circle.setFill(Color.LIGHTBLUE);
    circle.setStroke(Color.BLACK);

    Text text = new Text(node.value);
    text.layoutXProperty().bind(circle.centerXProperty().subtract(text.getLayoutBounds().getWidth() / 2));
    text.layoutYProperty().bind(circle.centerYProperty().add(text.getLayoutBounds().getHeight() / 4));

    treeDrawingPane.getChildren().addAll(circle, text);
  }

  private void disconnect() {
    try {
      if (socket != null && !socket.isClosed())
        socket.close();
    } catch (IOException e) {
      System.err.println("Error closing connection: " + e.getMessage());
    }
  }
}
