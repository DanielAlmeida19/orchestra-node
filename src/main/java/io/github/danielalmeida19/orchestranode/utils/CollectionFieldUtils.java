package io.github.danielalmeida19.orchestranode.utils;

import java.lang.reflect.Field;
import java.util.List;

import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ListField;

/**
 * Utilitary class to useful operations necessary for Collection Fields
 *
 * @author Daniel
 */
public class CollectionFieldUtils {

    // private static final Logger log =
    // LoggerFactory.getLogger(CollectionFieldUtils.class);

    /**
     * Method to be capable of extract the value of a Collection field from an
     * instance of class that generate the original collection of the field
     */
    public static <T, K> T getValueFieldFromClassCollection(
            CollectionField<T> collectionField,
            Class<K> instanceClass,
            K instance,
            Class<T> clazz)
            throws IllegalArgumentException, IllegalAccessException {

        for (Field field : instanceClass.getFields()) {

            if (!field.isAnnotationPresent(OrchestraField.class)) {
                continue;
            }

            // If collection field name and the compiled field name are the same
            if (collectionField.getName().equals(field.getName())) {
                field.setAccessible(true);

                var value = field.get(instance);

                if (!(field.getType() == clazz)) {
                    throw new IllegalArgumentException("The instance type is wrong mapped to Collection");
                }

                return clazz.cast(value);

            }

            var fieldAnnotation = field.getAnnotation(OrchestraField.class);

            // If collection field name have the name altered in own construction
            if (collectionField.getName().equals(fieldAnnotation.name())) {
                field.setAccessible(true);

                var value = field.get(instance);

                if (!(field.getType() == clazz)) {
                    throw new IllegalArgumentException("The instance type is wrong mapped to Collection");
                }

                return clazz.cast(value);
            }
        }

        throw new IllegalArgumentException("The original class does not have this field");

    }

    /**
     * Method to reponse if a type is primitive (boolean, int, float, double and
     * String)
     */
    public static boolean isPrimitiveType(Class<?> type) {
        return type.isPrimitive() || type.equals(String.class);

    }

    // public static <E, T> List<E> getValueFieldFromList(String fieldName, T
    // instance, Class<T> clazz,
    // Class<E> elementType) {
    // for (Field field : clazz.getFields()) {
    // if (!field.isAnnotationPresent(OrchestraField.class)) {
    // continue;
    // }
    //
    // // If collection field name and the compiled field name are the same
    // if (fieldName.equals(field.getName())) {
    // field.setAccessible(true);
    //
    // var value = field.get(instance);
    //
    // return clazz.cast(value);
    //
    // }
    //
    // var fieldAnnotation = field.getAnnotation(OrchestraField.class);
    //
    // // If collection field name have the name altered in own construction
    // if (collectionField.getName().equals(fieldAnnotation.name())) {
    // field.setAccessible(true);
    //
    // var value = field.get(instance);
    //
    // if (!(field.getType() == clazz)) {
    // throw new IllegalArgumentException("The instance type is wrong mapped to
    // Collection");
    // }
    //
    // return clazz.cast(value);
    // }
    // }
    // }
}
