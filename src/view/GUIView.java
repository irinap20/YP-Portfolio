package view;

import controller.Features;
import controller.ImageUtilAddition;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ImageInterface;

/**
 * This represents the GUI view for the Image Editor Program using Java Swing. The view uses the
 * action listener interface to link action events with specific objects in the Features class. This
 * is sent to the controller and is operated there. The menu in GUI is divided into four classes:
 * File, Editor, Layers, Create.
 */
public class GUIView extends JFrame implements IEditorView {

  // all the features of this program
  private JMenuBar menuBar = new JMenuBar();
  private JMenuItem load;
  private JMenuItem loadAll;
  private JMenuItem loadBatch;
  private JMenuItem save;
  private JMenuItem saveall;
  private JMenuItem blur;
  private JMenuItem sharpen;
  private JMenuItem greyScale;
  private JMenuItem sepia;
  private JMenuItem downScale;
  private JMenuItem mosaic;
  private JMenuItem createLayer;
  private JMenuItem invisible;
  private JMenuItem removeLayer;
  private JMenuItem currentLayer;
  private JMenuItem checkerboard;
  private JMenuItem rainbow;
  private JLabel imageLabel;
  private JScrollPane scrollable;
  // where the image is displayed
  private JPanel panel;
  private JPanel operations;
  private JPanel textArea;
  private JPanel layerOperations;
  private JPanel fileOperations;
  private JPanel inside;
  private JPanel imagePanel;
  // where the features are
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton greyscaleButton;
  private JButton sepiaButton;
  private JButton checkerboardButton;
  private JButton rainbowButton;
  private JButton saveButton;
  private JButton saveAllButton;
  private JButton loadButton;
  private JButton loadAllButton;
  private JButton createLayerButton;
  private JButton removeLayerButton;
  private JButton invisibleButton;
  private JButton mosaicButton;
  private JButton downScaleButton;
  private JTextArea input;
  private List<JButton> listOfLayers = new ArrayList<>();

  /**
   * Constructor for the GUIView class. It initializes and calls helper methods to build the view.
   */
  public GUIView() {
    super();
    this.setTitle("Image Editor");
    this.setBackground(Color.gray);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    buildMenu();
    buildImage();
    buildButtons();
    scrollable.setBorder(BorderFactory.createLineBorder(Color.CYAN));
    scrollable.getViewport().getView().setBackground(Color.gray);
    this.add(scrollable, BorderLayout.CENTER);
    this.setSize(scrollable.getSize());
    operations.setBorder(BorderFactory.createLineBorder(Color.CYAN));
    operations.setBackground(Color.DARK_GRAY);
    this.add(operations, BorderLayout.WEST);
    textArea.setBackground(Color.DARK_GRAY);
    textArea.setBorder(BorderFactory.createLineBorder(Color.CYAN));
    this.add(textArea, BorderLayout.SOUTH);
    layerOperations.setBackground(Color.DARK_GRAY);
    inside.setBackground(Color.DARK_GRAY);
    panel.setBackground(Color.DARK_GRAY);
    panel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
    this.add(panel, BorderLayout.EAST);
    fileOperations.setBorder(BorderFactory.createLineBorder(Color.CYAN));
    fileOperations.setBackground(Color.DARK_GRAY);
    this.add(fileOperations, BorderLayout.NORTH);
    imagePanel = new JPanel();
    imagePanel.setLayout(new OverlayLayout(imagePanel));
    this.pack();
  }

  // build the menu bar
  private void buildMenu() {
    fileMenu();
    editorMenu();
    layerMenu();
    createMenu();
    this.setJMenuBar(menuBar);
  }

