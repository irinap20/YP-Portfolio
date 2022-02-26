
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.Features;
import controller.InteractiveController;
import model.MultiImageProcessingModel;
import model.MultiImageProcessingModelImpl;
import org.junit.Test;
import view.GUIView;
import view.IEditorView;

/**
 * Testing the interactive controller class.
 */
public class InteractiveControllerTest {

  @Test
  public void testInteractiveController() {
    MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
    IEditorView view = new GUIView();
    Features controller = new InteractiveController(model, view);
    controller.layerCommands("createlayer");
    assertTrue(controller.isVisible());
    controller.layerCommands("createlayer");
    controller.layerCommands("createlayer");
    controller.setCommands("current", "1");
    controller.setCommands("load", "res/cartoon.jpg");
    controller.setCommands("current", "2");
    controller.setCommands("load", "res/image1.jpeg");
    controller.setCommands("current", "3");
    controller.setCommands("load", "res/image2.jpeg");
    controller.applyEffect("greyscale");
    controller.draw("downscale", "400", "400");
    controller.layerCommands("invisible");
    controller.layerCommands("createlayer");
    controller.setCommands("load", "res/diagram.png");
    controller.layerCommands("removelayer");
    controller.setCommands("saveall", "res/mixed.jpg");
    assertFalse(controller.isVisible());
  }
}