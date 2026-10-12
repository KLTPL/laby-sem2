import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main server class that listens for incoming client connections.
 * For each connected client, it spawns a new independent thread
 * (ClientHandler).
 */
public class TreeServer {

  /** The port on which the server will listen for connections. */
  private static final int PORT = 8080;

  /**
   * The main entry point for the server application.
   * Starts the ServerSocket and runs an infinite loop to accept clients.
   *
   * @param args Command line arguments (not used).
   */
  public static void main(String[] args) {
    System.out.println("Starting Tree Server on port " + PORT + "...");

    BinaryTree<Integer> sharedIntTree = new BinaryTree<>();
    BinaryTree<Double> sharedDoubleTree = new BinaryTree<>();
    BinaryTree<String> sharedStringTree = new BinaryTree<>();
    BinaryTree<Product> sharedProductTree = new BinaryTree<>();

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server is running and waiting for clients.");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected: " + clientSocket.getInetAddress());

        ClientHandler handler = new ClientHandler(clientSocket, sharedIntTree, sharedDoubleTree, sharedStringTree,
            sharedProductTree);
        new Thread(handler).start();
      }
    } catch (IOException e) {
      System.err.println("Server exception: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
