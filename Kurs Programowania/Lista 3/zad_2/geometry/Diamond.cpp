#include "Diamond.hpp"
#include <cmath>

namespace geometry {
Diamond::Diamond(double side, double a)
    : Quadrangle(side, side, side, side, a) {}

double Diamond::calculateArea() const {

  double angleInRadians = angle * std::acos(-1.0) / 180.0;
  return side1 * side1 * std::sin(angleInRadians);
}

double Diamond::calculatePerimeter() const { return 4.0 * side1; }
} // namespace geometry
