package geometry;

public class Square extends Quadrangle {

  public Square(double side) {
    super(side, side, side, side, 90.0);
  }

  @Override
  public double calculateArea() {
    return side1 * side1;
  }

  @Override
  public double calculatePerimeter() {
    return 4 * side1;
  }
}
