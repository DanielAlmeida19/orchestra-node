package io.github.danielalmeida19.orchestranode.database;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.UuidRepresentation;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Util class to connect fisically to the database from a Connection instance of
 * the framework
 *
 * @author Daniel
 */
public class MongoConnector {

    private static Connection connection;
    private static MongoConnector instance;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public MongoConnector(Connection newConnection) {
        connection = newConnection;
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings settings = MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(new ConnectionString(connection.getURI()))
                .codecRegistry(codecRegistry)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.mongoDatabase = mongoClient.getDatabase(connection.getDatabaseName());
    }

    public static synchronized MongoConnector getInstance() {
        if (instance == null) {
            instance = new MongoConnector(connection);
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return mongoDatabase;
    }

    public static String getUri() {
        return connection.getURI();
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
