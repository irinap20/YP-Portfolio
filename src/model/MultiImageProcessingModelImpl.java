package model;

import java.util.Stack;

/**
 * The MultiImageProcessingModelImpl is what implements the MultiImageprocessingModel interface. It
 * provides more functionality than the ImageProcessingModel and hence is the enhanced version.
 */
public class MultiImageProcessingModelImpl implements
    MultiImageProcessingModel {

  private final Stack<ILayer> stackOfLayers;
  private ImageProcessingModelImpl model;
  private ILayer currentLayer;

  /**
   * Constructs a model all empty.
   */
  public MultiImageProcessingModelImpl() {
    //this.currentLayer = null;
    this.stackOfLayers = new Stack<>();
    this.model = new ImageProcessingModelImpl();
    this.currentLayer = null;
  }

  @Override
  public void createLayer() {
    this.stackOfLayers.push(new Layer());
    this.currentLayer = this.stackOfLayers.get(this.stackOfLayers.size() - 1);
  }

  // set it to certain layer (image or empty);
  @Override
  public void setCurrent(int index) {
    if (index < 0 || index >= stackOfLayers.size()) {
      throw new IllegalArgumentException("Index out of bound.");
    }
    this.currentLayer = this.stackOfLayers.get(index);
    try {
      // if there is an image
      model.setImage(this.currentLayer.getImage());
    } catch (IllegalStateException e) {
      // if there's no image, set the img to null
      model = new ImageProcessingModelImpl();
    }
  }

  // load image
  @Override
  public void setImage(ImageInterface img) {
    if (img == null) {
      throw new IllegalArgumentException("Null image.");
    }
    if (this.currentLayer == null) {
      throw new IllegalStateException("Create a Layer First");
    }
    this.currentLayer.setImage(img);
    model.setImage(img);
  }

  // load Effect
  @Override
  public void setEffect(ImageEditors effect) {
    if (effect == null) {
      throw new IllegalArgumentException("Effect Can't Be Null");
    }
    model.setEffect(effect);
  }

  @Override
  public void applyOperation() {
    if (model.getEffect() == null || model.getImage() == null) {
      throw new IllegalStateException("Effect and Image should be loaded first");
    }
    if (this.currentLayer == null) {
      throw new IllegalStateException("Create a Layer First");
    }
    if (model.getEffect() instanceof Downscale) {
      for (ILayer layer : this.stackOfLayers) {
        layer.setImage(model.getEffect().apply(layer.getImage()));
      }
    }
    model.setImage(model.getEffect().apply(model.getImage()));
  }

  @Override
  public ILayer removeLayer(int index) {
    if (index < 0 || index >= this.stackOfLayers.size()) {
      throw new IllegalArgumentException("Index Out of Bounds");
    }
    if (this.currentLayer == null) {
      throw new IllegalStateException("Create a Layer First");
    }
    // if the deleting layer is the currentLayer and there is more layer below
    if (getCurrent() - 1 == index && index > 0) {
      this.setCurrent(index - 1);// how bout this
    } else if (getCurrent() - 1 == index && index == 0) {
      this.currentLayer = null;
      model = new ImageProcessingModelImpl();
    }
    return this.stackOfLayers.remove(index);
  }

  @Override
  public void moveLayer(int from, int to) {
    if (from < 0 || from >= this.stackOfLayers.size() || to < 0 || to >= this.stackOfLayers
        .size()) {
      throw new IllegalArgumentException("Index Out of Bounds!");
    }
    if (this.currentLayer == null) {
      throw new IllegalStateException("Create a Layer First");
    }
    ILayer destination = this.stackOfLayers.get(to);
    ILayer replacement = this.stackOfLayers.get(from);
    this.stackOfLayers.set(from, destination);
    this.stackOfLayers.set(to, replacement);
  }

  // same as old model, use delegation
  @Override
  public String toString() {
    //this.set();
    StringBuilder res = new StringBuilder();
    for (ILayer img : stackOfLayers) {
      res.append(img.toString()).append("\n");
    }
    return res.toString();
  }

  // visible/invisible
  @Override
  public boolean isVisible() {
    if (this.currentLayer == null) {
      throw new IllegalStateException("the current layer is null");
    }
    return this.currentLayer.getVisibility();
  }

  // visible/invisible
  @Override
  public boolean isThisVisible(int index) {
    if (this.stackOfLayers.size() <= index || index < 0) {
      throw new IllegalArgumentException("Index Out of Bounds!");
    }
    ILayer layer = this.stackOfLayers.get(index);
    return layer.getVisibility();
  }

  // visible/invisible
  @Override
  public void visible(int index, boolean b) throws IllegalArgumentException {
    if (this.stackOfLayers.size() < index || index < 0) {
      throw new IllegalArgumentException("Index Out of Bounds!");
    }
    if (this.currentLayer == null) {
      throw new IllegalStateException("Create a Layer First");
    }
    ILayer img = this.stackOfLayers.get(index);
    img.setVisibility(b);
  }

  @Override
  public ImageInterface getImage() throws IllegalStateException {
    return model.getImage();
  }

  @Override
  public ImageEditors getEffect() {
    return model.getEffect();
  }

  @Override
  public int getHeightImage() {
    return model.getHeightImage();
  }

  @Override
  public int getWidthImage() {
    return model.getWidthImage();
  }


  @Override
  public ImageInterface draw(ImageProgram ip) {
    if (ip == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    if (this.currentLayer == null) {
      throw new IllegalStateException("Create a Layer First");
    }
    return ip.create();
  }

  @Override
  public Stack<ILayer> getAll() {
    Stack<ILayer> result = new Stack<>();
    for (ILayer layer : this.stackOfLayers) {
      result.add(layer);
    }
    return result;
  }

  @Override
  public int getCurrent() {
    /*
    if (this.currentLayer == null) {
      throw new IllegalStateException("Create a Layer First");
    }*/
    return this.stackOfLayers.indexOf(this.currentLayer) + 1;
  }
}




