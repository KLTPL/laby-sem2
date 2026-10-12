/**
 * A generic implementation of a Binary Search Tree (BST).
 * Allows insertion, deletion, searching, and hierarchical string representation
 * of elements.
 *
 * @param <T> The type of elements maintained by this tree. Must implement
 *            Comparable.
 */
public class BinaryTree<T extends Comparable<T>> {

  /** The root node of the binary tree. */
  private Node<T> root;

  /**
   * Default constructor initializing an empty binary tree.
   */
  public BinaryTree() {
    this.root = null;
  }

  /**
   * Inserts a new value into the binary search tree.
   * If the value already exists, it will not be inserted again.
   *
   * @param value The value to insert.
   */
  public synchronized void insert(T value) {
    root = insertRecursive(root, value);
  }

  /**
   * Searches for a specific value within the binary tree.
   *
   * @param value The value to search for.
   * @return true if the value is found, false otherwise.
   */
  public synchronized boolean search(T value) {
    return searchRecursive(root, value);
  }

  /**
   * Deletes a specific value from the binary tree if it exists.
   *
   * @param value The value to be removed.
   */
  public synchronized void delete(T value) {
    root = deleteRecursive(root, value);
  }

  /**
   * Generates a string representation of the tree showing its hierarchy.
   * The tree is displayed rotated 90 degrees counterclockwise.
   *
   * @return A formatted multi-line string representing the tree structure.
   */
  public String draw() {
    if (root == null) {
      return "The tree is empty.\n";
    }
    StringBuilder sb = new StringBuilder();
    buildTreeString(root, 0, sb);
    return sb.toString();
  }

  /**
   * Serializes the binary tree into a string format for network transmission.
   * Format: value(left_subtree)(right_subtree). Empty nodes are represented as
   * ().
   * Example: A root of 5 with children 3 and 8 becomes: 5(3()())(8()())
   *
   * @return The serialized string representation of the tree.
   */
  public synchronized String serialize() {
    return serializeRecursive(root);
  }

  /**
   * Recursive helper to build the serialized string.
   *
   * @param node The current node.
   * @return The string representation of this subtree.
   */
  private String serializeRecursive(Node<T> node) {
    if (node == null) {
      return "()";
    }
    return node.value.toString() +
        "(" + serializeRecursive(node.left) + ")" +
        "(" + serializeRecursive(node.right) + ")";
  }

  /**
   * Recursive helper method to insert a new value.
   *
   * @param current The current node being evaluated.
   * @param value   The value to insert.
   * @return The new node structure after insertion.
   */
  private Node<T> insertRecursive(Node<T> current, T value) {
    if (current == null) {
      return new Node<>(value);
    }

    int comparison = value.compareTo(current.value);
    if (comparison < 0) {
      current.left = insertRecursive(current.left, value);
    } else if (comparison > 0) {
      current.right = insertRecursive(current.right, value);
    }

    return current;
  }

  /**
   * Recursive helper method to search for a value.
   *
   * @param current The current node being evaluated.
   * @param value   The value to search for.
   * @return true if found, false otherwise.
   */
  private boolean searchRecursive(Node<T> current, T value) {
    if (current == null) {
      return false;
    }

    int comparison = value.compareTo(current.value);
    if (comparison == 0) {
      return true;
    }

    return comparison < 0
        ? searchRecursive(current.left, value)
        : searchRecursive(current.right, value);
  }

  /**
   * Recursive helper method to delete a value from the tree.
   *
   * @param current The current node being evaluated.
   * @param value   The value to delete.
   * @return The updated node structure after deletion.
   */
  private Node<T> deleteRecursive(Node<T> current, T value) {
    if (current == null) {
      return null;
    }

    int comparison = value.compareTo(current.value);

    if (comparison < 0) {
      current.left = deleteRecursive(current.left, value);
      return current;
    } else if (comparison > 0) {
      current.right = deleteRecursive(current.right, value);
      return current;
    } else {

      if (current.left == null) {
        return current.right;
      } else if (current.right == null) {
        return current.left;
      }

      T smallestValue = findSmallestValue(current.right);
      current.value = smallestValue;

      current.right = deleteRecursive(current.right, smallestValue);
      return current;
    }
  }

  /**
   * Helper method to find the smallest value in a given subtree.
   *
   * @param root The root node of the subtree.
   * @return The smallest value found.
   */
  private T findSmallestValue(Node<T> root) {
    return root.left == null ? root.value : findSmallestValue(root.left);
  }

  /**
   * Recursive helper method to build the hierarchical string of the tree.
   *
   * @param node  The current node being processed.
   * @param level The current depth level in the tree (used for indentation).
   * @param sb    The StringBuilder accumulating the final string.
   */
  private void buildTreeString(Node<T> node, int level, StringBuilder sb) {
    if (node == null) {
      return;
    }

    buildTreeString(node.right, level + 1, sb);

    for (int i = 0; i < level; i++) {
      sb.append("      ");
    }

    sb.append(node.value.toString()).append("\n");

    buildTreeString(node.left, level + 1, sb);
  }
}
