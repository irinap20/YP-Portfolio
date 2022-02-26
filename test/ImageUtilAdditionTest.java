import static org.junit.Assert.assertEquals;

import controller.ImageUtilAddition;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Downscale;
import model.ImageEditors;
import model.ImageInterface;
import model.Mosaic;
import org.junit.Test;

/**
 * Testing Image Util Addition class.
 */
public class ImageUtilAdditionTest {

  @Test
  public void readImage() throws IOException {
    ImageInterface cartoon = ImageUtilAddition.readImage("res/cartoon.jpeg");

    // half-size image
    ImageEditors halfSize = new Downscale(cartoon.getHeight() / 2, cartoon.getWidth() / 2);
    ImageInterface cartoonHalfSize = halfSize.apply(cartoon);
    assertEquals(360, cartoonHalfSize.getHeight());
    assertEquals(640, cartoonHalfSize.getWidth());
    ImageUtilAddition.export(cartoonHalfSize, "res/cartoonHalfSize.jpeg");

    // square image
    ImageEditors square = new Downscale(Math.min(cartoon.getHeight(), cartoon.getWidth()),
        Math.min(cartoon.getHeight(), cartoon.getWidth()));
    ImageInterface cartoonSquare = square.apply(cartoon);
    assertEquals(720, cartoonSquare.getHeight());
    assertEquals(720, cartoonSquare.getWidth());
    ImageUtilAddition.export(cartoonSquare, "res/cartoonSquare.jpeg");

    // mosaic 1000 seeds
    ImageEditors mosaic1000Seeds = new Mosaic(1000);
    ImageInterface cartoonMosaic1000Seeds = mosaic1000Seeds.apply(cartoon);
    assertEquals(720, cartoonMosaic1000Seeds.getHeight());
    assertEquals(1280, cartoonMosaic1000Seeds.getWidth());
    ImageUtilAddition.export(cartoonMosaic1000Seeds, "res/cartoonMosaic1000seeds.jpg");

    // mosaic 100 seeds
    ImageEditors mosaic100Seeds = new Mosaic(100);
    ImageInterface cartoonMosaic100Seeds = mosaic100Seeds.apply(cartoon);
    assertEquals(720, cartoonMosaic100Seeds.getHeight());
    assertEquals(1280, cartoonMosaic100Seeds.getWidth());
    ImageUtilAddition.export(cartoonMosaic100Seeds, "res/cartoonMosaic100Seeds.jpg");

    // mosaic 10000 seeds
    ImageEditors mosaic10000Seeds = new Mosaic(10000);
    ImageInterface cartoonMosaic10000Seeds = mosaic10000Seeds.apply(cartoon);
    assertEquals(973, cartoonMosaic10000Seeds.getHeight());
    assertEquals(1800, cartoonMosaic10000Seeds.getWidth());
    ImageUtilAddition.export(cartoonMosaic10000Seeds, "res/cartoonMosaic10000seeds.jpg");
  }

  @Test(expected = FileNotFoundException.class)
  public void readImageFNF() throws IOException {
    ImageInterface helloWorld = ImageUtilAddition.readImage("res/helloWorld.jpg");
  }
}