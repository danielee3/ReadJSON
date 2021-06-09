package research.readjson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReadTest {
	public static void main(String[] args) {
		
		String url_string = "https://raw.githubusercontent.com/aliciayuting/cascade/dfg/src/applications/demos/dairy_farm/dfgs.json";
		Read readJSON = new Read(url_string);
		readJSON.toJsonElement();
		if (readJSON.isJsonArray()) {
			ArrayList<Map<String, Object>> list = readJSON.getJsonList();
			for (Object o:list) {
				System.out.println(o);
			}
		} else {
			LinkedHashMap<String, Object> map = readJSON.getJsonMap();
			System.out.println(map);
		}
	}
}
