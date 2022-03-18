package me.exzibyte.Utilities;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import me.exzibyte.Quiver;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

public class GuildConfig {

    private final Quiver quiver;
    private static Logging logging = new Logging();

    public GuildConfig(Quiver quiver){
        this.quiver = quiver;
    }

    public HashMap<String, HashMap<String, String>> configuration = new HashMap<String, HashMap<String, String>>();

    public void load(){

        MongoCollection<Document> guilds =  quiver.getDatabase().getCollection("guilds");

        FindIterable<Document> iterable = guilds.find();
        MongoCursor<Document> cursor = iterable.iterator();

        try{
            while(cursor.hasNext()){
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(cursor.next().toJson());

                HashMap<String, String> data = new HashMap<String, String>();
                logging.info(this.getClass(), obj.toString());
                data.put("locale", obj.get("locale").toString());
                data.put("prefix", obj.get("prefix").toString());
                data.put("logChannel", obj.get("logChannelID").toString());
                data.put("joinLog", obj.get("joinLogID").toString());
                data.put("muteRole", obj.get("muteRoleID").toString());

                configuration.put(obj.get("guildID").toString(), data);

            }
        } catch (ParseException ex){
            logging.error(this.getClass(), ex.toString());
        } finally {
            configuration.forEach((k,v) -> {
               logging.info(this.getClass(), String.format("Key: %s, Value: %s", k, v));
            });
            cursor.close();
        }


    }


    public String get(String key, String guildID){
        return configuration.get(guildID).get(key).toString();
    }

    public String get(String key, Long guildID){
        return configuration.get(guildID).get(key).toString();
    }
}
