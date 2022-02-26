package model;

/**
 * The state of the image processing model. Represents the current state including the current
 * image, editor, height of the image, and width of the image.
 */
public interface ImageProcessingModelState {

  /**
   * Get the image awaiting to be processed of this image processing model.
   *
   * @return the image in this image processing model
   * @throws IllegalStateException when image processing has not started
   */
  ImageInterface getImage();

  /**
   * Get the image editor of this image processing model.
   *
   * @return the image editor within this image processing model
   * @throws IllegalStateException when image processing has not started
   */
  ImageEditors getEffect();

  /**
   * Get the height of the image.
   *
   * @return the height of the image as an integer
   * @throws IllegalStateException when the image is not given
   */
  int getHeightImage();

  /**
   * Get the width of the image.
   *
   * @return the width of the image as an integer
   * @throws IllegalStateException when the image is not given
   */
  int getWidthImage();

}
