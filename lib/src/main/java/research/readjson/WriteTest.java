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

public class WriteTest {
	public static void main(String[] args) {
        // create a list of LinkedHashMap
        ArrayList<Map<String, Object>> lst= new ArrayList<>();
        // Create a new ordered map.
        Map<String, Object> myLinkedHashMap= new LinkedHashMap<>();
        Map<String, Object> graphMap1= new LinkedHashMap<>();
        Map<String, Object> destMap1= new LinkedHashMap<>();
        Map<String, Object> graphMap2= new LinkedHashMap<>();
        Map<String, Object> destMap2= new LinkedHashMap<>();

        // Add items, in-order, to the map.
        myLinkedHashMap.put("id", "8ac4c636-9d92-11eb-9dbc-0242ac110002");
        myLinkedHashMap.put("desc", "Dairy Farm DEMO DFG");

        graphMap1.put("object_pool_pathname", "/dairy_farm/front_end");
        destMap1.put("/dairy_farm/compute", "put");
        destMap1.put("conditional", "no");
        var destList1= List.of(destMap1);
        graphMap1.put("destinations", destList1);

        graphMap2.put("object_pool_pathname", "/dairy_farm/compute");
        destMap2.put("/dairy_farm/storage", "put");
        destMap2.put("conditional", "no");
        var destList2= List.of(destMap2);
        graphMap2.put("destinations", destList2);

        var graphList= List.of(graphMap1, graphMap2);
        myLinkedHashMap.put("graph", graphList);

        lst.add(myLinkedHashMap);
        // Instantiate a new Gson instance.
        Gson gson= new GsonBuilder().setPrettyPrinting().create();

        // Convert the ordered map into an ordered string.
        String json= gson.toJson(lst, ArrayList.class);
        
        
        Write writeJSON = new Write();
        writeJSON.saveFile(json);
                

    }
}
