package research.readjson;

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

/** Takes a JSON file and converts it to Java data structures. */
public class Read {
	private URL url_string;
	private String jsonString;
	private JsonArray jsonArray;
	private JsonObject jsonObject;
	private ArrayList<Map<String, Object>> jsonList;
	private LinkedHashMap<String, Object> jsonMap;
	
	/** An empty constructor. */
	private Read() {
	}
	
	/** 
	 * Constructor: creates an instance of Read class with an input JSON file. The JSON file given as its url address then gets saved as a Java String. 
	 * @param url_string the url address of the json file. 
	 * */
	public Read(String url_string) {
		try {
			this.url_string = new URL(url_string);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.url_string.openStream()));
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
}
