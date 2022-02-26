

import static org.junit.Assert.assertEquals;

import model.Downscale;
import model.Image;
import model.ImageEditors;
import model.RGB;
import org.junit.Test;

/**
 * Testing the downsize operation.
 */
public class DownscaleTest {

  @Test(expected = IllegalArgumentException.class)
  public void constructorException() {
    ImageEditors ds = new Downscale(0, -2);
  }

  @Test(expected = IllegalStateException.class)
  public void applyISE() {
    RGB black = new RGB(0, 0, 0);
    RGB white = new RGB(255, 255, 255);
    RGB[][] bw2By2 = new RGB[][]{{black, white}, {white, black}};
    Image img = new Image(bw2By2);
    ImageEditors triple = new Downscale(img.getHeight() * 3, img.getWidth() * 3);
    triple.apply(img);
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyIAE() {
    ImageEditors ds = new Downscale(45, 67);
    ds.apply(null);
  }

  @Test
  public void apply() {
    RGB black = new RGB(0, 0, 0);
    RGB white = new RGB(255, 255, 255);
    RGB gray = new RGB(128, 128, 128);
    RGB[][] bw2By2 = new RGB[][]{{black, white}, {white, black}};
    Image img = new Image(bw2By2);
    ImageEditors half = new Downscale(img.getHeight() / 2, img.getWidth() / 2);
    RGB[][] b1By1 = new RGB[][]{{black}};
    Image dsImg = new Image(b1By1);
    assertEquals(dsImg, half.apply(img));

    RGB[][] bw3By3 = new RGB[][]{{black, white, black}, {white, black, white},
        {black, white, black}};
    Image img2 = new Image(bw3By3);
    ImageEditors twoThird = new Downscale(img2.getHeight() * 2 / 3, img2.getWidth() * 2 / 3);
    RGB[][] bg2By2 = new RGB[][]{{black, gray}, {gray, gray}};
    Image dsImg2 = new Image(bg2By2);
    assertEquals(dsImg2, twoThird.apply(img2));
  }
}