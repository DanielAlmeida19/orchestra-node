package io.github.danielalmeida19.orchestranode.utils;

import java.util.List;

import io.github.danielalmeida19.orchestranode.OrchestraNode;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;

/**
 * Utilitary class to useful operations necessary for Collections
 *
 * @author Daniel
 */
public class CollectionUtils {

    /**
     * Method to get a collection by name in the list of collections presents in
     * this execution of OrchestraNode or return null
     */
    public static Collection getCollectionByName(String name) {
        List<Collection> collections = OrchestraNode.getCollections();
        for (Collection collection : collections) {
            if (collection.getName().equals(name)) {
                return collection;
            }
        }

        return null;
    }

    /**
     * Method to search a collection by name in the list of collections presents in
     * this execution of OrchestraNode
     */
    public static boolean thereIsCollectionByName(String name) {
        List<Collection> collections = OrchestraNode.getCollections();
        for (Collection collection : collections) {
            if (collection.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
