import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;

class Agent {
	MyState goalState;
	int goalx, goaly;

	// Path Drawing happens here
	void drawPlan(Graphics g, Model m) {
		g.setColor(Color.red);

		// Get rid of this line ASAP
		g.drawLine((int)m.getX(), (int)m.getY(), (int)m.getDestinationX(), (int)m.getDestinationY());

		// Chain up a bunch of lines to make the squiggly line to the goal
		MyState prev = null;
		for(MyState s = goalState; s != null; s = s.parent) { // Read up on back-patching
			g.drawLine(s.x, s.y, prev.x, prev.y);
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

			// goalx = e.getX();
			// goaly = e.getY();
			m.setDestination(e.getX(), e.getY());
			// Put search algorithms here
		}

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
