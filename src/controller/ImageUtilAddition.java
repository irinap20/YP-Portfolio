package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import model.Image;
import model.ImageInterface;
import model.RGB;

/**
 * This class is an addition (extension) to the ImageUtil class. It contains more method to support
 * PNG and JPEG images.
 */
public class ImageUtilAddition extends ImageUtil {

  /**
   * Read an image file and return contents to ImageInterface.
   *
   * @param filename the path to the file (file name of the file)
   * @return image form that this program utilize.
   */
  public static ImageInterface readImage(String filename) throws IOException {
    BufferedImage input;

    input = ImageIO.read(new FileInputStream(filename));
    RGB[][] pixels = new RGB[input.getHeight()][input.getWidth()];

    for (int i = 0; i < input.getHeight(); i++) {
      for (int j = 0; j < input.getWidth(); j++) {
        int rgb = input.getRGB(j, i);
        Color col = new Color(rgb, true);
        int red = col.getRed();
        int green = col.getGreen();
        int blue = col.getBlue();
        RGB pixel = new RGB(red, green, blue);
        pixels[i][j] = pixel;
      }
    }
    ImageInterface image = new Image(pixels, 256);
    return image;
  }

  /**
   * Export an image to a file in the given format.
   *
   * @param img      the image that needs to be exported
   * @param filename the path to the file
   * @throws IOException if the file cannot be written to the provided path
   */
  public static void export(ImageInterface img, String filename) throws IOException {
    if (img == null || filename == null) {
      throw new IllegalArgumentException("inputs can't be null");
    }
    BufferedImage output = ImageUtilAddition.convertImage(img);
    String fileType = filename.substring(filename.indexOf(".") + 1);
    System.out.print(fileType);
    ImageIO.write(output, fileType, new File(filename));
    System.out.print("Image Exported");
  }

  /**
   * Converts an Image to a BufferedImage.
   *
   * @param img the image that needs to be converted to BufferedImage
   * @return BufferedImage that can be used for export
   */
  public static BufferedImage convertImage(ImageInterface img) throws IOException {
    BufferedImage bufferedImage = new BufferedImage(img.getWidth(), img.getHeight(),
        BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        RGB rgb = img.getPixel(j, i);
        int red = rgb.getRed();
        int green = rgb.getGreen();
        int blue = rgb.getBlue();
        Color c = new Color(ImageUtilAddition.clamp(red, 255),
            ImageUtilAddition.clamp(green, 255), ImageUtilAddition.clamp(blue, 255));
        int rgba = c.getRGB();
        bufferedImage.setRGB(j, i, rgba);
      }
    }
    return bufferedImage;
  }

  /**
   * This method exports the text file that has all the location of the images.
   *
   * @param filename the new file name
   * @param content  the location of images
   */
  public static void exportText(String filename, String content) throws IllegalArgumentException {
    try {
      FileOutputStream newFile = new FileOutputStream(filename);
      try {
        newFile.write(content.getBytes());
        System.out.println("File Exported");
      } catch (IOException e) {
        throw new IllegalArgumentException("File Formatting Exception");
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
  }

  /**
   * Read an image file and convert it into String.
   *
   * @param filename the path of the file
   * @return an image object that was converted from the PPM format
   * @throws IllegalArgumentException if the given file path is not found
   */
  public static String readTextFile(String filename) throws IllegalArgumentException {
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
      builder.append(s + System.lineSeparator());
    }
    return builder.toString();
  }

  /**
   * Ensures a channel value of an RGB is within the accepted range. Prevents overflow and
   * underflow.
   *
   * @param channel  the channel value to be evaluated
   * @param maxValue the maximum value a channel can reach
   * @return the channel value after fixing to the appropriate range
   */
  public static int clamp(int channel, int maxValue) {
    if (channel > maxValue) {
      return maxValue;
    } else if (channel < 0) {
      return 0;
    } else {
      return channel;
    }
  }

  private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnoprstuvwxyz0123456789";

  /**
   * This is a method to generate the random name for files. It uses the ALPHA_NUMERIC_STRING.
   *
   * @param count the number of different counts
   * @return a random alphanumeric string
   */
  public static String randomAlphaNumeric(int count) {
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
      int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
      builder.append(ALPHA_NUMERIC_STRING.charAt(character));
    }
    return builder.toString();
  }
}


