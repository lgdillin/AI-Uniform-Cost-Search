import java.util.TreeSet;

class MyPlanner {

  MyPlanner() {

  }

  void uniform_cost_search(MyState startState, MyState goalState) {
    TreeSet<MyState> frontier = new TreeSet<MyState>();
    TreeSet<MyState> visited = new TreeSet<MyState>();

    startState.cost = 0.0;
    startState.parent = null;
    beenthere.add(startState);
    frontier.add(startState);


    while(frontier.size() > 0) {

      // get lowest-cost state
      MyState s = frontier.first();

      if(s.state.isEqual(goalState.state))
        return s;

      for each action, a {
        MyState child = transition(s, a); // compute the next state
        acost = action_cost(s, a); // compute the cost of the action
        if(child is in beenthere) {
          oldchild = beenthere.find(child)
          if(s.cost + acost < oldchild.cost) {
            oldchild.cost = s.cost + acost;
            oldchild.parent = s;
          }
        }
        else {
          child.cost = s.cost + acost;
          child.parent = s;
          frontier.add(child);
          beenthere.add(child);
        }
      }
    }
    throw new RuntimeException("There is no path to the goal");
  }

  
}
