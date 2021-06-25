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
		
		String filepath = "./json/json.txt";
		Read readJSON = new Read(filepath);
		
		readJSON.toJsonElement();
		if (readJSON.arraySize() > 0) {
			ArrayList<Map<String, Object>> list = readJSON.getJsonList();
			for (Object o:list) {
				System.out.println(o);
				//readJSON.graphToNodes(readJSON.getGraph((Map)o));
				//TODO: Write here
				Write write = new Write();
				String s = write.mapToJSON((Map)o);
				write.saveFile(s);
			}
		} else {
			LinkedHashMap<String, Object> map = readJSON.getJsonMap();
			System.out.println(map);
		}
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
