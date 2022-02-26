import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// represents a single cell of the game 
class Vertex {
  // x position of the cell
  int x;
  // y position of the cell
  int y;

  Random rand = new Random();
  // edges to the current cell
  ArrayList<Edge> outedges;

  boolean explored;
  boolean path;

  int numb = 14;

  // Constructor
  Vertex(int x, int y) {
    this.x = x;
    this.y = y;
    this.outedges = new ArrayList<Edge>();
//    this.explored = false;
//    this.path = false;
  }

  void createEdges(Vertex other, ArrayList<Edge> list) {
    int randomNumber = rand.nextInt(50);
    Edge newEdge = new Edge(this, other, randomNumber);
    list.add(newEdge);
  }

  // adding node to the given list
  ArrayList<Edge> getEdges(ArrayList<Edge> list) {
    for (Edge e : this.outedges) {
      if (!list.contains(e)) {
        list.add(e);
      }
    }
    return list;
  }

  // draws the vertex
  WorldImage drawCell() {
    return new RectangleImage(numb, numb, OutlineMode.OUTLINE, Color.gray);
  }

  // draws the seen Vertex
  WorldImage drawSeen() {
    if (this.explored) {
      return new RectangleImage(numb, numb, OutlineMode.SOLID, Color.pink.darker());
    }
    return new EmptyImage();
  }

  // draws the vertex
  WorldImage drawPath() {
    if (this.path) {
      return new RectangleImage(13, 13, OutlineMode.SOLID, Color.pink.darker().darker());
    }
    return new EmptyImage();
  }

  // draws playerVertex
  WorldImage drawPlayer() {
    return new RectangleImage(13, 13, OutlineMode.SOLID, Color.pink);
  }

  @Override
//are they the same? 
  public boolean equals(Object other) {
    if (other instanceof Vertex) {
      Vertex v = (Vertex) other;
      return v.x == this.x && v.y == this.y;
    }
    return false;
  }

}