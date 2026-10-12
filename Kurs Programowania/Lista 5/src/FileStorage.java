import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.List;

/**
 * Utility class handling file input/output operations for shapes.
 */
public class FileStorage {

  /**
   * @brief Saves a list of shape models to a file.
   * @param stage  The primary stage for centering the dialog.
   * @param models The list of ShapeModel objects to save.
   */
  public static void save(Stage stage, List<ShapeModel> models) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Shapes");
    File file = fileChooser.showSaveDialog(stage);

    if (file != null) {
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
        oos.writeObject(models);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * @brief Loads a list of shape models from a file.
   * @param stage The primary stage for centering the dialog.
   * @return A list of reconstructed ShapeModel objects, or null if
   *         cancelled/failed.
   */
  @SuppressWarnings("unchecked")
  public static List<ShapeModel> load(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load Shapes");
    File file = fileChooser.showOpenDialog(stage);

    if (file != null) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        return (List<ShapeModel>) ois.readObject();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }
}
