import controller.ImageUtil;
import model.ImageInterface;
import model.RGB;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the ImageUtil class. This class is independent of the model class
 */

public class ImageUtilTest {

  @Test(expected = IllegalArgumentException.class)
  public void testReadPPMInvalidFile() {
    ImageUtil.readPPM("hello");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadPPMNNonPPMFile() {
    ImageUtil.readPPM("img.jpg");
  }

  @Test
  public void testReadPPM() {
    RGB red = new RGB(255, 0, 0);
    RGB blue = new RGB(0, 0, 255);
    ImageInterface image = ImageUtil.readPPM("res/checkerboard.ppm");
    Assert.assertEquals(4, image.getHeight());
    Assert.assertEquals(4, image.getWidth());
    Assert.assertEquals(red, image.getPixel(0, 0));
    Assert.assertEquals(red, image.getPixel(1, 0));
    Assert.assertEquals(red, image.getPixel(1, 0));
    Assert.assertEquals(red, image.getPixel(1, 1));
    Assert.assertEquals(blue, image.getPixel(2, 0));
    Assert.assertEquals(blue, image.getPixel(3, 1));
    Assert.assertEquals(blue, image.getPixel(2, 1));
    Assert.assertEquals(blue, image.getPixel(0, 2));
    Assert.assertEquals(blue, image.getPixel(1, 2));
    Assert.assertEquals(blue, image.getPixel(3, 1));
    Assert.assertEquals(red, image.getPixel(2, 2));
    Assert.assertEquals(red, image.getPixel(3, 2));
    Assert.assertEquals(blue, image.getPixel(0, 3));
    Assert.assertEquals(blue, image.getPixel(1, 3));
    Assert.assertEquals(red, image.getPixel(2, 3));
    Assert.assertEquals(red, image.getPixel(3, 3));
  }
}
