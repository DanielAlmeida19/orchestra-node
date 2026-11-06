package io.github.danielalmeida19.orchestranode;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.github.danielalmeida19.orchestranode.database.Connection;
import io.github.danielalmeida19.orchestranode.database.ConnectionState;
import io.github.danielalmeida19.orchestranode.database.OrchestraNodeDriver;
import io.github.danielalmeida19.orchestranode.database.dao.MongoEntityDAO;
import io.github.danielalmeida19.orchestranode.model.Address;
import io.github.danielalmeida19.orchestranode.model.Person;

public class MongoConnectorTest {

    private Connection connection;
    private MongoEntityDAO<Person> personDao;

    @Before
    public void setUp() {
        OrchestraNode.init(
                "io.github.danielalmeida19.orchestranode.model",
                OrchestraNodeDriver.MONGO);

        connection = new Connection(
                OrchestraNodeDriver.MONGO,
                "localhost",
                "27017",
                "example",
                ConnectionState.AVAILABLE,
                "testadmin",
                "password");

        personDao = new MongoEntityDAO<>(connection, Person.class);
    }

    @Test
    public void testInsertion() {

        Person p1 = new Person(
                "Daniel",
                "Almeida",
                true,
                List.of("Java", "Linux"),
                new Address("The fool street", 0));

        personDao.save(p1);
    }

    @Test
    public void testFindById() {
        Person p1 = new Person(
                "Daniel",
                "Almeida",
                true,
                List.of("Java", "Linux"),
                new Address("The fool street", 0));

        Person p2 = personDao.findById("6aeca9858adee10124d16c1b");

        assertEquals(p1, p2);
    }

}
