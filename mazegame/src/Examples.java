import java.util.ArrayList;

import tester.Tester;
import tester.Tester;
import java.util.*;

class MazeGameExamples {
  MazeGameExamples() {
  }

  MazeGame world;

  ArrayList<ArrayList<Vertex>> board1;
  ArrayList<ArrayList<Vertex>> board2;
  ArrayList<ArrayList<Vertex>> board3;

  ArrayList<Vertex> reconstructedpath;

  ArrayList<Vertex> list1;
  ArrayList<Vertex> list2;
  ArrayList<Vertex> list3;
  ArrayList<Vertex> list4;

  ArrayList<Vertex> gridlist1;
  ArrayList<Vertex> gridlist2;
  ArrayList<Vertex> gridlist3;

  ArrayList<Edge> edges;
  ArrayList<Edge> edges2;
  ArrayList<Edge> sortededges;
  ArrayList<Edge> edgesintree;

  Vertex a;
  Vertex b;
  Vertex c;
  Vertex d;
  Vertex e;
  Vertex f;

  Vertex g;
  Vertex h;
  Vertex i;
  Vertex j;
  Vertex k;
  Vertex l;

  Vertex a1;
  Vertex a2;
  Vertex a3;
  Vertex a4;
  Vertex a5;
  Vertex a6;
  Vertex a7;
  Vertex a8;
  Vertex a9;

  Edge eToC;
  Edge cToD;
  Edge aToB;
  Edge bToE;
  Edge bToC;
  Edge fToD;
  Edge aToE;
  Edge bToF;

  Edge gToH;
  Edge gToJ;
  Edge hToI;
  Edge hToK;
  Edge jToI;
  Edge jToK;
  Edge kToI;
  Edge iToL;

  HashMap<Vertex, Vertex> rep;
  HashMap<Vertex, Vertex> linkedrep;
  HashMap<Vertex, Vertex> cameFromEdge;

  Player player;

  public void initData() {
    this.world = new MazeGame(2, 3);
    // vertices of map
    this.a = new Vertex(0, 0);
    this.b = new Vertex(0, 1);
    this.c = new Vertex(0, 2);
    this.d = new Vertex(1, 0);
    this.e = new Vertex(1, 1);
    this.f = new Vertex(1, 2);

    // example of edges in a map
    this.eToC = new Edge(this.e, this.c, 15);
    this.cToD = new Edge(this.c, this.d, 25);
    this.aToB = new Edge(this.a, this.b, 30);
    this.bToE = new Edge(this.b, this.e, 35);
    this.bToC = new Edge(this.b, this.c, 40);
    this.fToD = new Edge(this.f, this.d, 50);
    this.aToE = new Edge(this.a, this.e, 50);
    this.bToF = new Edge(this.b, this.f, 50);

    // examples of vertices
    this.g = new Vertex(0, 0);
    this.h = new Vertex(0, 1);
    this.i = new Vertex(0, 2);
    this.j = new Vertex(1, 0);
    this.k = new Vertex(1, 1);
    this.i = new Vertex(1, 2);

    // examples of vertices
    this.a1 = new Vertex(0, 0);
    this.a2 = new Vertex(0, 1);
    this.a3 = new Vertex(0, 2);
    this.a4 = new Vertex(1, 0);
    this.a5 = new Vertex(1, 1);
    this.a6 = new Vertex(1, 2);
    this.a7 = new Vertex(1, 0);
    this.a8 = new Vertex(1, 1);
    this.a9 = new Vertex(1, 2);

    // examples of edges
    this.gToH = new Edge(this.g, this.h, 1);
    this.gToJ = new Edge(this.g, this.j, 1);
    this.hToI = new Edge(this.h, this.i, 1);
    this.hToK = new Edge(this.h, this.k, 1);
    this.iToL = new Edge(this.i, this.l, 1);
    this.jToK = new Edge(this.j, this.k, 1);
    this.kToI = new Edge(this.k, this.i, 1);

    this.list3 = new ArrayList<Vertex>(Arrays.asList(this.g, this.h, this.i));
    this.list4 = new ArrayList<Vertex>(Arrays.asList(this.j, this.k, this.l));
    this.board1 = new ArrayList<ArrayList<Vertex>>(Arrays.asList(this.list3, this.list4));

    this.edges2 = new ArrayList<Edge>(
        Arrays.asList(this.gToH, this.gToJ, this.hToI, this.hToK, this.iToL, this.jToK, this.kToI));

    this.a.outedges.add(this.aToB);
    this.a.outedges.add(this.aToE);
    this.b.outedges.add(this.bToC);
    this.b.outedges.add(this.bToE);
    this.b.outedges.add(this.bToF);
    this.c.outedges.add(this.cToD);
    this.f.outedges.add(this.fToD);
    this.e.outedges.add(this.eToC);

    this.list1 = new ArrayList<Vertex>(Arrays.asList(this.a, this.b, this.c));
    this.list2 = new ArrayList<Vertex>(Arrays.asList(this.d, this.e, this.f));
    this.board2 = new ArrayList<ArrayList<Vertex>>(Arrays.asList(this.list1, this.list2));

    this.edges = new ArrayList<Edge>(Arrays.asList(this.aToB, this.bToC, this.bToF, this.eToC,
        this.aToE, this.bToE, this.cToD, this.fToD));

    this.sortededges = new ArrayList<Edge>(Arrays.asList(this.eToC, this.cToD, this.aToB, this.bToE,
        this.bToC, this.fToD, this.aToE, this.bToF));

    this.rep = new HashMap<Vertex, Vertex>();
    this.rep.put(this.a, this.a);
    this.rep.put(this.b, this.b);
    this.rep.put(this.c, this.c);
    this.rep.put(this.d, this.d);
    this.rep.put(this.e, this.f);
    this.rep.put(this.f, this.f);

    this.linkedrep = new HashMap<Vertex, Vertex>();
    this.linkedrep.put(this.a, this.e);
    this.linkedrep.put(this.b, this.a);
    this.linkedrep.put(this.c, this.e);
    this.linkedrep.put(this.d, this.e);
    this.linkedrep.put(this.e, this.e);
    this.linkedrep.put(this.f, this.d);

    this.edgesintree = new ArrayList<Edge>(
        Arrays.asList(this.eToC, this.cToD, this.aToB, this.bToE, this.fToD));

    this.player = new Player(0, 0);

    this.gridlist1 = new ArrayList<Vertex>(Arrays.asList(this.a1, this.a2, this.a3));
    this.gridlist2 = new ArrayList<Vertex>(Arrays.asList(this.a4, this.a5, this.a6));
    this.gridlist3 = new ArrayList<Vertex>(Arrays.asList(this.a7, this.a8, this.a9));
    this.board3 = new ArrayList<ArrayList<Vertex>>(
        Arrays.asList(this.gridlist1, this.gridlist2, this.gridlist3));

    this.cameFromEdge = new HashMap<Vertex, Vertex>();
    this.cameFromEdge.put(this.a2, this.a1);
    this.cameFromEdge.put(this.a3, this.a2);
    this.cameFromEdge.put(this.a4, this.a3);
    this.cameFromEdge.put(this.a5, this.a4);
    this.cameFromEdge.put(this.a6, this.a4);

    this.reconstructedpath = new ArrayList<Vertex>(
        Arrays.asList(this.a9, this.a4, this.a3, this.a2, this.a1));

  }

