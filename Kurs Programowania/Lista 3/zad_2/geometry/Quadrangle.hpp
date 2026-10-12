#pragma once
#include "Figure.hpp"

namespace geometry {
class Quadrangle : public Figure {
protected:
  double side1;
  double side2;
  double side3;
  double side4;
  double angle;

public:
  Quadrangle(double s1, double s2, double s3, double s4, double a);
  virtual ~Quadrangle() = default;
};
} // namespace geometry
