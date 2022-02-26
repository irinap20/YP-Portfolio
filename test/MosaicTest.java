
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.CheckerBoard;
import model.Image;
import model.ImageEditors;
import model.ImageInterface;
import model.ImageProgram;
import model.Mosaic;
import model.RGB;
import model.Rainbow;
import org.junit.Test;

/**
 * Testing the image mosaic operation.
 */
public class MosaicTest {

  @Test(expected = IllegalArgumentException.class)
  public void constructorIAE() {
    ImageEditors mosaic = new Mosaic(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNull() {
    ImageEditors mosaic = new Mosaic(100);
    mosaic.apply(null);
  }

  @Test(expected = IllegalStateException.class)
  public void applyTooManySeeds() {
    ImageProgram rainbowCreator = new Rainbow(7, 7);
    ImageInterface rainbow = rainbowCreator.create();
    ImageEditors mosaic = new Mosaic(rainbow.getHeight() * rainbow.getWidth() + 1);
    mosaic.apply(rainbow);
  }

  @Test
  public void apply() {
    RGB black = new RGB(0, 0, 0);
    RGB white = new RGB(255, 255, 255);
    RGB[] checkerColors = new RGB[]{black, white};
    ImageProgram checkerCreator = new CheckerBoard(3, 4, checkerColors);
    ImageInterface checkerboard = checkerCreator.create();
    ImageEditors mosaic = new Mosaic(1);
    RGB gray = new RGB(128, 128, 128);
    RGB[][] grayPixels = new RGB[12][12];
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < 12; j++) {
        grayPixels[i][j] = gray;
      }
    }
    ImageInterface grayBoard = new Image(grayPixels);
    assertEquals(grayBoard, mosaic.apply(checkerboard));
  }
}