import java.util.ArrayList;
import java.util.Iterator;

class ChangeColors implements Iterator<Cell> {

  // the list of items that this iterator iterates over
  ArrayList<Cell> flooded; 

  // Construct an iterator for a given ArrayList
  ChangeColors(ArrayList<Cell> flooded) {
    this.flooded = flooded; 
  }

  @Override
  public boolean hasNext() {
    return this.flooded.iterator().hasNext();
  }

  @Override
  public Cell next() {
    Cell temp = this.flooded.iterator().next();
    this.flooded.iterator().remove();
    return temp;
  }
}