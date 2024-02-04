package prj1_p2;

//Sevil Ghasemi ID:985361047
import java.util.PriorityQueue;

import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.Comparator;

public class AStar {

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
		Node startNode = new Node(prblm.getStartState());
		startNode.pathCost += heuristic(startNode.toString());
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
			Node n = fringe.poll();
			if (heuristic(n.toString()) == 0) {
				System.out.println("Sevil Ghasemi ID:985361047");
				System.out.println("A*");
				System.out.println("Number of nodes pushed into the fringe is: " + fringeCount);
				System.out.println("Number of nodes expanded before reaching the goal is: " + expandedCount);
				return n;
			}
			expandedCount++;
			explored.add(n.toString());
			n.pathCost -= heuristic(n.toString());
			
			Action[] children = new Action[] {};
			children = prblm.succFunc(n.state);
			for (int i = 0; i < children.length; i++) {
				Node child = new Node(children[i].successor);
				child.parent = n;
				child.pathCost = n.pathCost + children[i].cost + heuristic(child.state.toString());
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

	public int heuristic(String state) {
		if (state.compareTo("Arad") == 0)
			return 366;
		else if (state.compareTo("Zerind") == 0)
			return 374;
		else if (state.compareTo("Oradea") == 0)
			return 380;
		else if (state.compareTo("Sibiu") == 0)
			return 253;
		else if (state.compareTo("Fagaras") == 0)
			return 176;
		else if (state.compareTo("Rimnicu Vilcea") == 0)
			return 193;
		else if (state.compareTo("Pitesti") == 0)
			return 100;
		else if (state.compareTo("Timisoara") == 0)
			return 329;
		else if (state.compareTo("Lugoj") == 0)
			return 244;
		else if (state.compareTo("Mehadia") == 0)
			return 241;
		else if (state.compareTo("Drobeta") == 0)
			return 242;
		else if (state.compareTo("Craiova") == 0)
			return 160;
		else if (state.compareTo("Bucharest") == 0)
			return 0;
		else if (state.compareTo("Giurgiu") == 0)
			return 77;

		return 0;

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