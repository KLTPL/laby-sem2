public class Figure {
  public interface OneParamFigure {
    double calculateArea(double a);

    double calculatePerimeter(double a);

    String getName();
  }

  public interface TwoParamFigure {
    double calculateArea(double a, double b);

    double calculatePerimeter(double a, double b);

    String getName();
  }

  public enum OneParam implements OneParamFigure {
    CIRCLE {
      @Override
      public double calculateArea(double r) {
        return Math.PI * r * r;
      }

      @Override
      public double calculatePerimeter(double r) {
        return 2 * Math.PI * r;
      }

      @Override
      public String getName() {
        return "Circle";
      }
    },
    SQUARE {
      @Override
      public double calculateArea(double side) {
        return side * side;
      }

      @Override
      public double calculatePerimeter(double side) {
        return 4 * side;
      }

      @Override
      public String getName() {
        return "Rectangle";
      }
    },
    PENTAGON {
      @Override
      public double calculateArea(double side) {
        return (Math.sqrt(25 + 10 * Math.sqrt(5)) / 4) * side * side;
      }

      @Override
      public double calculatePerimeter(double side) {
        return 5 * side;
      }

      @Override
      public String getName() {
        return "Pentagon";
      }
    },
    HEXAGON {
      @Override
      public double calculateArea(double side) {
        return (3 * Math.sqrt(3) / 2) * side * side;
      }

      @Override
      public double calculatePerimeter(double side) {
        return 6 * side;
      }

      @Override
      public String getName() {
        return "Hexagon";
      }
    }
  }

  public enum TwoParam implements TwoParamFigure {
    RECTANGLE {
      @Override
      public double calculateArea(double a, double b) {
        return a * b;
      }

      @Override
      public double calculatePerimeter(double a, double b) {
        return 2 * a + 2 * b;
      }

      @Override
      public String getName() {
        return "Rectangle";
      }
    },
    DIAMOND {
      @Override
      public double calculateArea(double side, double angle) {
        return side * side * Math.sin(Math.toRadians(angle));
      }

      @Override
      public double calculatePerimeter(double side, double angle) {
        return 4 * side;
      }

      @Override
      public String getName() {
        return "Diamond";
      }
    }
  }
}
