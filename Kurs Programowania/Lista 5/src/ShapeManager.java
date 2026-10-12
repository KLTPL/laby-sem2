import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages drawing logic, shape manipulation, and canvas events.
 */
public class ShapeManager {

  private final Pane drawingPane;
  private DrawMode currentMode = DrawMode.NONE;

  private Shape currentShape;
  private Shape activeShape;

  private double startX;
  private double startY;
  private double dragOffsetX;
  private double dragOffsetY;

  public ShapeManager(Pane drawingPane) {
    this.drawingPane = drawingPane;
    this.drawingPane.setOnMousePressed(this::handleMousePressed);
    this.drawingPane.setOnMouseDragged(this::handleMouseDragged);
    this.drawingPane.setOnMouseReleased(this::handleMouseReleased);
  }

  public void setMode(DrawMode mode) {
    this.currentMode = mode;
    if (mode != DrawMode.SELECT) {
      setActiveShape(null);
    }
  }

  private void setActiveShape(Shape shape) {
    if (activeShape != null) {
      activeShape.setStrokeWidth(1);
      activeShape.setStroke(Color.BLACK);
      activeShape.getStrokeDashArray().clear();
    }

    activeShape = shape;

    if (activeShape != null) {
      activeShape.setStrokeWidth(3);
      activeShape.setStroke(Color.RED);
      activeShape.getStrokeDashArray().addAll(10d, 5d);
      activeShape.toFront();
    }
  }

  private void setupShapeEvents(Shape shape) {
    shape.setOnMousePressed(e -> {
      if (currentMode == DrawMode.SELECT) {
        setActiveShape(shape);
        dragOffsetX = e.getSceneX() - shape.getTranslateX();
        dragOffsetY = e.getSceneY() - shape.getTranslateY();
        e.consume();
      }
    });

    shape.setOnMouseDragged(e -> {
      if (currentMode == DrawMode.SELECT && activeShape == shape) {
        shape.setTranslateX(e.getSceneX() - dragOffsetX);
        shape.setTranslateY(e.getSceneY() - dragOffsetY);
        e.consume();
      }
    });

    shape.setOnScroll(e -> {
      if (currentMode == DrawMode.SELECT && activeShape == shape) {
        double delta = e.getDeltaY() == 0.0 ? e.getDeltaX() : e.getDeltaY();
        if (delta == 0.0)
          return;

        if (e.isShiftDown()) {
          shape.setRotate(shape.getRotate() + Math.signum(delta) * 10.0);
        } else {
          double scaleFactor = delta > 0 ? 1.1 : 0.9;
          shape.setScaleX(shape.getScaleX() * scaleFactor);
          shape.setScaleY(shape.getScaleY() * scaleFactor);
        }
        e.consume();
      }
    });

    ContextMenu contextMenu = new ContextMenu();
    ColorPicker colorPicker = new ColorPicker();
    CustomMenuItem colorItem = new CustomMenuItem(colorPicker);
    colorItem.setHideOnClick(false);
    contextMenu.getItems().add(colorItem);

    colorPicker.setOnAction(event -> {
      if (activeShape != null) {
        activeShape.setFill(colorPicker.getValue());
      }
    });

    shape.setOnContextMenuRequested(e -> {
      if (currentMode == DrawMode.SELECT && activeShape == shape) {
        if (shape.getFill() instanceof Color) {
          colorPicker.setValue((Color) shape.getFill());
        }
        contextMenu.show(shape, e.getScreenX(), e.getScreenY());
        e.consume();
      }
    });
  }

  private void handleMousePressed(MouseEvent e) {
    if (currentMode == DrawMode.NONE)
      return;

    if (currentMode == DrawMode.SELECT) {
      setActiveShape(null);
      return;
    }

    startX = e.getX();
    startY = e.getY();

    if (currentMode == DrawMode.POLYGON) {
      if (e.getButton() == MouseButton.SECONDARY) {
        currentShape = null;
        return;
      }

      if (!(currentShape instanceof Polygon)) {
        Polygon polygon = new Polygon();
        polygon.setFill(Color.LIGHTCORAL);
        polygon.setStroke(Color.BLACK);
        polygon.getPoints().addAll(startX, startY);
        setupShapeEvents(polygon);
        currentShape = polygon;
        drawingPane.getChildren().add(currentShape);
      } else {
        ((Polygon) currentShape).getPoints().addAll(startX, startY);
      }
      return;
    }

    if (e.getButton() != MouseButton.PRIMARY)
      return;

    if (currentMode == DrawMode.RECTANGLE) {
      Rectangle rect = new Rectangle(startX, startY, 0, 0);
      rect.setFill(Color.LIGHTBLUE);
      rect.setStroke(Color.BLACK);
      setupShapeEvents(rect);
      currentShape = rect;
      drawingPane.getChildren().add(currentShape);
    } else if (currentMode == DrawMode.CIRCLE) {
      Circle circle = new Circle(startX, startY, 0);
      circle.setFill(Color.LIGHTGREEN);
      circle.setStroke(Color.BLACK);
      setupShapeEvents(circle);
      currentShape = circle;
      drawingPane.getChildren().add(currentShape);
    }
  }

