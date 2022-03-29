package me.exzibyte.Utilities;

import com.mongodb.client.MongoCollection;
import me.exzibyte.Quiver;
import org.bson.Document;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Class to load data from the database
 */

public class GuildManager {

    private final Quiver quiver;
    private final HashMap<String, GuildConfig> guilds;

    public GuildManager(Quiver quiver) {
        this.quiver = quiver;
        this.guilds = new HashMap<>();
        load();
    }

    @Nullable
    public GuildConfig getConfig(String guildID){
        return guilds.get(guildID);
    }

    private void load() {
        MongoCollection<Document> guildsDoc = quiver.getDatabase().getCollection("guilds");
        for (Document document : guildsDoc.find()) {
            guilds.put(document.getString("guildID"), new GuildConfig(document));
        }
    }
}
