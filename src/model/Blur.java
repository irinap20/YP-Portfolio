package model;

/**
 * Performs an image operation that blurs the current image.
 */
public class Blur extends Filtering {

  /**
   * Constructs a blur operation with a default kernel.
   */
  public Blur() {
    super(new double[][]{{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.062}});
  }

}
