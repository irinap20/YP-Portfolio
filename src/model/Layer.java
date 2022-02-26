package model;

import java.util.Optional;

/**
 * This class represents a layer and contains an Optional Image and boolean (visibility) as its
 * fields. The image could be empty or present and the visibility of the layer changes depending on
 * the boolean value.
 */
public class Layer implements ILayer {

  Optional<ImageInterface> img;
  boolean visible;

  /**
   * The constructor for the layer class that takes in Image as its parameter.
   */
  public Layer() {
    this.img = Optional.empty();
    this.visible = true;
  }

  /**
   * The constructor for the layer class which takes in an Image as its parameter.
   *
   * @param image an Image to store in the layer: has to be present.
   */
  public Layer(ImageInterface image) {
    if (image == null) {
      throw new IllegalArgumentException("Image Can't Be Null");
    }
    this.img = Optional.of(image.getImage());
    this.visible = true;
  }

  @Override
  public String toString() {
    String res = "";
    if (img.isPresent()) {
      res = img.get().toString();
    }
    return res + visible + "\n";
  }

  @Override
  public Image getImage() throws IllegalStateException {
    if (img.isPresent()) {
      return img.get().getImage();
    } else {
      throw new IllegalStateException("No image in this layer.");
    }
  }


  @Override
  public void setVisibility(boolean visible) {
    this.visible = visible;
  }

  @Override
  public void setImage(ImageInterface img) {
    this.img = Optional.of(img);
  }

  @Override
  public boolean getVisibility() {
    return this.visible;
  }

  @Override
  public boolean isEmpty() {
    return this.img.isEmpty();
  }
}
