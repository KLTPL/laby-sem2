package geometry;

public abstract class Quadrangle extends Figure {
  protected double side1;
  protected double side2;
  protected double side3;
  protected double side4;
  protected double angle;

  public Quadrangle(double side1, double side2, double side3, double side4, double angle) {
    this.side1 = side1;
    this.side2 = side2;
    this.side3 = side3;
    this.side4 = side4;
    this.angle = angle;
  }
}
