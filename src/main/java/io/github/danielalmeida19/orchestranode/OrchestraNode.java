package io.github.danielalmeida19.orchestranode;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.danielalmeida19.orchestranode.annotations.OrchestraCollection;
import io.github.danielalmeida19.orchestranode.database.Connection;
import io.github.danielalmeida19.orchestranode.evolutioncore.SchemaProcessor;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.evolutioncore.schemamodificationmodel.SchemaVersion;

/**
 * This is a util class to initiate and use the middleware in different modes
 *
 * @author Daniel
 */
public class OrchestraNode {

    /**
     * Attribute tha represents the managed package of the model classes that will
     * be extracted and tranformed in Collections
     */
    private static String orchestraNodeManagedPackage;

    /**
     * Attrubute that represents the list of collections actually managed by
     * OrchestraNode
     */
    private static List<Collection> collections;

    /**
     * Attribute that represents the list of connections that can be used to create
     * new databases for new schema versions
     */
    private static List<Connection> candidateConnections;

    /**
     * Attribute that represents the schema history of the OrchestraNode instance
     */
    private static List<SchemaVersion> schemaHistory;

    /**
     * Logger for OrchestraNode class
     */
    private static final Logger log = LoggerFactory.getLogger(OrchestraNode.class);

    // Getters and Setters
    public static String getOrchestraNodeManagedPackage() {
        return orchestraNodeManagedPackage;
    }

    public static void setOrchestraNodeManagedPackage(String orchestraNodeManagedPackage) {
        OrchestraNode.orchestraNodeManagedPackage = orchestraNodeManagedPackage;
    }

    public static List<Collection> getCollections() {
        return collections;
    }

    public static void setCollections(List<Collection> collections) {
        OrchestraNode.collections = collections;
    }

    public static List<Connection> getCandidateConnections() {
        return candidateConnections;
    }

    public static void setCandidateConnections(List<Connection> candidateConnections) {
        OrchestraNode.candidateConnections = candidateConnections;
    }

    public static List<SchemaVersion> getSchemaHistory() {
        return schemaHistory;
    }

    public static void setSchemaHistory(List<SchemaVersion> schemaHistory) {
        OrchestraNode.schemaHistory = schemaHistory;
    }

    /**
     * Method to init the OrchestraNode with just extract and manage models
     */
    public static void simpleInit(String managedPackage) {

        log.info("Initiating the middleware in simple mode...");

        orchestraNodeManagedPackage = managedPackage;

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
            // If the class doesn't have the annotation Collection, ignore
            if (!clazz.isAnnotationPresent(OrchestraCollection.class)) {
                continue;
            }

            Collection newCollection = SchemaProcessor.processClass(clazz);

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
                } else {
                    log.warn("The directory %s does not exist".formatted(resource.toString()));
                }
            }
            return classes;
        } catch (Exception e) {
            log.error(
                    "An error ocurred in package desserialization, check if the directory exists or the files are correct formatted",
                    e);
            return Collections.emptyList();
        }

    }

    public static void initialize(
            List<Connection> connectionsCandidates,
            String pathToLog) {

    }

}
