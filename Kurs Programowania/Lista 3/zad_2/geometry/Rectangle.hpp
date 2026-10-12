#pragma once
#include "Quadrangle.hpp"

namespace geometry {
class Rectangle : public Quadrangle {
public:
  Rectangle(double sideA, double sideB);
  double calculateArea() const override;
  double calculatePerimeter() const override;
};
} // namespace geometry
