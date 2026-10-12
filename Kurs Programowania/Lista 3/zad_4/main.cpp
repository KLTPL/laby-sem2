#include <algorithm>
#include <cctype>
#include <iomanip>
#include <iostream>
#include <regex>
#include <stdexcept>
#include <string>
#include <vector>

#include "Figure.hpp"

using namespace geometry;

struct FigureResult {
  std::string name;
  double area;
  double perimeter;
};

bool isNumeric(const std::string &str);
void validateParams(const std::vector<double> &params);
std::string toLowerCase(std::string str);
FigureResult calculateFigure(const std::string &token,
                             const std::vector<double> &params);
FigureResult determineQuadrangleWithEnum(double s1, double s2, double s3,
                                         double s4, double angle);

int main(int argc, char *argv[]) {
  std::vector<FigureResult> results;
  int i = 1;

  while (i < argc) {
    std::string token = toLowerCase(argv[i]);
    int nextIndex = i + 1;

    std::vector<double> params;
    while (nextIndex < argc && isNumeric(argv[nextIndex])) {
      params.push_back(std::stod(argv[nextIndex]));
      nextIndex++;
    }

    try {
      FigureResult res = calculateFigure(token, params);
      results.push_back(res);
    } catch (const std::invalid_argument &e) {
      std::cerr << "Blad dla figury '" << token << "': " << e.what()
                << std::endl;
    }

    i = nextIndex;
  }

  std::cout << "\nWYNIKI\n";
  for (const auto &res : results) {
    std::cout << "Nazwa: " << std::left << std::setw(10) << res.name
              << " | Pole: " << std::right << std::setw(6) << std::fixed
              << std::setprecision(2) << res.area << " | Obwod: " << std::right
              << std::setw(6) << std::fixed << std::setprecision(2)
              << res.perimeter << std::endl;
  }

  return 0;
}

FigureResult calculateFigure(const std::string &token,
                             const std::vector<double> &params) {
  if (token == "c") {
    if (params.size() != 1)
      throw std::invalid_argument("Kolo wymaga 1 parametru.");
    validateParams({params[0]});

    auto &calc = getCalculator(OneParam::Circle);
    return {calc.getName(), calc.calculateArea(params[0]),
            calc.calculatePerimeter(params[0])};
  } else if (token == "p") {
    if (params.size() != 1)
      throw std::invalid_argument("Pieciokat wymaga 1 parametru.");
    validateParams({params[0]});

    auto &calc = getCalculator(OneParam::Pentagon);
    return {calc.getName(), calc.calculateArea(params[0]),
            calc.calculatePerimeter(params[0])};
  } else if (token == "h") {
    if (params.size() != 1)
      throw std::invalid_argument("Szesciokat wymaga 1 parametru.");
    validateParams({params[0]});

    auto &calc = getCalculator(OneParam::Hexagon);
    return {calc.getName(), calc.calculateArea(params[0]),
            calc.calculatePerimeter(params[0])};
  } else if (token == "q") {
    if (params.size() == 2) {
      double side = params[0], angle = params[1];
      validateParams({side, angle});

      if (angle == 90.0) {
        auto &calc = getCalculator(OneParam::Square);
        return {calc.getName(), calc.calculateArea(side),
                calc.calculatePerimeter(side)};
      } else {
        auto &calc = getCalculator(TwoParam::Diamond);
        return {calc.getName(), calc.calculateArea(side, angle),
                calc.calculatePerimeter(side, angle)};
      }
    } else if (params.size() == 3) {
      double sideA = params[0], sideB = params[1], angle = params[2];
      validateParams({sideA, sideB, angle});

      if (angle == 90.0) {
        auto &calc = getCalculator(TwoParam::Rectangle);
        return {calc.getName(), calc.calculateArea(sideA, sideB),
                calc.calculatePerimeter(sideA, sideB)};
      } else {
        throw std::invalid_argument("Dla 3 parametrow kat musi wynosic 90.");
      }
    } else if (params.size() == 5) {
      double s1 = params[0], s2 = params[1], s3 = params[2], s4 = params[3],
             angle = params[4];
      validateParams({s1, s2, s3, s4, angle});
      return determineQuadrangleWithEnum(s1, s2, s3, s4, angle);
    } else {
      throw std::invalid_argument("Czworokat wymaga 2, 3 lub 5 parametrow.");
    }
  }
  throw std::invalid_argument("Nieznana figura: " + token);
}

FigureResult determineQuadrangleWithEnum(double s1, double s2, double s3,
                                         double s4, double angle) {
  if (s1 == s2 && s2 == s3 && s3 == s4) {
    if (angle == 90.0) {
      auto &calc = getCalculator(OneParam::Square);
      return {calc.getName(), calc.calculateArea(s1),
              calc.calculatePerimeter(s1)};
    } else {
      auto &calc = getCalculator(TwoParam::Diamond);
      return {calc.getName(), calc.calculateArea(s1, angle),
              calc.calculatePerimeter(s1, angle)};
    }
  } else if (angle == 90.0 &&
             ((s1 == s2 && s3 == s4) || (s1 == s3 && s2 == s4))) {
    double sideA = s1;
    double sideB = (s1 != s2) ? s2 : s3;

    auto &calc = getCalculator(TwoParam::Rectangle);
    return {calc.getName(), calc.calculateArea(sideA, sideB),
            calc.calculatePerimeter(sideA, sideB)};
  } else {
    throw std::invalid_argument(
        "Parametry nie tworza prostokata, rombu ani kwadratu.");
  }
}

bool isNumeric(const std::string &str) {
  static const std::regex pattern("-?\\d+(\\.\\d+)?");
  return std::regex_match(str, pattern);
}

void validateParams(const std::vector<double> &params) {
  for (double param : params) {
    if (param <= 0)
      throw std::invalid_argument("Wymiary/katy musza byc > 0");
  }
}

std::string toLowerCase(std::string str) {
  std::transform(str.begin(), str.end(), str.begin(),
                 [](unsigned char c) { return std::tolower(c); });
  return str;
}
