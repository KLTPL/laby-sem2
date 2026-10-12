#include <algorithm> // std::transform (zmiana na małe litery)
#include <cctype>
#include <iomanip> // setw, setprecision
#include <iostream>
#include <memory> // std::unique_ptr
#include <regex>
#include <stdexcept> // std::invalid_argument
#include <string>
#include <vector>

#include "Circle.hpp"
#include "Diamond.hpp"
#include "Hexagon.hpp"
#include "Pentagon.hpp"
#include "Rectangle.hpp"
#include "Square.hpp"

using namespace geometry;

bool isNumeric(const std::string &str);
void validateParams(const std::vector<double> &params);
std::string toLowerCase(std::string str);
std::string getSimpleName(const Figure *fig);
std::unique_ptr<Figure> determineQuadrangle(double s1, double s2, double s3,
                                            double s4, double angle);
std::unique_ptr<Figure> createFigure(const std::string &token,
                                     const std::vector<double> &params);

int main(int argc, char *argv[]) {
  std::vector<std::unique_ptr<Figure>> figureList;

  int i = 1;

  while (i < argc) {
    std::string token = toLowerCase(argv[i]);
    int nextIndex = i + 1;

    std::vector<double> params;
    while (nextIndex < argc && isNumeric(argv[nextIndex])) {
      params.push_back(std::stod(argv[nextIndex])); // string -> double
      nextIndex++;
    }

    try {
      std::unique_ptr<Figure> fig = createFigure(token, params);
      figureList.push_back(std::move(fig));
    } catch (const std::invalid_argument &e) {
      std::cerr << "Blad dla figury '" << token << "': " << e.what()
                << std::endl;
    }

    i = nextIndex;
  }

  std::cout << "\nWYNIKI\n";
  for (const auto &fig : figureList) {
    std::cout << "Nazwa: " << std::left << std::setw(10)
              << getSimpleName(fig.get()) << " | Pole: " << std::right
              << std::setw(6) << std::fixed << std::setprecision(2)
              << fig->calculateArea() << " | Obwod: " << std::right
              << std::setw(6) << std::fixed << std::setprecision(2)
              << fig->calculatePerimeter() << std::endl;
  }

  return 0;
}

std::unique_ptr<Figure> createFigure(const std::string &token,
                                     const std::vector<double> &params) {
  if (token == "c") {
    if (params.size() != 1)
      throw std::invalid_argument("Kolo wymaga 1 parametru");
    validateParams({params[0]});
    return std::make_unique<Circle>(params[0]);
  } else if (token == "p") {
    if (params.size() != 1)
      throw std::invalid_argument("Pieciokat wymaga 1 parametru");
    validateParams({params[0]});
    return std::make_unique<Pentagon>(params[0]);
  } else if (token == "h") {
    if (params.size() != 1)
      throw std::invalid_argument("Szesciokat wymaga 1 parametru");
    validateParams({params[0]});
    return std::make_unique<Hexagon>(params[0]);
  } else if (token == "q") {
    if (params.size() == 2) {
      double side = params[0];
      double angle = params[1];
      validateParams({side, angle});

      if (angle == 90.0) {
        return std::make_unique<Square>(side);
      } else {
        return std::make_unique<Diamond>(side, angle);
      }
    } else if (params.size() == 3) {
      double sideA = params[0];
      double sideB = params[1];
      double angle = params[2];
      validateParams({sideA, sideB, angle});

      if (angle == 90.0) {
        return std::make_unique<Rectangle>(sideA, sideB);
      } else {
        throw std::invalid_argument(
            "Dla 3 parametrow obslugujemy tylko prostokaty wiec kat musi "
            "wynosic 90. Podano: " +
            std::to_string(angle));
      }
    } else if (params.size() == 5) {
      double s1 = params[0], s2 = params[1], s3 = params[2], s4 = params[3];
      double angle = params[4];
      validateParams({s1, s2, s3, s4, angle});

      return determineQuadrangle(s1, s2, s3, s4, angle);
    } else {
      throw std::invalid_argument(
          "Czworokat (q) wymaga 2 parametrow (bok kat) lub 5 parametrow (bok1 "
          "bok2 bok3 bok4 kat). Podano: " +
          std::to_string(params.size()));
    }
  } else {
    throw std::invalid_argument("Nieznana figura: " + token);
  }
}

std::unique_ptr<Figure> determineQuadrangle(double s1, double s2, double s3,
                                            double s4, double angle) {
  if (s1 == s2 && s2 == s3 && s3 == s4) {
    if (angle == 90.0) {
      return std::make_unique<Square>(s1);
    } else {
      return std::make_unique<Diamond>(s1, angle);
    }
  } else if (angle == 90.0 &&
             ((s1 == s2 && s3 == s4) || (s1 == s3 && s2 == s4))) {
    double sideA = s1;
    double sideB = (s1 != s2) ? s2 : s3;
    return std::make_unique<Rectangle>(sideA, sideB);
  } else {
    throw std::invalid_argument("Podane boki i kat nie tworza znanego nam "
                                "kwadratu rombu ani prostokata");
  }
}

bool isNumeric(const std::string &str) {
  static const std::regex pattern("-?\\d+(\\.\\d+)?");
  return std::regex_match(str, pattern);
}

void validateParams(const std::vector<double> &params) {
  for (double param : params) {
    if (param <= 0) {
      throw std::invalid_argument("Wymiary i katy musza byc wieksze od zera");
    }
  }
}

std::string toLowerCase(std::string str) {
  std::transform(str.begin(), str.end(), str.begin(),
                 [](unsigned char c) { return std::tolower(c); });
  return str;
}

std::string getSimpleName(const Figure *fig) {
  if (dynamic_cast<const Circle *>(fig))
    return "Circle";
  if (dynamic_cast<const Pentagon *>(fig))
    return "Pentagon";
  if (dynamic_cast<const Hexagon *>(fig))
    return "Hexagon";
  if (dynamic_cast<const Square *>(fig))
    return "Square";
  if (dynamic_cast<const Rectangle *>(fig))
    return "Rectangle";
  if (dynamic_cast<const Diamond *>(fig))
    return "Diamond";
  return "Figure";
}
