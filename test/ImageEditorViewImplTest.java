
import static junit.framework.TestCase.assertEquals;

import view.ImageEditorView;
import view.ImageEditorViewImpl;
import java.io.IOException;
import model.Image;
import model.ImageEditors;
import model.MultiImageProcessingModel;
import model.MultiImageProcessingModelImpl;
import model.RGB;
import model.Sharpening;
import org.junit.Test;

/**
 * This test checks the ImageEditorView.
 */
public class ImageEditorViewImplTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    ImageEditorView view = new ImageEditorViewImpl(null, null);
  }

  @Test
  public void testToString() {
    MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
    model.createLayer();
    ImageEditorView view = new ImageEditorViewImpl(model);
    assertEquals("ImageEditor Type: \n"
        + "Image Width: \n"
        + "Image Height: \n"
        + "Number of Layers: 1 \n"
        + "Current Layer: 1 \n", view.toString());
    model.setCurrent(0);
    RGB purple = new RGB(128, 0, 128);
    RGB cyan = new RGB(0, 255, 255);
    RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
    Image img = new Image(pixels);
    model.setImage(img);
    ImageEditors ie = new Sharpening();
    model.setEffect(ie);
    assertEquals("ImageEditor Type: ColorTransformation-class model.Sharpening \n"
        + "Image Width: 1 \n"
        + "Image Height: 2 \n"
        + "Number of Layers: 1 \n"
        + "Current Layer: 1 \n", view.toString());
  }

  @Test
  public void testToStringNoImage() {
    Appendable ap = new StringBuilder();
    MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
    ImageEditorView view = new ImageEditorViewImpl(model, ap);
    assertEquals("ImageEditor Type: \n"
        + "Image Width: \n"
        + "Image Height: \n"
        + "Number of Layers: 0 \n"
        + "Current Layer: 0 \n", view.toString());
  }


  @Test
  public void renderMessage() throws IOException {
    Appendable ap = new StringBuilder();
    MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
    ImageEditorView view = new ImageEditorViewImpl(model, ap);
    view.renderMessage("Hello World.");
    assertEquals("Hello World.", ap.toString());
  }

  @Test
  public void renderInformation() throws IOException {
    Appendable ap = new StringBuilder();
    MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
    ImageEditorView view = new ImageEditorViewImpl(model, ap);
    model.createLayer();
    model.createLayer();
    model.setCurrent(1);
    RGB purple = new RGB(128, 0, 128);
    RGB cyan = new RGB(0, 255, 255);
    RGB[][] pixels = new RGB[][]{{purple}, {cyan}};
    Image img = new Image(pixels);
    model.setImage(img);
    ImageEditors ie = new Sharpening();
    model.setEffect(ie);
    view.renderInformation();
    assertEquals("ImageEditor Type: ColorTransformation-class model.Sharpening \n"
        + "Image Width: 1 \n"
        + "Image Height: 2 \n"
        + "Number of Layers: 2 \n"
        + "Current Layer: 2 \n", ap.toString());
  }
}