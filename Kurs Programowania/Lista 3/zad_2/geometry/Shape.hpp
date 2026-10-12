#pragma once

namespace geometry {
class Shape {
public:
  virtual ~Shape() = default;

  virtual double calculateArea() const = 0;
  virtual double calculatePerimeter() const = 0;
};
} // namespace geometry
