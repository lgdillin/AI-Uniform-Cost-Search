import java.util.TreeSet;

class MyPlanner {

  MyPlanner() {

  }

  MyState uniform_cost_search(MyState startState, MyState goalState) {
    TreeSet<MyState> frontier = new TreeSet<MyState>();
    TreeSet<MyState> visited = new TreeSet<MyState>();

    startState.cost = 0.0;
    startState.parent = null;
    visited.add(startState);
    frontier.add(startState);


    while(frontier.size() > 0) {

      // get lowest-cost state
      MyState s = frontier.first();

      if(s.state.equals(goalState.state))
        return s;

      for(;;) { // each action, a {
        MyState child = transition(s, a); // compute the next state
        double acost = action_cost(s, a); // compute the cost of the action
        if(visited.contains(child)) {
          MyState oldchild = visited.find(child);

          if(s.cost + acost < oldchild.cost) {
            oldchild.cost = s.cost + acost;
            oldchild.parent = s;
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


}
