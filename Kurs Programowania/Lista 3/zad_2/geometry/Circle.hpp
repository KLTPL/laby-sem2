#pragma once
#include "Figure.hpp"

namespace geometry {
class Circle : public Figure {
private:
  double side;

public:
  Circle(double r);
  double calculateArea() const override;
  double calculatePerimeter() const override;
};
} // namespace geometry
