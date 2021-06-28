package research.readjson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReadTest {
	public static void main(String[] args) {
		
//		String url_string = "https://raw.githubusercontent.com/aliciayuting/cascade/dfg/src/applications/demos/dairy_farm/dfgs.json";		
//		Read readJSON = readWithURL(url_string);
		
		String filepath = "./json/readtest.txt";
		Read readJSON = new Read(filepath);
		
		readJSON.toJsonElement();
		if (readJSON.arraySize() > 0) {
			ArrayList<Map<String, Object>> list = readJSON.getJsonList();
			for (Object o:list) {
				System.out.println(o);
				Write write = new Write();
				readJSON.graphToNodes(readJSON.getGraph((Map)o));
				Map map = (Map) o;
				System.out.println(readJSON.getGraph(map));
				readJSON.addEdge(new Node("haha"), new Node("yes"), new ArrayList(), "put");
				readJSON.deleteNode("/dairy_farm/front_end");
				map.put("graph", readJSON.EdgesToGraph());
				System.out.println(readJSON.getGraph(map));
				write.saveFile(write.toJSON(map), "readtest.txt");
				
			}
		} else {
			LinkedHashMap<String, Object> map = readJSON.getJsonMap();
			Write write = new Write();
			readJSON.graphToNodes(readJSON.getGraph(map));
			readJSON.addEdge(new Node("haha"), new Node("yes"), new ArrayList(), "put");
//			readJSON.deleteNode("/dairy_farm/front_end");
			map.put("graph", readJSON.EdgesToGraph());
			write.saveFile(write.toJSON(map), "readtest.txt");
		}
		
		Read test = new Read("./json/empty.txt");
		//Read test = new Read();
		test.toJsonElement(); //maybe make this automatic?
		test.graphToNodes(test.getJsonList());
		Node one = new Node("1");
		Node two = new Node("2");
		Node three = new Node("3");
//		test.addEdge(one,  two, null, "put");
//		test.addEdge(two,  three, null, "put");
		Write write = new Write();
		write.saveFile(write.toJSON(test.EdgesToGraph()), "empty.txt");
		
	}
	
	public static Read readWithURL(String url_string) {
		URL url = null;
		try {
			url = new URL(url_string);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new Read(url);
	}
}
