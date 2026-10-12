#include "Circle.hpp"
#include <cmath>

namespace geometry {
Circle::Circle(double r) : side(r) {}

double Circle::calculateArea() const { return std::acos(-1.0) * side * side; }

double Circle::calculatePerimeter() const {
  return 2.0 * std::acos(-1.0) * side;
}
} // namespace geometry
