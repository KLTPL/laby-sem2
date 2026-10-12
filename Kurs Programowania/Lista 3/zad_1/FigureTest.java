import geometry.*;
import java.util.ArrayList;
import java.util.List;

public class FigureTest {

  public static void main(String[] args) {
    List<Figure> figureList = new ArrayList<>();
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
        Figure fig = createFigure(token, params);
        figureList.add(fig);
      } catch (IllegalArgumentException e) {
        System.err.println("Błąd dla figury '" + token + "': " + e.getMessage());
      }

      i = nextIndex;
    }

    System.out.println("\nWYNIKI");
    for (Figure fig : figureList) {
      System.out.printf("Nazwa: %-10s | Pole: %6.2f | Obwód: %6.2f%n",
          fig.getClass().getSimpleName(),
          fig.calculateArea(),
          fig.calculatePerimeter());
    }
  }

  private static Figure createFigure(String token, List<Double> params) {
    switch (token) {
      case "c":
        if (params.size() != 1)
          throw new IllegalArgumentException("Koło wymaga 1 parametru");
        validateParams(params.get(0));
        return new Circle(params.get(0));

      case "p":
        if (params.size() != 1)
          throw new IllegalArgumentException("Pięciokąt wymaga 1 parametru");
        validateParams(params.get(0));
        return new Pentagon(params.get(0));

      case "h":
        if (params.size() != 1)
          throw new IllegalArgumentException("Sześciokąt wymaga 1 parametru");
        validateParams(params.get(0));
        return new Hexagon(params.get(0));

      case "q":
        if (params.size() == 2) {
          double side = params.get(0);
          double angle = params.get(1);
          validateParams(side, angle);

          if (angle == 90.0) {
            return new Square(side);
          } else {
            return new Diamond(side, angle);
          }

        } else if (params.size() == 3) {
          double sideA = params.get(0);
          double sideB = params.get(1);
          double angle = params.get(2);
          validateParams(sideA, sideB, angle);

          if (angle == 90.0) {
            return new Rectangle(sideA, sideB);
          } else {
            throw new IllegalArgumentException(
                "Dla 3 parametrów obsługujemy tylko prostokąty więc kąt musi wynosić 90. Podano: " + angle);
          }

        } else if (params.size() == 5) {
          double s1 = params.get(0), s2 = params.get(1), s3 = params.get(2), s4 = params.get(3);
          double angle = params.get(4);
          validateParams(s1, s2, s3, s4, angle);

          return determineQuadrangle(s1, s2, s3, s4, angle);

        } else {
          throw new IllegalArgumentException(
              "Czworokąt (q) wymaga 2 parametrów (bok kąt) lub 5 parametrów (bok1 bok2 bok3 bok4 kąt). Podano: "
                  + params.size());
        }

      default:
        throw new IllegalArgumentException("Nieznana figura: " + token);
    }
  }

  private static Figure determineQuadrangle(double s1, double s2, double s3, double s4, double angle) {
    if (s1 == s2 && s2 == s3 && s3 == s4) {
      if (angle == 90.0) {
        return new Square(s1);
      } else {
        return new Diamond(s1, angle);
      }
    } else if (angle == 90.0 && ((s1 == s2 && s3 == s4) || (s1 == s3 && s2 == s4))) {
      double sideA = s1;
      double sideB = (s1 != s2) ? s2 : s3;
      return new Rectangle(sideA, sideB);
    } else {
      throw new IllegalArgumentException("Podane boki i kąt nie tworzą znanego nam kwadratu rombu ani prostokąta");
    }
  }

  private static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }

  private static void validateParams(double... params) {
    for (double param : params) {
      if (param <= 0) {
        throw new IllegalArgumentException("Wymiary i kąty muszą być większe od zera");
      }
    }
  }
}
