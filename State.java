

class State {
  int x, y;

  State(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean equals(State s) {
    if(this.x != s.x) return false;
    if(this.y != s.y) return false;

    return true;
  }
}
