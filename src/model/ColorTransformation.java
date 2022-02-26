package model;

import controller.ImageUtilAddition;

/**
 * This abstract class takes care of the color transformations being applied to images. Its
 * operations include greyscale and sepia.
 */
public abstract class ColorTransformation implements ImageEditors {

  double[][] matrix;
  private final int CHANNEL = 3;
  private final int RED = 0;
  private final int GREEN = 1;
  private final int BLUE = 2;

  /**
   * Constructs a color transformation editor with a matrix scale to be applied on the image.
   *
   * @param matrix the matrix to be multiplied with the color of the pixels
   * @throws IllegalArgumentException if the given matrix is null or matrix is invalid
   */
  ColorTransformation(double[][] matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix can't be null");
    }
    if (validMatrix(matrix)) {
      this.matrix = matrix;
    } else {
      throw new IllegalArgumentException("Matrix Invalid");
    }
  }

  /**
   * Determines whether the given matrix is a 3x3 matrix that can be multiplied with RGB values.
   *
   * @param matrix the matrix to be evaluated
   * @return a boolean indicating a valid matrix
   */
  private boolean validMatrix(double[][] matrix) {
    if (matrix.length != CHANNEL) {
      return false;
    }
    for (double[] m : matrix) {
      if (m.length != CHANNEL) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ImageInterface apply(ImageInterface img) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    RGB[][] newPixels = new RGB[img.getHeight()][img.getWidth()];
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        RGB oldColor = img.getPixel(j, i);
        RGB newColor = applyTransform(oldColor, img);
        newPixels[i][j] = newColor;
      }
    }
    return new Image(newPixels, img.getMaxValue());
  }


  @Override
  public String toString() {
    return String.format("ColorTransformation-%s", this.getClass());
  }

  /*
r′=a11r+a12g+a13b
g′=a21r+a22g+a23b
b′=a31r+a32g+a33b
 */

  /**
   * Transforms the color of every pixel in this image according to the scale matrix.
   *
   * @param color the old color of a pixel
   * @param img   the image where the color of a pixel will be transformed
   * @return the new color to be documented in the image
   */
  private RGB applyTransform(RGB color, ImageInterface img) {
    double[] orderRGB = new double[CHANNEL];
    for (int i = 0; i < CHANNEL; i++) {
      int num = 0;
      for (int j = 0; j < CHANNEL; j++) {
        // color.getValue(j) => 0 = gets red, 1 = gets Green, 2 = gets Blue
        num += color.getValue(j) * matrix[i][j];
      }
      orderRGB[i] = num;
    }
    return new RGB(ImageUtilAddition.clamp((int) orderRGB[RED], img.getMaxValue()),
        ImageUtilAddition.clamp((int) orderRGB[GREEN], img.getMaxValue()),
        ImageUtilAddition.clamp((int) orderRGB[BLUE], img.getMaxValue()));
  }

}
