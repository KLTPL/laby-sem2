#pragma once
#include "Quadrangle.hpp"

namespace geometry {
class Square : public Quadrangle {
public:
  Square(double side);
  double calculateArea() const override;
  double calculatePerimeter() const override;
};
} // namespace geometry
