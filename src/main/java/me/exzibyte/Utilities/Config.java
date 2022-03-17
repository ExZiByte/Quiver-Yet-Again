package me.exzibyte.Utilities;

import me.exzibyte.Quiver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Config {

    private final Quiver quiver;
    Logging logging = new Logging();
    public HashMap<String, String> configuration = new HashMap<>();
    private static HashMap<String, String> locales = new HashMap<>();

    public Config(Quiver quiver){
        this.quiver = quiver;
    }

    public void load(){
        JSONParser parser = new JSONParser();
        try(FileReader reader = new FileReader("config.json")){
            JSONObject obj = (JSONObject) parser.parse(reader);

            configuration.put("token", obj.get("token").toString());
            configuration.put("dbURI", obj.get("databaseURI").toString());
            configuration.put("shardCount", obj.get("shardCount").toString());
            configuration.put("disabledCommands", obj.get("disabledCommands").toString());
            //locales.put("availableLocales", obj.get("locales").toString());

        } catch(IOException | ParseException exception){
            logging.error(this.getClass(), exception.getMessage());
        }
    }

    public String get(String key){
        return configuration.get(key);
    }

}
