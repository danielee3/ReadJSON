package research.readjson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Write {

    
    
    public Write() {
    	
    }
    
    public String toJSON(Map map) {
    	// Instantiate a new Gson instance.
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        // Convert the ordered map into an ordered string.
        String json= gson.toJson(map, Map.class);
    	return json;
    }
    
    public String toJSON(List list) {
    	// Instantiate a new Gson instance.
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        // Convert the list of ordered maps into an ordered string.
        String json= gson.toJson(list, ArrayList.class);
    	return json;
    }
    
    public void saveFile(String json, String filename) {
        FileWriter fw;
		try {
			fw = new FileWriter("./json/"+filename, false);
			BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(json);
	        bw.close();
	        System.out.println("Saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void saveLog(String json, String filename) {
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        FileWriter fw;
		try {
			fw = new FileWriter("./json/"+timestamp.getTime() +".json", false);
			BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(json);
	        bw.close();
	        System.out.println("Saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}