  void testGame(Tester t) {
    MazeGame g = new MazeGame(30, 30);
    g.bigBang(g.width * 15, g.width * 15, 0.05);
  }

  // tests possibleEdges
  void testPossibleEdges(Tester t) {
    this.initData();
    t.checkExpect(this.world.connectAllEdges(), this.sortededges);
  }

  // Testing initialize()

  public void TestInitialize(Tester t) {
    initData();
    this.world.initialize();
    Vertex v1 = this.world.board.get(0).get(0);
    Vertex v2 = this.world.board.get(0).get(1);
    Vertex v3 = this.world.board.get(0).get(2);
    Vertex v4 = this.world.board.get(1).get(0);
    Vertex v5 = this.world.board.get(1).get(1);
    Vertex v6 = this.world.board.get(1).get(2);

    t.checkExpect(v1, a);
    t.checkExpect(v2, b);
    t.checkExpect(v3, c);
    t.checkExpect(v4, d);
    t.checkExpect(v5, e);
    t.checkExpect(v6, f);

  }

  // tests grid
  void testBoard(Tester t) {
    this.initData();
    for (int x = 0; x < this.world.board.size(); x++) {
      for (int y = 0; y < this.world.board.get(x).size(); y++) {
        Vertex v = this.world.board.get(x).get(y);
        ArrayList<Edge> e = new ArrayList<Edge>();
        t.checkExpect(v, new Vertex(x, y));
      }
    }
  }

  // tests reconstruct
  public void testReconstruct(Tester t) {
    this.initData();
    this.world.board = this.board3;
    this.world.reconstruct(this.cameFromEdge, this.a4);
    t.checkExpect(this.world.solution, this.reconstructedpath);
  }

  // test makePath
  public void makePath(Tester t) {
    this.initData();

    this.world.makePath();

    t.checkExpect(this.world.edgesInTree, this.edgesintree);
  }

  // test find
  public void find(Tester t) {

  }

  // test moveTo

}
