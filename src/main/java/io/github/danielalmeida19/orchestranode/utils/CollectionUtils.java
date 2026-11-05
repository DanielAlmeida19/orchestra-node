package io.github.danielalmeida19.orchestranode.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.bson.Document;

import io.github.danielalmeida19.orchestranode.OrchestraNode;
import io.github.danielalmeida19.orchestranode.annotations.OrchestraCollection;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.BooleanField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ListField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.NumberField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ObjectField;
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
    public static Collection getCollectionByClass(Class<?> clazz) {

        if (!clazz.isAnnotationPresent(OrchestraCollection.class)) {
            return null;
        }

        var annotation = clazz.getAnnotation(OrchestraCollection.class);

        List<Collection> collections = OrchestraNode.getCollections();
        for (Collection collection : collections) {
            String collectionName = collection.getName();
            if (collectionName.equals(clazz.getSimpleName()) || collectionName.equals(annotation.name())) {
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

    public static <T> Document getDocumentFromCollectionClass(
            List<CollectionField<?>> collectionFields,
            T instance,
            Class<T> clazz)
            throws IllegalArgumentException, IllegalAccessException {
        Document document = new Document();

        for (CollectionField<?> collectionField : collectionFields) {

            Field field = CollectionFieldUtils.findAnnotatedFieldByName(collectionField.getName(), clazz);

            switch (collectionField) {
                case BooleanField b:
                    document.put(b.getName(), CollectionFieldUtils.getBooleanFieldValue(field, instance));
                    break;

                case StringField s:
                    document.put(s.getName(),
                            CollectionFieldUtils.getStringFieldValue(field, instance));
                    break;

                case NumberField n:
                    document.put(n.getName(),
                            CollectionFieldUtils.getNumberFieldValue(field, instance));
                    break;

                case ListField<?> l:
                    Class<?> elementType = l.getElementType();

                    document.put(
                            l.getName(),
                            CollectionFieldUtils.getValueFieldFromList(
                                    l.getName(),
                                    instance,
                                    clazz,
                                    elementType));
                    break;

                case ObjectField o:

                    Class<?> internalClassType = o.getInternalClass();
                    Object actualFieldInstance = CollectionFieldUtils.getSubInstance(o.getName(), instance, clazz,
                            internalClassType);

                    document.put(
                            o.getName(),
                            recursiveDocument(o.getValue(), actualFieldInstance, internalClassType));

                default:
                    break;

            }

        }

        return document;
    }

    private static <T> Document recursiveDocument(
            List<CollectionField<?>> collectionFields,
            Object instance,
            Class<T> clazz)
            throws IllegalArgumentException, IllegalAccessException {
        return getDocumentFromCollectionClass(collectionFields, clazz.cast(instance), clazz);
    }
}
