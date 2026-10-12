#include "Square.hpp"

namespace geometry {

Square::Square(double side) : Quadrangle(side, side, side, side, 90.0) {}

double Square::calculateArea() const { return side1 * side1; }

double Square::calculatePerimeter() const { return 4.0 * side1; }
} // namespace geometry
