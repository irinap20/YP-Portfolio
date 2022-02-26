package model;

import controller.ImageUtilAddition;

/**
 * Represents the filtering operations that can be performed on an image.
 */
public abstract class Filtering implements ImageEditors {

  protected double[][] kernel;

  /**
   * Constructs a filtering operation of the given kernel.
   *
   * @param kernel the kernel that will be applied to the image
   * @throws IllegalArgumentException if kernel is null or kernel is not valid
   */
  public Filtering(double[][] kernel) {
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel can't be null.");
    }
    if (!isValidKernel(kernel)) {
      throw new IllegalArgumentException("Invalid Kernel");
    }
    this.kernel = kernel;
  }

  /**
   * The method applies the filter to the image.
   *
   * @param img the image to be edited
   * @return filtered Image
   */
  public ImageInterface apply(ImageInterface img) {
    if (img == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    RGB[][] newPixels = new RGB[img.getHeight()][img.getWidth()];
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        RGB newRGB = applyKernel(img, j, i);
        newPixels[i][j] = newRGB;
      }
    }
    return new Image(newPixels, img.getMaxValue());
  }


  @Override
  public String toString() {
    return String.format("ColorTransformation-%s", this.getClass());
  }

  /**
   * Applies this kernel to its covered range of pixels.
   *
   * @param img the image to be edited
   * @param x   the horizontal coordinate of current pixel to be applied kernel
   * @param y   the vertical coordinate of current pixel to be applied kernel
   * @return the new color this pixel now have after applying kernel
   */
  // uses the kernel to calculate each RGB values
  private RGB applyKernel(ImageInterface img, int x, int y) {
    // center of the kernel
    int center = (this.kernel.length) / 2;
    // position of the top-left corner of kernel on actual image
    int imgX = x - center;
    int imgY = y - center;
    double red = 0;
    double green = 0;
    double blue = 0;
    // each time kernel index increases by one (goes from top left to
    // right), the index on the image will also go from top-left to right
    for (int i = 0; i < this.kernel.length; i++) { // height
      for (int j = 0; j < this.kernel.length; j++) { // width
        // index moves with kernel index
        int moveX = imgX + j;
        int moveY = imgY + i;
        if (moveX >= 0 && moveX < img.getWidth() &&
            moveY >= 0 && moveY < img.getHeight()) {
          double val = this.kernel[i][j];
          red += val * (img.getPixel(moveX, moveY).getRed());
          green += val * (img.getPixel(moveX, moveY).getGreen());
          blue += val * (img.getPixel(moveX, moveY).getBlue());
        }
      }
    }
    int maxVal = img.getMaxValue();
    return new RGB(ImageUtilAddition.clamp((int) red, maxVal),
        ImageUtilAddition.clamp((int) green, maxVal),
        ImageUtilAddition.clamp((int) blue, maxVal));
  }


  /**
   * Determines whether the kernel is a square with a center, thus a valid kernel.
   *
   * @param kernel the kernel to be validated
   * @return a boolean indicating whether the given kernel is valid
   */
  private boolean isValidKernel(double[][] kernel) {
    for (double[] k : kernel) {
      if (kernel.length != k.length) {
        return false;
      }
    }
    return kernel.length % 2 == 1;
  }


}
