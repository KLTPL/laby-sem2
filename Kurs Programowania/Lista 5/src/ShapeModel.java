
/**
 * @file ShapeModel.java
 */

import java.io.Serializable;

/**
 * Class for serializing geometric shapes.
 */
public class ShapeModel implements Serializable {
  private static final long serialVersionUID = 1L;

  public String type;
  public double x, y, width, height, radius;
  public double[] points;
  public double translateX, translateY, rotate, scaleX, scaleY;
  public String colorHex;

  public ShapeModel() {
  }
}
