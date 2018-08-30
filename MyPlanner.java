import java.util.TreeSet;
import java.util.TreeMap;

class MyPlanner {

  MyPlanner() {

  }

  MyState uniform_cost_search(MyState startState, MyState goalState) {
    TreeSet<MyState> frontier = new TreeSet<MyState>();
    TreeMap<MyState> visited = new TreeMap<MyState>();

    startState.cost = 0.0;
    startState.parent = null;
    visited.add(startState);
    frontier.add(startState);


    while(frontier.size() > 0) {

      // get the next state out of the priority queue
      MyState s = frontier.first();

      // Check if we've reached the goal state
      if(s.state.equals(goalState.state))
        return s;

      for(int action = 0; action < 8; ++action) { // each action, a {
        MyState child = transition(s, action); // compute the next state
        double acost = action_cost(s, action); // compute the cost of the action

        // Back-patching
        if(visited.contains(child)) {
          MyState oldchild = visited.get(child);

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

  MyState transition(MyState s, int action) {
    return null;
  }

  double action_cost(MyState s, int action) {
    return 0.0;
  }


}
