import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main window for the dynamic thread simulation.
 * * This class provides the graphical user interface for the simulation.
 * It manages the dynamic grid of FieldPanels and provides controls
 * to expand the board during runtime.
 */
public class SimulationFrame extends JFrame {

  private final Random random;
  private final List<List<FieldPanel>> dynamicGrid;
  private final Object sharedLock;

  private final JPanel gridContainer;
  private int currentRowCount;
  private int currentColCount;
  private int idCounter = 1;
  private final int speed;
  private final double probability;

  /**
   * Constructor initializing the dynamic GUI and threads.
   * * @param rows Initial number of rows in the grid.
   * 
   * @param cols        Initial number of columns in the grid.
   * @param speed       The base execution speed for threads in milliseconds.
   * @param probability The probability of a field changing its color randomly.
   */
  public SimulationFrame(int rows, int cols, int speed, double probability) {
    this.currentRowCount = rows;
    this.currentColCount = cols;
    this.speed = speed;
    this.probability = probability;

    setTitle("Thread Simulation - Dynamic Grid");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);

    setLayout(new BorderLayout());

    this.random = new Random();
    this.dynamicGrid = new ArrayList<>();
    this.sharedLock = new Object();

    this.gridContainer = new JPanel();
    this.gridContainer.setLayout(new GridLayout(currentRowCount, currentColCount));
    add(this.gridContainer, BorderLayout.CENTER);

    JPanel controlPanel = new JPanel();
    JButton addRowBtn = new JButton("Add Row");
    JButton addColBtn = new JButton("Add Column");
    controlPanel.add(addRowBtn);
    controlPanel.add(addColBtn);
    add(controlPanel, BorderLayout.SOUTH);

    addRowBtn.addActionListener(e -> addRow());
    addColBtn.addActionListener(e -> addColumn());

    for (int r = 0; r < currentRowCount; r++) {
      List<FieldPanel> rowList = new ArrayList<>();
      for (int c = 0; c < currentColCount; c++) {
        FieldPanel panel = new FieldPanel(idCounter++, r, c, speed, probability, random, dynamicGrid, sharedLock);
        rowList.add(panel);
        gridContainer.add(panel);
      }
      dynamicGrid.add(rowList);
    }

    for (int r = 0; r < currentRowCount; r++) {
      for (int c = 0; c < currentColCount; c++) {
        new Thread(dynamicGrid.get(r).get(c)).start();
      }
    }
  }

  /**
   * Safely adds a new row of thread panels to the bottom of the grid.
   */
  private void addRow() {
    synchronized (sharedLock) {
      int newRowIdx = currentRowCount;
      currentRowCount++;
      gridContainer.setLayout(new GridLayout(currentRowCount, currentColCount));

      List<FieldPanel> newRow = new ArrayList<>();
      for (int c = 0; c < currentColCount; c++) {
        FieldPanel panel = new FieldPanel(idCounter++, newRowIdx, c, speed, probability, random, dynamicGrid,
            sharedLock);
        newRow.add(panel);
        gridContainer.add(panel);
        new Thread(panel).start();
      }
      dynamicGrid.add(newRow);

      gridContainer.revalidate();
      gridContainer.repaint();
    }
  }

  /**
   * Safely adds a new column of thread panels to the right of the grid.
   */
  private void addColumn() {
    synchronized (sharedLock) {
      int newColIdx = currentColCount;
      currentColCount++;
      gridContainer.setLayout(new GridLayout(currentRowCount, currentColCount));

      gridContainer.removeAll();

      for (int r = 0; r < currentRowCount; r++) {
        FieldPanel panel = new FieldPanel(idCounter++, r, newColIdx, speed, probability, random, dynamicGrid,
            sharedLock);
        dynamicGrid.get(r).add(panel);
        new Thread(panel).start();
      }

      for (int r = 0; r < currentRowCount; r++) {
        for (int c = 0; c < currentColCount; c++) {
          gridContainer.add(dynamicGrid.get(r).get(c));
        }
      }

      gridContainer.revalidate();
      gridContainer.repaint();
    }
  }
}
