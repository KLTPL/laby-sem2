#pragma once
#include <stdexcept>
#include <string>

namespace geometry {

// --- KLASY ABSTRAKCYJNE (Zamiast interfejsów) ---

class OneParamFigure {
public:
  virtual ~OneParamFigure() = default;
  virtual double calculateArea(double a) const = 0;
  virtual double calculatePerimeter(double a) const = 0;
  virtual std::string getName() const = 0;
};

class TwoParamFigure {
public:
  virtual ~TwoParamFigure() = default;
  virtual double calculateArea(double a, double b) const = 0;
  virtual double calculatePerimeter(double a, double b) const = 0;
  virtual std::string getName() const = 0;
};

// --- ENUMY ---

enum class OneParam { Circle, Square, Pentagon, Hexagon };
enum class TwoParam { Rectangle, Diamond };

// --- FUNKCJE ŁĄCZĄCE ENUM Z KLASĄ ABSTRAKCYJNĄ ---

const OneParamFigure &getCalculator(OneParam type);
const TwoParamFigure &getCalculator(TwoParam type);
} // namespace geometry
