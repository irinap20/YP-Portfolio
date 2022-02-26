
import static org.junit.Assert.assertEquals;

import model.ColorTransformation;
import model.ImageInterface;
import model.Image;
import model.RGB;
import model.Sepia;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testing image editors.
 */
public abstract class ColorTransformationTest {

  protected abstract ColorTransformation filter();

  /**
   * Test method for Sepia in ColorTransformation abstract class.
   */
  public static final class SepiaTest extends ColorTransformationTest {

    @Override
    protected ColorTransformation filter() {
      return new Sepia();
    }

    @Test
    public void apply() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image basicColors = new Image(pixels);
      ColorTransformation filter = filter();
      RGB upperLeft = new RGB((int) 48.195, (int) 42.84, (int) 33.405);
      RGB upperRight = new RGB((int) 255, (int) 255, (int) 205.53);
      RGB lowerLeft = new RGB((int) 100.215, (int) 88.995, (int) 69.36);
      RGB lowerRight = new RGB((int) 196.095, (int) 174.93, (int) 136.17);
      RGB[][] newPixels = new RGB[][]{{upperLeft, upperRight}, {lowerLeft, lowerRight}};
      Image sepiaBasic = new Image(newPixels);
      Assert.assertEquals(sepiaBasic, filter.apply(basicColors));
    }
  }

  /**
   * Test method for Greyscale in ColorTransformation abstract class.
   */
  public static final class Greyscale extends ColorTransformationTest {

    @Override
    protected ColorTransformation filter() {
      return new model.Greyscale();
    }

    @Test
    public void apply() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image basicColors = new Image(pixels);
      ColorTransformation filter = filter();
      RGB upperLeft = new RGB((int) 18.411, (int) 18.411, (int) 18.411);
      RGB upperRight = new RGB((int) 236.589, (int) 236.589, (int) 236.589);
      RGB lowerLeft = new RGB((int) 54.213, (int) 54.213, (int) 54.213);
      RGB lowerRight = new RGB((int) 182.316, (int) 182.316, (int) 182.316);
      RGB[][] newPixels = new RGB[][]{{upperLeft, upperRight}, {lowerLeft, lowerRight}};
      Image greyscaleBasic = new Image(newPixels);
      assertEquals(greyscaleBasic, filter.apply(basicColors));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNullImage() {
    ColorTransformation filter = filter();
    ImageInterface img = filter.apply(null);
  }
}