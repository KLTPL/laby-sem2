#pragma once
#include "Shape.hpp"

namespace geometry {

class Figure : public Shape {
public:
  virtual ~Figure() = default;
};
} // namespace geometry
