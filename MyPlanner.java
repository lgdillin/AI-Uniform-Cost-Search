class MyPlanner {
  method uniform_cost_search(MyState startState, MyState goalState) {
    PriorityQueue frontier = new PriorityQueue();
    Set beenthere = new Set();
    startState.cost = 0.0;
    startState.parent = null;
    beenthere.add(startState);
    frontier.add(startState);
    while(frontier.size() > 0) {
      MyState s = frontier.pop_first(); // get lowest-cost state
      if(s.state.isEqual(goalState.state))
        return s;
      for each action, a {
        child = transition(s, a); // compute the next state
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
