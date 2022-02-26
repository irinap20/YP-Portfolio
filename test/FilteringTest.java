import static junit.framework.TestCase.assertEquals;

import model.Blur;
import model.Filtering;
import model.ImageInterface;
import model.Image;
import model.RGB;
import model.Sharpening;
import org.junit.Test;

/**
 * This class tests the Filtering class as well as the classes that extend it (Blur and
 * Sharpening).
 */
public abstract class FilteringTest {

  protected abstract Filtering filter();

  /**
   * Testing the blur class and the apply method.
   */
  public static final class BlurTest extends FilteringTest {

    @Override
    protected Filtering filter() {
      return new Blur();
    }

    @Test
    public void apply() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image basicColors = new Image(pixels);
      Filtering filter = filter();
      RGB upperLeft = new RGB((int) 63.75, (int) 47.8125, (int) 63.75);
      RGB upperRight = new RGB((int) 79.6875, (int) 95.625, (int) 31.875);
      RGB lowerLeft = new RGB((int) 79.6875, (int) 47.8125, (int) 31.875);
      RGB lowerRight = new RGB((int) 63.75, (int) 95.625, (int) 15.9375);
      RGB[][] newPixels = new RGB[][]{{upperLeft, upperRight}, {lowerLeft, lowerRight}};
      Image blurredBasicColors = new Image(newPixels);
      assertEquals(blurredBasicColors, filter.apply(basicColors));
    }
  }

  /**
   * Testing the sharpening class and the apply method.
   */
  public static final class SharpeningTest extends FilteringTest {

    @Override
    protected Filtering filter() {
      return new Sharpening();
    }

    @Test
    public void apply() {
      RGB blue = new RGB(0, 0, 255);
      RGB yellow = new RGB(255, 255, 0);
      RGB red = new RGB(255, 0, 0);
      RGB green = new RGB(0, 255, 0);
      RGB[][] pixels = new RGB[][]{{blue, yellow}, {red, green}};
      Image basicColors = new Image(pixels);
      Filtering filter = filter();
      RGB upperLeft = new RGB((int) 127.5, (int) 127.5, 255);
      RGB upperRight = new RGB(255, 255, (int) 63.75);
      RGB lowerLeft = new RGB(255, (int) 127.5, (int) 63.75);
      RGB lowerRight = new RGB((int) 127.5, 255, (int) 63.75);
      RGB[][] newPixels = new RGB[][]{{upperLeft, upperRight}, {lowerLeft, lowerRight}};
      Image sharpenedBasicColors = new Image(newPixels);
      assertEquals(sharpenedBasicColors, filter.apply(basicColors));
    }


  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNullImage() {
    Filtering filter = filter();
    ImageInterface img = filter.apply(null);
  }
}