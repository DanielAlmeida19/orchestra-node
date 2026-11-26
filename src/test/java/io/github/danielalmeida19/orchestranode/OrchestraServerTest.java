package io.github.danielalmeida19.orchestranode;

import java.util.List;

import org.junit.Test;

import io.github.danielalmeida19.orchestranode.database.Connection;
import io.github.danielalmeida19.orchestranode.database.ConnectionState;
import io.github.danielalmeida19.orchestranode.database.OrchestraNodeDriver;
import io.github.danielalmeida19.orchestranode.migration.MigrationStrategy;

/**
 * OrchestraServerTest
 */
public class OrchestraServerTest {

    @Test
    public void serverStartTest() {
        OrchestraNodeConfiguration config = new OrchestraNodeConfiguration(
                List.of(
                        new Connection(OrchestraNodeDriver.MONGO,
                                "localhost",
                                "27017",
                                "example",
                                ConnectionState.AVAILABLE,
                                "testadmin",
                                "password")),
                "io.github.danielalmeida19.orchestranode.model",
                null,
                1,
                0,
                MigrationStrategy.EAGER,
                100);

        OrchestraNode.setConfiguration(config);
        OrchestraNode.start();
    }

}
