package view;

import controller.Features;
import model.ImageInterface;

/**
 * This is an interface for the GUI view class. It has methods such as addFeatures, makeVisible,
 * addButtonLayer etc. These methods are used by the controller to send information to view.
 */
public interface IEditorView extends ImageEditorView {

  /**
   * This method accepts the features object from the controller. It uses the features to perform
   * its functionality.
   *
   * @param features features coming from controller class.
   */
  void addFeatures(Features features);

  /**
   * This method is called on the view object in the controller class to make the image and view
   * visible. It's called in the controller's start method.
   */
  void makeVisible();

  /**
   * This method is used to add a layer button in the view. The controller sends in the name and its
   * functionality and the view accepts them to create a new layer button.
   *
   * @param name     the name of the button.
   * @param features object that the view operates on.
   */
  void addButtonLayer(String name, Features features);

  /**
   * This method shows the image in the view (this depends on the invisible/visible status of the
   * layer).
   *
   * @param image the image to show
   * @param b     whether to show or not
   */
  void showImage(ImageInterface image, Boolean b);

  /**
   * This method is used to remove the buttons in the view.
   *
   * @param index which layer to eliminate
   */
  void removeButtonLayer(int index);
}
