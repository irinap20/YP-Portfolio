package view;

import java.io.IOException;

/**
 * This class is the interface ImageEditorView. It controls the visual display (textual-view) of the
 * Image Editor Program.
 */

public interface ImageEditorView {

  /**
   * Prints the model information to string as follows.
   *
   * <pre>
   *    * ImageEditor[b]Type:[b]...[n]
   *    * Image[b]Width:[b]...[n]
   *    * Image[b]Height:[b]...[n]
   *    * Current[b]Layer:[b]...[n]
   *    *
   *    * where [b] is a single blankspace, [n] is newline. Note that there is no
   *    * newline on the last line.
   *    * </pre>
   *
   * @return the formatted string above
   */
  String toString();

  /**
   * Render the message to the provided data destination.
   * @param message the message to be transmitted
   * @throws IOException if transmission of the message fails
   */
  void renderMessage(String message) throws IOException;

  /**
   * Render the current state to the destination. The renderInformation method
   * should be in the format of toString() method above.
   * @throws IOException if transmission to the data destination fails
   */
  void renderInformation() throws IOException;

}
