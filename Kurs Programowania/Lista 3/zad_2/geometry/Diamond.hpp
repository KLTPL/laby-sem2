#pragma once
#include "Quadrangle.hpp"

namespace geometry {
class Diamond : public Quadrangle {
public:
  Diamond(double side, double angle);
  double calculateArea() const override;
  double calculatePerimeter() const override;
};
} // namespace geometry
