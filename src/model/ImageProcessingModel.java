package model;

/**
 * This class represents the model interface for the Image Processing program. It includes various
 * image editing methods such as Filtering (blurring and sharpening images) and ColorTransformation
 * (creating gray scale and sepia images).
 */
public interface ImageProcessingModel extends ImageProcessingModelState {

  /**
   * Sets the image field of this image processing model.
   *
   * @param img the image to be processed
   * @throws IllegalArgumentException if the given image is null
   */
  void setImage(ImageInterface img);

  /**
   * Sets the image editors field of this image processing model.
   *
   * @param effect the image editor that processes the image
   * @throws IllegalArgumentException if the given image editor is null
   */
  void setEffect(ImageEditors effect);

  /**
   * Apply the image editor's operation onto the image.
   *
   * @throws IllegalStateException if such image processing model does not exist
   */
  void applyOperation();


}
