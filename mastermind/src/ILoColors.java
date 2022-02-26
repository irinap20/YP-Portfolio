import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color; // general colors (as triples of red,green,blue values)
                      // and predefined colors (Color.RED, Color.GRAY, etc.)
                      // list of colors 

interface ILoColors {

  // counts the list of colors
  int count();

  // draws the list of colors into a row
  WorldImage draw();

  // produces a new list of Colors in reverse order
  ILoColors reverse();

  // places the Colors in reverse order in the list
  ILoColors reverseHelper(ILoColors empty);

  // draws the unguessed circles
  WorldImage drawUnguessed();

  // draws the template for unguessed circles
  WorldImage drawUnguessedAll(int numberOfGuesses);

  // gets the color according to the string
  Color getColor(int s);

  // gets the color that's in the position s in the list of colors
  Color getPosition(int s, int a);

  // constructs a list of colors 
  ILoColors constructColors(Color s);

  // removes one color from the list of colors 
  ILoColors remove();
  
  //finds if the list has the other list's colors 
  ILoColors contains (ILoColors other) {
    return false; 
  }
}

//to represent an empty list of Colors
class MtLoColors implements ILoColors {
  MtLoColors() {
    // represents an empty list of Colors
  }

  // counts the number of colors
  public int count() {
    return 0;
  }

  public WorldImage draw() {
    return new EmptyImage();
  }

  // produces the reversed list of this list
  public ILoColors reverse() {
    return this;
  }

  // reverses the list of Colors
  public ILoColors reverseHelper(ILoColors empty) {
    return empty;
  }

  // draws the unguessed circles in the list of Colors
  public WorldImage drawUnguessed() {
    return new EmptyImage();
  }

  public WorldImage drawUnguessedAll(int numberOfGuesses) {
    return new EmptyImage();
  }

  // gets the right color in the list of color options
  public Color getColor(int s) {
    return null;
  }

  //gets the color that's in the position s in the list of colors 
  public Color getPosition(int s, int a) {
    return null;
  }

  // constructs a new list of colors 
  public ILoColors constructColors(Color s) {
    return new ConsLoColors(s, this);
  }

  // removes one color from the list of colors 
  public ILoColors remove() {
    return this;
  }

}

// represents a non-empty list of Colors 
class ConsLoColors implements ILoColors {
  Color first;
  ILoColors rest;

  ConsLoColors(Color first, ILoColors rest) {
    this.first = first;
    this.rest = rest;
  }

  // counts the number of colors
  public int count() {
    return 1 + this.rest.count();
  }

  // reverses the order of the list of colors
  public ILoColors reverse() {
    return this.reverseHelper(new MtLoColors());
  }

  // reverses the list of colors
  public ILoColors reverseHelper(ILoColors other) {
    return this.rest.reverseHelper(new ConsLoColors(this.first, other));
  }

  // draws a row of color options
  public WorldImage draw() {
    // radius of the circle
    int radius = 20;
    // a circle
    WorldImage circle = new CircleImage(radius, OutlineMode.SOLID, this.first);
    return new BesideImage(circle, this.rest.draw());

    //     return new OverlayImage(circle.movePinhole(2 * radius + 10, 0),
    //        this.rest.draw().movePinhole(2 * radius + 10, 0));
  }

  // draws the unguessed circles in the list of circles
  public WorldImage drawUnguessed() {
    // radius of the circle
    int radius = 20;
    // a circle
    WorldImage circle = new CircleImage(radius, OutlineMode.OUTLINE, Color.black);
    return new BesideImage(circle, this.rest.drawUnguessed());
    //    return new OverlayImage(circle.movePinhole(2 * radius + 10, 0),
    //        this.rest.drawUnguessed().movePinhole(2 * radius + 10, 0));
  }

  //draws the unguessed circles in the list of circles   
  public WorldImage drawUnguessedAll(int numberOfGuesses) {
    if (numberOfGuesses > 0) {
      return new OverlayImage(this.drawUnguessed().movePinhole(0, 50),
          this.drawUnguessedAll(numberOfGuesses - 1).movePinhole(0, 50));
    } else {
      return new EmptyImage();
    }
  }

