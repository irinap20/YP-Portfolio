package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import model.ImageInterface;
import model.Image;
import model.RGB;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file and convert it into an ImageInterface object.
   *
   * @param filename the path of the file
   * @return an image object that was converted from the PPM format
   * @throws IllegalArgumentException if the given file path is not found
   */
  public static ImageInterface readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    RGB[][] pixels = new RGB[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        RGB color = new RGB(r, g, b);
        pixels[i][j] = color;
      }
    }
    return new Image(pixels, maxValue);
  }

  /**
   * export an image file with the given destination file path and given content.
   *
   * @param img      the image to be exported
   * @param filename the path of the destination file
   * @throws IllegalArgumentException if the given file path is not found or the new file was not
   *                                  able to write
   */

  public static void exportFile(ImageInterface img, String filename)
      throws IllegalArgumentException {
    try {
      FileOutputStream newFile = new FileOutputStream(filename);
      try {
        newFile.write(ImageUtil.write(img).getBytes());
        System.out.println("Image Exported");
      } catch (IOException e) {
        throw new IllegalArgumentException("File Formatting Exception");
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + ImageUtil.write(img) + " not found!");
    }
  }


  /**
   * Format an image into PPM format ready to export.
   *
   * @return a PPM format string with the content of the image
   */

  private static String write(ImageInterface img) {
    StringBuilder content = new StringBuilder();
    content.append("P3\n");
    content.append(img.getWidth()).append(" ");
    content.append(img.getHeight()).append("\n");
    content.append(img.getMaxValue()).append("\n");
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        RGB color = img.getPixel(j, i);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        content.append(red).append("\n");
        content.append(green).append("\n");
        content.append(blue).append("\n");
      }
    }
    return content.toString();
  }


}
