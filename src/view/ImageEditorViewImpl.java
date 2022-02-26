package view;

import java.io.IOException;
import model.MultiImageProcessingModel;

/**
 * This class represents the visual display of of ImageEditor. It implements FreecellView and
 * appendable * as its destination to transmit the information to the given appendable.
 */
public class ImageEditorViewImpl implements ImageEditorView {

  MultiImageProcessingModel model;
  Appendable destination;

  /**
   * The constructor for ImageEditorView initializes both this.model and this.destination. It sets
   * this.destination as null.
   *
   * @param model the ImageProcessingModelState to operate on
   */
  public ImageEditorViewImpl(MultiImageProcessingModel model) {
    this.model = model;
    this.destination = null;
  }


  /**
   * This constructor also takes in appendable object as its input. The constructor initializes
   * this.destination to be the given appendable object.
   *
   * @param model       the ImageProcessingModelState to operate on
   * @param destination the appendable destination to send information
   */
  public ImageEditorViewImpl(MultiImageProcessingModel model, Appendable destination) {
    if (model == null || destination == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    this.destination = destination;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    }
    try {
      model.getImage();
      model.getEffect();
    } catch (IllegalStateException e) {
      StringBuilder string = new StringBuilder();
      string.append("ImageEditor Type: \n");
      string.append("Image Width: \n");
      string.append("Image Height: \n");
      string.append(String.format("Number of Layers: %d \n", model.getAll().size()));
      string.append(String.format("Current Layer: %d \n", model.getCurrent()));
      return string.toString();
    }
    str.append(String.format("ImageEditor Type: %s \n", model.getEffect().toString()));
    str.append(String.format("Image Width: %d \n", model.getWidthImage()));
    str.append(String.format("Image Height: %d \n", model.getHeightImage()));
    str.append(String.format("Number of Layers: %d \n", model.getAll().size()));
    str.append(String.format("Current Layer: %d \n", model.getCurrent()));
    return str.toString();
  }


  @Override
  public void renderMessage(String message) throws IOException {
    if (this.destination == null || message == null) {
      System.out.print(message);
    } else {
      this.destination.append(message);
    }
  }

  @Override
  public void renderInformation() throws IOException {
    String info = this.toString();
    if (this.destination == null) {
      System.out.print(info);
    } else {
      this.destination.append(info);
    }
  }
}
