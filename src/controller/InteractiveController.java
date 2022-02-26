package controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import model.Blur;
import model.CheckerBoard;
import model.Downscale;
import model.Greyscale;
import model.ILayer;
import model.ImageInterface;
import model.Layer;
import model.Mosaic;
import model.MultiImageProcessingModel;
import model.Rainbow;
import model.Sepia;
import model.Sharpening;
import view.IEditorView;

/**
 * InteractiveController is the GUI controller for the MVC ImageEditorProgram. It is used to run the
 * model (MultiImageProcessingModel) and input from a batch file or the GUI. It contains the method
 * to operate the model and view of the program.
 */
public class InteractiveController implements ImageEditorController, Features {

  private MultiImageProcessingModel model;
  private IEditorView view;
  private StringBuilder fileNames = new StringBuilder();
  private Readable batchCommand;

  /**
   * The interactiveController constructor takes in a model and initializes the model.
   */
  public InteractiveController(MultiImageProcessingModel m, IEditorView v) {
    model = m;
    view = v;
  }

  /**
   * The start method executes the model and runs the view. It also passes the parsed commands to
   * other helper methods to perform the operation.
   */
  @Override
  public void start() {
    //provide view with all the callbacks
    view.addFeatures(this);
    view.makeVisible();
  }

  @Override
  public void process() throws IllegalStateException {
    int count;
    String[] commands;
    if (this.batchCommand == null) {
      throw new IllegalStateException("Command is Null.");
    }
    Scanner scan = new Scanner(this.batchCommand);
    while (scan.hasNextLine()) {
      count = 0;
      commands = new String[100];
      Scanner line = new Scanner(scan.nextLine());
      while (line.hasNext()) {
        if (count == 0) {
          commands[count] = line.next();
          count++;
        } else if (commands[0].equals("setImage")) {
          commands[1] = line.next().trim();
          commands[2] = line.next().trim();
          setCommands(commands[1], commands[2]);
          count++;
        } else if (commands[0].equals("layer")) {
          commands[1] = line.next().trim();
          layerCommands(commands[1]);
          count++;
        } else if (commands[0].equals("apply")) {
          commands[1] = line.next().trim();
          applyEffect(commands[1]);
          count++;
        } else if (commands[0].equals("draw")) {
          commands[1] = line.next().trim();
          commands[2] = line.next().trim();
          commands[3] = line.next().trim();
          draw(commands[1], commands[2], commands[3]);
          count++;
        } else {
          commands[count] = line.next().toLowerCase();
          count++;
        }
      }
    }
  }


  @Override
  public void setCommands(String command, String fileName) {
    if (command == null || fileName == null) {
      renderMessage("Needs At Least 2 Arguments");
    } else {
      switch (command.toLowerCase()) {
        case "loadall":
          this.loadAll(fileName);
          imageView();
          break;
        case "load":
          this.load(fileName);
          imageView();
          break;
        case "save":
          this.save(fileName);
          break;
        case "saveall":
          this.saveAll(fileName);
          break;
        case "current":
          this.setCurrent(fileName);
          break;
        case "mosaic":
          this.mosaic(fileName);
          imageView();
          break;
        default:
          renderMessage("Invalid Command");
      }
    }
  }

  // load multilayer
  private void loadAll(String fileName) {
    if (fileName == null) {
      renderMessage("Could Not Load All Images");
    } else {
      // pass the path to the file as a parameter
      String files = ImageUtilAddition.readTextFile(fileName);
      String[] imageLocations;
      imageLocations = files.split("\n");
      for (int i = 0; i < imageLocations.length; i++) {
        String record = imageLocations[i];
        if (record.equals("!Layer!")) {
          createLayer();
        } else if (record.contains(".png")) {
          createLayer();
          this.load(record);
        } else {
          renderMessage("Can't Load this MultiLayer!");
        }
      }
    }
  }

