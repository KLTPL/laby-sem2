#pragma once
#include "Figure.hpp"

namespace geometry {
class Hexagon : public Figure {
private:
  double side;

public:
  Hexagon(double s);
  double calculateArea() const override;
  double calculatePerimeter() const override;
};
} // namespace geometry
