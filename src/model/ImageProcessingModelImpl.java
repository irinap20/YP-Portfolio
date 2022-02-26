package model;

/**
 * This class represents the implementation of the image processing model interface. It handles the
 * import and export of an image and performs specific operations on the image.
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {

  private ImageInterface img;
  private ImageEditors ie;

  /**
   * Constructs an implementation of image processing model with an image and an editing operation.
   *
   * @param img the image awaiting to be modified
   * @param ie  the operation to be performed on the image
   * @throws IllegalArgumentException when null arguments were passed
   */
  public ImageProcessingModelImpl(ImageInterface img, ImageEditors ie) {
    if (img == null || ie == null) {
      throw new IllegalArgumentException("Null image or image editors.");
    }
    this.img = img;
    this.ie = ie;
  }

  /**
   * Constructs an ImageProcessingModelImpl object without setting the fields, the default
   * constructor.
   */
  public ImageProcessingModelImpl() {
    this.img = null;
    this.ie = null;
  }

  /**
   * Determines whether the image processing has started, meaning fields were initialized.
   *
   * @return a boolean representing whether the image processing has started
   */
  private boolean hasStarted() {
    return img != null && ie != null;
  }

  @Override
  public ImageInterface getImage() {
    if (img != null) {
      return this.img;
    } else {
      throw new IllegalStateException("Cannot get image because image has not been loaded.");
    }
  }

  @Override
  public void setImage(ImageInterface img) {
    if (img == null) {
      throw new IllegalArgumentException("Image Can't Be Null");
    }
    this.img = img;
  }


  @Override
  public ImageEditors getEffect() {
    if (ie != null) {
      return this.ie;
    } else {
      throw new IllegalStateException(
          "Cannot get effect because image editor has not been loaded.");
    }
  }

  @Override
  public void setEffect(ImageEditors ie) {
    if (ie == null) {
      throw new IllegalArgumentException("Effect Can't Be Null");
    }
    this.ie = ie;
  }

  @Override
  public int getHeightImage() {
    if (this.img == null) {
      throw new IllegalStateException("Image needs to be loaded");
    }
    return img.getHeight();
  }

  @Override
  public int getWidthImage() {
    if (this.img == null) {
      throw new IllegalStateException("Image needs to be loaded");
    }
    return img.getWidth();
  }

  @Override
  public void applyOperation() {
    if (hasStarted()) {
      this.img = ie.apply(img);
    } else {
      throw new IllegalStateException(
          "Cannot apply operation because image processing has not started.");
    }
  }

}
