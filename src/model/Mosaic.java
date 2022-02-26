package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an image mosaic feature that applies mosaic to an image based on the number of seeds.
 */
public class Mosaic implements ImageEditors {

  private final Random rand;
  private final int numSeeds;

  /**
   * Creates an image mosaic object with the given number of seeds, which determines the degree of
   * mosaicing.
   *
   * @param numSeeds number of pixels to be selected in the image that are seeds, starting from 1
   * @throws IllegalArgumentException if number of seeds is too small
   */
  public Mosaic(int numSeeds) {
    if (numSeeds <= 0) {
      throw new IllegalArgumentException("Need more seeds to perform mosaic.");
    }
    this.rand = new Random();
    this.numSeeds = numSeeds;
  }

  @Override
  public ImageInterface apply(ImageInterface img) {
    if (img == null) {
      throw new IllegalArgumentException("Null image.");
    }
    if (numSeeds > img.getWidth() * img.getHeight()) {
      throw new IllegalStateException("Too many seeds.");
    }

    // randomly create a number of seeds
    Map<Indices, List<Indices>> groups = new HashMap<>(numSeeds);
    for (int i = 0; i < numSeeds; i++) {
      int row = rand.nextInt(img.getHeight());
      int column = rand.nextInt(img.getWidth());
      Indices seedPosn = new Indices(row, column);
      if (!groups.containsKey(seedPosn)) {
        groups.put(seedPosn, new ArrayList<>());
      } else {
        i -= 1;
      }
    }

    // for every pixel in the image
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Indices pixelPosn = new Indices(i, j);
        // if not a seed
        if (!groups.containsKey(pixelPosn)) {
          //find its closet seed and join its group
          findSeed(pixelPosn, groups);
        }
      }
    }

    RGB[][] newPixels = new RGB[img.getHeight()][img.getWidth()];
    // average color in every group
    for (Indices seed : groups.keySet()) {
      averageColor(seed, groups, img.getPixels(), newPixels);
    }

    return new Image(newPixels);
  }

  /**
   * Finds the closet seed of the given pixel position and joins the group of that seed.
   *
   * @param pixelPosn the current pixel position awaiting to be paired with the closet seed
   * @param groups    all seed groups available
   */
  private void findSeed(Indices pixelPosn, Map<Indices, List<Indices>> groups) {
    Indices closet = null;
    double min = Double.POSITIVE_INFINITY;
    for (Indices seed : groups.keySet()) {
      if (pixelPosn.distance(seed) < min) {
        min = pixelPosn.distance(seed);
        closet = seed;
      }
    }
    groups.get(closet).add(pixelPosn);
  }

  /**
   * Average the colors of the group that the given seed belongs and reflect on the new mosaic image
   * pixels.
   *
   * @param seed      the current seed of the group
   * @param groups    all seed groups
   * @param oldPixels the pixels of the original image
   * @param newPixels the pixels of the mosaic image
   */
  private void averageColor(Indices seed, Map<Indices, List<Indices>> groups, RGB[][] oldPixels,
      RGB[][] newPixels) {
    int totalR = oldPixels[seed.getI()][seed.getJ()].getRed();
    int totalG = oldPixels[seed.getI()][seed.getJ()].getGreen();
    int totalB = oldPixels[seed.getI()][seed.getJ()].getBlue();
    int size = 1;

    for (Indices pixelPosn : groups.get(seed)) {
      totalR += oldPixels[pixelPosn.getI()][pixelPosn.getJ()].getRed();
      totalG += oldPixels[pixelPosn.getI()][pixelPosn.getJ()].getGreen();
      totalB += oldPixels[pixelPosn.getI()][pixelPosn.getJ()].getBlue();
      size += 1;
    }

    int aveR = totalR / size;
    int aveG = totalG / size;
    int aveB = totalB / size;
    RGB groupColor = new RGB(aveR, aveG, aveB);

    newPixels[seed.getI()][seed.getJ()] = groupColor;
    for (Indices pixelPosn : groups.get(seed)) {
      newPixels[pixelPosn.getI()][pixelPosn.getJ()] = groupColor;
    }
  }

  /**
   * Represents the indices of a pixel in a 2D array of pixels.
   */
  private static class Indices {

    private final int i;
    private final int j;

    /**
     * Constructs an indices with the given i and j index.
     *
     * @param i the row index of a 2D array
     * @param j the column index of a 2D array
     * @throws IllegalArgumentException if row and column indexing is too small
     */
    private Indices(int i, int j) {
      if (i < 0 || j < 0) {
        throw new IllegalArgumentException("Invalid indices.");
      }
      this.i = i;
      this.j = j;
    }

    /**
     * Gets the row index of this indices.
     *
     * @return the row index value
     */
    private int getI() {
      return this.i;
    }

    /**
     * Gets the column index of this indices.
     *
     * @return the column index value
     */
    private int getJ() {
      return this.j;
    }

    /**
     * Compute the distance between this indices and the given indices.
     *
     * @param other the given indices to be compare with this indices
     * @return the distance between this indices and the given indices in a double
     * @throws IllegalArgumentException if null argument is passed
     */
    private double distance(Indices other) {
      if (other == null) {
        throw new IllegalArgumentException("The given indices is null.");
      }
      return Math
          .sqrt(Math.pow(other.getI() - this.getI(), 2) + Math.pow(other.getJ() - this.getJ(), 2));
    }
  }
}
