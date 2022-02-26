package model;

/**
 * Represents all types of operations that can be performed on an image.
 */
public interface ImageEditors {

  /**
   * Apply this image operation onto an image.
   *
   * @param img the image to apply the editors.
   * @return the image after this image operation
   * @throws IllegalArgumentException if the given image is null
   */
  ImageInterface apply(ImageInterface img);

  /**
   * Overrides the toString method.
   *
   * @return the name of the image editor
   */
  String toString();

}
