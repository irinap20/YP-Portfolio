package model;

/**
 * Performs a sepia effect on an image using the pre-defined sepia matrix.
 */
public class Sepia extends ColorTransformation {

  /**
   * Constructs a sepia editor object with a set color transformation matrix.
   */
  public Sepia() {
    super(new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}});
  }

}
