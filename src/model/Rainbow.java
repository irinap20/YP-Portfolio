package model;

/**
 * The class that creates rainbow images.
 */
public class Rainbow implements ImageProgram {

  private final int width; // width of the rainbow image
  private final int height; // height of the rainbow image
  private final RGB red = new RGB(255, 0, 0);
  private final RGB orange = new RGB(255, 165, 0);
  private final RGB yellow = new RGB(255, 255, 0);
  private final RGB green = new RGB(0, 255, 0);
  private final RGB blue = new RGB(0, 0, 255);
  private final RGB indigo = new RGB(75, 0, 130);
  private final RGB violet = new RGB(238, 130, 238);

  /**
   * Construct a rainbow object with the given image dimension.
   *
   * @param width  the width of the image, starting from 0
   * @param height the height of the image, starting from 0 and must be a factor of 7 for 7 colors
   */
  public Rainbow(int width, int height) {
    if (width <= 0 || height <= 0 || height % 7 != 0) {
      throw new IllegalArgumentException("Invalid width or height.");
    }
    this.width = width;
    this.height = height;
  }

  @Override
  public ImageInterface create() {
    RGB[][] pixels = new RGB[height][width];
    int counter = 0;
    while (counter < 7) {
      RGB color = decideColor(counter);
      for (int i = counter * (height / 7); i < (counter + 1) * (height / 7); i++) {
        for (int j = 0; j < width; j++) {
          pixels[i][j] = color;
        }
      }
      counter += 1;
    }
    return new Image(pixels);
  }

  /**
   * Decides the rainbow color to create based on a counter.
   *
   * @param counter ranging from 0 to 6, each associated with one rainbow color
   * @return a rainbow color of a pixel
   */
  private RGB decideColor(int counter) {
    RGB color;
    switch (counter) {
      case 0:
        color = red;
        break;
      case 1:
        color = orange;
        break;
      case 2:
        color = yellow;
        break;
      case 3:
        color = green;
        break;
      case 4:
        color = blue;
        break;
      case 5:
        color = indigo;
        break;
      case 6:
        color = violet;
        break;
      default:
        throw new IllegalArgumentException("Unexpected counter value: " + counter);
    }
    return color;
  }
}
