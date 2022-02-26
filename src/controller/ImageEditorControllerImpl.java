package controller;

import view.ImageEditorView;
import view.ImageEditorViewImpl;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
import model.Blur;
import model.CheckerBoard;
import model.Greyscale;
import model.ImageInterface;
import model.MultiImageProcessingModel;
import model.Rainbow;
import model.Sepia;
import model.Sharpening;

/**
 * ImageEditorControllerImpl is the controller of the MVC ImageEditor program. It uses Model and
 * View to interact with the users of this program.
 */
public class ImageEditorControllerImpl implements ImageEditorController {

  private MultiImageProcessingModel model;
  private String[] command;
  private Readable input;
  private Appendable ap;
  private HashMap<String, Runnable> runnables;
  private int count; // number of commands in one line
  private StringBuilder fileNames;
  private ImageEditorView view;

  /**
   * This is the constructor for ImageEditorContorllerImpl. It takes in a MultiImageProcessingModel
   * and Readable and PrintStream as its output.
   *
   * @param model the model to operate on.
   * @param input the readable input for this program to operate.
   * @param out   place to send out the result.
   */
  public ImageEditorControllerImpl(MultiImageProcessingModel model, Readable input,
      PrintStream out) {
    if (model == null || input == null || out == null) {
      throw new IllegalArgumentException("Invalid(Null) Argument.");
    }
    this.model = model;
    this.command = new String[100];
    this.input = input;
    this.ap = out;
    this.runnables = new HashMap<>();
    this.fileNames = new StringBuilder();
    this.view = new ImageEditorViewImpl(this.model, this.ap);
  }


  private void set() {
    runnables.put("create-layer", new CreateLayer());
    runnables.put("remove-layer", new RemoveLayer());
    runnables.put("current", new SetCurrent());
    runnables.put("load", new LoadImage());
    runnables.put("blur", new Blurring());
    runnables.put("sharpen", new Sharpen());
    runnables.put("greyscale", new GreyScale());
    runnables.put("sepia", new SepiaFilter());
    runnables.put("save", new Save());
    runnables.put("invisible", new Invisible());
    runnables.put("checkerboard", new DrawCheckerboard());
    runnables.put("rainbow", new DrawRainbow());
    runnables.put("saveall", new SaveAll());
  }

  /**
   * The start method is what runs the imageEditorController. It starts the program and takes
   * various operations to perform its functionality.
   */
  @Override
  public void start() {
    set();
    if (this.command == null) {
      throw new IllegalStateException("Command is Null.");
    }
    // current state
    renderInformation(view);
    Scanner scan = new Scanner(this.input);
    while (scan.hasNextLine()) {
      count = 0;
      this.command = new String[100];
      Scanner line = new Scanner(scan.nextLine());
      while (line.hasNext()) {
        if (count == 0) {
          this.command[count] = line.next();
          renderMessage(view, this.command[count]);
          count++;
        } else if (this.command[0].equals("load") || this.command[0].equals("remove-layer")
            || this.command[0].equals("current") || this.command[0].equals("save")
            || this.command[0].equals("saveall") || this.command[0].equals("invisible") ||
            this.command[0].equals("create-project")) {
          if (!line.hasNext()) {
            renderMessage(view, "Please input a valid second command.");
          } else {
            this.command[1] = line.next().trim();
            renderMessage(view, this.command[0] + " " + this.command[1]);
            count++;
          }
        } else if (this.command[0].equals("checkerboard") || this.command[0].equals("rainbow")) {
          this.command[1] = line.next().trim();
          this.command[2] = line.next().trim();
          renderMessage(view, this.command[0] + " " + this.command[1] + " " + this.command[2]);
          count++;
        } else {
          this.command[count] = line.next().toLowerCase();
          renderMessage(view, this.command[count]);
          count++;
        }
      }
      if (runnables.containsKey(this.command[0])) {
        this.runnables.get(this.command[0]).run();
        renderInformation(view);
      } else {
        renderMessage(view, "Please input a valid command.");
      }
    }
  }

  // renderInformation add new line (new method, due to code duplication)
  private void renderInformation(ImageEditorView view) {
    try {
      view.renderInformation();
      ap.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable Failed");
    }
  }

  // renderMessage add new line (new method, due to code duplication)
  private void renderMessage(ImageEditorView view, String s) {
    try {
      view.renderMessage(s);
      ap.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable Failed");
    }
  }

  private class CreateLayer implements Runnable {

    @Override
    public void run() {
      model.createLayer();
    }
  }

  private class RemoveLayer implements Runnable {

    @Override
    public void run() {
      if (count < 2) {
        renderMessage(view, "Invalid Command: Could Not Load Image.");
      }
      try {
        Integer.parseInt(command[1]); // if it's not a number
      } catch (NumberFormatException e) {
        renderMessage(view, "Please Provide Valid Number");
      }
      if (Integer.parseInt(command[1]) > model.getAll().size()) {
        renderMessage(view, "Please Enter a Valid Index.");
      }
      model.removeLayer(Integer.parseInt(command[1]) - 1);
    }
  }

  private class DrawCheckerboard implements Runnable {

