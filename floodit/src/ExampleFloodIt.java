import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javalib.worldimages.Posn;
import tester.Tester;

class ExampleFloodIt {

  void testGame(Tester t) {
    int gridSize = 10;
    int numberOfColors = 4;

    FloodItWorld board = new FloodItWorld(numberOfColors, gridSize);
    board.bigBang(30 * (gridSize + 10), (32 * gridSize), 0.09);
  }

  ArrayList<Cell> row = new ArrayList<Cell>();
  ArrayList<Cell> row2 = new ArrayList<Cell>();
  Cell cell1 = new Cell(0, 0, false, Color.pink);
  Cell cell2 = new Cell(1, 0, false, Color.pink);
  Cell cell3 = new Cell(0, 1, false, Color.blue);
  Cell cell4 = new Cell(1, 1, false, Color.green);

  // initializes the cells
  void initialize() {
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);
  }

  // tests update neighbors
  void testupdateNeighbors(Tester t) {
    initialize();
    t.checkExpect(cell1.left, cell1);
    t.checkExpect(cell1.right, cell2);
    t.checkExpect(cell2.right, cell2);
    t.checkExpect(cell2.left, cell1);
    t.checkExpect(cell3.right, cell4);
    t.checkExpect(cell4.left, cell3);
    t.checkExpect(cell1.top, cell3);
    t.checkExpect(cell2.top, cell4);
    t.checkExpect(cell3.bottom, cell1);
    t.checkExpect(cell4.bottom, cell2);
  }

  void testfloodIt(Tester t) {
    initialize();
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);
    finalboard.currentColor = Color.pink;

    cell3.floodIt(Color.green, finalboard.flooded);
    cell3.floodIt(Color.blue, finalboard.flooded);

    t.checkExpect(finalboard.flooded.size(), 1);
    finalboard.flooded.clear();

    ArrayList<Cell> list = new ArrayList<Cell>();
    finalboard.currentColor = Color.white;
    finalboard.previousColor = Color.pink;
    finalboard.floodIt(cell1, list);
    t.checkExpect(cell1.flooded, true);
    t.checkExpect(cell1.color, Color.white);
    t.checkExpect(list, new ArrayList<Cell>(Arrays.asList(cell2)));
  }

  void testgetCell(Tester t) {
    initialize();
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);

    t.checkExpect(finalboard.getCell(new Posn(31, 31)), cell1);
    t.checkExpect(finalboard.getCell(new Posn(62, 31)), cell2);
    t.checkExpect(finalboard.getCell(new Posn(31, 62)), cell3);
    t.checkExpect(finalboard.getCell(new Posn(62, 62)), cell4);
    t.checkExpect(finalboard.getCell(new Posn(28, 30)), cell1);
    t.checkExpect(finalboard.getCell(new Posn(67, 32)), cell2);
    t.checkExpect(finalboard.getCell(new Posn(25, 52)), cell3);
    t.checkExpect(finalboard.getCell(new Posn(70, 75)), cell4);
  }

  void testonkeyEvent(Tester t) {
    initialize();
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);

    t.checkExpect(finalboard.clicks, 0);
    t.checkExpect(finalboard.score, 0.0);
  }

  void testmakeColorList(Tester t) {
    initialize();
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);

    t.checkExpect(finalboard.makeColorList(),
        new ArrayList<Color>(Arrays.asList(Color.pink, Color.blue, Color.green)));
  }

  void testmakeColorlist(Tester t) {
    initialize();
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);

    t.checkExpect(finalboard.makeColorList(),
        new ArrayList<Color>(Arrays.asList(Color.pink, Color.blue, Color.green)));
  }

  void testonMouseClicked(Tester t) {
    initialize();
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);
    t.checkExpect(finalboard.board.get(0).get(0).color, Color.pink);
    finalboard.currentColor = Color.green;
    finalboard.onMouseClicked(new Posn(50, 60));
    t.checkExpect(finalboard.clicks, 1);
    t.checkExpect(finalboard.previousColor, Color.pink);
    t.checkExpect(finalboard.currentColor, Color.blue);
  }

  void testonMouseMoved(Tester t) {
    initialize();
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);
    finalboard.currentColor = Color.green;
    finalboard.outlinedPrev = cell3;
    finalboard.onMouseMoved(new Posn(50, 60));
    t.checkExpect(cell3.outline, true);
    finalboard.onMouseMoved(new Posn(50, 50));
    t.checkExpect(cell3.outline, false);
    t.checkExpect(finalboard.outlinedPrev, cell1);
    t.checkExpect(cell1.outline, true);
  }

  void testGetLimit(Tester t) {
    initialize();
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    row.add(cell1);
    row.add(cell2);

    row2.add(cell3);
    row2.add(cell4);

    board.add(row);
    board.add(row2);

    FloodItWorld finalboard = new FloodItWorld(board);

    finalboard.updateNeighbors(cell1, board);
    finalboard.updateNeighbors(cell2, board);
    finalboard.updateNeighbors(cell3, board);
    finalboard.updateNeighbors(cell4, board);
    t.checkExpect(finalboard.getLimit(14, 4), 28);
  }

}
