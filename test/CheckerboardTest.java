import model.CheckerBoard;
import model.ImageInterface;
import model.ImageProgram;
import model.RGB;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the ImageProgram class, its constructor and method.
 */
public class CheckerboardTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalid() {
    RGB red = new RGB(255, 0, 0);
    RGB blue = new RGB(0, 0, 255);
    RGB[] color = new RGB[]{red, blue};
    new CheckerBoard(0, 20, color);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidNumTiles() {

    RGB red = new RGB(255, 0, 0);
    RGB blue = new RGB(0, 0, 255);
    RGB[] color = new RGB[]{red, blue};
    new CheckerBoard(2, 0, color);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidColorNull() {
    new CheckerBoard(0, 20, null);
  }

  @Test
  public void testCreate() {
    RGB red = new RGB(255, 0, 0);
    RGB blue = new RGB(0, 0, 255);
    RGB[] color = new RGB[]{red, blue};
    ImageProgram checkerBoard = new CheckerBoard(2, 2, color);
    ImageInterface image = checkerBoard.create();
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
