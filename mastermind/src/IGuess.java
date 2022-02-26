import java.awt.Color;

import javalib.worldimages.WorldImage;
import tester.Tester;

interface IGuess {
  WorldImage draw();

  IGuess updateGuess(Color s);

  IGuess updateFinishedGuess();

  int length();

  IGuess remove();
}

// unfinished guesses 
class UnfinishedGuess implements IGuess {
  // row of list of colors
  ILoColors colorsOfCircle;

  UnfinishedGuess(ILoColors colorsOfCircle) {
    this.colorsOfCircle = colorsOfCircle;
  }

  public WorldImage draw() {
    return this.colorsOfCircle.reverse().drawUnguessed();
  }

  public IGuess updateGuess(Color s) {
    return new UnfinishedGuess(this.colorsOfCircle.constructColors(s));
  }

  public IGuess updateFinishedGuess() {
    return new FinishedGuess(this.colorsOfCircle);
  }

  @Override
  public int length() {
    return this.colorsOfCircle.count();
  }

  public IGuess remove() {
    return new UnfinishedGuess(this.colorsOfCircle.remove());
  }

}

// finished guesses 
class FinishedGuess implements IGuess {
  // row of list of colors
  ILoColors colorsOfCircles;

  FinishedGuess(ILoColors colorsOfCircles) {
    this.colorsOfCircles = colorsOfCircles;
  }

  public WorldImage draw() {
    return this.colorsOfCircles.reverse().draw();
  }

  public IGuess updateGuess(Color s) {
    return this;
  }

  public IGuess updateFinishedGuess() {
    return this;
  }

  public int length() {
    return this.colorsOfCircles.count();
  }

  public IGuess remove() {
    return this;
  }

}

class ExamplesGuess {
  ExamplesGuess() {
  }

  Color red = Color.red;

  ILoColors c1 = new ConsLoColors(Color.red,
      new ConsLoColors(Color.orange, new ConsLoColors(Color.yellow, new ConsLoColors(Color.green,
          new ConsLoColors(Color.blue, new ConsLoColors(Color.pink, new MtLoColors()))))));
  ILoColors c2 = new ConsLoColors(Color.red, new ConsLoColors(Color.orange,
      new ConsLoColors(Color.yellow, new ConsLoColors(Color.green, new MtLoColors()))));
  ILoColors empty = new MtLoColors();
  ILoColors c3 = new ConsLoColors(Color.red,
      new ConsLoColors(Color.yellow, new ConsLoColors(Color.pink, new MtLoColors())));
  ILoColors c4 = new ConsLoColors(Color.blue, new ConsLoColors(Color.red,
      new ConsLoColors(Color.yellow, new ConsLoColors(Color.pink, new MtLoColors()))));

  IGuess unfinished = new UnfinishedGuess(c3);
  IGuess finished = new FinishedGuess(c4);
  ILoGuess emptyGuess = new MtLoGuess();
  ILoGuess finishedGuess = new ConsLoGuess(finished, new ConsLoGuess(finished, emptyGuess));

  //testing draw methods 
  //I need help testing this out!!!!!
  boolean testdraw(Tester t) {
    return t.checkExpect(this.finished.draw(), this.c4.reverse().draw());
  }

  //test update guess 
  boolean testupdateGuess(Tester t) {
    return t.checkExpect(this.emptyGuess.updateGuesses(red), new ConsLoGuess(
        new UnfinishedGuess(new ConsLoColors(red, new MtLoColors())), new MtLoGuess()), "1");
  }

  //test updateFinishedGuess 
  boolean testupdateFinishedGuess(Tester t) {
    return t.checkExpect(this.finished.updateFinishedGuess(), this.finished)
        && t.checkExpect(this.unfinished.updateFinishedGuess(), new FinishedGuess(c3));
  }

  //test length 
  boolean testlength(Tester t) {
    return t.checkExpect(this.finished.length(), 4) && t.checkExpect(this.unfinished.length(), 3);
  }

  // test Remove 
  boolean testremove(Tester t) {
    return t.checkExpect(this.unfinished.remove(),
        new UnfinishedGuess(
            new ConsLoColors(Color.yellow, new ConsLoColors(Color.pink, new MtLoColors()))))
        && t.checkExpect(this.finished.remove(), this.finished);
  }

}
