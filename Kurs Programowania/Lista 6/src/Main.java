
/**
 * @mainpage Thread Simulation - Project Documentation
 *
 * <h2>About the Project</h2>
 * <p>This project implements a multithreaded simulation using the Java Swing library. 
 * The application presents a grid of fields, where each field operates as an independent thread.</p>
 *
 * <h2>Main Features</h2>
 * <ul>
 * <li><b>Concurrency:</b> Each field runs in its own thread and modifies its state periodically.</li>
 * <li><b>Torus Topology:</b> Cells on the edges of the board communicate with cells on the opposite side.</li>
 * <li><b>Interaction:</b> Ability to pause and resume individual threads by clicking on them with a mouse.</li>
 * <li><b>Dynamic Scaling:</b> Ability to add new rows and columns during runtime without stopping the simulation.</li>
 * <li><b>Thread Safety:</b> Advanced synchronization (Shared Lock) is used to eliminate deadlocks and race conditions.</li>
 * </ul>
 *
 * <h2>How to Run</h2>
 * <p>The entry point for the application is the {@link Main} class. It launches the main 
 * simulation window on the dedicated Event Dispatch Thread (EDT) to ensure GUI safety.</p>
 *
 * @author Kacper Lebiedziński
 **/
import javax.swing.SwingUtilities;

/**
 * Main class to bootstrap the simulation application.
 *
 * This class serves as the entry point for the application. It
 * initializes
 * the simulation parameters and launches the main graphical user
 * interface
 * on the Event Dispatch Thread (EDT) to ensure thread safety.
 */
public class Main {

  /**
   * Main execution method.
   *
   * @param args Command line arguments (currently not used).
   */
  public static void main(String[] args) {
    int rows = 5;
    int cols = 5;
    int speed = 1000;
    double probability = 0.5;

    SwingUtilities.invokeLater(() -> {
      SimulationFrame frame = new SimulationFrame(rows, cols, speed, probability);
      frame.setVisible(true);
    });
  }
}
