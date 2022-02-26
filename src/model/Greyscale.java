package model;

/**
 * This class performs a greyscale operation on an image using a pre-defined greyscale matrix.
 */
public class Greyscale extends ColorTransformation {

  /**
   * Constructs a greyscale object with a set matrix that will be applied to an image.
   */
  public Greyscale() {
    super(new double[][]{{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}});
  }
}
