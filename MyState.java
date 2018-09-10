class MyState {
  float x, y;
  public double cost;
  double heuristic;
  MyState parent;
  // State state;

  MyState() {

  }

  MyState(MyState p) {
    this.x = p.x;
    this.y = p.y;
  }

  MyState(float x, float y) {
    this.x = x;
    this.y = y;
  }

  MyState(double cost, MyState p) {
    this.cost = cost;
    parent = p;

    if(p != null) {
      this.x = parent.x;
      this.y = parent.y;
    }
  }

  MyState(double cost, MyState p, float x, float y) {
    this.cost = cost;
    parent = p;
    this.x = x;
    this.y = y;
  }

}
