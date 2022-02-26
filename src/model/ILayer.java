package model;

/**
 * This is an interface for Layer. It extends the ImageInterface (to manipulate the image stored in
 * the image) and additional functionality to manipulate the layer class itself.
 */
public interface ILayer {

  /**
   * Determines if the layer should be visible or not.
   *
   * @param visible the desired visibility of this image
   */
  void setVisibility(boolean visible);

  /**
   * Sets the image field of this layer.
   *
   * @param img the image to be loaded into this layer
   */
  void setImage(ImageInterface img);

  /**
   * Gets the visibility of this layer.
   *
   * @return the visibility of this layer as a boolean
   */
  boolean getVisibility();

  /**
   * Gets the image in the ILayer.
   *
   * @return an ImageInterface
   */
  ImageInterface getImage();

  /**
   * Shows if the image in the layer is empty.
   *
   * @return a boolean
   */
  boolean isEmpty();
}
