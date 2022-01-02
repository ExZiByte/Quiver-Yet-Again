package me.exzibyte.Utilities;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.exzibyte.Quiver;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public class Database {

    private final Quiver quiver;
    private final MongoClientURI clientURI;
    private MongoClient client;
    private MongoDatabase db;

    public Database(Quiver quiver){
        clientURI = new MongoClientURI(quiver.getConfig().get("dbURI"));
        this.quiver = quiver;
    }

    public void connect() {
        client = new MongoClient(clientURI);
        db = client.getDatabase("Quiver");
    }

    public MongoCollection<Document> getCollection(String collection) {
        return db.getCollection(collection);
    }

    public void close() {
        client.close();
    }
}