  // gets the right color in the list of color options
  public Color getColor(int s) {
    return this.getPosition(s, 1);
  }

  //gets the color that's in the position s in the list of colors 
  public Color getPosition(int s, int a) {
    if (s == a) {
      return this.first;
    } else {
      return this.rest.getPosition(s, a + 1);
    }
  }

  public ILoColors constructColors(Color s) {
    return new ConsLoColors(s, this);
  }

  // removes color from the list of colors 
  public ILoColors remove() {
    return this.rest;
  }

}

class ExamplesColors {
  ExamplesColors() {
  };

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
  ILoColors c5 = new ConsLoColors(Color.red, new ConsLoColors(Color.orange,
      new ConsLoColors(Color.yellow, new ConsLoColors(Color.green, new MtLoColors()))));

  // testing draw method 
  boolean testdraw(Tester t) {
    int radius = 20;
    WorldImage circle = new CircleImage(radius, OutlineMode.SOLID, Color.red);
    WorldImage circle1 = new CircleImage(radius, OutlineMode.SOLID, Color.yellow);
    WorldImage circle2 = new CircleImage(radius, OutlineMode.SOLID, Color.pink);

    return t.checkExpect(this.c3.draw(), new BesideImage(circle, circle1, circle2, new EmptyImage()));
    //    return t.checkExpect(this.c3.draw(),
    //        new OverlayImage(circle.movePinhole(2 * radius + 10, 0),
    //            new OverlayImage(circle1.movePinhole(2 * radius + 10, 0),
    //                new OverlayImage(circle2.movePinhole(2 * radius + 10, 0),
    //                    new RectangleImage(0, 0, OutlineMode.SOLID, new Color(0, 0, 0, 0))
    //                        .movePinhole(2 * radius + 10, 0)))));

  }

  // testing drawUnguessed
  boolean testdrawUnguessed(Tester t) {
    int radius = 20;
    WorldImage circle = new CircleImage(radius, OutlineMode.OUTLINE, Color.black);
    WorldImage circle1 = new CircleImage(radius, OutlineMode.OUTLINE, Color.black);
    WorldImage circle2 = new CircleImage(radius, OutlineMode.OUTLINE, Color.black);
    return t.checkExpect(this.c3.drawUnguessed(), new BesideImage(circle, circle1, circle2, new EmptyImage()));
  }

  //tests remove method
  boolean testcheckRemove(Tester t) {
    return t.checkExpect(c5.remove(), new ConsLoColors(Color.orange,
        new ConsLoColors(Color.yellow, new ConsLoColors(Color.green, new MtLoColors()))));
  }
  //tests reverse method 

  boolean testreverse(Tester t) {
    return t.checkExpect(this.c3.reverse(), new ConsLoColors(Color.pink,
        new ConsLoColors(Color.yellow, new ConsLoColors(Color.red, new MtLoColors()))));
  }

  // counts the list of colors
  boolean testcount(Tester t) {
    return t.checkExpect(this.c2.count(), 4);
  }

  // testing getColor
  boolean testgetColor(Tester t) {
    return t.checkExpect(this.c1.getColor(1), Color.red);
  }

  // testing getPosition 
  boolean testgetPosition(Tester t) {
    return t.checkExpect(this.c1.getPosition(2, 1), Color.orange);
  }

  // testing constructColors

  boolean testconstructColors(Tester t) {
    return t.checkExpect(this.c1.constructColors(Color.red), new ConsLoColors(Color.red, c1));
  }

  // testing remove 

  boolean testingremove(Tester t) {
    return t.checkExpect(this.c1.remove(),
        new ConsLoColors(Color.orange, new ConsLoColors(Color.yellow, new ConsLoColors(Color.green,
            new ConsLoColors(Color.blue, new ConsLoColors(Color.pink, new MtLoColors()))))));
  }
}
