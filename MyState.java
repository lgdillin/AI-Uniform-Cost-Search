class MyState {
  float x, y;
  public double cost;
  double heuristic;
  MyState parent;
  State state;

  MyState(double cost, MyState p) {
    parent = p;

  }

  double transition(State s, State there) {
    return 0.0;
  }
}
