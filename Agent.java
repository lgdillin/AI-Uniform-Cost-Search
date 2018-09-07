import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeSet;

class Agent {
	float goalX, goalY;
	MyState goalState;

	Agent() {

	}

	// Path Drawing happens here
	void drawPlan(Graphics g, Model m) {
		g.setColor(Color.red);

		// Get rid of this line ASAP
		g.drawLine((int)m.getX(), (int)m.getY(), (int)m.getDestinationX(), (int)m.getDestinationY());

		// Chain up a bunch of lines to make the squiggly line to the goal
		MyState prev = null; // <-- deal with this
		for(MyState s = goalState; s != null; s = s.parent) { // Read up on back-patching
			//g.drawLine((int)s.x, (int)s.y, (int)prev.x, (int)prev.y);
			prev = s;
		}
	}

	// Searching happens here
	void update(Model m)
	{
		goalState = new MyState(m.getX(), m.getY());

		// Check for mouse input
		Controller c = m.getController();
		while(true)
		{
			MouseEvent e = c.nextMouseEvent();
			if(e == null)
				break;

			goalState.x = e.getX();
			goalState.y = e.getY();
			//m.setDestination(e.getX(), e.getY());
		}

		// Put search algorithms here
		// myPlanner.setModel(m); // I don't like this but I wanted to make the code less memory intensive
		//
		// float cost = m.getTravelSpeed(m.getX(), m.getY());
		// MyState startState = new MyState(cost, null);
		// startState.x = m.getX();
		// startState.y = m.getY();
		//
		// goalState = myPlanner.uniformCostSearch(startState, goalState);
		// MyState nextState = myPlanner.findNextState(goalState);
		// m.setDestination(nextState.x, nextState.y);
		// MyState destination = new State();
		// MyState start = new State();
		//myPlanner.uniformCostSearch()

		// Search for path to goal
		// Queue q
		// Set s

		// goalState is the most child, and following each parent takes us back to our starting position
		MyState startState = new MyState(0.0, null, m.getX(), m.getY());

		goalState = uniformCostSearch(startState, goalState, m); // <-- This object is our goal
		// find_next_state it starts at goal state and followers parents' back to the start+1 and returns that as next state
		MyState nextState = findNextState(goalState);

		// m.setDestination(nextState.x, nextState.y);

	}

	MyState uniformCostSearch(MyState startState, MyState goalState, Model m) {
		StateComparator sc = new StateComparator();
		CostComparator cc = new CostComparator();

		TreeSet<MyState> frontier = new TreeSet<MyState>(cc);
		TreeSet<MyState> visited = new TreeSet<MyState>(sc);
		visited.add(startState);
		frontier.add(startState);


		while(frontier.size() > 0) {

			// get the next state out of the priority queue
			MyState s = frontier.pollFirst();
			//MyState s = frontier.first();

			// Check if we've reached the goal state
			if((int)(s.x / 10) == (int)(goalState.x / 10) && (int)(s.y / 10) == (int)(goalState.y / 10))
				return s;

			// Compute the cost of taking a step in each of the 8 adjacent directions
			for(int action = 0; action < 8; ++action) {
				MyState child = transition(s, action); // compute the next state
				double acost = actionCost(child, action, m); // compute the cost of the action

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

		return goalState;

		throw new RuntimeException("There is no path to the goal");
	}

	// Compute the state for a given action
	MyState transition(MyState s, int action) {
		MyState newMyState = new MyState(0.0f, s);

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

	// Calculate the cost of moving to a given state
	double actionCost(MyState s, int action, Model m) {
		//System.out.println(s.x + "    " + s.y);
		double cost = (1.0f / m.getTravelSpeed(s.x, s.y));
		return cost;
	}

	// Find the next optimal step
	MyState findNextState(MyState goalState) {
		MyState s = goalState;
		while(s.parent != null) {
			s = s.parent;
		}
		return s;
	}

	public static void main(String[] args) throws Exception
	{
		Controller.playGame();
	}
}
