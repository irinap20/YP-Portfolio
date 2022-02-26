import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color; // general colors (as triples of red,green,blue values)
                      // and predefined colors (Color.RED, Color.GRAY, etc.)

class Mastermind extends World {
  boolean hasDuplicate;
  int length;
  int guesses;
  ILoColors coloroptions;

  ILoColors answersequence;
  ILoGuess guessSequence;

  Mastermind(boolean hasDuplicate, int length, int guesses, ILoColors coloroptions,
      ILoColors answersequence, ILoGuess guessSequence) {
    this.hasDuplicate = hasDuplicate;
    this.length = new Utils().checkRange(length, coloroptions.count(), hasDuplicate,
        "Invalid Length of Sequence:" + Integer.toString(length));
    this.guesses = new Utils().checkRange(guesses,
        "Invalid Number of Guesses:" + Integer.toString(guesses));
    this.coloroptions = new Utils().checkColors(length, coloroptions.count(), hasDuplicate,
        coloroptions, "Invalid Length of List of Colors:" + Integer.toString(coloroptions.count()));
    this.answersequence = answersequence;
    this.guessSequence = guessSequence;
  }

  Mastermind(boolean hasDuplicate, int length, int guesses, ILoColors coloroptions) {
    this.hasDuplicate = hasDuplicate;
    this.length = new Utils().checkRange(length, coloroptions.count(), hasDuplicate,
        "Invalid Length of Sequence:" + Integer.toString(length));
    this.guesses = new Utils().checkRange(guesses,
        "Invalid Number of Guesses:" + Integer.toString(guesses));
    this.coloroptions = new Utils().checkColors(length, coloroptions.count(), hasDuplicate,
        coloroptions, "Invalid Length of List of Colors:" + Integer.toString(coloroptions.count()));

  }

  WorldImage drawColorOptions() {
    ILoColors reversedList = this.coloroptions;
    return reversedList.draw();
  }

  int countColorOptions() {
    return this.coloroptions.count();
  }

  // produces a WorldScene
  public WorldScene makeScene() {
    WorldScene answerCoveredTemplate = this.coverAnswer();

    return answerCoveredTemplate;
  }

  // draws the template of the game
  public WorldScene template() {

    WorldImage colorOptions = this.drawColorOptions();
    WorldImage answer = this.answersequence.draw();
    WorldImage unguessedTemplate = this.answersequence.drawUnguessedAll(this.guesses);
    int imageWidth = (this.countColorOptions() * 40 + 10 * (this.countColorOptions() + 2));
    int imageWidth2 = (this.length * 40 + 10 * (this.length + 2));
    int width = imageWidth + 100;
    int emptycirclewidth = this.length * 25;
    int coloroptionwidth = this.countColorOptions() * 25;
    int answersequence  = this.length * 25;
    int height = 60 + (70 * guesses);
    WorldImage background = new RectangleImage(width, height, OutlineMode.SOLID,
        Color.PINK.darker());

    return new WorldScene(width, height).placeImageXY(background, width / 2, height / 2)
        .placeImageXY(answer, answersequence, 20)
        .placeImageXY(colorOptions,coloroptionwidth , height - 30)
        .placeImageXY(unguessedTemplate, emptycirclewidth , height - 35)
        .placeImageXY(guessSequence.draw(), imageWidth2, height - 85);
  }

  // draws the template with the covered answer
  public WorldScene coverAnswer() {
    int imageWidth2 = (this.length * 40 + 10 * (this.length + 2));
    WorldImage coverBox = new RectangleImage(imageWidth2 + 10, 50, OutlineMode.SOLID, Color.black);

    return this.template().placeImageXY(coverBox, 90, 20);
  }

  // produces the lastScene
  public WorldScene lastScene(String s) {
    int imageWidth2 = (this.length * 40 + 10 * (this.length + 2));
    return this.template().placeImageXY(new TextImage(s, 30, Color.white), imageWidth2 + 10, 20);
  }

