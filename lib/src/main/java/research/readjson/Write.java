package research.readjson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Write {

    
    
    public Write() {
    	
    }
    
    public String mapToJSON(Map map) {
    	// Instantiate a new Gson instance.
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        // Convert the ordered map into an ordered string.
        String json= gson.toJson(map, Map.class);
    	return json;
    }
    
    public String listToJSON(List list) {
    	// Instantiate a new Gson instance.
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        // Convert the ordered map into an ordered string.
        String json= gson.toJson(list, ArrayList.class);
    	return json;
    }
    
    public void saveFile(String json) {
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        FileWriter fw;
        FileWriter history;
		try {
			fw = new FileWriter("./json/json.json", false);
			history = new FileWriter("./json/"+timestamp.getTime() +".json", false);
			BufferedWriter bw = new BufferedWriter(fw);
			BufferedWriter bwh = new BufferedWriter(history);
	        bw.write(json);
	        bwh.write(json);
	        bw.close();
	        bwh.close();
	        System.out.println("Saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}