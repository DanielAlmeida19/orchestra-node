package io.github.danielalmeida19.orchestranode;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.mongodb.client.MongoDatabase;

import io.github.danielalmeida19.orchestranode.connectors.Connection;
import io.github.danielalmeida19.orchestranode.connectors.ConnectionState;
import io.github.danielalmeida19.orchestranode.connectors.OrchestraNodeDriver;
import io.github.danielalmeida19.orchestranode.connectors.mongo.MongoConnector;

public class MongoConnectorTest {

    @Test
    public void testConnection() {

        MongoConnector connector = new MongoConnector(
                new Connection(OrchestraNodeDriver.MONGO, "localhost", "27017", "example", ConnectionState.AVAILABLE));

        MongoDatabase database = connector.getDatabase();

        assertNotNull(database);

        connector.closeConnection();
    }

    public void testInsertion() {
        MongoConnector connector = new MongoConnector(
                new Connection(OrchestraNodeDriver.MONGO, "localhost", "27017", "example", ConnectionState.AVAILABLE));

        MongoDatabase database = connector.getDatabase();

        assertNotNull(database);

        connector.closeConnection();
    }
}
