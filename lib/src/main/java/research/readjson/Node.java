package research.readjson;

/** A node within the graph. */
public class Node {
	private String pathname;
	
	/** 
	 * Constructor: creates an instance of Node class with the given pathname.
	 * @param pathname the pathname of the Node
	 * */
	public Node(String pathname) {
		this.pathname = pathname;
	}
	
	/**
	 * Returns the pathname of the Node 
	 * @return the pathname of the Node 
	 * */
	public String getPathname() {
		return pathname;
	}
}
