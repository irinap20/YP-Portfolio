
package model;

/**
 * Represents a color of a pixel. A color has red, green, and blue attributes.
 */
public class RGB implements IRGB {

  private final int red;
  private final int blue;
  private final int green;

  /**
   * Constructs a color with the given attributes.
   *
   * @param red   the red attribute of a color, starting from 0.
   * @param blue  the blue attribute of a color, starting from 0.
   * @param green the green attribute of a color, starting from 0.
   * @throws IllegalArgumentException if any of the color value is out of range
   */
  public RGB(int red, int green, int blue) {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("Invalid color values.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getValue(int index) {
    int[] listOfRGB = new int[3];
    listOfRGB[0] = this.red;
    listOfRGB[1] = this.green;
    listOfRGB[2] = this.blue;
    if (index < 0 || index >= listOfRGB.length) {
      throw new IllegalArgumentException("Invalid index.");
    }
    return listOfRGB[index];
  }

  /*
  @Override
  public String toString() {
    return "(" + this.red + ", " + this.green
        + ", " + this.blue + ")"; }
   */

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof RGB) {
      return this.red == ((RGB) obj).red && this.blue == ((RGB) obj).blue
          && this.green == ((RGB) obj).green;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return (this.red * 100) + (this.green * 10) + this.green;
  }
}
