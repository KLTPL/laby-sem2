import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles an individual client session in a separate thread.
 * Maintains isolated tree instances for the connected client and processes
 * network commands.
 */
public class ClientHandler implements Runnable {

  /** The socket connection to the client. */
  private final Socket clientSocket;

  /**
   * The currently active tree type for this session (Integer, Double, or String).
   */
  private String currentType;

  private final BinaryTree<Integer> intTree;
  private final BinaryTree<Double> doubleTree;
  private final BinaryTree<String> stringTree;
  private final BinaryTree<Product> productTree;

  /**
   * Constructor initializing the handler with the client's socket.
   *
   * @param socket The socket associated with the connected client.
   */
  public ClientHandler(Socket socket,
      BinaryTree<Integer> intTree,
      BinaryTree<Double> doubleTree,
      BinaryTree<String> stringTree,
      BinaryTree<Product> productTree) {
    this.clientSocket = socket;
    this.currentType = "Integer";
    this.intTree = intTree;
    this.doubleTree = doubleTree;
    this.stringTree = stringTree;
    this.productTree = productTree;
  }

  /**
   * The main execution method for the thread.
   * Reads requests from the client, processes them, and sends responses.
   */
  @Override
  public void run() {
    try (
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
      String request;
      while ((request = in.readLine()) != null) {
        System.out.println("Received: " + request);
        String response = processCommand(request);

        out.println(response);
        out.println("<<END>>");
      }
    } catch (IOException e) {
      System.err.println("Connection error with client: " + e.getMessage());
    } finally {
      try {
        clientSocket.close();
        System.out.println("Client disconnected.");
      } catch (IOException e) {
        System.err.println("Error closing socket: " + e.getMessage());
      }
    }
  }

  /**
   * Parses the incoming command string and performs the requested operation on
   * the tree.
   *
   * @param request The raw string command received from the client (e.g., "INSERT
   *                5").
   * @return The string response to be sent back to the client.
   */
  private String processCommand(String request) {
    String[] parts = request.trim().split("\\s+", 2);
    String command = parts[0].toUpperCase();
    String argument = parts.length > 1 ? parts[1] : "";

    try {
      switch (command) {
        case "SET_TYPE":
          currentType = argument;
          return "Type set to " + currentType + ".";

        case "INSERT":
          insertValue(argument);
          return getCurrentTreeDraw();

        case "DELETE":
          deleteValue(argument);
          return getCurrentTreeDraw();

        case "SEARCH":
          boolean found = searchValue(argument);
          return found ? "Element '" + argument + "' found in the tree."
              : "Element '" + argument + "' NOT found.";

        case "DRAW":
          return getCurrentTreeDraw();

        default:
          return "Unknown command.";
      }
    } catch (NumberFormatException e) {
      return "Error: Invalid number format for type " + currentType + ".";
    } catch (Exception e) {
      return "Error processing command: " + e.getMessage();
    }
  }

  /** Inserts a value into the currently active tree. */
  private void insertValue(String arg) {
    if (currentType.equals("Integer"))
      intTree.insert(Integer.parseInt(arg));
    else if (currentType.equals("Double"))
      doubleTree.insert(Double.parseDouble(arg));
    else if (currentType.equals("String"))
      stringTree.insert(arg);
    else
      productTree.insert(Product.parse(arg));
  }

  /** Deletes a value from the currently active tree. */
  private void deleteValue(String arg) {
    if (currentType.equals("Integer"))
      intTree.delete(Integer.parseInt(arg));
    else if (currentType.equals("Double"))
      doubleTree.delete(Double.parseDouble(arg));
    else if (currentType.equals("String"))
      stringTree.delete(arg);
    else
      productTree.delete(Product.parse(arg));
  }

  /** Searches for a value in the currently active tree. */
  private boolean searchValue(String arg) {
    if (currentType.equals("Integer"))
      return intTree.search(Integer.parseInt(arg));
    if (currentType.equals("Double"))
      return doubleTree.search(Double.parseDouble(arg));
    if (currentType.equals("String"))
      return stringTree.search(arg);
    return productTree.search(Product.parse(arg));
  }

  /** Retrieves the drawn string representation of the currently active tree. */
  private String getCurrentTreeDraw() {
    if (currentType.equals("Integer"))
      return intTree.serialize();
    if (currentType.equals("Double"))
      return doubleTree.serialize();
    if (currentType.equals("String"))
      return stringTree.serialize();
    return productTree.serialize();
  }
}
