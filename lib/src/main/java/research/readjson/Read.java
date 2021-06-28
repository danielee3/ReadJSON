package research.readjson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

/** Takes a JSON file and converts it to Java data structures. */
public class Read {
	private String jsonString;
	private JsonArray jsonArray;
	private JsonObject jsonObject;
	private ArrayList<Map<String, Object>> jsonList;
	private LinkedHashMap<String, Object> jsonMap;
	private HashMap<String, Node> nodes;
	private HashMap<Node, Edge> edges;
	
	/** An empty constructor. */
	public Read() {
		nodes = new HashMap<>();
		edges = new HashMap<>();
	}
	
	/** 
	 * Constructor: creates an instance of Read class with an input JSON file. The JSON file given as its url address then gets saved as a Java String. 
	 * @param url_string the url address of the json file. 
	 * */
	public Read(URL url) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
		    StringBuilder stringBuilder = new StringBuilder();
		    String inputLine;
		    while ((inputLine = bufferedReader.readLine()) != null)
		    {
		        stringBuilder.append(inputLine);
		        stringBuilder.append(System.lineSeparator());
		    }

		    bufferedReader.close();
		    jsonString = stringBuilder.toString().trim();
        }catch (IOException e){
            e.printStackTrace();
        }
	}
	
	/** 
	 * Constructor: creates an instance of Read class with an input JSON file. The JSON file given as its file path then gets saved as a Java String. 
	 * @param filepath the local file path of the json file.
	 * */
	public Read(String filepath) {
		try {
			//this.url_string = new URL(url_string);
			FileReader fr = new FileReader(filepath);
			BufferedReader bufferedReader = new BufferedReader(fr);
		    StringBuilder stringBuilder = new StringBuilder();
		    String inputLine;
		    while ((inputLine = bufferedReader.readLine()) != null)
		    {
		        stringBuilder.append(inputLine);
		        stringBuilder.append(System.lineSeparator());
		    }

		    bufferedReader.close();
		    jsonString = stringBuilder.toString().trim();
        }catch (IOException e){
            e.printStackTrace();
        }
	}
	
	
	/**
	 * Returns the given JSON file as a String.  
	 * @return a String form of the JSON file. 
	 * */
	public String getJsonString() {
		return jsonString;
	}
	
	/** 
	 * Returns the given JSON file as an ArrayList, when the given JSON element is a JSON array
	 * @return an ArrayList form of the JSON array. 
	 * */
	public ArrayList<Map<String, Object>> getJsonList() {
		return jsonList;
	}
	
	/** 
	 * Returns the given JSON file as a LinkedHashMap, when the given JSON element is a single JSON object or a JSON array of length one.
	 * @return a LinkedHashMap form of the JSON object. 
	 * */
	public LinkedHashMap<String, Object> getJsonMap() {
		return jsonMap;
	}
	
	/** Execute the conversion of the JSON file into Java data structures. */
	public void toJsonElement() {
		if (jsonString.length() == 0) {
			nodes = new HashMap<>();
			edges = new HashMap<>();
			return;
		}
		if (jsonString.charAt(0) == '[') { // JSON array
			toArrayList();
		} else { // JSON object
			toLinkedHashMap();
		}
	}
	
	/** 
	 * Returns the size of the input JSON array, or 0 if the given JSON file is not a JSON array but a single JSON object.
	 * @return the size of the input JSON array. 
	 * */
	public int arraySize() {
		if (jsonArray == null) return 0;
		return jsonArray.size();
	}
	
	/** Converts jsonArray into Java ArrayList. */
	private void toArrayList() {
		jsonList = new ArrayList<>();
		toJsonArray();
		for (JsonElement element : jsonArray) {
			JsonObject object = element.getAsJsonObject();
			jsonMap = new Gson().fromJson(object, LinkedHashMap.class);
			jsonList.add(jsonMap);
		}
	}
	
	/**Converts jsonObject into Java LinkedHashMap. */
	private void toLinkedHashMap() {
		toJsonObject();
		jsonMap = new Gson().fromJson(jsonObject, LinkedHashMap.class);
	}
	
	/** Converts the String form of the JSON file into a JsonArray when jsonString starts and ends with square brackets. */
	private void toJsonArray() {
		jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
	}
	
	/** Converts the String form of the JSON file into a JsonObject when jsonString starts and ends with curly brackets. */
	private void toJsonObject() {
		jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
	}
	
	/** 
	 * Extracts the graph part from a JSON Object. 
	 * @param obj a JSON object in the form of Map.
	 * @return the graph in the form of ArrayList.
	 * */
	public ArrayList getGraph(Map obj) {
		ArrayList list = (ArrayList) obj.get("graph");
		return list;
	}
	
	/**
	 * 
	 * @param graph
	 */
	public void graphToNodes(ArrayList graph) {
		if (graph == null) return;
		nodes = new HashMap<>();
		edges = new HashMap<>();
		for (Object o:graph) {
			Map map = (Map) o;
			String pathname = map.get("object_pool_pathname").toString();
			Node node = new Node(pathname);
			nodes.put(pathname, node);
			Edge edge = graphToEdge(node, map);
			edges.put(node, edge);
		}
	}
	
	public void graphToNodes() {
		//When there is graph
		//when there is not... make one!!
	}
	
	/**
	 * 
	 * @param node the Node from which Edge branches
	 * @param map the LinkedTreeMap form of graph
	 * @return the Edge that branches from the Node
	 */
	private Edge graphToEdge(Node node, Map map) {
		ArrayList logics = (ArrayList) map.get("data_path_logic_list");
		HashMap<Node, String> destinations = new HashMap<>();
		ArrayList dList = (ArrayList) map.get("destinations");
		for (Object o:dList) {
			Map dMap = (Map) o;
			for (Object d:dMap.keySet()) {
				String pathname = (String)d;
				if (nodes.containsKey(pathname)) {
					destinations.put(nodes.get(pathname), (String)dMap.get(d));
				} else {
					Node newNode = new Node(pathname);
					nodes.put(pathname, newNode);
					destinations.put(newNode, (String)dMap.get(d));
				}
			}
		}
		Edge edge = new Edge(node, logics, destinations);
		return edge;	
	}
	
	//TODO
	public ArrayList<Map> EdgesToGraph() {
		ArrayList<Map> list = new ArrayList<>();
		for (Node node:edges.keySet()) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put("object_pool_pathname", node.getPathname());
			map.put("data_path_logic_list", edges.get(node).getLogics());
			ArrayList<Object> destinationList = new ArrayList<>();
			LinkedHashMap<String, String> destinations = new LinkedHashMap<>();
			for (Node dn:edges.get(node).getDestinations().keySet()) {
				//LinkedHashMap<String, String> destinations = new LinkedHashMap<>();
				destinations.put(dn.getPathname(), edges.get(node).getDestinations().get(dn));
				//destinationList.add(destinations);
			}
			destinationList.add(destinations);
			map.put("destinations", destinationList);
			list.add(map);
		}
		return list;
	}
	
	public void addNode(Node node) {
		nodes.put(node.getPathname(), node);
	}
	
	public void addEdge(Node node, Node destination, ArrayList<String> logicList, String method) {
		addNode(node);
		addNode(destination);
		if (edges.containsKey(node)) {
			edges.get(node).addDestination(destination, method);
		} else {
			HashMap<Node, String> destinations = new HashMap<>();
			Edge edge = new Edge(node, logicList, destinations);
			edge.addDestination(destination, method);
			edges.put(node, edge);
		}
	}
	
	public void deleteNode(Node node) {
		nodes.remove(node.getPathname());
		edges.remove(node);
	}
	
	public void deleteNode(String pathname) {
		edges.remove(nodes.get(pathname));
		nodes.remove(pathname);
	}
	
	public HashMap getNodes() {
		return nodes;
	}
	
	public HashMap getEdges() {
		return edges;
	}
}
