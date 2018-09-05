class MyState {
  float x, y;
  public double cost;
  double heuristic;
  MyState parent;
  // State state;

  MyState(double cost, MyState p) {
    this.cost = cost;
    parent = p;
  }

  MyState(double cost, MyState p, float x, float y) {
    this.cost = cost;
    parent = p;
    this.x = x;
    this.y = y;
  }
}
