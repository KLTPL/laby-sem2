
/**
 * @file MainApp.java
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The main entry point for the app.
 */
public class MainApp extends Application {

  private ShapeManager shapeManager;

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Pane drawingPane = new Pane();

    Rectangle clipRect = new Rectangle();
    clipRect.widthProperty().bind(drawingPane.widthProperty());
    clipRect.heightProperty().bind(drawingPane.heightProperty());
    drawingPane.setClip(clipRect);

    shapeManager = new ShapeManager(drawingPane);

    MenuBar menuBar = createMenuBar(primaryStage);

    root.setTop(menuBar);
    root.setCenter(drawingPane);

    Scene scene = new Scene(root, 800, 600);
    primaryStage.setTitle("Simple Graphic Editor");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * @brief Constructs the menu bar.
   */
  private MenuBar createMenuBar(Stage stage) {
    MenuBar menuBar = new MenuBar();

    Menu fileMenu = new Menu("File");
    MenuItem saveItem = new MenuItem("Save...");
    MenuItem loadItem = new MenuItem("Load...");

    saveItem.setOnAction(e -> FileStorage.save(stage, shapeManager.exportModels()));
    loadItem.setOnAction(e -> shapeManager.importModels(FileStorage.load(stage)));
    fileMenu.getItems().addAll(saveItem, loadItem);

    Menu shapesMenu = new Menu("Shapes");
    MenuItem selectItem = new MenuItem("Select / Edit");
    MenuItem circleItem = new MenuItem("Circle");
    MenuItem rectangleItem = new MenuItem("Rectangle");
    MenuItem polygonItem = new MenuItem("Polygon");

    selectItem.setOnAction(e -> shapeManager.setMode(DrawMode.SELECT));
    circleItem.setOnAction(e -> shapeManager.setMode(DrawMode.CIRCLE));
    rectangleItem.setOnAction(e -> shapeManager.setMode(DrawMode.RECTANGLE));
    polygonItem.setOnAction(e -> shapeManager.setMode(DrawMode.POLYGON));
    shapesMenu.getItems().addAll(selectItem, circleItem, rectangleItem, polygonItem);

    Menu helpMenu = new Menu("Help");
    MenuItem manualItem = new MenuItem("User Manual");
    MenuItem infoItem = new MenuItem("Info");

    manualItem.setOnAction(e -> showManualDialog());
    infoItem.setOnAction(e -> showInfoDialog());
    helpMenu.getItems().addAll(manualItem, infoItem);

    menuBar.getMenus().addAll(fileMenu, shapesMenu, helpMenu);
    return menuBar;
  }

  private void showManualDialog() {
    Alert manualAlert = new Alert(Alert.AlertType.INFORMATION);
    manualAlert.setTitle("User Manual");
    manualAlert.setHeaderText("How to use the editor");
    manualAlert.setContentText(
        "1. DRAWING:\n" +
            "   - Select a shape from the 'Shapes' menu.\n" +
            "   - Circle/Rectangle: Click and drag on the canvas.\n" +
            "   - Polygon: Left-click to add points, Right-click to finish.\n\n" +
            "2. EDITING (Select 'Select / Edit'):\n" +
            "   - Selecting: Click on a shape.\n" +
            "   - Moving: Drag the selected shape.\n" +
            "   - Scaling: Scroll mouse wheel on the selected shape.\n" +
            "   - Rotating: Hold SHIFT + scroll mouse wheel.");
    manualAlert.showAndWait();
  }

  private void showInfoDialog() {
    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle("About Program");
    infoAlert.setHeaderText("Simple Graphic Editor");
    infoAlert.setContentText("Purpose: Creating and editing geometric shapes.\nAuthor: Kacper Lebiedziński");
    infoAlert.showAndWait();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
