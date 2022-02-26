package model;

/**
 * Interface that represents colors.
 */
public interface IRGB {

  /**
   * Get the red value of this color.
   *
   * @return the red RGB value in integer format.
   */
  int getRed();

  /**
   * Get the green value of this color.
   *
   * @return the green RGB value in integer format.
   */
  int getGreen();

  /**
   * Get the blue value of this color.
   *
   * @return the blue RGB value in integer format.
   */
  int getBlue();

  /**
   * Get the color value of this color according to the given index. 0 - red  1 - green  2 - blue
   *
   * @return the RGB value demanded by the index in integer format.
   * @throws IllegalArgumentException if index out of bounds (less than 0 or greater than 2)
   */
  int getValue(int index);

}
