import java.util.ArrayList;
import java.util.List;

public class FigureTest {
  private static class FigureResult {
    String name;
    double area;
    double perimeter;

    FigureResult(String name, double area, double perimeter) {
      this.name = name;
      this.area = area;
      this.perimeter = perimeter;
    }
  }

  public static void main(String[] args) {
    List<FigureResult> results = new ArrayList<>();
    int i = 0;

    while (i < args.length) {
      String token = args[i].toLowerCase();
      int nextIndex = i + 1;

      List<Double> params = new ArrayList<>();
      while (nextIndex < args.length && isNumeric(args[nextIndex])) {
        params.add(Double.parseDouble(args[nextIndex]));
        nextIndex++;
      }

      try {
        FigureResult result = calculateFigure(token, params);
        results.add(result);
      } catch (IllegalArgumentException e) {
        System.err.println("Błąd dla figury '" + token + "': " + e.getMessage());
      }

      i = nextIndex;
    }

    System.out.println("\nWYNIKI");
    for (FigureResult res : results) {
      System.out.printf("Nazwa: %-10s | Pole: %6.2f | Obwód: %6.2f%n",
          res.name, res.area, res.perimeter);
    }
  }

  private static FigureResult calculateFigure(String token, List<Double> params) {
    switch (token) {
      case "c":
        if (params.size() != 1)
          throw new IllegalArgumentException("Koło wymaga 1 parametru.");
        validateParams(params.get(0));

        return new FigureResult(
            Figure.OneParam.CIRCLE.getName(),
            Figure.OneParam.CIRCLE.calculateArea(params.get(0)),
            Figure.OneParam.CIRCLE.calculatePerimeter(params.get(0)));

      case "p":
        if (params.size() != 1)
          throw new IllegalArgumentException("Pięciokąt wymaga 1 parametru.");
        validateParams(params.get(0));
        return new FigureResult(
            Figure.OneParam.PENTAGON.getName(),
            Figure.OneParam.PENTAGON.calculateArea(params.get(0)),
            Figure.OneParam.PENTAGON.calculatePerimeter(params.get(0)));

      case "h":
        if (params.size() != 1)
          throw new IllegalArgumentException("Sześciokąt wymaga 1 parametru.");
        validateParams(params.get(0));
        return new FigureResult(
            Figure.OneParam.HEXAGON.getName(),
            Figure.OneParam.HEXAGON.calculateArea(params.get(0)),
            Figure.OneParam.HEXAGON.calculatePerimeter(params.get(0)));

      case "q":
        if (params.size() == 2) {
          double side = params.get(0);
          double angle = params.get(1);
          validateParams(side, angle);

          if (angle == 90.0) {
            return new FigureResult(
                Figure.OneParam.SQUARE.getName(),
                Figure.OneParam.SQUARE.calculateArea(side),
                Figure.OneParam.SQUARE.calculatePerimeter(side));
          } else {
            return new FigureResult(
                Figure.TwoParam.DIAMOND.getName(),
                Figure.TwoParam.DIAMOND.calculateArea(side, angle),
                Figure.TwoParam.DIAMOND.calculatePerimeter(side, angle));
          }
        } else if (params.size() == 3) {
          double sideA = params.get(0), sideB = params.get(1), angle = params.get(2);
          validateParams(sideA, sideB, angle);

          if (angle == 90.0) {
            return new FigureResult(
                Figure.TwoParam.RECTANGLE.getName(),
                Figure.TwoParam.RECTANGLE.calculateArea(sideA, sideB),
                Figure.TwoParam.RECTANGLE.calculatePerimeter(sideA, sideB));
          } else {
            throw new IllegalArgumentException("Dla 3 parametrów (prostokąt) kąt musi wynosić 90.");
          }
        } else if (params.size() == 5) {
          double s1 = params.get(0), s2 = params.get(1), s3 = params.get(2), s4 = params.get(3);
          double angle = params.get(4);
          validateParams(s1, s2, s3, s4, angle);

          return determineQuadrangleWithEnum(s1, s2, s3, s4, angle);
        } else {
          throw new IllegalArgumentException("Czworokąt (q) wymaga 2, 3 lub 5 parametrów.");
        }

      default:
        throw new IllegalArgumentException("Nieznana figura: " + token);
    }
  }

  private static FigureResult determineQuadrangleWithEnum(double s1, double s2, double s3, double s4, double angle) {
    if (s1 == s2 && s2 == s3 && s3 == s4) {
      if (angle == 90.0) {
        return new FigureResult(
            Figure.OneParam.SQUARE.getName(),
            Figure.OneParam.SQUARE.calculateArea(s1),
            Figure.OneParam.SQUARE.calculatePerimeter(s1));
      } else {
        return new FigureResult(
            Figure.TwoParam.DIAMOND.getName(),
            Figure.TwoParam.DIAMOND.calculateArea(s1, angle),
            Figure.TwoParam.DIAMOND.calculatePerimeter(s1, angle));
      }
    } else if (angle == 90.0 && ((s1 == s2 && s3 == s4) || (s1 == s3 && s2 == s4))) {
      double sideA = s1;
      double sideB = (s1 != s2) ? s2 : s3;
      return new FigureResult(
          Figure.TwoParam.RECTANGLE.getName(),
          Figure.TwoParam.RECTANGLE.calculateArea(sideA, sideB),
          Figure.TwoParam.RECTANGLE.calculatePerimeter(sideA, sideB));
    } else {
      throw new IllegalArgumentException("Podane parametry nie tworzą kwadratu, rombu ani prostokąta.");
    }
  }

  private static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }

  private static void validateParams(double... params) {
    for (double param : params) {
      if (param <= 0) {
        throw new IllegalArgumentException("Wymiary i kąty muszą być większe od zera!");
      }
    }
  }
}
