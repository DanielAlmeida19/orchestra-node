package io.github.danielalmeida19.orchestranode;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.mongodb.client.MongoDatabase;

import io.github.danielalmeida19.orchestranode.database.Connection;
import io.github.danielalmeida19.orchestranode.database.ConnectionState;
import io.github.danielalmeida19.orchestranode.database.MongoConnector;
import io.github.danielalmeida19.orchestranode.database.OrchestraNodeDriver;
import io.github.danielalmeida19.orchestranode.database.dao.MongoEntityDAO;
import io.github.danielalmeida19.orchestranode.model.Address;
import io.github.danielalmeida19.orchestranode.model.Person;

public class MongoConnectorTest {

    private static final Connection CONNECTION = new Connection(
            OrchestraNodeDriver.MONGO,
            "localhost",
            "27017",
            "example",
            ConnectionState.AVAILABLE,
            "testadmin",
            "password");

    @Test
    public void testConnection() {

        MongoConnector connector = new MongoConnector(CONNECTION);

        MongoDatabase database = connector.getDatabase();

        assertNotNull(database);

        connector.closeConnection();
    }

    @Test
    public void testInsertion() {
        OrchestraNode.init("io.github.danielalmeida19.orchestranode.model", OrchestraNodeDriver.MONGO);
        MongoEntityDAO<Person> personDao = new MongoEntityDAO<>(CONNECTION, Person.class);

        Person p1 = new Person(
                "Daniel",
                "Almeida",
                true,
                List.of("Java", "Linux"),
                new Address("The fool street", 0));

        personDao.save(p1);
    }

}
