import static junit.framework.TestCase.assertEquals;

import model.Image;
import model.RGB;
import org.junit.Test;

/**
 * Testing Image class.
 */
public class ImageTest {

  @Test
  public void testConstructorOneArg() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB red = new RGB(255, 0, 0);
    RGB green = new RGB(0, 255, 0);
    RGB[][] pixels = new RGB[2][2];
    pixels[0][0] = blue;
    pixels[0][1] = yellow;
    pixels[1][0] = red;
    pixels[1][1] = green;
    Image basicColors = new Image(pixels);
    assertEquals(2, basicColors.getHeight());
    assertEquals(2, basicColors.getWidth());
    assertEquals(255, basicColors.getMaxValue());
    assertEquals(blue, basicColors.getPixel(0, 0));
    assertEquals(yellow, basicColors.getPixel(1, 0));
    assertEquals(red, basicColors.getPixel(0, 1));
    assertEquals(green, basicColors.getPixel(1, 1));
  }

  @Test
  public void testConstructorTwoArg() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB red = new RGB(255, 0, 0);
    RGB green = new RGB(0, 255, 0);
    RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
    Image basicColors = new Image(pixels, 240);
    assertEquals(2, basicColors.getHeight());
    assertEquals(2, basicColors.getWidth());
    assertEquals(240, basicColors.getMaxValue());
    assertEquals(blue, basicColors.getPixel(0, 0));
    assertEquals(yellow, basicColors.getPixel(1, 0));
    assertEquals(red, basicColors.getPixel(0, 1));
    assertEquals(green, basicColors.getPixel(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull() {
    RGB[][] pixels = null;
    Image basicColors = new Image(pixels);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorPixelsContainsNull() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB red = new RGB(255, 0, 0);
    RGB green = new RGB(0, 255, 0);
    RGB white = new RGB(255, 255, 255);
    RGB[][] pixels = new RGB[2][3];
    pixels[0][0] = blue;
    pixels[0][1] = yellow;
    pixels[1][0] = red;
    pixels[1][1] = green;
    pixels[1][2] = white;
    Image basicColors = new Image(pixels); // throws IAE
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorPixelsNotRec() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB red = new RGB(255, 0, 0);
    RGB green = new RGB(0, 255, 0);
    RGB white = new RGB(255, 255, 255);
    RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}, {white}};
    Image basicColors = new Image(pixels); // throws IAE
  }

  @Test
  public void getPixel() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB red = new RGB(255, 0, 0);
    RGB green = new RGB(0, 255, 0);
    RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
    Image basicColors = new Image(pixels);
    assertEquals(red, basicColors.getPixel(0, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPixelIAE() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB red = new RGB(255, 0, 0);
    RGB green = new RGB(0, 255, 0);
    RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
    Image basicColors = new Image(pixels);
    RGB nonExist = basicColors.getPixel(2, 1);
  }

  @Test
  public void getHeight() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB[][] pixels = new RGB[][]{{blue, yellow}};
    Image basicColors = new Image(pixels);
    assertEquals(1, basicColors.getHeight());
  }

  @Test
  public void getWidth() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB[][] pixels = new RGB[][]{{blue, yellow}};
    Image basicColors = new Image(pixels);
    assertEquals(2, basicColors.getWidth());
  }

  @Test
  public void getMaxValue() {
    RGB blue = new RGB(0, 0, 255);
    RGB yellow = new RGB(255, 255, 0);
    RGB[][] pixels = new RGB[][]{{blue, yellow}};
    Image basicColors = new Image(pixels);
    assertEquals(255, basicColors.getMaxValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorPixelsEmpty() {
    RGB[][] pixels = new RGB[][]{};
    Image img = new Image(pixels);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorPixelsEmptyRow() {
    RGB[][] pixels = new RGB[][]{{}, {}, {}};
    Image img = new Image(pixels);
  }

}