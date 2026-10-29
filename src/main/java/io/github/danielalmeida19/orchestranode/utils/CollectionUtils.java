package io.github.danielalmeida19.orchestranode.utils;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import io.github.danielalmeida19.orchestranode.OrchestraNode;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.BooleanField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ListField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.NumberField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.StringField;

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

    public static <T> Document getDocumentFromCollectionClass(Collection collection, T instance, Class<T> clazz)
            throws IllegalArgumentException, IllegalAccessException {
        Document document = new Document();

        for (CollectionField<?> collectionField : collection.getFields()) {
            switch (collectionField) {
                case BooleanField b:
                    document.put(b.getName(), CollectionFieldUtils
                            .getValueFieldFromClassCollection(b, clazz, instance, boolean.class));
                    break;

                case StringField s:
                    document.put(s.getName(),
                            CollectionFieldUtils.getValueFieldFromClassCollection(s, clazz, instance, String.class));
                    break;

                case NumberField n:
                    document.put(n.getName(),
                            CollectionFieldUtils.getValueFieldFromClassCollection(n, clazz, instance, Number.class));
                    break;

                case ListField<?> l:
                    Class<?> elementType = l.getElementType();

                    // Verify if is a list of primitive values
                    if (CollectionFieldUtils.isPrimitiveType(elementType)) {
                        document.put(l.getName(), l.getValue());

                    } else {
                        // This assume that DOES NOT have nested lists like List<List<String>> in the
                        // object directally.
                        // The treatment for these cases will be implemented in the future

                        //
                    }

                default:
                    break;

            }

            document.put(collectionField.getName(), CollectionFieldUtils
                    .getValueFieldFromClassCollection(collectionField, clazz, instance, null));
        }

        return document;
    }
}
