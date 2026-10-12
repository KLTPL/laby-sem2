/**
 * Represents a single node in the generic Binary Search Tree.
 *
 * @param <T> The type of the value held in the node. Must implement the
 *            Comparable interface.
 */
public class Node<T extends Comparable<T>> {

  /** The value stored in this node. */
  public T value;

  /** Reference to the left child node. */
  public Node<T> left;

  /** Reference to the right child node. */
  public Node<T> right;

  /**
   * Constructor initializing the node with a specific value.
   * By default, the node has no children (left and right are null).
   *
   * @param value The value to be stored in the node.
   */
  public Node(T value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}
