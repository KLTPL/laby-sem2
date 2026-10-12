#include "BinaryTree.h"
#include <iostream>
#include <string>

/**
 * @brief A custom user-defined class representing a store product.
 */
class Product {
public:
  int productId;
  std::string name;
  double price;

  /**
   * @brief Default constructor required for std::cin parsing.
   */
  Product() : productId(0), name(""), price(0.0) {}

  /**
   * @brief Constructor for the Product object.
   * @param id The unique product ID.
   * @param name The product's name.
   * @param price The product's price.
   */
  Product(int id, std::string name, double price)
      : productId(id), name(name), price(price) {}

  bool operator<(const Product &other) const {
    return productId < other.productId;
  }

  bool operator>(const Product &other) const {
    return productId > other.productId;
  }

  bool operator==(const Product &other) const {
    return productId == other.productId;
  }

  /**
   * @brief Overloads the stream insertion operator to easily print the product.
   */
  friend std::ostream &operator<<(std::ostream &os, const Product &p) {
    os << p.name << " [ID: " << p.productId << ", $" << p.price << "]";
    return os;
  }

  /**
   * @brief Overloads the stream extraction operator to read the product from
   * console. Format expected: <ID> <Name> <Price> (e.g., 105 Laptop 999.99)
   */
  friend std::istream &operator>>(std::istream &is, Product &p) {
    is >> p.productId >> p.name >> p.price;
    return is;
  }
};

/**
 * @brief A generic function that runs an interactive console loop for a
 * specific tree type.
 * * @tparam T The data type of the BinaryTree.
 * @param typeName The name of the type (used for console prompts).
 */
template <typename T> void runTreeInteractive(const std::string &typeName) {
  BinaryTree<T> tree;
  std::string command;

  std::cout << "\n=========================================" << std::endl;
  std::cout << "  Interactive Mode: " << typeName << " Tree" << std::endl;
  std::cout
      << "  Commands: insert <val>, search <val>, delete <val>, draw, back"
      << std::endl;
  if (typeName == "Product") {
    std::cout << "  Product format: <ID> <Name> <Price> (e.g., insert 105 "
                 "Laptop 999.99)"
              << std::endl;
    std::cout
        << "  For search/delete, full format is needed (e.g., delete 105 x 0)"
        << std::endl;
  }
  std::cout << "=========================================\n" << std::endl;

  while (true) {
    std::cout << typeName << " > ";
    std::cin >> command;

    if (command == "back" || command == "exit") {
      break;
    } else if (command == "draw") {
      tree.draw();
    } else if (command == "insert") {
      T val;
      std::cin >> val;
      tree.insert(val);
      std::cout << "Action: Inserted element." << std::endl;
    } else if (command == "search") {
      T val;
      std::cin >> val;
      if (tree.search(val)) {
        std::cout << "Action: Element FOUND in the tree." << std::endl;
      } else {
        std::cout << "Action: Element NOT FOUND." << std::endl;
      }
    } else if (command == "delete") {
      T val;
      std::cin >> val;
      tree.remove(val);
      std::cout << "Action: Element removed (if it existed)." << std::endl;
    } else {
      std::cout << "Unknown command. Use: insert, search, delete, draw, back"
                << std::endl;
      std::cin.clear();
      std::cin.ignore(10000, '\n');
    }
  }
}

/**
 * @brief The main function displaying the initial type selection menu.
 * @return Exit status code.
 */
int main() {
  int choice = 0;

  while (true) {
    std::cout << "\n=== BINARY TREE SERVER (C++ Console) ===" << std::endl;
    std::cout << "Select the type of tree to operate on:" << std::endl;
    std::cout << "1. Integer" << std::endl;
    std::cout << "2. Double" << std::endl;
    std::cout << "3. String" << std::endl;
    std::cout << "4. Product (Custom User Type)" << std::endl;
    std::cout << "5. Exit" << std::endl;
    std::cout << "Choice: ";

    if (!(std::cin >> choice)) {
      std::cout << "Invalid input. Exiting." << std::endl;
      break;
    }

    switch (choice) {
    case 1:
      runTreeInteractive<int>("Integer");
      break;
    case 2:
      runTreeInteractive<double>("Double");
      break;
    case 3:
      runTreeInteractive<std::string>("String");
      break;
    case 4:
      runTreeInteractive<Product>("Product");
      break;
    case 5:
      std::cout << "Exiting program. Goodbye!" << std::endl;
      return 0;
    default:
      std::cout << "Invalid choice. Please select 1-5." << std::endl;
    }
  }

  return 0;
}
