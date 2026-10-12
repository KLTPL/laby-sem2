#include <iostream>
#include <string>
#include <stdexcept>
#include "PascalTriangleRow.hpp"

int main(int argc, char* argv[]) {
    if (argc < 2) {
        return 0;
    }
    long long n;
    try {
        n = std::stoi(argv[1]); 
        if (n < 0) {
            std::cout << argv[1] << " - nieprawidlowy numer wiersza\n";
            return 0;
        }
    } 
    catch (const std::invalid_argument&) {
        std::cout << argv[1] << " - nieprawidlowa dana\n";
        return 0;
    }
    PascalTriangleRow pascal(n-1);
    pascal.fill();
    for (long long i = 0; i <= n-1; i++) {
        std::cout << pascal.row[i] << " ";
    }
    return 0;
}