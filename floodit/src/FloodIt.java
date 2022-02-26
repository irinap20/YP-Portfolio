
import java.util.ArrayList;

import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class FloodItWorld extends World {

  // number of colors the game will have
  int colorCount;
  // grid size
  int gridSize;
  // All the flooded cells of the game
  ArrayList<Cell> flooded = new ArrayList<Cell>();
  // current color
  Color currentColor;
  // clicked color
  Color previousColor;
  // The board
  ArrayList<ArrayList<Cell>> board;
  // the chosen Cell
  Cell outlinedPrev;
  // how many times the player clicked
  int clicks;
  // how many times the player can click
  int limit;
  int imageSize = 26;
  double tickCount;
  double score;

  // main constructor
  FloodItWorld(int colorCount, int gridSize) {
    if (gridSize > 26) {
      throw new IllegalArgumentException("Grid size can not be greater than 26");
    }
    else {
      this.gridSize = gridSize;
    }
    if (colorCount > 8 || gridSize <= colorCount) {
      throw new IllegalArgumentException("Color size can not be greater than 8");
    }
    else {
      this.colorCount = colorCount;
    }
    this.board = this.makeBoard();
    this.clicks = 0;
    this.limit = this.getLimit(gridSize, colorCount);
    this.tickCount = 0;
    this.outlinedPrev = this.board.get(0).get(0);
    this.currentColor = this.board.get(0).get(0).color;
  }

  // for tester
  FloodItWorld(ArrayList<ArrayList<Cell>> board) {
    this.board = board;
    this.gridSize = this.board.size();
    this.clicks = 0;
    this.limit = this.getLimit(gridSize, colorCount);
    this.tickCount = 0;
    this.outlinedPrev = this.board.get(0).get(0);
  }

  // calculates the limit
  public int getLimit(int gridSize, int colorCount) {
    return colorCount * (gridSize / 2);
  }

  // makes the board for the game
  public ArrayList<ArrayList<Cell>> makeBoard() {
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    // makes a board
    for (int i = 0; i < this.gridSize; i = i + 1) {
      ArrayList<Cell> row = this.makeRow();
      board.add(row);
      for (int n = 0; n < this.gridSize; n = n + 1) {
        board.get(i).get(n).y = i;
      }
    }

    // updates the neighboring cells
    for (ArrayList<Cell> row : board) {
      for (Cell cell : row) {
        this.updateNeighbors(cell, board);
      }
    }

    Cell currentCell = board.get(0).get(0);
    this.currentColor = currentCell.color;
    return board;

  }

  // constructs each row
  public ArrayList<Cell> makeRow() {
    ArrayList<Cell> row = new ArrayList<Cell>();
    for (int n = 0; n < this.gridSize; n = n + 1) {
      row.add(new Cell(n, 0, false, this.colorCount));
    }
    return row;
  }

  // draws the game
  public WorldScene makeScene() {
    WorldScene board = this.drawCells();
    return this.drawScores(board);
  }

  // draws the scores underneath
  WorldScene drawScores(WorldScene originalImage) {
    int allowedClicks = this.limit;
    int actualClicks = this.clicks;

    WorldImage counts = new TextImage("Step: " + actualClicks + "/" + allowedClicks,
        this.gridSize + 10, Color.BLACK);
    WorldImage time = new TextImage("Time:" + " " + this.tickCount / 10, this.gridSize + 10,
        Color.black);
    WorldImage gridSize = new TextImage("Size: " + this.gridSize, this.gridSize + 10, Color.BLACK);
    WorldImage colors = new TextImage("Color: " + this.colorCount, this.gridSize + 10, Color.BLACK);
    WorldImage note = new TextImage(
        "Solve it in: " + ((colorCount * (this.gridSize / 3)) + 60) + " s", this.gridSize + 10,
        Color.BLACK);

    originalImage.placeImageXY(gridSize, (6 * this.gridSize + (imageSize * this.gridSize)) + 50,
        this.gridSize + 10);
    originalImage.placeImageXY(colors, (6 * this.gridSize + (imageSize * this.gridSize)) + 50,
        this.gridSize * 2 + 20);
    originalImage.placeImageXY(counts, (6 * this.gridSize + (imageSize * this.gridSize)) + 50,
        this.gridSize * 4 + 30);
    originalImage.placeImageXY(time, (6 * this.gridSize + (imageSize * this.gridSize)) + 50,
        this.gridSize * 5 + 45);
    originalImage.placeImageXY(note, (6 * this.gridSize + (imageSize * this.gridSize)) + 50,
        this.gridSize * 6 + 55);
    return originalImage;

  }

  // creates a list of colors
  public ArrayList<Color> makeColorList() {
    ArrayList<Color> colorList = new ArrayList<Color>();
    for (ArrayList<Cell> row : this.board) {
      for (Cell c : row) {
        if (!colorList.contains(c.color)) {
          colorList.add(c.color);
        }
      }
    }
    return colorList;
  }

  // draws all the cells
  WorldScene drawCells() {
    WorldScene image = getEmptyScene();
    for (int i = 0; i < this.gridSize; i = i + 1) {
      for (int n = 0; n < this.gridSize; n = n + 1) {
        Cell current = this.board.get(i).get(n);
        int xPos = (current.x + 1) * (imageSize + 1);
        int yPos = (current.y + 1) * (imageSize + 1);
        Posn imagePosn = new Posn(xPos, yPos);
        image.placeImageXY(current.draw(), imagePosn.x, imagePosn.y);
      }
    }
    return image;
  }

  // updates the neighboring cells
  public void updateNeighbors(Cell current, ArrayList<ArrayList<Cell>> board) {
    if (current.x != 0) {
      current.setLeft(board.get(current.y).get(current.x - 1));
    }
    if (current.x != this.gridSize - 1) {
      current.setRight(board.get(current.y).get(current.x + 1));
    }
    if (current.y != this.gridSize - 1) {
      current.setTop(board.get(current.y + 1).get(current.x));
    }
    if (current.y != 0) {
      current.setBottom(board.get(current.y - 1).get(current.x));
    }
  }

  // onKeyEvent for FloodIt
  public void onKeyEvent(String c) {
    if (c.equals("r")) {
      this.board = this.makeBoard();
      this.clicks = 0;
      this.score = 0.0;
    }
  }

  // onMouseClicked for FloodIt
  public void onMouseClicked(Posn posn) {
    if (this.getCell(posn).color.equals(this.currentColor)) {
      return;
    }
    else {
      this.clicks++;
      this.flooded.clear();
      this.flooded.add(board.get(0).get(0));
      Cell clicked = this.getCell(posn);
      this.previousColor = this.board.get(0).get(0).color;
      this.currentColor = clicked.color;
    }
  }

  // highlights the selected cell
  public void onMouseMoved(Posn posn) {
    Cell clickedCell = this.getCell(posn);
    if (clickedCell.equals(this.outlinedPrev)) {
      clickedCell.outline = true;
    }
    else {
      this.outlinedPrev.outline = false;
      this.outlinedPrev = clickedCell;
      clickedCell.outline = true;
    }
  }

  // gets the cell that matches the posn
  public Cell getCell(Posn posn) {
    int posnX = posn.x / 26;
    int posnY = posn.y / 26;
    if (posnX > this.gridSize || (posnY > this.gridSize)) {
      return new Cell(0, 0, true, this.currentColor);
    }
    if (posnX == 0 && posnY == 0) {
      return this.board.get(posnY).get(posnX);
    }
    else if (posnX != 0 && posnY == 0) {
      return this.board.get(posnY).get(posnX - 1);
    }
    else if (posnX == 0 && posnY != 0) {
      return this.board.get(posnY - 1).get(posnX);
    }
    else {
      return this.board.get(posnY - 1).get(posnX - 1);
    }
  }

  // onTick method for the game
  public void onTick() {
    this.tickCount = this.tickCount + 1;
    if (this.makeColorList().size() == 1) {
      if (this.clicks <= this.limit) {
        this.endOfWorld("YOU WIN");
      }
      else if (this.clicks > this.limit) {
        this.endOfWorld("YOU LOSE");
      }
    }
    else if (this.clicks > this.limit) {
      this.endOfWorld("YOU LOSE");
    }
    else if (this.tickCount / 10 > (colorCount * (gridSize / 3)) + 60) {
      this.endOfWorld("YOU LOSE");
    }
    ArrayList<Cell> floodedCells = new ArrayList<Cell>();
    for (Cell c : this.flooded) {
      this.floodIt(c, floodedCells);
    }
    this.flooded.clear();
    this.flooded.addAll(floodedCells);
    floodedCells.clear();
  }

  // determines if the cell should be added to flooded list
  // adds all the cells with the same color as the current color
  public void floodIt(Cell c, ArrayList<Cell> list) {
    c.color = this.currentColor;
    c.flooded = true;
    list.addAll(c.addToFlooded(this.previousColor));
  }

  // last image
  public WorldScene lastScene(String s) {
    int size = (this.gridSize / 2) * imageSize;
    WorldImage winLose = new TextImage(s, this.gridSize + 10, Color.black);
    WorldImage background = new RectangleImage(this.gridSize * 20, this.gridSize + 10,
        OutlineMode.SOLID, Color.white);
    OverlayImage finalImage = new OverlayImage(winLose, background);
    WorldScene initial = this.makeScene();
    initial.placeImageXY(finalImage, size + 20, size);
    return initial;
  }

}
