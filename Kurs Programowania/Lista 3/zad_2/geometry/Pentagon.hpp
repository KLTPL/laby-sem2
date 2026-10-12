#pragma once
#include "Figure.hpp"

namespace geometry {
class Pentagon : public Figure {
private:
  double side;

public:
  Pentagon(double s);
  double calculateArea() const override;
  double calculatePerimeter() const override;
};
} // namespace geometry
