package geometry;

public class Pentagon extends Figure {
  private double side;

  public Pentagon(double side) {
    this.side = side;
  }

  @Override
  public double calculateArea() {
    return 1 / 4 * Math.sqrt(5 * (5 + 2 * Math.sqrt(5)) * side * side);
  }

  @Override
  public double calculatePerimeter() {
    return 5 * side;
  }
}