  private void handleMouseDragged(MouseEvent e) {
    if (currentShape == null || currentMode == DrawMode.POLYGON || currentMode == DrawMode.SELECT)
      return;

    double currentX = e.getX();
    double currentY = e.getY();

    if (currentMode == DrawMode.RECTANGLE && currentShape instanceof Rectangle) {
      Rectangle rect = (Rectangle) currentShape;
      rect.setWidth(Math.abs(currentX - startX));
      rect.setHeight(Math.abs(currentY - startY));
      rect.setX(Math.min(startX, currentX));
      rect.setY(Math.min(startY, currentY));
    } else if (currentMode == DrawMode.CIRCLE && currentShape instanceof Circle) {
      Circle circle = (Circle) currentShape;
      circle.setRadius(Math.sqrt(Math.pow(currentX - startX, 2) + Math.pow(currentY - startY, 2)));
    }
  }

  private void handleMouseReleased(MouseEvent e) {
    if (currentMode != DrawMode.POLYGON) {
      currentShape = null;
    }
  }

  /**
   * @brief Extracts serializable models from the current canvas shapes.
   * @return A list of ShapeModel objects.
   */
  public List<ShapeModel> exportModels() {
    List<ShapeModel> models = new ArrayList<>();
    for (var node : drawingPane.getChildren()) {
      if (node instanceof Shape) {
        Shape s = (Shape) node;
        ShapeModel sm = new ShapeModel();

        sm.translateX = s.getTranslateX();
        sm.translateY = s.getTranslateY();
        sm.rotate = s.getRotate();
        sm.scaleX = s.getScaleX();
        sm.scaleY = s.getScaleY();
        sm.colorHex = s.getFill().toString();

        if (s instanceof Circle) {
          sm.type = "CIRCLE";
          sm.x = ((Circle) s).getCenterX();
          sm.y = ((Circle) s).getCenterY();
          sm.radius = ((Circle) s).getRadius();
        } else if (s instanceof Rectangle) {
          sm.type = "RECTANGLE";
          sm.x = ((Rectangle) s).getX();
          sm.y = ((Rectangle) s).getY();
          sm.width = ((Rectangle) s).getWidth();
          sm.height = ((Rectangle) s).getHeight();
        } else if (s instanceof Polygon) {
          sm.type = "POLYGON";
          Polygon p = (Polygon) s;
          sm.points = new double[p.getPoints().size()];
          for (int i = 0; i < p.getPoints().size(); i++) {
            sm.points[i] = p.getPoints().get(i);
          }
        }
        models.add(sm);
      }
    }
    return models;
  }

  /**
   * @brief Reconstructs shapes from models and renders them on the canvas.
   * @param models The list of ShapeModel objects to load.
   */
  public void importModels(List<ShapeModel> models) {
    if (models == null)
      return;

    drawingPane.getChildren().clear();
    setActiveShape(null);

    for (ShapeModel sm : models) {
      Shape s = null;
      if ("CIRCLE".equals(sm.type)) {
        s = new Circle(sm.x, sm.y, sm.radius);
      } else if ("RECTANGLE".equals(sm.type)) {
        s = new Rectangle(sm.x, sm.y, sm.width, sm.height);
      } else if ("POLYGON".equals(sm.type)) {
        Polygon p = new Polygon();
        for (double val : sm.points)
          p.getPoints().add(val);
        s = p;
      }

      if (s != null) {
        s.setTranslateX(sm.translateX);
        s.setTranslateY(sm.translateY);
        s.setRotate(sm.rotate);
        s.setScaleX(sm.scaleX);
        s.setScaleY(sm.scaleY);
        s.setFill(Color.valueOf(sm.colorHex));
        s.setStroke(Color.BLACK);

        setupShapeEvents(s);
        drawingPane.getChildren().add(s);
      }
    }
  }
}
