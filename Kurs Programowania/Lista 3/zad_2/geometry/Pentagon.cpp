#include "Pentagon.hpp"
#include <cmath>

namespace geometry {
Pentagon::Pentagon(double s) : side(s) {}

double Pentagon::calculateArea() const {
  return (std::sqrt(25.0 + 10.0 * std::sqrt(5.0)) / 4.0) * side * side;
}

double Pentagon::calculatePerimeter() const { return 5.0 * side; }
} // namespace geometry