  private void load(String fileName) {
    if (fileName == null) {
      renderMessage("Could Not Load Image");
    } else {
      if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("ppm")) {
        ImageInterface image = ImageUtil.readPPM(fileName);
        try {
          model.setImage(image);
        } catch (IllegalArgumentException e) {
          renderMessage(e.getMessage());
        }
      } else if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("jpeg") ||
          fileName.substring(fileName.lastIndexOf(".") + 1).equals("png") ||
          fileName.substring(fileName.lastIndexOf(".") + 1).equals("jpg")) {
        try {
          ImageInterface image = ImageUtilAddition.readImage(fileName);
          try {
            model.setImage(image);
            renderMessage("Loaded the Image");
          } catch (IllegalArgumentException | IllegalStateException e) {
            renderMessage(e.getMessage());
          }
        } catch (IOException e) {
          renderMessage("Could Not Load The File!");
        }
      }
    }
  }

  private void save(String fileName) {
    if (fileName == null) {
      renderMessage("Error: Could Not Save File");
    } else {
      if ((!model.isThisVisible(model.getAll().size() - 1)) ||
          (model.getAll().get(model.getAll().size() - 1)
              .isEmpty())) { // if the topMost layer is not visible
        ILayer result = new Layer();
        ILayer layerWithImage = this.saveTopMostVisible(result, model.getAll().size() - 1);
        saveHelper(layerWithImage.getImage(), fileName);
      } else {
        saveHelper(model.getImage(), fileName);
      }
    }
  }

  private void saveHelper(ImageInterface image, String fileName) {
    String type;
    type = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    if (fileName.contains("/")) {
      String exportFileName = "res/" + fileName.substring(fileName.lastIndexOf("/") + 1);
      exportingHelper(type, image, exportFileName);
    }
  }

  private void exportingHelper(String type, ImageInterface image, String exportFileName) {
    if (type.equals("ppm")) {
      //Creating a File object
      ImageUtilAddition.exportFile(image, exportFileName);
    } else if (type.equals("jpg") || type.equals("jpeg") || type.equals("png")) {
      //Creating a File object
      try {
        ImageUtilAddition.export(image, exportFileName);
        System.out.print(exportFileName + "\n");
        renderMessage("Image Exported");
      } catch (IOException e) {
        renderMessage(e.getMessage());
      }
    }
  }

  private ILayer saveTopMostVisible(ILayer result, int index) {
    for (int i = index; i >= 0; i--) {
      ILayer layer = model.getAll().get(i);
      if (model.isThisVisible(i) && !layer.isEmpty()) {
        result = layer;
        break;
      } else {
        continue;
      }
    }
    return result;
  }

  private void saveAll(String fileName) {
    if (fileName == null) {
      renderMessage("Error: Cannot Save Image");
    } else {
      // list of file locations
      for (int j = 0; j < model.getAll().size(); j++) {
        String randomName = ImageUtilAddition.randomAlphaNumeric(10);
        ILayer layer = model.getAll().get(j);
        if (!layer.isEmpty()) {
          ImageInterface img = layer.getImage();// checks if layer has image
          exportingHelper("png", img, "res/" + randomName + ".png");
          fileNames.append("res/" + randomName + ".png" + "\n");
        } else {
          fileNames.append("!Layer!" + "\n");
        }
      }
      if (fileName.contains("/")) {
        try {
          if (fileName.contains("res/")) {
            ImageUtilAddition
                .exportText("res/" + fileName.substring(fileName.lastIndexOf("/") + 1) + ".txt",
                    fileNames.toString());
            renderMessage("File Exported!");
          } else {
            renderMessage("Please Save In The Res Folder");
          }
        } catch (IllegalArgumentException e) {
          renderMessage(e.getMessage());
        }
      }
    }
  }


  private void setCurrent(String index) {
    if (index == null) {
      renderMessage(index);
    } else {
      try {
        Integer.parseInt(index); // if it's not a number
      } catch (NumberFormatException e) {
        renderMessage("Please Provide Valid Number");
      }
      if (Integer.parseInt(index) >= model.getAll().size() || Integer.parseInt(index) < 0) {
        renderMessage("Please Enter a Valid Index.");
      }
      model.setCurrent(Integer.parseInt(index) - 1);
      renderMessage("Current Layer Set to " + index);
    }
  }

  @Override
  public void layerCommands(String cmd) {
    if (cmd == null) {
      renderMessage("Needs at Least One Argument");
    } else {
      switch (cmd.toLowerCase()) {
        case "createlayer":
          this.createLayer();
          imageView();
          break;
        case "invisible":
          this.invisible();
          imageView();
          break;
        case "removelayer":
          this.removeLayer();
          imageView();
          break;
        default:
          renderMessage("Invalid Command");
      }
    }
  }

  @Override
  public boolean isVisible() {
    if (model == null) {
      renderMessage("Error!");
    }
    return model.isVisible();
  }

  private void invisible() {
    try {
      if (model.isVisible()) {
        model.visible(model.getCurrent() - 1, false);
        renderMessage("Layer Set Invisible!");
      } else {
        model.visible(model.getCurrent() - 1, true);
        renderMessage("Layer Set Visible!");
      }
    } catch (IllegalArgumentException e) {
      renderMessage("Create A Layer First!");
    }
  }

  private void createLayer() {
    model.createLayer();
    int numb = model.getAll().size();
    view.addButtonLayer(Integer.toString(numb), this);
    renderMessage("Layer Created");
  }

  // removes the CURRENT LAYER
  private void removeLayer() {
    try {
      view.removeButtonLayer(model.getCurrent() - 1);
      model.removeLayer(model.getCurrent() - 1);
      renderMessage("Layer Removed");
    } catch (IllegalArgumentException | IllegalStateException e) {
      renderMessage("Select the Current Layer First. Couldn't Remove The Layer.");
    }
  }

  @Override
  public void draw(String cmd, String index, String secondIndex) {
    if (cmd == null || index == null || secondIndex == null) {
      renderMessage("Needs at Least 3 Arguments");
    } else {
      switch (cmd.toLowerCase()) {
        case "checkerboard":
          this.drawCheckerboard(index, secondIndex);
          imageView();
          break;
        case "rainbow":
          this.drawRainbow(index, secondIndex);
          imageView();
          break;
        case "downscale":
          this.downScale(index, secondIndex);
          imageView();
          break;
        default:
          renderMessage("Invalid Command");
      }
    }
  }


  private void drawCheckerboard(String index, String secondIndex) {
    if (index == null || secondIndex == null) {
      renderMessage("Needs at Least 1 Arguments");
    } else {
      try {
        Integer.parseInt(index); // if it's not a number
        Integer.parseInt(secondIndex); // if it's not a number
      } catch (NumberFormatException e) {
        renderMessage("Please Provide Valid Number");
      }
      ImageInterface img = model
          .draw(new CheckerBoard(Integer.parseInt(index), Integer.parseInt(secondIndex)));
      this.createLayer();
      model.setImage(img);
    }
  }


  private void drawRainbow(String index, String secondIndex) {
    try {
      Integer.parseInt(index); // if it's not a number
      Integer.parseInt(secondIndex); // if it's not a number
    } catch (NumberFormatException e) {
      renderMessage("Please Provide Valid Number");
    }
    ImageInterface img = model
        .draw(new Rainbow(Integer.parseInt(index), Integer.parseInt(secondIndex)));
    this.createLayer();
    model.setImage(img);
  }

  @Override
  public void applyEffect(String cmd) {
    if (cmd == null) {
      renderMessage("Needs at Least One Argument");
    } else {
      switch (cmd.toLowerCase()) {
        case "blur":
          this.blur();
          imageView();
          break;
        case "sepia":
          this.sepiaFilter();
          imageView();
          break;
        case "greyscale":
          this.greyScale();
          imageView();
          break;
        case "sharpen":
          this.sharpen();
          imageView();
          break;
        default:
          renderMessage("Invalid Command");
      }
    }
  }

  private void mosaic(String num) {
    int number = 0;
    try {
      number += Integer.valueOf(num);
    } catch (NumberFormatException e) {
      renderMessage("Please Input A Valid Number");
    }
    try {
      model.setEffect(new Mosaic(number));
      model.applyOperation();
      renderMessage("Mosaic Filter Applied!");
    } catch (IllegalStateException e) {
      renderMessage("Please Load the Image First");
    }
  }

  private void downScale(String index, String secondIndex) {
    int first = 0;
    int second = 0;
    try {
      first += Integer.valueOf(index);
      second += Integer.valueOf(secondIndex);
    } catch (NumberFormatException e) {
      renderMessage("Please Input A Valid Number");
    }
    try {
      model.setEffect(new Downscale(first, second));
      model.applyOperation();
      renderMessage("Downscale Filter Applied!");
    } catch (IllegalStateException e) {
      renderMessage("Please Load the Image First");
    }
  }

  private void blur() {
    try {
      model.setEffect(new Blur());
      model.applyOperation();
      renderMessage("Blur Filter Applied!");
    } catch (IllegalStateException e) {
      renderMessage("Please Load the Image First");
    }
  }

  private void sharpen() {
    try {
      model.setEffect(new Sharpening());
      model.applyOperation();
      renderMessage("Sharpen Filter Applied!");
    } catch (IllegalStateException e) {
      renderMessage("Please Load the Image First");
    }
  }

  private void greyScale() {
    try {
      model.setEffect(new Greyscale());
      model.applyOperation();
      renderMessage("Greyscale Filter Applied!");
    } catch (IllegalStateException e) {
      renderMessage("Please Load the Image First");
    }
  }

  private void sepiaFilter() {
    try {
      model.setEffect(new Sepia());
      model.applyOperation();
      renderMessage("Sepia Filter Applied!");
    } catch (IllegalStateException e) {
      renderMessage("Please Load the Image First");
    }
  }


  // renderMessage add new line (new method, due to code duplication)
  private void renderMessage(String s) {
    try {
      view.renderMessage(s);
    } catch (IOException e) {
      renderMessage("Appendable Failed");
    }
  }


  @Override
  public void readScript(String fileName) {
    if (fileName == null) {
      renderMessage("Null Argument");
    }
    try {
      FileReader file = new FileReader(fileName);
      this.batchCommand = file;
      System.out.print("HELlo");
      process();
      System.out.print("HELlo");
    } catch (IOException e) {
      renderMessage("Appendable Failed");
    }
  }


  private void imageView() {
    if (!model.getAll().get(model.getAll().size() - 1).getVisibility()) {
      outerloop:
      for (int i = model.getAll().size() - 1; i >= 0; i--) {
        if (model.getAll().get(i).getVisibility()) {
          if (model.getAll().get(i).isEmpty()) {
            view.showImage(null, false);
          } else {
            view.showImage(model.getAll().get(i).getImage(), true);
            break outerloop;
          }
        }
      }
    } else {
      try {
        view.showImage(model.getAll().pop().getImage(), model.getAll().pop().getVisibility());
      } catch (IllegalStateException e) {
        view.showImage(null, false);
      }
    }
  }
}

