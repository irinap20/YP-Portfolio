package model;

/**
 * Represents the ImageInterface and is open to extension if new types of images are added to the
 * program. It includes methods such as getWidth(), getMaxValue() and write().
 */
public interface ImageInterface {

  /**
   * Get the specified pixel within the image pixels.
   *
   * @param x the x-coordinate of the pixel in the image pixels
   * @param y the y-coordinate of the pixel in the image pixels
   * @return the pixel with the given (x,y) coordinates in the pixels
   */
  RGB getPixel(int x, int y);

  /**
   * Get the height of the image.
   *
   * @return an integer representing the height of the image
   */
  int getHeight();


  /**
   * Get the width of the image.
   *
   * @return an integer representing the height of the image
   */
  int getWidth();

  /**
   * Get the maximum RGB value of the image.
   *
   * @return an integer representing the maximum RGB value
   */
  int getMaxValue();

  /**
   * Gets this image or the image of a layer.
   * @return this image or the image in a layer
   */
  Image getImage();

  /**
   * Gets the pixels in this image or this layer.
   *
   * @return the 2D array of pixels in this image/layer
   */
  RGB[][] getPixels();
}
