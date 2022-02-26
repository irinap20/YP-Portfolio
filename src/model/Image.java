package model;

import java.util.Arrays;

/**
 * Represents a 2D image made of pixels.
 */
public class Image implements ImageInterface {

  private final RGB[][] pixels;
  private final int maxVal;

  /**
   * Construct an image with the given pixels and a set maximum color value.
   *
   * @param pixels an 2D array of pixels that represents an image
   * @throws IllegalArgumentException if the given pixels is null or is not a rectangle
   */
  public Image(RGB[][] pixels) {
    if (pixels == null || !validPixels(pixels)) {
      throw new IllegalArgumentException("Pixels can't be null.");
    }
    RGB[][] copy = new RGB[pixels.length][pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        copy[i][j] = new RGB(pixels[i][j].getRed(), pixels[i][j].getGreen(),
            pixels[i][j].getBlue());
      }
    }
    this.pixels = copy;
    this.maxVal = 255;
  }

  /**
   * Constructs an image with the given pixels, width of the image, height of the image, and a
   * maximum RGB value.
   *
   * @param pixels an 2D array of pixels that represents an image
   * @param maxVal the maximum RGB value of the image
   */
  public Image(RGB[][] pixels, int maxVal) {
    if (pixels == null || !validPixels(pixels) || maxVal < 0) {
      throw new IllegalArgumentException("Input Invalid");
    }
    RGB[][] copy = new RGB[pixels.length][pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        copy[i][j] = new RGB(pixels[i][j].getRed(), pixels[i][j].getGreen(),
            pixels[i][j].getBlue());
      }
    }
    this.pixels = copy;
    this.maxVal = maxVal;
  }


  @Override
  public RGB[][] getPixels() {
    if (this.pixels == null || !validPixels(this.pixels)) {
      throw new IllegalStateException("No pixels loaded in this image.");
    }
    RGB[][] copy = new RGB[pixels.length][pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        copy[i][j] = new RGB(pixels[i][j].getRed(), pixels[i][j].getGreen(),
            pixels[i][j].getBlue());
      }
    }
    return copy;
  }

  /**
   * Get the specified pixel within the image pixels.
   *
   * @param x the x-coordinate of the pixel in the image pixels
   * @param y the y-coordinate of the pixel in the image pixels
   * @return the pixel with the given (x,y) coordinates in the pixels
   * @throws IllegalArgumentException if given indices are invalid
   */
  @Override
  public RGB getPixel(int x, int y) {
    if (y >= 0 && y < pixels.length && x >= 0 && x < pixels[y].length) {
      return pixels[y][x];
    } else {
      throw new IllegalArgumentException("Index out of bounds.");
    }
  }

  @Override
  public int getHeight() {
    return this.pixels.length;
  }

  @Override
  public int getWidth() {
    if (pixels.length > 0) {
      return pixels[0].length;
    }
    return 0;
  }


  /**
   * Get the maximum RGB value of the image.
   *
   * @return an integer representing the maximum RGB value
   */
  @Override
  public int getMaxValue() {
    return this.maxVal;
  }

  /*
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        s.append(pixels[i][j].toString());
      }
    }
    return s.toString();
  }
*/

  /**
   * Determines whether the given pixels is valid.
   *
   * @param pixels the pixels of the image
   * @return a boolean of whether the given pixels is valid
   */
  private boolean validPixels(RGB[][] pixels) {
    boolean result = true;
    if (pixels.length == 0) {
      result = false;
    }
    for (RGB[] row : pixels) {
      if (row.length != pixels[0].length || row.length == 0) {
        result = false;
        break;
      }
      for (RGB pixel : row) {
        if (pixel == null) {
          result = false;
          break;
        }
      }
    }
    return result;
  }

  /**
   * Format an image into PPM format ready to export.
   *
   * @return a PPM format string with the content of the image
   */
  public String toString() {
    StringBuilder content = new StringBuilder();
    content.append(this.getHeight()).append(" ");
    content.append(this.getWidth()).append("\n");
    content.append(this.maxVal).append("\n");
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        RGB color = this.getPixel(j, i);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        content.append(red).append("\n");
        content.append(green).append("\n");
        content.append(blue).append("\n");
      }
    }
    return content.toString();
  }

  @Override
  public Image getImage() {
    return new Image(this.pixels, this.maxVal);
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Image) {
      return Arrays.deepEquals(this.pixels, ((Image) obj).pixels)
          && this.getHeight() == ((Image) obj).getHeight()
          && this.getWidth() == ((Image) obj).getWidth() && this.maxVal == ((Image) obj).maxVal;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(this.pixels) * this.getHeight() * this.getWidth() * this.maxVal;
  }
}
