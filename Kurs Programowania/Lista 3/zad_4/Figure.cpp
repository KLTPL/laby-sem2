#include "Figure.hpp"
#include <cmath>

namespace geometry {

class CircleCalc : public OneParamFigure {
  double calculateArea(double a) const override {
    return std::acos(-1.0) * a * a;
  }
  double calculatePerimeter(double a) const override {
    return 2.0 * std::acos(-1.0) * a;
  }
  std::string getName() const override { return "Circle"; }
};

class SquareCalc : public OneParamFigure {
  double calculateArea(double a) const override { return a * a; }
  double calculatePerimeter(double a) const override { return 4.0 * a; }
  std::string getName() const override { return "Rectangle"; }
};

class PentagonCalc : public OneParamFigure {
  double calculateArea(double a) const override {
    return (std::sqrt(25.0 + 10.0 * std::sqrt(5.0)) / 4.0) * a * a;
  }
  double calculatePerimeter(double a) const override { return 5.0 * a; }
  std::string getName() const override { return "Pentagon"; }
};

class HexagonCalc : public OneParamFigure {
  double calculateArea(double a) const override {
    return (3.0 * std::sqrt(3.0) / 2.0) * a * a;
  }
  double calculatePerimeter(double a) const override { return 6.0 * a; }
  std::string getName() const override { return "Hexagon"; }
};

class RectangleCalc : public TwoParamFigure {
  double calculateArea(double a, double b) const override { return a * b; }
  double calculatePerimeter(double a, double b) const override {
    return 2.0 * a + 2.0 * b;
  }
  std::string getName() const override { return "Rectangle"; }
};

class DiamondCalc : public TwoParamFigure {
  double calculateArea(double a, double angle) const override {
    double rad = angle * std::acos(-1.0) / 180.0;
    return a * a * std::sin(rad);
  }
  double calculatePerimeter(double a, double b) const override {
    return 4.0 * a;
  }
  std::string getName() const override { return "Diamond"; }
};

const OneParamFigure &getCalculator(OneParam type) {
  static const CircleCalc circle;
  static const SquareCalc square;
  static const PentagonCalc pentagon;
  static const HexagonCalc hexagon;

  switch (type) {
  case OneParam::Circle:
    return circle;
  case OneParam::Square:
    return square;
  case OneParam::Pentagon:
    return pentagon;
  case OneParam::Hexagon:
    return hexagon;
  default:
    throw std::invalid_argument("Nieznany typ OneParam");
  }
}

const TwoParamFigure &getCalculator(TwoParam type) {
  static const RectangleCalc rectangle;
  static const DiamondCalc diamond;

  switch (type) {
  case TwoParam::Rectangle:
    return rectangle;
  case TwoParam::Diamond:
    return diamond;
  default:
    throw std::invalid_argument("Nieznany typ TwoParam");
  }
}
} // namespace geometry
