package pck;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileLogger {
    private final static String file = "history.json";

    public JSONArray getLogFile() {
        System.out.println(System.getProperty("user.dir"));
        JSONParser parser = new JSONParser();
        JSONArray result = null;
        try {
            FileReader fileReader = new FileReader(file);
            Object object = parser.parse(fileReader);
            if (object instanceof JSONArray) {
                result = (JSONArray) object;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            if (result == null) {
                result = new JSONArray();
            }
        }
        return result;
    }

    private void saveLogFile(JSONArray data) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(data.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addEntry(String name, String timestamp){
        JSONArray data = getLogFile();
        JSONObject entry = new JSONObject();
        entry.put("name", name);
        entry.put("timestamp", timestamp);
        data.add(entry);
        saveLogFile(data);
    }
}