  // ends the game when there is no more circle to go offscreen
  public WorldEnd worldEnds() {
    if (this.guesses < guessSequence.length()) {
      return new WorldEnd(true, this.lastScene("Win!"));
    } else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  //produce a circle where the key clicked
  public World onKeyEvent(String key) {
    ILoGuess updateUnfinished = guessSequence
        .updateGuesses(this.coloroptions.getColor(Integer.valueOf(key)));

    if ("123456789".contains(key)) {
      if (0 < Integer.valueOf(key) && Integer.valueOf(key) < this.coloroptions.count()) {
        return new Mastermind(this.hasDuplicate, this.length, this.guesses, this.coloroptions,
            this.answersequence, updateUnfinished);
      }
    } else if (key.equals("enter") && this.length == this.guessSequence.lengthIGuess()) {
      return new Mastermind(this.hasDuplicate, this.length, this.guesses, this.coloroptions,
          this.answersequence, this.guessSequence.updateFinishedGuesses());
    } else if (key.equals("delete")) {
      return new Mastermind(this.hasDuplicate, this.length, this.guesses, this.coloroptions,
          this.answersequence, this.guessSequence.remove());
    }
    return this;
  }
}

class ExamplesMyWorldProgram {
  ExamplesMyWorldProgram() {
  };

  ILoColors c1 = new ConsLoColors(Color.red,
      new ConsLoColors(Color.orange, new ConsLoColors(Color.yellow, new ConsLoColors(Color.green,
          new ConsLoColors(Color.blue, new ConsLoColors(Color.pink, new MtLoColors()))))));
  ILoColors c2 = new ConsLoColors(Color.red, new ConsLoColors(Color.orange,
      new ConsLoColors(Color.yellow, new ConsLoColors(Color.green, new MtLoColors()))));
  ILoColors empty = new MtLoColors();
  ILoColors c3 = new ConsLoColors(Color.red,
      new ConsLoColors(Color.yellow, new ConsLoColors(Color.pink, new MtLoColors())));
  IGuess finished = new FinishedGuess(c3);
  ILoGuess emptyGuess = new MtLoGuess();
  ILoGuess finishedGuess = new ConsLoGuess(finished, new ConsLoGuess(finished, emptyGuess));

  World game1 = new Mastermind(true, 4, 10, c2, c2, emptyGuess);

  // testing big bang
  boolean testBigBang(Tester t) {
    Mastermind w = new Mastermind(true, 3, 4, c2, c2, finishedGuess);
    int worldWidth = 500;
    int worldHeight = 1000;
    return w.bigBang(worldWidth, worldHeight);
  }

  //tests if the parameter is valid 
  boolean testcheckConstructorException(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Invalid Length of Sequence:20"), "Mastermind", false, 20, 10,
        c1)
        && t.checkConstructorException(new IllegalArgumentException("Invalid Number of Guesses:-3"),
            "Mastermind", false, 2, -3, c1)
        && t.checkConstructorException(
            new IllegalArgumentException("Invalid Length of Sequence:-9"), "Mastermind", false, -9,
            4, c1)
        && t.checkConstructorException(
            new IllegalArgumentException("Invalid Length of Sequence:-2"), "Mastermind", false, -2,
            4, c1)
        && t.checkConstructorException(new IllegalArgumentException("Invalid Length of Sequence:2"),
            "Mastermind", true, 2, 4, empty);
  }

  // testing test draw tree
  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(700, 1000);
    WorldScene s = new WorldScene(700, 1000);
    return c.drawScene(new Mastermind(true, 4, 10, c2, c2, emptyGuess).makeScene()) && c.show();
  }

  //testing onkey event 
  boolean testonKeyevent(Tester t) {
    return t.checkExpect(this.game1.onKeyEvent("1"),
        new Mastermind(true, 4, 10, c2, c2, this.emptyGuess.updateFinishedGuesses()));
  }
}
