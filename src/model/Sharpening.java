package model;

/**
 * Performs an image operation that sharpens the current image.
 */
public class Sharpening extends Filtering {

  /**
   * Constructs a sharpening operation with a default kernel.
   */
  public Sharpening() {
    super(new double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}});
  }
}
