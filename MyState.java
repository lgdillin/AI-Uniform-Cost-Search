class MyState {
  public double cost;
  double heuristic;
  MyState parent;
  State state;

  MyState(double cost, MyState p) {
    parent = p;

  }
}
