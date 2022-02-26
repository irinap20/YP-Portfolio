package model;


/**
 * Represents a downsizing image processing operation that downsizes an image to the given
 * dimension.
 */
public class Downscale implements ImageEditors {

  private final int newHeight;
  private final int newWidth;

  /**
   * Default constructor that creates an object of model.Downscale with the given height and width.
   * Represents a downsize operation that will downsize an image to the given size.
   *
   * @param newHeight the new height of the image in integer
   * @param newWidth  the new width of the image in integer
   * @throws IllegalArgumentException if either height or width is invalid
   */
  public Downscale(int newHeight, int newWidth) {
    if (newHeight <= 0 || newWidth <= 0) {
      throw new IllegalArgumentException("New dimension is too small.");
    }
    this.newHeight = newHeight;
    this.newWidth = newWidth;
  }

  @Override
  public ImageInterface apply(ImageInterface img) {
    if (img == null) {
      throw new IllegalArgumentException("Null image.");
    }
    if (newHeight > img.getHeight() || newWidth > img.getWidth()) {
      throw new IllegalStateException("Please reset the new image dimension.");
    }
    RGB[][] newPixels = new RGB[newHeight][newWidth];
    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth; j++) {
        double row = ((double) i) / newHeight * img.getHeight();
        double column = ((double) j) / newWidth * img.getWidth();
        newPixels[i][j] = new RGB(computeColor(row, column, img, 0),
            computeColor(row, column, img, 1),
            computeColor(row, column, img, 2));
      }
    }
    return new Image(newPixels);
  }

  /**
   * Computes the specified color value of the pixel in the downsized image from the given location
   * in the original image based on the coordinates accuracy.
   *
   * @param row        the y-coordinate of the mapped pixel in the original image of type double
   * @param column     the x-coordinate of the mapped pixel in the original image of type double
   * @param img        the original image before downsizing
   * @param colorIndex the color index indicating which color value to get (0-red 1-green 2-blue)
   * @return the computed color value of the pixel in the downsized image as an integer
   */
  private int computeColor(double row, double column, ImageInterface img, int colorIndex) {
    if (row % 1 == 0 && column % 1 == 0) {
      return img.getPixel((int) column, (int) row).getValue(colorIndex);
    } else if (row % 1 == 0) {
      return computeColorColDouble(row, column, img, colorIndex);
    } else if (column % 1 == 0) {
      return computeColorRowDouble(row, column, img, colorIndex);
    } else {
      return computeColorAllDouble(row, column, img, colorIndex);
    }
  }

  /**
   * Computes the specified color value of the pixel in the downsized image from the given location
   * in the original image when the y-coordinate is a non-integer but the x-coordinate is an
   * integer.
   *
   * @param row        the y-coordinate of the mapped pixel in the original image in decimal
   * @param column     the x-coordinate of the mapped pixel in the original image in integer
   * @param img        the original image before downsizing
   * @param colorIndex the color index indicating which color value to get (0-red 1-green 2-blue)
   * @return the computed color value of the pixel in the downsized image as an integer
   */
  private int computeColorRowDouble(double row, double column, ImageInterface img, int colorIndex) {
    RGB a = img.getPixel((int) column, (int) Math.floor(row));
    RGB b = img.getPixel((int) column, (int) Math.ceil(row));
    double lowerRowDif = row - Math.floor(row);
    double upperRowDif = Math.ceil(row) - row;
    double colorVal = a.getValue(colorIndex) * upperRowDif + b.getValue(colorIndex) * lowerRowDif;
    return (int) Math.round(colorVal);
  }

  /**
   * Computes the specified color value of the pixel in the downsized image from the given location
   * in the original image when the x-coordinate is a non-integer but the y-coordinate is an
   * integer.
   *
   * @param row        the y-coordinate of the mapped pixel in the original image in integer
   * @param column     the x-coordinate of the mapped pixel in the original image in decimal
   * @param img        the original image before downsizing
   * @param colorIndex the color index indicating which color value to get (0-red 1-green 2-blue)
   * @return the computed color value of the pixel in the downsized image as an integer
   */
  private int computeColorColDouble(double row, double column, ImageInterface img, int colorIndex) {
    RGB a = img.getPixel((int) Math.floor(column), (int) row);
    RGB b = img.getPixel((int) Math.ceil(column), (int) row);
    double lowerColDif = column - Math.floor(column);
    double upperColDif = Math.ceil(column) - column;
    double colorVal = a.getValue(colorIndex) * upperColDif + b.getValue(colorIndex) * lowerColDif;
    return (int) Math.round(colorVal);
  }


  /**
   * Computes the specified color value of the pixel in the downsized image from the given location
   * in the original image if the location coordinates are non-integers.
   *
   * @param row        the y-coordinate of the mapped pixel in the original image in decimal
   * @param column     the x-coordinate of the mapped pixel in the original image in decimal
   * @param img        the original image before downsizing
   * @param colorIndex the color index indicating which color value to get (0-red 1-green 2-blue)
   * @return the computed color value of the pixel in the downsized image as an integer
   */
  private int computeColorAllDouble(double row, double column, ImageInterface img, int colorIndex) {
    RGB a = img.getPixel((int) Math.floor(column), (int) Math.floor(row));
    RGB b = img.getPixel((int) Math.ceil(column), (int) Math.floor(row));
    RGB c = img.getPixel((int) Math.floor(column), (int) Math.ceil(row));
    RGB d = img.getPixel((int) Math.ceil(column), (int) Math.ceil(row));
    double lowerColDif = column - Math.floor(column);
    double upperColDif = Math.ceil(column) - column;
    double m = b.getValue(colorIndex) * (lowerColDif) + a.getValue(colorIndex) * (upperColDif);
    double n = d.getValue(colorIndex) * (lowerColDif) + c.getValue(colorIndex) * (upperColDif);
    double colorVal = n * (row - Math.floor(row)) + m * (Math.ceil(row) - row);
    return (int) Math.round(colorVal);
  }
}