  // helper function to create file menu. The file menu has
  // save, save all, load and load all as its options.
  private void fileMenu() {
    JMenu fileSubmenu = new JMenu("File");
    load = new JMenuItem("Load Image");
    fileSubmenu.add(load);
    loadBatch = new JMenuItem("Load Batch");
    fileSubmenu.add(loadBatch);
    loadAll = new JMenuItem("Load MultiLayer");
    fileSubmenu.add(loadAll);
    save = new JMenuItem("Save Image");
    fileSubmenu.add(save);
    saveall = new JMenuItem("Save All");
    fileSubmenu.add(saveall);
    menuBar.add(fileSubmenu);
  }

  // helper function to create editor menu. The editor menu has
  // filtering and transformation operations.
  private void editorMenu() {
    JMenu editorMenu = new JMenu("Editors");
    blur = new JMenuItem("Blur");
    editorMenu.add(blur);
    sharpen = new JMenuItem("Sharpen");
    editorMenu.add(sharpen);
    greyScale = new JMenuItem("GreyScale");
    editorMenu.add(greyScale);
    sepia = new JMenuItem("Sepia");
    editorMenu.add(sepia);
    downScale = new JMenuItem("Downscaling");
    editorMenu.add(downScale);
    mosaic = new JMenuItem("Mosaic");
    editorMenu.add(mosaic);
    menuBar.add(editorMenu);
  }

  // helper function to create layer menu. The layer menu has
  // operations such as creating layer, setting current layer,
  // removing a layer, and making a layer invisible.
  private void layerMenu() {
    JMenu layersSubmenu = new JMenu("Layers");
    createLayer = new JMenuItem("Create Layer");
    layersSubmenu.add(createLayer);
    removeLayer = new JMenuItem("Remove Layer");
    layersSubmenu.add(removeLayer);
    currentLayer = new JMenuItem("Set Current Layer");
    layersSubmenu.add(currentLayer);
    invisible = new JMenuItem("Make Invisible");
    layersSubmenu.add(invisible);
    menuBar.add(layersSubmenu);
  }

  // helper function to draw checkerboard and rainbow menu.
  private void createMenu() {
    JMenu createMenu = new JMenu("Draw");
    checkerboard = new JMenuItem("Draw Checkerboard");
    createMenu.add(checkerboard);
    rainbow = new JMenuItem("Draw Rainbow");
    createMenu.add(rainbow);
    menuBar.add(createMenu);
  }

  // helper method for creating image pane and allows for
  private void buildImage() {
    scrollable = new JScrollPane();
    scrollable.setPreferredSize(new Dimension(700, 500));
    scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    add(scrollable, BorderLayout.CENTER);
  }


  //  This is a helper method to build the button panel and it's contents.
  //  It has a text area and buttons to operate certain functionality of this program.
  //  The buttons include: apply filter save
  private void buildButtons() {
    buildButtonWest();
    buildButtonEast();
    buildTextSouth();
    buildButtonNorth();
  }

  // Helper method to build buttons on the left of the program.
  private void buildButtonWest() {
    operations = new JPanel();
    GridLayout gridLayout = new GridLayout(8, 1, 0, 0);
    operations.setLayout(gridLayout);
    operations.setBorder(new EmptyBorder(new Insets(15, 15, 15, 15)));
    blurButton = new JButton("Blur");
    operations.add(blurButton);
    sepiaButton = new JButton("Sepia");
    operations.add(sepiaButton);
    greyscaleButton = new JButton("GreyScale");
    operations.add(greyscaleButton);
    sharpenButton = new JButton("Sharpen");
    operations.add(sharpenButton);
    rainbowButton = new JButton("Rainbow");
    operations.add(rainbowButton);
    checkerboardButton = new JButton("CheckerBoard");
    operations.add(checkerboardButton);
    downScaleButton = new JButton("Downscale");
    operations.add(downScaleButton);
    mosaicButton = new JButton("Mosaic");
    operations.add(mosaicButton);
  }

