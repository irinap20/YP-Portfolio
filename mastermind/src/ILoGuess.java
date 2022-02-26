import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import java.awt.Color; // general colors (as triples of red,green,blue values)
                      // and predefined colors (Color.RED, Color.GRAY, etc.

interface ILoGuess {
  // draw the guesses
  WorldImage draw();

  // number of guesses
  int length();

  // updates the guess
  ILoGuess updateGuesses(Color s);

  // counts the number of colors in the guess
  int lengthIGuess();

  // updates the unfinished guesses to finished guesses 
  ILoGuess updateFinishedGuesses();

  ILoGuess remove();

}

// Empty list of guesses 
class MtLoGuess implements ILoGuess {
  MtLoGuess() {
  };

  // draws the guesses
  public WorldImage draw() {
    return new EmptyImage();
  }

  // draws the guesses
  public int length() {
    return 0;
  }

  // updates the guesses
  public ILoGuess updateGuesses(Color s) {
    return new ConsLoGuess(new UnfinishedGuess((new MtLoColors()).constructColors(s)), this);
  }

  // updates the finished guesses
  public ILoGuess updateFinishedGuesses() {
    return this;
  }

  public ILoColors getColors() {
    return this.getColors();
  }

  public int lengthIGuess() {
    // TODO Auto-generated method stub
    return 0;
  }

  public ILoGuess remove() {
    return this;
  }

}

// List of guesses 
class ConsLoGuess implements ILoGuess {
  IGuess first;
  ILoGuess rest;

  public ConsLoGuess(IGuess first, ILoGuess rest) {
    this.first = first;
    this.rest = rest;
  }

  // draws the guesses
  public WorldImage draw() {
    // radius of the circle
    int radius = 20;
    // a circle
    WorldImage circles = this.first.draw();
    return new OverlayImage(circles.movePinhole(2 * radius + 10, 0),
        this.rest.draw().movePinhole(2 * radius + 10, 0));
  }

  //draws the guesses 
  public int length() {
    return 1 + this.rest.length();
  }

  public ILoGuess updateGuesses(Color s) {
    return new ConsLoGuess(this.first.updateFinishedGuess(), rest);
  }

  //counts the number of colors in the guess 
  public int lengthIGuess() {
    return this.first.length();
  }

  // updates the unfinished guess to finished guess
  public ILoGuess updateFinishedGuesses() {
    return new ConsLoGuess(this.first.updateFinishedGuess(), this.rest.updateFinishedGuesses());
  }

  // dont need this 
  // removes one color form the list of colors 
  public ILoGuess remove() {
    return new ConsLoGuess(this.first.remove(), this.rest);
  }

}

class ExamplesILoGuess {
  ExamplesILoGuess() {
  }

  ILoColors c1 = new ConsLoColors(Color.red,
      new ConsLoColors(Color.orange, new ConsLoColors(Color.yellow, new ConsLoColors(Color.green,
          new ConsLoColors(Color.blue, new ConsLoColors(Color.pink, new MtLoColors()))))));
  ILoColors c2 = new ConsLoColors(Color.red, new ConsLoColors(Color.orange,
      new ConsLoColors(Color.yellow, new ConsLoColors(Color.green, new MtLoColors()))));
  ILoColors empty = new MtLoColors();
  ILoColors c3 = new ConsLoColors(Color.red,
      new ConsLoColors(Color.yellow, new ConsLoColors(Color.pink, new MtLoColors())));
  IGuess finished = new FinishedGuess(c3);
  IGuess unfinished = new UnfinishedGuess(c3);
  ILoGuess emptyGuess = new MtLoGuess();
  ILoGuess unfinishedGuess = new ConsLoGuess(unfinished, new MtLoGuess());
  ILoGuess finishedGuess2 = new ConsLoGuess(finished,  new MtLoGuess());
  
  ILoGuess finishedGuess = new ConsLoGuess(finished, new ConsLoGuess(finished, emptyGuess));

//  // testing draw
//  boolean testdraw(Tester t) {
//    return t.checkExpect(this.emptyGuess.draw(), null);
//  }

  // test length
  boolean testlength(Tester t) {
    return t.checkExpect(this.emptyGuess.length(), 0)
        && t.checkExpect(this.finishedGuess.length(), 2);
  }

  // test updateGuesses 
  boolean testupdateGuesses(Tester t) {
    return t.checkExpect(this.emptyGuess.updateGuesses(Color.red), new ConsLoGuess(
        new UnfinishedGuess(new ConsLoColors(Color.red, new MtLoColors())), new MtLoGuess()));

  }

  //test updateFinishedGuesses
  boolean testupdateFinishedGuesses(Tester t) {
    
    return t.checkExpect(this.finishedGuess.updateFinishedGuesses(), this.finishedGuess)
        && t.checkExpect(this.unfinishedGuess.updateFinishedGuesses(), this.finishedGuess2);

  }

  // Testing lengthIGuess 
  boolean testlengthIGuess(Tester t) {
    return t.checkExpect(this.finishedGuess.lengthIGuess(), 3);
  }
  // testing remove

  boolean testremove(Tester t) {
    return t.checkExpect(this.finishedGuess.remove(), finishedGuess)
        && t.checkExpect(this.unfinishedGuess.remove(),
            new ConsLoGuess(
                new UnfinishedGuess(
                    new ConsLoColors(Color.yellow, new ConsLoColors(Color.pink, new MtLoColors()))),
                new MtLoGuess()));
  }

}
