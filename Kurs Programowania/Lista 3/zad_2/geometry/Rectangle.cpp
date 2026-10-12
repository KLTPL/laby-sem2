#include "Rectangle.hpp"

namespace geometry {
Rectangle::Rectangle(double sideA, double sideB)
    : Quadrangle(sideA, sideB, sideA, sideB, 90.0) {}

double Rectangle::calculateArea() const { return side1 * side2; }

double Rectangle::calculatePerimeter() const {
  return 2.0 * side1 + 2.0 * side2;
}
} // namespace geometry
