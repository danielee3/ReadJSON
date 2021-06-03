

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

public class Read {
	public static void main(String[] args) {
		
		String url_string = "https://raw.githubusercontent.com/aliciayuting/cascade/dfg/src/applications/demos/dairy_farm/dfgs.json";
		String jsonString = buildJsonString(url_string);
		//jsonString = jsonString.substring(1, jsonString.length()-1);
		if (jsonString.charAt(0) == '[') {
			//JSON array
			List<Map<String, Object>> list = new ArrayList<>();
			JsonArray jsonArray = toJsonArray(jsonString);
			for (JsonElement element:jsonArray) {
				JsonObject object = element.getAsJsonObject();
				LinkedHashMap<String, Object> map = new Gson().fromJson(object, LinkedHashMap.class); 
				list.add(map);
			}
			System.out.println(list);
		} else {
			//JSON object
			JsonObject jsonObject = toJsonObject(jsonString);
			LinkedHashMap<String, Object> map = new Gson().fromJson(jsonObject,  LinkedHashMap.class);
			System.out.println(map);
		}
		

	}
	
	public static String buildJsonString(String url_string) {
		try {
			URL url = new URL(url_string);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
		    StringBuilder stringBuilder = new StringBuilder();
		    String inputLine;
		    while ((inputLine = bufferedReader.readLine()) != null)
		    {
		        stringBuilder.append(inputLine);
		        stringBuilder.append(System.lineSeparator());
		    }

		    bufferedReader.close();
		    return stringBuilder.toString().trim();
        }catch (IOException e){
            e.printStackTrace();
        }
		return "";
	}
	
	public static JsonArray toJsonArray(String jsonString) {
		return JsonParser.parseString(jsonString).getAsJsonArray();
	}
	
	public static JsonObject toJsonObject(String jsonString) {
		return JsonParser.parseString(jsonString).getAsJsonObject();
	}
}
