package controller;

/**
 * This interface includes all features that the Image Program supports. It consists of methods such
 * as Save, Load, Color Transformation and Filtering operations, exit program and drawing.
 */
public interface Features {

  /**
   * This method process is to process the script batch commands.
   */
  void process();

  /**
   * This method contains the functionalities that require the use of loading an image to the
   * program and saving them (ex. load and loadAll/ save and save all).
   */
  void setCommands(String command, String fileName);

  /**
   * This method contains the functionalities that are related to a layer of an image (ex. creating
   * a layer, removing a layer, setting the current layer, setting the current layer's visibility).
   */
  void layerCommands(String cmd);


  /**
   * This method contains the functionalities relating to drawing an image (ex. checkerboard and
   * rainbow).
   */
  void draw(String command, String first, String second);

  /**
   * This method contains the functionalities relating to editing the image. It contains the image
   * editor functions (ex. blur, sepia, greyscale).
   */
  void applyEffect(String command);

  /**
   * This method reads the given file and processes through the givens script.
   */
  void readScript(String fileName);

  /**
   * This method indicates whether the model is visible or not. It is implemented in the
   * controller of this program.
   *
   * @return the boolean indicating if it's visible.
   */
  boolean isVisible();

}
