package research.readjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** An Edge within the graph. */
public class Edge {
	private Node node;
	private ArrayList<String> logics;
	private HashMap<Node, String> destinations;
	
	/** 
	 * Constructor: creates an instance of Edge class
	 * @param node the initial Node
	 * @param logics data_path_logic_list of the Node
	 * @param destinations list of destinations and corresponding methods
	 * */
	public Edge (Node node, ArrayList logics, HashMap destinations) {
		this.node = node;
		this.logics = logics;
		this.destinations = destinations;
	}
	
	/** 
	 * Add a new destination or changes the method related to an existing destination. 
	 * @param destination destination Node
	 * @param method method related to the destination Node
	 * */
	public void addDestination(Node destination, String method) {
		destinations.put(destination,  method);
	}
	
	/** 
	 * Remove an existing destination. 
	 * @param destination destination Node
	 * */
	public void removeDestination(Node destination) {
		if (destinations.remove(destination) == null) {
			System.out.println("Destination does not exist.");
		} else {
			System.out.println("Destination removed.");
		}
	}
	
	public Node getNode() {
		return node;
	}
	
	public ArrayList getLogics() {
		return logics;
	}
	
	public HashMap<Node, String> getDestinations() {
		return destinations;
	}
}
