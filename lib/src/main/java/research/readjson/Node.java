package research.readjson;

/** A node within the graph. */
public class Node {
	private String pathname;
	private String cellType;
	
	/** 
	 * Constructor: creates an instance of Node class with the given pathname.
	 * @param pathname the pathname of the Node
	 * */
	public Node(String pathname) {
		this.pathname = pathname;
	}
	
	public Node(String pathname, String cellType) {
		this.pathname = pathname;
		this.cellType = cellType;
	}
	
	/**
	 * Returns the pathname of the Node 
	 * @return the pathname of the Node 
	 * */
	public String getPathname() {
		return pathname;
	}
	
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	
	public String getCellType() {
		return cellType;
	}
}
