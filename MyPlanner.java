import java.util.TreeSet;

class MyPlanner {
  Model model;

  MyPlanner() {
  }

  void setModel(Model m) {
    this.model = m;
  }

  MyState uniformCostSearch(MyState startState, MyState goalState) {
    StateComparator sc = new StateComparator();
    CostComparator cc = new CostComparator();

    TreeSet<MyState> frontier = new TreeSet<MyState>(cc);
    TreeSet<MyState> visited = new TreeSet<MyState>(sc);

    startState.cost = 0.0;
    startState.parent = null;
    visited.add(startState);
    frontier.add(startState);


    while(frontier.size() > 0) {

      // get the next state out of the priority queue
      MyState s = frontier.pollFirst();
      //MyState s = frontier.first();

      // Check if we've reached the goal state
      if(s.equals(goalState))
        return s;

      // Compute the cost of taking a step in each of the 8 adjacent directions
      for(int action = 0; action < 8; ++action) {
        MyState child = transition(s, action); // compute the next state
        double acost = actionCost(child, action); // compute the cost of the action

        // Back-patching
        if(visited.contains(child)) {
          MyState oldChild = visited.floor(child);

          // Check to see if oldChild is the one we're looking for
          if(oldChild == null) System.out.println("AAAAAAH");

          if(s.cost + acost < oldChild.cost) {
            oldChild.cost = s.cost + acost;
            oldChild.parent = s;
          }

        } else {
          child.cost = s.cost + acost;
          child.parent = s;
          frontier.add(child);
          visited.add(child);
        }

      }
    }

    throw new RuntimeException("There is no path to the goal");
  }

  MyState findNextState(MyState goalState) {
    MyState s = goalState;
    while(s.parent != null) {
      s = s.parent;
    }
    return s;
  }

  // Compute the state for a given action
  MyState transition(MyState s, int action) {
    MyState newMyState = new MyState(0.0f, s);
    newMyState.x = s.x;
    newMyState.y = s.y;

    if(action == 0) { // Move Right
      newMyState.x += 10.0f;
    } else if(action == 1) { // Move Right - Down
      newMyState.x += 10.0f;
      newMyState.y += -10.0f;
    } else if(action == 2) { // Move Down
      newMyState.y += -10.0f;
    } else if(action == 3) { // Move Left - Down
      newMyState.x += -10.0f;
      newMyState.y += -10.0f;
    } else if(action == 4) { // Move Left
      newMyState.x += -10.0f;
    } else if(action == 5) { // Move Left - Up
      newMyState.x += -10.0f;
      newMyState.y += 10.0f;
    } else if(action == 6) { // Move Up
      newMyState.y += 10.0f;
    } else if(action == 7) { // Move Right - Up
      newMyState.x += 10.0f;
      newMyState.y += 10.0f;
    } else {
      throw new RuntimeException("Invalid direction: " + action);
    }

    return newMyState;
  }

  // double actionCost(MyState s, int action) {
  //   double cost = 0.0f;
  //   if(action == 0) { // Move Right
  //     cost = model.getTravelSpeed(s.x, s.y);
  //   } else if(action == 1) { // Move Right - Down
  //     cost = model.getTravelSpeed(s.x, s.y);
  //   } else if(action == 2) { // Move Down
  //     cost = model.getTravelSpeed(s.x, s.y);
  //   } else if(action == 3) { // Move Left - Down
  //     cost = model.getTravelSpeed(s.x, s.y);
  //   } else if(action == 4) { // Move Left
  //     cost = model.getTravelSpeed(s.x, s.y);
  //   } else if(action == 5) { // Move Left - Up
  //     cost = model.getTravelSpeed(s.x, s.y);
  //   } else if(action == 6) { // Move Up
  //     cost = model.getTravelSpeed(s.x, s.y);
  //   } else if(action == 7) { // Move Right - Up
  //     cost = model.getTravelSpeed(s.x, s.y);
  //   } else {
  //     throw new RuntimeException("Invalid direction: " + action);
  //   }
  //
  //   return cost;
  // }

  double actionCost(MyState s, int action) {
    double cost = model.getTravelSpeed(s.x, s.y);
    return cost;
  }


}
