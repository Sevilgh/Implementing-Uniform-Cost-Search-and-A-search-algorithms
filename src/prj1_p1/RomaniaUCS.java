package prj1_p1;

//Sevil Ghasemi ID:985361047
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;

public class RomaniaUCS {

	public static void main(String[] args) { // ------------------------------------MAIN--------------------------------

		RomaniaProblem prblm = new RomaniaProblem("Lugoj", "Bucharest");
		SearchAgent_UCS agent = new SearchAgent_UCS();
		Node node = agent.solve(prblm);
		node.printBackTrace();
	}
}

class SearchAgent_UCS { // ------------------------------Intelligent
						// Agent-----------------------------------------

	public Node solve(RomaniaProblem prblm) {
		int fringeCount = 0;
		int expandedCount = 0;
		// define and initialize START NODE, FRINGE and EXPLORED SET (for GRAPH SEARCH)
		Node startNode = new Node(prblm.getStartState());
		PriorityQueue<Node> fringe = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node i, Node j) {
				if (i.pathCost > j.pathCost)
					return 1;
				if (i.pathCost < j.pathCost)
					return -1;
				else
					return 0;
			}
		});
		fringe.add(startNode);
		fringeCount++;
		Set<String> explored = new HashSet<String>();

		do {
			// select a node from fringe for expansion based on UCS (use PriorityQueue)
			Node n = fringe.poll();
			// do goal test,
			// if true, return current node
			if (prblm.goalTest(n.state)) {
				System.out.println("Sevil Ghasemi ID:985361047");
				System.out.println("UCS");
				System.out.println("Number of nodes pushed into the fringe is: " + fringeCount);
				System.out.println("Number of nodes expanded before reaching the goal is: " + expandedCount);
				return n;
			}
			
			// insert the node into explored list
			expandedCount++;
			explored.add(n.toString());
			
			// else insert children obtained from successor function into fringe
			Action[] children = new Action[] {};
			children = prblm.succFunc(n.state);
			for (int i = 0; i < children.length; i++) {
				Node child = new Node(children[i].successor);
				child.parent = n;
				child.pathCost = n.pathCost + children[i].cost;
				boolean alreadyExists = false;
				Node[] fringeArray = fringe.toArray(new Node[fringe.size()]);
				for (int j = 0; j < fringeArray.length; j++) {
					if ((fringeArray[j].state.city).compareTo(child.toString()) == 0) {
						alreadyExists = true;
						break;
					}
				}
				if (!explored.contains(child.state.toString()) && alreadyExists == false) {
					fringe.add(child);
					fringeCount++;
				} else {
					for (int j = 0; j < fringeArray.length; j++) {
						if (fringeArray[j].state == child.state && fringeArray[j].pathCost > child.pathCost) {
							fringe.remove(fringeArray[j]);
							fringeArray[j].pathCost = child.pathCost;
							fringe.add(fringeArray[j]);
						}
					}
				}
			}
		} while (!fringe.isEmpty());

		Node empty = new Node(new State("")); // if not find the goal return an empty node
		return empty;
	}

}

class State { // -------------------States in problem
				// formulation-------------------------------------
	public String city;

	public State(String city) {
		this.city = city;
	}

	public String toString() {
		return this.city;
	}
}

class Action { // --------------------Actions in problem
				// formulation------------------------------------
	public State successor;
	public double cost;

	public Action(State succ, double c) {
		this.successor = succ;
		this.cost = c;
	}

	public String toString() {
		return (successor.toString() + ", " + cost);
	}
}

class Node { // -----------------------Nodes in the search
				// tree----------------------------------------
	public final State state;
	public double pathCost;
	public Node parent; // will be actually point to a Node object

	public Node(State stValue) {
		this.state = stValue;
		this.pathCost = 0;
		this.parent = null;
	}

	public String toString() {
		return this.state.city;
	}

	public void printBackTrace() {
		if (parent != null)
			parent.printBackTrace();
		System.out.println("\t" + this.state.city + "\t" + pathCost);
	}
}

class RomaniaProblem { // ----------------------------------problem
						// formulation----------------------------------
	State startState;
	State goalState;
	// state space defined by initial state, actions, transition model

	public RomaniaProblem(String start, String goal) {
		startState = new State(start);
		goalState = new State(goal);
	}

	public State getStartState() {
		return startState;
	}

	public boolean goalTest(State st) {
		if (st.city.equals(this.goalState.city))
			return true;
		else
			return false;
	}

	public Action[] succFunc(State st) {
		Action[] children = new Action[] {};

		if (st.city.equals("Arad"))
			children = new Action[] { new Action(new State("Zerind"), 75), new Action(new State("Sibiu"), 140),
					new Action(new State("Timisoara"), 118) };
		else if (st.city == "Zerind")
			children = new Action[] { new Action(new State("Arad"), 75), new Action(new State("Oradea"), 71) };
		else if (st.city == "Oradea")
			children = new Action[] { new Action(new State("Zerind"), 71), new Action(new State("Sibiu"), 151) };
		else if (st.city == "Sibiu")
			children = new Action[] { new Action(new State("Arad"), 140), new Action(new State("Fagaras"), 99),
					new Action(new State("Oradea"), 151), new Action(new State("Rimnicu Vilcea"), 80), };
		else if (st.city == "Fagaras")
			children = new Action[] { new Action(new State("Sibiu"), 99), new Action(new State("Bucharest"), 211) };
		else if (st.city == "Rimnicu Vilcea")
			children = new Action[] { new Action(new State("Sibiu"), 80), new Action(new State("Pitesti"), 97),
					new Action(new State("Craiova"), 146) };
		else if (st.city == "Pitesti")
			children = new Action[] { new Action(new State("Rimnicu Vilcea"), 97),
					new Action(new State("Bucharest"), 101), new Action(new State("Craiova"), 138) };
		else if (st.city == "Timisoara")
			children = new Action[] { new Action(new State("Arad"), 118), new Action(new State("Lugoj"), 111) };
		else if (st.city == "Lugoj")
			children = new Action[] { new Action(new State("Timisoara"), 111), new Action(new State("Mehadia"), 70) };
		else if (st.city == "Mehadia")
			children = new Action[] { new Action(new State("Lugoj"), 70), new Action(new State("Drobeta"), 75) };
		else if (st.city == "Drobeta")
			children = new Action[] { new Action(new State("Mehadia"), 75), new Action(new State("Craiova"), 120) };
		else if (st.city == "Craiova")
			children = new Action[] { new Action(new State("Drobeta"), 120),
					new Action(new State("Rimnicu Vilcea"), 146), new Action(new State("Pitesti"), 138) };
		else if (st.city == "Bucharest")
			children = new Action[] { new Action(new State("Pitesti"), 101), new Action(new State("Giurgiu"), 90),
					new Action(new State("Fagaras"), 211) };
		else if (st.city == "Giurgiu")
			children = new Action[] { new Action(new State("Bucharest"), 90) };

		return children;

	}

}