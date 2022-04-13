package me.exzibyte.Utilities;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import me.exzibyte.Quiver;
import me.exzibyte.models.GuildConfig;
import me.exzibyte.models.GuildEntity;
import me.exzibyte.models.MemberEntity;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class Database {

    public static final PojoCodecProvider CODEC_PROVIDER = PojoCodecProvider.builder()
            .register(GuildEntity.class)
            .register(GuildConfig.class)
            .register(MemberEntity.class)
            .build();
    public static CodecRegistry CODEC_REGISTRY = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(CODEC_PROVIDER));
    private final Quiver quiver;
    private MongoClient client;
    private MongoDatabase db;

    public Database(Quiver quiver) {
        this.quiver = quiver;

        var settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(quiver.getConfig().get("dbURI")))
                .codecRegistry(CODEC_REGISTRY)
                .build();
        this.client = MongoClients.create(settings);
    }

    public void connect() {
        db = client.getDatabase("Quiver");
    }

    public MongoCollection<Document> getCollection(String collection) {
        return db.getCollection(collection);
    }


    public <T> MongoCollection<T> getCollection(String collection, Class<T> model) {
        return db.getCollection(collection, model);
    }
}
