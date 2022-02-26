
import controller.ImageEditorController;
import controller.ImageEditorControllerImpl;
import java.io.InputStreamReader;
import java.io.PrintStream;
import model.MultiImageProcessingModel;
import model.MultiImageProcessingModelImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test class represents the tests for controllers.
 */
public class ImageEditorControllerImplTest {

  private class MockController implements ImageEditorController {

    String s;

    private MockController(String s) {
      this.s = s;
    }

    @Override
    public void start() {
      s += " hello";
    }

    public String getS() {
      return s;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullModel() {
    Readable rd = new InputStreamReader(System.in);
    PrintStream out = new PrintStream(System.out);
    ImageEditorController controller = new ImageEditorControllerImpl(null, rd, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullReadable() {
    MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
    PrintStream out = new PrintStream(System.out);
    new ImageEditorControllerImpl(model, null, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullAppendable() {
    MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
    Readable rd = new InputStreamReader(System.in);
    new ImageEditorControllerImpl(model, rd, null);
  }


  @Test
  public void start() {
    MultiImageProcessingModel model = new MultiImageProcessingModelImpl();
    String s = "hello";
    MockController controller = new MockController(s);
    controller.start();
    Assert.assertEquals("hello hello", controller.getS());
  }
}