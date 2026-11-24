package io.github.danielalmeida19.orchestranode;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.danielalmeida19.orchestranode.evolutioncore.SchemaProcessor;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.evolutioncore.schemamodificationmodel.SchemaVersion;

/**
 * This is a util class to initiate and use the middleware in different modes
 *
 * @author Daniel
 */
public class OrchestraNode implements Runnable {

    /**
     * Attribute that represents the previous configuration of the Orchestra Node
     * approach
     */
    private static OrchestraNodeConfiguration configuration;

    /**
     * Attrubute that represents the list of collections actually managed by
     * OrchestraNode
     */
    private static List<Collection> collections;

    /**
     * Attribute that represents the schema history of the OrchestraNode instance
     */
    private static List<SchemaVersion> schemaHistory;

    /**
     * Logger for OrchestraNode class
     */
    private static final Logger log = LoggerFactory.getLogger(OrchestraNode.class);

    // ---------------------------------------------------------
    // Getters and Setters
    // ---------------------------------------------------------

    public static OrchestraNodeConfiguration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(OrchestraNodeConfiguration configuration) {
        OrchestraNode.configuration = configuration;
    }

    public static List<Collection> getCollections() {
        return collections;
    }

    public static void setCollections(List<Collection> collections) {
        OrchestraNode.collections = collections;
    }

    public static List<SchemaVersion> getSchemaHistory() {
        return schemaHistory;
    }

    public static void setSchemaHistory(List<SchemaVersion> schemaHistory) {
        OrchestraNode.schemaHistory = schemaHistory;
    }

    @Override
    public void run() {
        // TODO: Auto-generated method stub

    }

    /**
     * Method to init the OrchestraNode with just extract and manage models
     */
    public static void simpleInit(String managedPackage) {

        log.info("Initiating the middleware in simple mode...");

        collections = SchemaProcessor.extractCollections(managedPackage);

        // Remove the null elements of the list
        collections.removeIf(element -> element == null);

        // Verify if the list is empty for a warnin
        if (collections.isEmpty()) {
            log.warn("No collections managed now by OrchestraNode");
        }

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

}
