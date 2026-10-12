#include "Hexagon.hpp"
#include <cmath>

namespace geometry {
Hexagon::Hexagon(double s) : side(s) {}

double Hexagon::calculateArea() const {
  return (3.0 * std::sqrt(3.0) / 2.0) * side * side;
}

double Hexagon::calculatePerimeter() const { return 6.0 * side; }
} // namespace geometry
