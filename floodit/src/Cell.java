import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

//Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  int colorCount;
  boolean flooded;
  boolean outline;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;
  int imageSize = 26;

  // main constructor
  Cell(int x, int y, boolean flooded, int colorCount) {
    this.x = x;
    this.y = y;
    this.flooded = false;
    this.outline = false;
    this.color = makeRandomColor(colorCount);
    this.left = this;
    this.top = this;
    this.right = this;
    this.bottom = this;
  }

  // main constructor
  Cell(int x, int y, boolean flooded, Color color) {
    this.x = x;
    this.y = y;
    this.flooded = false;
    this.outline = false;
    this.color = color;
    this.left = this;
    this.top = this;
    this.right = this;
    this.bottom = this;
  }

  // EFFECT: Makes a random color
  Color makeRandomColor(int colorQuant) {
    Random rand = new Random();
    int randNumber = rand.nextInt(colorQuant);
    Color red = Color.red;
    Color blue = Color.blue;
    Color green = Color.green;
    Color pink = Color.pink;
    Color yellow = Color.yellow;
    Color cyan = Color.cyan;
    Color purple = Color.magenta;
    Color orange = Color.orange;
    ArrayList<Color> listOfColors = new ArrayList<Color>(
        Arrays.asList(red, blue, green, pink, yellow, cyan, purple, orange));
    Color randomColor = listOfColors.get(randNumber);
    return randomColor;
  }

  // sets this.left cell to the given cell
  public void setLeft(Cell lc) {
    this.left = lc;
    lc.right = this;
  }

  // EFFECT: sets a given cell to left of this,
  // and this to the right of given
  public void setRight(Cell rc) {
    this.right = rc;
    rc.left = this;
  }

  // EFFECT: sets a given cell to left of this,
  // and this to the right of given
  public void setTop(Cell tc) {
    this.top = tc;
    tc.bottom = this;
  }

  // EFFECT: sets a given cell to left of this,
  // and this to the right of given
  public void setBottom(Cell bc) {
    this.bottom = bc;
    bc.top = this;
  }

  // EFFECT: Draws the cell
  WorldImage draw() {
    RectangleImage cell = new RectangleImage(imageSize, imageSize, OutlineMode.SOLID, this.color);
    // outlines cell if cursor is over it
    if (this.outline) {
      WorldImage outline = new RectangleImage(22, 22, OutlineMode.SOLID, this.color.darker());
      return outline;
    }
    else {
      return cell;
    }
  }

  public ArrayList<Cell> addToFlooded(Color c) {
    ArrayList<Cell> list = new ArrayList<Cell>();
    this.left.floodIt(c, list);
    this.right.floodIt(c, list);
    this.top.floodIt(c, list);
    this.bottom.floodIt(c, list);
    return list;
  }

  public void floodIt(Color current, ArrayList<Cell> flooded) {
    if (this.color.equals(current)) {
      if (flooded.contains(this)) {
        return;
      }
      else {
        flooded.add(this);
      }
    }
  }
}
