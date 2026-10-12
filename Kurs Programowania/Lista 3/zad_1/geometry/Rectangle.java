package geometry;

public class Rectangle extends Quadrangle {
  public Rectangle(double side1, double side2) {
    super(side1, side2, side1, side2, 90.0);
  }

  @Override
  public double calculateArea() {
    return side1 * side2;
  }

  @Override
  public double calculatePerimeter() {
    return 2 * side1 + 2 * side2;
  }
}
