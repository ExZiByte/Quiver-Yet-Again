package me.exzibyte.Utilities;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.exzibyte.Quiver;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.jetbrains.annotations.NotNull;

public class Database {

    private final Quiver quiver;
    private final MongoClientURI clientURI;
    private MongoClient client;
    private MongoDatabase db;

    public Database(Quiver quiver){
        // all this shit below just registers the object representations of mongo objects
        var provider = PojoCodecProvider.builder()
                .register("me.exzibyte.entity")
                // TODO create a convention to keep all fields in the same order as the document
                // we can do it in the future, i have to research that later
                .build();
        var registry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(), CodecRegistries.fromProviders(provider));
        var options = MongoClientOptions.builder()
                .codecRegistry(registry);

        clientURI = new MongoClientURI(quiver.getConfig().get("dbURI"), options);
        this.quiver = quiver;
    }

    public void connect() {
        client = new MongoClient(clientURI);
        db = client.getDatabase("Quiver");
    }

    public MongoDatabase get() {
        return db;
    }

    public MongoCollection<Document> getCollection(String collection) {
        return db.getCollection(collection);
    }

    public void close() {
        client.close();
    }
}
