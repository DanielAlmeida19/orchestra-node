package io.github.danielalmeida19.orchestranode;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.danielalmeida19.orchestranode.connectors.OrchestraNodeDriver;
import io.github.danielalmeida19.orchestranode.evolutioncore.SchemaProcessor;
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

    private static final Logger log = LoggerFactory.getLogger(OrchestraNode.class);

    /**
     * Method to init the OrchestraNode approach monitoring.
     */
    public static void init(String managedPackage, OrchestraNodeDriver dbms) {

        log.info("Initiating the middleware...");

        orchestraNodeManagedPackage = managedPackage;
        databaseManagement = dbms;

        collections = extractCollections(managedPackage);

        // Remove the null elements of the list
        collections.removeIf(element -> element == null);

        // Verify if the list is empty for a warnin
        if (collections.isEmpty()) {
            log.warn("No collections managed now by OrchestraNode");
        }

    }

    /**
     * Generic method to get the list of collections from a list of compiled classes
     */
    public static List<Collection> extractCollections(String managedPackage) {

        List<Class<?>> classes = getClassesFromPackage(managedPackage);

        // Verify if the list of classes is empty
        if (classes.isEmpty()) {
            log.warn("No classes to be managed, check if the package are correct");
            return null;
        }
        List<Collection> extractedCollections = new ArrayList<>();

        // Process the classes from the managed package
        for (Class<?> clazz : classes) {
            Collection newCollection = SchemaProcessor.processClass(clazz);

            if (newCollection == null) {
                continue;
            }

            log.info("Adding collection " + newCollection.getName());
            extractedCollections.add(newCollection);
        }

        return extractedCollections;

    }

    /**
     * Generic method to get a list of compiled classes inside the managed package
     * passed in param
     */
    private static List<Class<?>> getClassesFromPackage(String managedPackage) {

        try {
            List<Class<?>> classes = new ArrayList<>();

            String path = managedPackage.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(path);

            if (resource != null) {
                File directory = new File(resource.getFile());
                if (directory.exists()) {
                    for (String file : directory.list()) {
                        if (file.endsWith(".class")) {
                            String className = managedPackage + '.' + file.substring(0, file.length() - 6);
                            classes.add(Class.forName(className));
                        }
                    }
                }
            }
            return classes;
        } catch (Exception e) {
            log.error(
                    "An error ocurred in package desserialization, check if the directory exists or the files are correct formatted",
                    e);
            return null;
        }

    }

    public static String getOrchestraNodeManagedPackage() {
        return orchestraNodeManagedPackage;
    }

    public static void setOrchestraNodeManagedPackage(String orchestraNodeManagedPackage) {
        OrchestraNode.orchestraNodeManagedPackage = orchestraNodeManagedPackage;
    }

    public static OrchestraNodeDriver getDatabaseManagement() {
        return databaseManagement;
    }

    public static void setDatabaseManagement(OrchestraNodeDriver databaseManagement) {
        OrchestraNode.databaseManagement = databaseManagement;
    }

    public static List<Collection> getCollections() {
        return collections;
    }

    public static void setCollections(List<Collection> collections) {
        OrchestraNode.collections = collections;
    }
}