    @Override
    public void run() {
      try {
        Integer.parseInt(command[1]); // if it's not a number
        Integer.parseInt(command[2]); // if it's not a number
      } catch (NumberFormatException e) {
        renderMessage(view, "Please Provide Valid Number");
      }
      ImageInterface img = model
          .draw(new CheckerBoard(Integer.parseInt(command[1]), Integer.parseInt(command[2])));
      model.createLayer();
      model.setImage(img);
    }
  }


  private class DrawRainbow implements Runnable {

    @Override
    public void run() {
      try {
        Integer.parseInt(command[1]); // if it's not a number
        Integer.parseInt(command[2]); // if it's not a number
      } catch (NumberFormatException e) {
        renderMessage(view, "Please Provide Valid Number");
      }
      ImageInterface img = model
          .draw(new Rainbow(Integer.parseInt(command[1]), Integer.parseInt(command[2])));
      model.createLayer();
      model.setImage(img);
    }
  }

  private class SetCurrent implements Runnable {

    @Override
    public void run() {
      try {
        Integer.parseInt(command[1]); // if it's not a number
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Please Provide Valid Number");
      }
      if (Integer.parseInt(command[1]) > model.getAll().size()) {
        renderMessage(view, "Please Enter a Valid Index.");
      }
      model.setCurrent(Integer.parseInt(command[1]) - 1);
    }
  }

  // loads an image to the program using the setImage method in model
  private class LoadImage implements Runnable {


    @Override
    public void run() {
      if (count < 2) {
        ImageEditorView view = new ImageEditorViewImpl(model);
        renderMessage(view, "Invalid Command: Could Not Load Image.");
      } else {
        if (command[1].substring(command[1].indexOf(".") + 1).equals("txt")) {
          // pass the path to the file as a parameter
          String files = ImageUtilAddition.readTextFile(command[1]);
          String[] listOfFiles = files.split("\n");
          for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i];
            try {
              ImageInterface image = ImageUtilAddition.readImage(fileName);
              model.createLayer();
              model.setImage(image);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        } else {
          try {
            ImageInterface image = ImageUtilAddition.readImage(command[1]);
            model.setImage(image);
          } catch (IOException e) {
            renderMessage(view, "Could Not Find File.");
          }
        }
      }
    }

  }

  private class Blurring implements Runnable {

    @Override
    public void run() {
      if (count > 1) {
        renderMessage(view, "Error: Cannot Apply Filter");
      } else {
        try {
          model.setEffect(new Blur());
          model.applyOperation();
        } catch (IllegalStateException e) {
          renderMessage(view, "Please Load the Image First");
        }
      }
    }
  }

  private class Sharpen implements Runnable {

    @Override
    public void run() {
      if (count > 1) {
        renderMessage(view, "Error: Cannot Apply Filter");
      } else {
        try {
          model.setEffect(new Sharpening());
          model.applyOperation();
        } catch (IllegalStateException e) {
          renderMessage(view, "Please Load the Image First");
        }
      }
    }
  }

  private class GreyScale implements Runnable {

    @Override
    public void run() {
      if (count > 1) {
        renderMessage(view, "Error: Cannot Apply Filter");
      } else {
        try {
          model.setEffect(new Greyscale());
          model.applyOperation();
        } catch (IllegalStateException e) {
          renderMessage(view, "Please Load the Image First");
        }
      }
    }
  }

  private class SepiaFilter implements Runnable {

    @Override
    public void run() {
      if (count > 1) {
        renderMessage(view, "Error: Cannot Apply Filter");
      } else {
        try {
          model.setEffect(new Sepia());
          model.applyOperation();
        } catch (IllegalStateException e) {
          renderMessage(view, "Please Load the Image First");
        }
      }
    }
  }

  // Saves(exports) the image to a file using th export() method.
  private class Save implements Runnable {

    @Override
    public void run() {
      if (count < 2) {
        ImageEditorView view = new ImageEditorViewImpl(model);
        renderMessage(view, "Error: Cannot Save Image");
      } else {
        String fileName = command[1];
        String fileType = fileName.substring(fileName.indexOf(".") + 1).toLowerCase();
        if (fileType.equals("ppm")) {
          //Creating a File object
          ImageUtilAddition.exportFile(model.getImage(), fileName);
          fileNames.append(command[1] + "\n");
        } else if (fileType.equals("jpeg") || fileType.equals("png")) {
          //Creating a File object
          try {
            ImageUtilAddition.export(model.getImage(), fileName);
            fileNames.append(command[1] + "\n");
            System.out.println("Image Exported");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }


  // Saves(exports) All the image to a text file using th exportFile() method.
  private class SaveAll implements Runnable {

    @Override
    public void run() {
      if (count < 2) {
        renderMessage(view, "Error: Cannot Save Image");
      } else {
        System.out.println(fileNames.toString());
        try {
          ImageUtilAddition.exportText(command[1] + ".txt", fileNames.toString());
        } catch (IllegalArgumentException e) {
          renderMessage(view, e.getMessage());
        }
      }
    }
  }


  private class Invisible implements Runnable {

    @Override
    public void run() {
      if (count < 2) {
        renderMessage(view, "Error: Cannot Change Visibility");
      }
      try {
        Integer.parseInt(command[1]); // if it's not a number
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Please Provide Valid Number");
      }
      model.visible(Integer.parseInt(command[1]) - 1, false);
    }
  }

}
