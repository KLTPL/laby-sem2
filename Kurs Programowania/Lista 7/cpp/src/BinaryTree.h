#ifndef BINARY_TREE_H
#define BINARY_TREE_H

#include <iostream>
#include <string>

/**
 * @brief Represents a single node in the generic Binary Search Tree.
 *
 * @tparam T The type of the value held in the node. Must support <, >, ==
 * operators and << for streams.
 */
template <typename T> class Node {
public:
  T value;
  Node<T> *left;
  Node<T> *right;

  /**
   * @brief Constructor initializing the node with a specific value.
   * @param val The value to be stored in the node.
   */
  Node(T val) : value(val), left(nullptr), right(nullptr) {}
};

/**
 * @brief A generic implementation of a Binary Search Tree (BST).
 * Allows insertion, removal, searching, and hierarchical console
 * representation.
 *
 * @tparam T The type of elements maintained by this tree.
 */
template <typename T> class BinaryTree {
private:
  Node<T> *root; ///< The root node of the binary tree.

  /**
   * @brief Recursive helper to destroy the tree and free memory.
   * @param current The current node being deleted.
   */
  void destroyTree(Node<T> *current) {
    if (current != nullptr) {
      destroyTree(current->left);
      destroyTree(current->right);
      delete current;
    }
  }

  /**
   * @brief Recursive helper method to insert a new value.
   * @param current The current node being evaluated.
   * @param value The value to insert.
   * @return The new node structure after insertion.
   */
  Node<T> *insertRecursive(Node<T> *current, T value) {
    if (current == nullptr) {
      return new Node<T>(value);
    }

    if (value < current->value) {
      current->left = insertRecursive(current->left, value);
    } else if (value > current->value) {
      current->right = insertRecursive(current->right, value);
    }
    return current;
  }

  /**
   * @brief Recursive helper method to search for a value.
   * @param current The current node being evaluated.
   * @param value The value to search for.
   * @return true if found, false otherwise.
   */
  bool searchRecursive(Node<T> *current, T value) {
    if (current == nullptr)
      return false;

    if (value == current->value)
      return true;

    if (value < current->value) {
      return searchRecursive(current->left, value);
    } else {
      return searchRecursive(current->right, value);
    }
  }

  /**
   * @brief Recursive helper method to remove a value from the tree.
   * @param current The current node being evaluated.
   * @param value The value to delete.
   * @return The updated node structure after deletion.
   */
  Node<T> *removeRecursive(Node<T> *current, T value) {
    if (current == nullptr)
      return current;

    if (value < current->value) {
      current->left = removeRecursive(current->left, value);
    } else if (value > current->value) {
      current->right = removeRecursive(current->right, value);
    } else {
      if (current->left == nullptr) {
        Node<T> *temp = current->right;
        delete current;
        return temp;
      } else if (current->right == nullptr) {
        Node<T> *temp = current->left;
        delete current;
        return temp;
      }

      T smallestValue = findSmallestValue(current->right);
      current->value = smallestValue;
      current->right = removeRecursive(current->right, smallestValue);
    }
    return current;
  }

  /**
   * @brief Helper method to find the smallest value in a subtree.
   * @param current The root node of the subtree.
   * @return The smallest value found.
   */
  T findSmallestValue(Node<T> *current) {
    T minv = current->value;
    while (current->left != nullptr) {
      minv = current->left->value;
      current = current->left;
    }
    return minv;
  }

  /**
   * @brief Recursive helper method to draw the tree to the console.
   * @param current The current node being processed.
   * @param level The current depth level in the tree.
   */
  void drawRecursive(Node<T> *current, int level) {
    if (current == nullptr)
      return;

    drawRecursive(current->right, level + 1);

    for (int i = 0; i < level; i++) {
      std::cout << "      ";
    }
    std::cout << current->value << std::endl;

    drawRecursive(current->left, level + 1);
  }

public:
  /**
   * @brief Default constructor initializing an empty binary tree.
   */
  BinaryTree() : root(nullptr) {}

  /**
   * @brief Destructor that frees all dynamically allocated nodes.
   */
  ~BinaryTree() { destroyTree(root); }

  /**
   * @brief Inserts a new value into the binary search tree.
   * @param value The value to insert.
   */
  void insert(T value) { root = insertRecursive(root, value); }

  /**
   * @brief Searches for a specific value within the binary tree.
   * @param value The value to search for.
   * @return true if found, false otherwise.
   */
  bool search(T value) { return searchRecursive(root, value); }

  /**
   * @brief Removes a specific value from the binary tree if it exists.
   * @param value The value to be removed.
   */
  void remove(T value) { root = removeRecursive(root, value); }

  /**
   * @brief Draws the tree structure to the standard output.
   */
  void draw() {
    if (root == nullptr) {
      std::cout << "The tree is empty." << std::endl;
      return;
    }
    drawRecursive(root, 0);
    std::cout << "------------------------------------------------"
              << std::endl;
  }
};

#endif
