import tester.Tester;

class ExampleCells {
  ExampleCells() {
  }

  Cell cell1 = new Cell(0, 0, false, 4);
  Cell cell2 = new Cell(1, 0, false, 4);
  Cell cell3 = new Cell(2, 0, false, 4);
  Cell cell4 = new Cell(1, 1, false, 4);
  Cell cell5 = new Cell(1, 2, false, 4);

  // tests the set methods
  void testSetTopLeftBottomRight(Tester t) {
    t.checkExpect(cell1.right, cell1);
    t.checkExpect(cell2.left, cell2);
    cell1.setRight(cell2);
    t.checkExpect(cell1.right, cell2);
    t.checkExpect(cell1.left, cell1);
    t.checkExpect(cell2.right, cell2);
    t.checkExpect(cell3.left, cell3);
    cell3.setLeft(cell2);
    cell2.setRight(cell3);
    t.checkExpect(cell2.right, cell3);
    t.checkExpect(cell3.left, cell2);
    t.checkExpect(cell2.top, cell2);
    t.checkExpect(cell4.bottom, cell4);
    cell2.setTop(cell4);
    t.checkExpect(cell2.top, cell4);
    t.checkExpect(cell4.bottom, cell2);
    cell4.setBottom(cell2);
    t.checkExpect(cell2.top, cell4);
    t.checkExpect(cell4.bottom, cell2);
  }
}
