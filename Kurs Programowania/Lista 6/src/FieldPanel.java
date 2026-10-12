import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;

/**
 * Panel representing a single dynamic field in the simulation.
 * * This class operates as an independent thread. It manages its own state,
 * handles pausing/resuming via mouse clicks, and communicates with its
 * neighbors using a shared lock to prevent race conditions.
 */
public class FieldPanel extends JPanel implements Runnable {
  private final int id;
  private final int row, col;
  private final int speed;
  private final double probability;
  private final Random random;
  private final List<List<FieldPanel>> dynamicGrid;
  private final Object sharedLock;
  private Color currentColor;
  private volatile boolean isPaused = false;

  /**
   * Constructor for the dynamic FieldPanel.
   * * @param id The unique identifier for this thread.
   * 
   * @param row         The logical row position in the grid.
   * @param col         The logical column position in the grid.
   * @param speed       The base execution speed in milliseconds.
   * @param probability The probability of randomly changing color.
   * @param random      The shared random number generator.
   * @param dynamicGrid Reference to the dynamic grid of all panels.
   * @param sharedLock  The global lock used for synchronization.
   */
  public FieldPanel(int id, int row, int col, int speed, double probability,
      Random random, List<List<FieldPanel>> dynamicGrid, Object sharedLock) {
    this.id = id;
    this.row = row;
    this.col = col;
    this.speed = speed;
    this.probability = probability;
    this.random = random;
    this.dynamicGrid = dynamicGrid;
    this.sharedLock = sharedLock;

    this.currentColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    setBackground(this.currentColor);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        togglePause();
      }
    });
  }

  /**
   * Safely toggles the pause state of this specific thread.
   * If unpaused, it notifies the thread to wake up.
   */
  public synchronized void togglePause() {
    isPaused = !isPaused;
    if (isPaused) {
      SwingUtilities.invokeLater(() -> setBackground(Color.DARK_GRAY));
    } else {
      this.notify();
    }
  }

  /**
   * Checks if the current thread is paused.
   * * @return true if the panel is paused, false otherwise.
   */
  public boolean isPaused() {
    return isPaused;
  }

  /**
   * Safely retrieves the current color of the panel.
   * * @return The current Color object.
   */
  public Color getCurrentColor() {
    return this.currentColor;
  }

  /**
   * The main execution loop of the thread.
   * Handles sleeping, logical color updates, and GUI repaints.
   */
  @Override
  public void run() {
    while (true) {
      try {
        synchronized (this) {
          while (isPaused) {
            this.wait();
          }
        }

        double multiplier = 0.5 + random.nextDouble();
        long sleepTime = (long) (speed * multiplier);
        Thread.sleep(sleepTime);

        synchronized (sharedLock) {
          System.out.println("Start: " + id);
          int currentRows = dynamicGrid.size();
          int currentCols = currentRows > 0 ? dynamicGrid.get(0).size() : 0;

          if (currentRows == 0 || currentCols == 0 || row >= currentRows || col >= currentCols) {
            continue;
          }

          if (random.nextDouble() < probability) {
            this.currentColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
          } else {
            List<FieldPanel> activeNeighbors = new ArrayList<>();

            FieldPanel up = dynamicGrid.get((row - 1 + currentRows) % currentRows).get(col);
            FieldPanel down = dynamicGrid.get((row + 1) % currentRows).get(col);
            FieldPanel left = dynamicGrid.get(row).get((col - 1 + currentCols) % currentCols);
            FieldPanel right = dynamicGrid.get(row).get((col + 1) % currentCols);

            if (!up.isPaused())
              activeNeighbors.add(up);
            if (!down.isPaused())
              activeNeighbors.add(down);
            if (!left.isPaused())
              activeNeighbors.add(left);
            if (!right.isPaused())
              activeNeighbors.add(right);

            if (!activeNeighbors.isEmpty()) {
              int sumRed = 0, sumGreen = 0, sumBlue = 0;
              for (FieldPanel neighbor : activeNeighbors) {
                Color c = neighbor.getCurrentColor();
                sumRed += c.getRed();
                sumGreen += c.getGreen();
                sumBlue += c.getBlue();
              }
              int count = activeNeighbors.size();
              this.currentColor = new Color(sumRed / count, sumGreen / count, sumBlue / count);
            }
          }

          if (!isPaused) {
            SwingUtilities.invokeLater(() -> {
              setBackground(this.currentColor);
              repaint();
            });
          }
          System.out.println("End: " + id);
        }

      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      }
    }
  }
}
