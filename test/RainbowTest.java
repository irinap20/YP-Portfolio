
import static org.junit.Assert.assertEquals;

import model.ImageInterface;
import model.ImageProgram;
import model.RGB;
import model.Rainbow;
import org.junit.Test;

/**
 * Testing rainbow class to successfully create a rainbow image.
 */
public class RainbowTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFactorOf7() {
    ImageProgram unevenRainbow = new Rainbow(12, 12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorDimensionTooSmall() {
    ImageProgram unevenRainbow = new Rainbow(0, 0);
  }

  @Test
  public void create() {
    ImageProgram rainbow = new Rainbow(14, 14);
    ImageInterface rainbowImg = rainbow.create();
    assertEquals(255, rainbowImg.getMaxValue());
    assertEquals(14, rainbowImg.getHeight());
    assertEquals(14, rainbowImg.getWidth());
    RGB indigo = new RGB(75, 0, 130);
    assertEquals(indigo, rainbowImg.getPixel(4, 10));
  }
}