  // Helper method to build buttons on top of the program.
  private void buildButtonNorth() {
    fileOperations = new JPanel();
    GridLayout gridLayout = new GridLayout(1, 4, 0, 0);
    fileOperations.setLayout(gridLayout);
    fileOperations.setBorder(new EmptyBorder(new Insets(15, 15, 15, 15)));
    loadAllButton = new JButton("Load MultiLayer");
    fileOperations.add(loadAllButton, JButton.CENTER);
    loadButton = new JButton("Load");
    fileOperations.add(loadButton, JButton.CENTER);
    saveAllButton = new JButton("Save All");
    fileOperations.add(saveAllButton, JButton.CENTER);
    saveButton = new JButton("Save");
    fileOperations.add(saveButton, JButton.CENTER);
  }

  // Helper method to build the operations in teh East section of the program.
  // Includes operations such as create layer, remove layer, make invisible/visible
  private void buildButtonEast() {
    JScrollPane layers;
    panel = new JPanel();
    GridLayout mainLayout = new GridLayout(2, 0, 0, -400);
    panel.setLayout(mainLayout);
    layerOperations = new JPanel();
    FlowLayout flowLayout = new FlowLayout();
    layerOperations.setLayout(flowLayout);
    layerOperations.setSize(10, 5);
    panel.add(layerOperations);
    createLayerButton = new JButton("Create");
    layerOperations.add(createLayerButton);
    removeLayerButton = new JButton("Remove");
    layerOperations.add(removeLayerButton);
    invisibleButton = new JButton("Invisible");
    layerOperations.add(invisibleButton);
    inside = new JPanel();
    layers = new JScrollPane(inside);
    layers.getViewport().getView().setBackground(Color.pink);
    BoxLayout boxLayoutY = new BoxLayout(inside, BoxLayout.Y_AXIS);
    inside.setLayout(boxLayoutY);
    panel.add(layers);
  }

  // Helper method to build the text area in the program. It's located
  // below the image display.
  private void buildTextSouth() {
    textArea = new JPanel();
    JLabel title = new JLabel("Message");
    input = new JTextArea("Message Output");
    textArea.add(input);
    Font font1 = new Font("SansSerif", Font.BOLD, 25);
    textArea.setFont(font1);
    input.setCaretColor(Color.BLUE);
    input.add(title);
    input.setEditable(false);
    input.setSize(100, 300);
  }

  @Override
  public void showImage(ImageInterface image, Boolean b) {
    if (b) {
      try {
        BufferedImage bufferedImage = ImageUtilAddition.convertImage(image);
        imageLabel.setIcon(new ImageIcon(bufferedImage));
        imageLabel.repaint();
      } catch (IOException e) {
        this.renderMessage(e.toString());
      }
    } else {
      imageLabel.setIcon(null);
    }
  }

  @Override
  public void renderMessage(String message) {
    input.setText(message);
  }

  @Override
  public void renderInformation() {
    //
  }

