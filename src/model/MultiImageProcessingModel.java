package model;

import java.util.Stack;

/**
 * The MultiImageProcessingModel is an interface for the multiImageProcessingModelImpl. It extends
 * teh ImageProcessingModel and adds new functionality to the program. This class allows the user to
 * work with multiple layers of images.
 */
public interface MultiImageProcessingModel extends ImageProcessingModel {

  /**
   * Creates a layer for the image.
   */
  void createLayer();

  /**
   * removes the created layer in a given index.
   *
   * @param index the index of the layer to delete
   * @return a layer that has been removed
   */
  ILayer removeLayer(int index);

  /**
   * moves the layer from one index to another.
   *
   * @param from the index to move from
   * @param to   the index to place
   */
  void moveLayer(int from, int to);

  /**
   * Sets the current layer to be the index.
   *
   * @param index the index to set the current layer to
   */
  void setCurrent(int index);

  /**
   * Sets the visibility of the image layer.
   *
   * @param index the layer to change visibility
   * @param b     make it visible or invisible
   */
  void visible(int index, boolean b);

  /**
   * Is the layer visible.
   *
   * @return boolean that represents whether the layer is visible.
   */
  boolean isVisible();

  /**
   * Is the layer in the given index visible.
   *
   * @return boolean that represents whether the layer is visible.
   */
  boolean isThisVisible(int index);

  /**
   * Draws the image program in the model.
   *
   * @param ip the image program to draw
   * @return an image
   */
  ImageInterface draw(ImageProgram ip);

  /**
   * Gets all the layer in the stack.
   *
   * @return a stack of ILayers
   */
  Stack<ILayer> getAll();

  /**
   * Gets the current layer.
   *
   * @return the index of the current layer
   */
  int getCurrent();

}

