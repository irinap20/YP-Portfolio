package model;

/**
 * A class that creates checkerboard images.
 */
public class CheckerBoard implements ImageProgram {

  private final int tileSize; // size of each square tile in number of pixels
  private final int numTiles; // number of tiles on each side of a checkerboard
  private final RGB[] colors; // colors to use

  /**
   * Constructs a checkerboard object containing its information.
   *
   * @param tileSize size of each square tile in number of pixels
   * @param numTiles number of tiles on each side of the checkerboard
   * @param colors   colors to use in the checkerboard
   * @throws IllegalArgumentException if any of the inputs are invalid
   */
  public CheckerBoard(int tileSize, int numTiles, RGB[] colors) {
    if (tileSize <= 0 || numTiles <= 0 || colors == null || colors.length <= 0) {
      throw new IllegalArgumentException("invalid arguments to construct a checkerboard.");
    }
    this.tileSize = tileSize;
    this.numTiles = numTiles;
    this.colors = colors;
  }

  /**
   * Constructs a checkerboard object containing its information.
   *
   * @param tileSize size of each square tile in number of pixels
   * @param numTiles number of tiles on each side of the checkerboard
   * @throws IllegalArgumentException if any of the inputs are invalid
   */
  public CheckerBoard(int tileSize, int numTiles) {
    if (tileSize <= 0 || numTiles <= 0) {
      throw new IllegalArgumentException("invalid arguments to construct a checkerboard.");
    }
    this.tileSize = tileSize;
    this.numTiles = numTiles;
    RGB[] colors = new RGB[2];
    colors[0] = new RGB(250, 0, 0);
    colors[1] = new RGB(0, 250, 0);
    this.colors =  colors;
  }

  @Override
  public ImageInterface create() {
    RGB[][] pixels = new RGB[tileSize * numTiles][tileSize * numTiles];
    int counter = 0;  // indicates which color to use from the colors provided by user
    for (int i = 0; i < numTiles; i++) {
      for (int j = 0; j < numTiles; j++) {
        counter = (i + j) % colors.length;
        colorSquareTile(pixels, i, j, colors[counter]);
      }
    }
    return new Image(pixels);
  }

  /**
   * Helps color a square tile in an image.
   *
   * @param pixels the image where a square tile needs to be colored
   * @param row    the row of the coordinates of the square tile
   * @param column the column of the coordinates of the square tile
   * @param color  the color to be colored to the square tile
   */
  private void colorSquareTile(RGB[][] pixels, int row, int column, RGB color) {
    for (int i = 0; i < tileSize; i++) {
      for (int j = 0; j < tileSize; j++) {
        pixels[row * tileSize + i][column * tileSize + j] = color;
      }
    }
  }
}