  @Override
  public void addFeatures(Features features) {
    load.addActionListener(l -> {
      setLoad(features);
    });
    loadButton.addActionListener(l -> {
      setLoad(features);
    });

    loadBatch.addActionListener(l -> {
      setLoadBatch(features);
    });

    loadAll.addActionListener(l -> {
      setLoadAll(features);
    });
    loadAllButton.addActionListener(l -> {
      setLoadAll(features);
    });

    save.addActionListener(l -> {
      setSave("save", features);
    });
    saveButton.addActionListener(l -> {
      setSave("save", features);
    });

    saveall.addActionListener(l -> {
      setSave("saveall", features);
      renderMessage("All Images Saved!");
    });
    saveAllButton.addActionListener(l -> {
      setSave("saveall", features);
      renderMessage("All Images Saved!");
    });

    blur.addActionListener(l -> {
      features.applyEffect("blur");
    });
    blurButton.addActionListener(l -> {
      features.applyEffect("blur");
    });

    sepia.addActionListener(l -> {
      features.applyEffect("sepia");
    });
    sepiaButton.addActionListener(l -> {
      features.applyEffect("sepia");
    });

    sharpen.addActionListener(l -> {
      features.applyEffect("sharpen");
    });
    sharpenButton.addActionListener(l -> {
      features.applyEffect("sharpen");
    });

    greyScale.addActionListener(l -> {
      features.applyEffect("greyscale");
    });
    greyscaleButton.addActionListener(l -> {
      features.applyEffect("greyscale");
    });

    downScale.addActionListener(l -> {
      downScale(features);
    });
    downScaleButton.addActionListener(l -> {
      downScale(features);
    });

    mosaic.addActionListener(l -> {
      mosaic(features);
    });
    mosaicButton.addActionListener(l -> {
      mosaic(features);
    });

    createLayer.addActionListener(l -> {
      features.layerCommands("createlayer");
    });
    createLayerButton.addActionListener(l -> {
      features.layerCommands("createlayer");
    });

    removeLayer.addActionListener(l -> {
      features.layerCommands("removeLayer");
    });
    removeLayerButton.addActionListener(l -> {
      features.layerCommands("removeLayer");
    });

    currentLayer.addActionListener(l -> {
      this.setCurrentLayerButton(features);
    });

    invisible.addActionListener(l -> {
      features.layerCommands("invisible");
    });
    invisibleButton.addActionListener(l -> {
      features.layerCommands("invisible");
    });

    checkerboard.addActionListener(l -> {
      drawChecker(features);
    });
    checkerboardButton.addActionListener(l -> {
      drawChecker(features);
    });
    rainbow.addActionListener(l -> {
      drawRainbow(features);
    });
    rainbowButton.addActionListener(l -> {
      drawRainbow(features);
    });
  }

  @Override
  public void addButtonLayer(String name, Features features) {
    JButton newButton = new JButton("                      "
        + "Layer" + name + ""
        + "                         ");
    newButton.addActionListener(l -> {
      features.setCommands("current", Integer.toString(listOfLayers.indexOf(newButton) + 1));
    });
    inside.add(newButton);
    listOfLayers.add(newButton);
  }


  @Override
  public void removeButtonLayer(int index) {
    if (index >= listOfLayers.size() || index < 0) {
      throw new IllegalArgumentException();
    }
    inside.remove(index);
    inside.repaint();
    listOfLayers.remove(listOfLayers.get(index));
  }

  private void downScale(Features features) {
    JTextField newHeight = new JTextField(5);
    JTextField newWidth = new JTextField(5);
    JPanel myPanel = new JPanel();
    myPanel.add(new JLabel("New Width:"));
    myPanel.add(newWidth);
    myPanel.add(new JLabel("New Height:"));
    myPanel.add(newHeight);
    int s = JOptionPane.showConfirmDialog(this, myPanel,
        "Please Enter The New Width And Height.", JOptionPane.OK_CANCEL_OPTION);
    if (s == JOptionPane.OK_OPTION) {
      try {
        features.draw("downscale", newWidth.getText(), newHeight.getText());
        renderMessage("Image Downscaled!");
      } catch (IllegalArgumentException e) {
        renderMessage(e.getMessage());
      }
    }
  }

  private void mosaic(Features features) {
    JTextField numSeeds = new JTextField(5);
    JPanel myPanel = new JPanel();
    myPanel.add(new JLabel("Num of Seeds:"));
    myPanel.add(numSeeds);
    int s = JOptionPane.showConfirmDialog(this, myPanel,
        "Please Enter The Number of Seeds", JOptionPane.OK_CANCEL_OPTION);
    if (s == JOptionPane.OK_OPTION) {
      try {
        features.setCommands("mosaic", numSeeds.getText());
      } catch (IllegalArgumentException e) {
        renderMessage(e.getMessage());
      }
    }
  }

  private void setCurrentLayerButton(Features features) {
    String s = (String) JOptionPane.showInputDialog(this, "Choose Layer:",
        "Set Current", JOptionPane.PLAIN_MESSAGE, null, null,
        null);
    if ((s != null) && (s.length() > 0)) {
      features.setCommands("current", s);
    }
  }

