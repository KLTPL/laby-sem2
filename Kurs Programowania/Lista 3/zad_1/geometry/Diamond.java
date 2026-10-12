package geometry;

public class Diamond extends Quadrangle {
  public Diamond(double side, double angle) {
    super(side, side, side, side, angle);
  }

  @Override
  public double calculateArea() {
    double angleInRadians = Math.toRadians(angle);
    return side1 * side1 * Math.sin(angleInRadians);
  }

  @Override
  public double calculatePerimeter() {
    return 4 * side1;
  }
}
