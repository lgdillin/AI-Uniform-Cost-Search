import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

class Agent {
	int paint;
	float goalX, goalY;
	MyState goalState;
	TreeSet<MyState> frontier, visited;

	Agent() {
		paint = 0;
		goalState = new MyState(-1, -1);
	}

	// Path Drawing happens here
	void drawPlan(Graphics g, Model m) {
		if(paint % 10 == 0) {
			g.setColor(Color.red);

			// Chain up a bunch of lines to make the squiggly line to the goal
			MyState prev = goalState; // <-- deal with this
			for(MyState s = goalState; s != null; s = s.parent) { // Read up on back-patching
				g.drawLine((int)s.x, (int)s.y, (int)prev.x, (int)prev.y);
				prev = s;
			}

			g.setColor(Color.yellow);
			Iterator<MyState> it = frontier.iterator();
			while(it.hasNext()) {
				MyState s = it.next();
				g.fillOval((int)(s.x), (int)(s.y), 10, 10);
			}
			paint = 0;
		} else
			++paint;

	}

	// Searching happens here
	void update(Model m)
	{
		// Initialization
		if(goalState.x == -1 && goalState.y == -1)
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

		// goalState is the most child, and following each parent takes us back to our starting position
		MyState startState = new MyState(0.0, null, m.getX(), m.getY());

		goalState = uniformCostSearch(startState, goalState, m); // <-- This object is our goal
		// find_next_state it starts at goal state and followers parents' back to the start+1 and returns that as next state
		MyState nextState = findNextState(startState, goalState);

		// Moves the robot to the given (x,y) position
		m.setDestination(nextState.x, nextState.y);

	}

	MyState uniformCostSearch(MyState startState, MyState goalState, Model m) {
		StateComparator sc = new StateComparator();
		CostComparator cc = new CostComparator();

		frontier = new TreeSet<MyState>(cc);
		visited = new TreeSet<MyState>(sc);
		visited.add(startState);
		frontier.add(startState);


		while(frontier.size() > 0) {

			// get the next state out of the priority queue
			MyState s = frontier.pollFirst();
			//MyState s = frontier.first();

			// Check if we've reached the goal state
			if(equalStates(s, goalState))
				return s;

			// Compute the cost of taking a step in each of the 8 adjacent directions
			for(int action = 0; action < 8; ++action) {
				MyState child = transition(s, action); // compute the next state
				double acost = actionCost(child, action, m); // compute the cost of the action

				// Back-patching
				if(visited.contains(child)) {
					MyState oldChild = visited.floor(child);
					// Check to see if oldChild is the one we're looking for
					if(equalStates(oldChild, child)) {

					} else {
						if(s.cost + acost < oldChild.cost) {
							oldChild.cost = s.cost + acost;
							oldChild.parent = s;
						}
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

	// Compute the state for a given action
	MyState transition(MyState s, int action) {
		MyState newMyState = new MyState(0.0f, s);

		if(action == 0) { // Move Right
			newMyState.x += 10.0f;
		} else if(action == 1) { // Move Right - Down
			newMyState.x += 10.0f;
			newMyState.y += 10.0f; // was -10
		} else if(action == 2) { // Move Down
			newMyState.y += 10.0f; // was -10
		} else if(action == 3) { // Move Left - Down
			newMyState.x += -10.0f;
			newMyState.y += 10.0f; // was -10
		} else if(action == 4) { // Move Left
			newMyState.x += -10.0f;
		} else if(action == 5) { // Move Left - Up
			newMyState.x += -10.0f;
			newMyState.y += -10.0f; // was 10
		} else if(action == 6) { // Move Up
			newMyState.y += -10.0f;
		} else if(action == 7) { // Move Right - Up
			newMyState.x += 10.0f;
			newMyState.y += -10.0f;
		} else {
			throw new RuntimeException("Invalid direction: " + action);
		}

		// Safety first!
		if(newMyState.x < 0) newMyState.x = 0;
		if(newMyState.y < 0) newMyState.y = 0;
		if(newMyState.x >= 1200) newMyState.x = 1199;
		if(newMyState.y >= 600) newMyState.y = 599;


		return newMyState;
	}

	// Calculate the cost of moving to a given state
	double actionCost(MyState s, int action, Model m) {
		//double cost = (m.getDistanceToDestination(0) / m.getTravelSpeed(s.x, s.y));
		double cost = (1.0f / m.getTravelSpeed(s.x, s.y));
		return cost;
	}

	// Find the next optimal step
	MyState findNextState(MyState startState, MyState goalState) {
		MyState s = goalState;
		while(s.parent != null) {
			if(equalStates(startState, s.parent))
				return s;

			s = s.parent;
		}
		return s;
	}

	// Compare two states and see if they are equal
	boolean equalStates(MyState a, MyState b) {
		int ax = (int)(a.x * 0.1f);
		int ay = (int)(a.y * 0.1f);

		int bx = (int)(b.x * 0.1f);
		int by = (int)(b.y * 0.1f);

		// Pretend X is the most significant coordinate
		if(ax == bx && ay == by) return true;
		else return false;
	}

	public static void main(String[] args) throws Exception
	{
		Controller.playGame();
	}
}