  private void setLoadBatch(Features features) {
    final JFileChooser loader = new JFileChooser(".");
    FileNameExtensionFilter filterTxt = new FileNameExtensionFilter(".txt", "txt");
    loader.setFileFilter(filterTxt);
    int response = loader.showOpenDialog(null);
    if (response == JFileChooser.APPROVE_OPTION) {
      File selectedFile = loader.getSelectedFile();
      features.readScript(selectedFile.getPath());
    }
  }

  private void setLoadAll(Features features) {
    final JFileChooser loader = new JFileChooser(".");
    FileNameExtensionFilter filterTxt = new FileNameExtensionFilter(".txt", "txt");
    loader.setFileFilter(filterTxt);
    int response = loader.showOpenDialog(null);
    if (response == JFileChooser.APPROVE_OPTION) {
      File selectedFile = loader.getSelectedFile();
      features.setCommands("loadall", selectedFile.getPath());
    }
  }

  private void setLoad(Features features) {
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG, PNG, PPM", "jpg", "jpeg", "png", "ppm");
    final JFileChooser chooser = new JFileChooser(".");
    chooser.setFileFilter(filter);
    chooser.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
    int response = chooser.showOpenDialog(null);
    if (response == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      try {
        features.setCommands("load", file.getPath());
      } catch (IllegalArgumentException e) {
        renderMessage(e.getMessage());
      }
    }
  }

  private void drawChecker(Features features) {
    JTextField tileSize = new JTextField(5);
    JTextField numTiles = new JTextField(5);
    JPanel myPanel = new JPanel();
    myPanel.add(new JLabel("Tile Size:"));
    myPanel.add(tileSize);
    myPanel.add(new JLabel("Number of Tiles (Side):"));
    myPanel.add(numTiles);
    int s = JOptionPane.showConfirmDialog(this, myPanel,
        "Please Enter Tile Size and Number of Tiles On Each Side.", JOptionPane.OK_CANCEL_OPTION);
    if (s == JOptionPane.OK_OPTION) {
      try {
        features.draw("checkerboard", tileSize.getText(), numTiles.getText());
        renderMessage("Checkerboard Created!");
      } catch (IllegalArgumentException e) {
        renderMessage(e.getMessage());
      }
    }
  }

  private void drawRainbow(Features features) {
    JTextField width = new JTextField(5);
    JTextField height = new JTextField(5);
    JPanel myPanel = new JPanel();
    myPanel.add(new JLabel("Width:"));
    myPanel.add(width);
    myPanel.add(new JLabel("Height:"));
    myPanel.add(height);
    int s = JOptionPane.showConfirmDialog(this, myPanel,
        "Please Enter Tile Size and Number of Tiles On Each Side.", JOptionPane.OK_CANCEL_OPTION);
    if (s == JOptionPane.OK_OPTION) {
      try {
        features.draw("rainbow", width.getText(), height.getText());
        renderMessage("Rainbow Created!");
      } catch (IllegalArgumentException e) {
        renderMessage(e.getMessage());
      }
    }
  }


  private void setSave(String command, Features features) {
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG, PNG, PPM", "jpg", "jpeg", "png", "ppm");
    FileNameExtensionFilter textFilter = new FileNameExtensionFilter(
        "TXT", "txt");

    final JFileChooser saver = new JFileChooser("./res");
    saver.setDialogTitle("Save");
    if (command.equals("save")) {
      saver.setFileFilter(filter);
    }
    if (command.equals("saveall")) {
      saver.setFileFilter(textFilter);
    }
    int response = saver.showSaveDialog(this);
    if (response == JFileChooser.APPROVE_OPTION) {
      File selectedFile = saver.getSelectedFile();
      try {
        features.setCommands(command, selectedFile.getPath());
      } catch (IllegalArgumentException e) {
        renderMessage(e.getMessage());
      }
    }
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }
}

