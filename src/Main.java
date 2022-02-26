import controller.ImageEditorController;
import controller.ImageEditorControllerImpl;
import controller.InteractiveController;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.MultiImageProcessingModel;
import model.MultiImageProcessingModelImpl;
import view.GUIView;
import view.IEditorView;

/**
 * The driver for model that runs and activates the controller.
 */
public class Main {

  private static final String DIR = "res/"; // to modify the base directory

  /**
   * The main method that runs the controller.
   *
   * @param args argument String input
   * @throws IOException if FileReader fail
   */
  public static void main(String[] args) throws IOException {
    // Interactive
    if (args.length == 1 && args[0].equals("-text")) {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      ImageEditorController controller = new ImageEditorControllerImpl(model,
          new InputStreamReader(System.in), System.out);
      controller.start();
    }
    else if (args.length == 1 && args[0].equals("-interactive")) {
      MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
      IEditorView view = new GUIView();
      ImageEditorController controller = new InteractiveController(model, view);
      controller.start();
    }
    // Script
    else if (args.length == 2 && args[0].equals("-script")) {
      try {
        MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
        ImageEditorController controller = new ImageEditorControllerImpl(model,
            new FileReader(DIR + args[1]), System.out);
        controller.start();
      } catch (IndexOutOfBoundsException | IOException e) {
        throw new IOException("Invalid File. Check File Directory");
      }
    }
    // invalid
    else {
      throw new IllegalArgumentException("Invalid commands.");
    }
  }
}
