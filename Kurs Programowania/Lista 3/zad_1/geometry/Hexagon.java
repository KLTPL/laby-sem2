package geometry;

public class Hexagon extends Figure {
  private double side;

  public Hexagon(double side) {
    this.side = side;
  }

  @Override
  public double calculateArea() {
    return Math.sqrt(3) * 3 * side * side / 2;
  }

  @Override
  public double calculatePerimeter() {
    return 6 * side;
  }

}
