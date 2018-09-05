import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;

class Agent {
	MyState goalState;
	MyPlanner myPlanner;
	float goalX, goalY;

	Agent() {
		myPlanner = new MyPlanner();
	}

	// Path Drawing happens here
	void drawPlan(Graphics g, Model m) {
		g.setColor(Color.red);

		// Get rid of this line ASAP
		g.drawLine((int)m.getX(), (int)m.getY(), (int)m.getDestinationX(), (int)m.getDestinationY());

		// Chain up a bunch of lines to make the squiggly line to the goal
		MyState prev = null; // <-- deal with this
		for(MyState s = goalState; s != null; s = s.parent) { // Read up on back-patching
			g.drawLine((int)s.x, (int)s.y, (int)prev.x, (int)prev.y);
			prev = s;
		}
	}

	// Searching happens here
	void update(Model m)
	{
		// Check for mouse input
		Controller c = m.getController();
		while(true)
		{
			MouseEvent e = c.nextMouseEvent();
			if(e == null)
				break;

			goalX = e.getX();
			goalY = e.getY();
			//m.setDestination(e.getX(), e.getY());
		}

		// Put search algorithms here
		myPlanner.setModel(m); // I don't like this but I wanted to make the code less memory intensive

		float cost = m.getTravelSpeed(m.getX(), m.getY());
		MyState startState = new MyState(cost, null);
		startState.x = m.getX();
		startState.y = m.getY();

		goalState = myPlanner.uniformCostSearch(startState, goalState);
		MyState nextState = myPlanner.findNextState(goalState);
		m.setDestination(nextState.x, nextState.y);
		// MyState destination = new State();
		// MyState start = new State();
		//myPlanner.uniformCostSearch()

		// Search for path to goal
		// Queue q
		// Set s
		// goalState = doUCS(q, s); <-- This object is our goal
		// It is the most child, and following each parent takes us back to our starting position

		// nextState = find_next_state(goal_state);
		// find_next_state it starts at goal state and followers parents' back to the start+1 and returns that as next state

		// m.setDestination(nextState.x, nextState.y);

	}

	public static void main(String[] args) throws Exception
	{
		Controller.playGame();
	}
}
