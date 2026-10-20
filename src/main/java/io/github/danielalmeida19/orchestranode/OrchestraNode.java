package io.github.danielalmeida19.orchestranode;

import java.util.ArrayList;
import java.util.List;

import io.github.danielalmeida19.orchestranode.connectors.OrchestraNodeDriver;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;

/**
 * This is a util class to initiate the middleware in different modes
 *
 * @author Daniel
 */
public class OrchestraNode {

    private static String orchestraNodeManagedPackage;
    private static OrchestraNodeDriver databaseManagement;
    private static List<Collection> collections;

    public static void init(String managedPackage, OrchestraNodeDriver dbms) {
        orchestraNodeManagedPackage = managedPackage;
        databaseManagement = dbms;

    }
}
