package io.github.danielalmeida19.orchestranode.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;

/**
 * Utilitary class to useful operations necessary for Collection Fields
 *
 * @author Daniel
 */
public class CollectionFieldUtils {

    private static final Logger log = LoggerFactory.getLogger(CollectionFieldUtils.class);

    /**
     * Method to be capable of extract the value of a Collection field from an
     * instance of class that generate the original collection of the field
     */
    // public static <T, K> T getValueFieldFromClassCollection(
    // CollectionField<T> collectionField,
    // Class<K> instanceClass,
    // K instance,
    // Class<T> clazz)
    // throws IllegalArgumentException, IllegalAccessException {
    //
    // Field field = findAnnotatedFieldByName(collectionField.getName(),
    // instanceClass);
    //
    // if (field == null) {
    // String message = "The original class \"%s\" does not have the field \"%s\""
    // .formatted(instanceClass.getName(), collectionField.getName());
    // throw new IllegalArgumentException(message);
    // }
    //
    // field.setAccessible(true);
    //
    // log.debug("Type of the field: " + field.getType());
    // log.debug("Expected Class value: " + clazz);
    //
    // if ((field.getType() != clazz)) {
    // throw new IllegalArgumentException("The instance type is wrong mapped to
    // Collection");
    // }
    //
    // return (T) (field.get(instance));
    //
    // }

    /**
     * Method to get a Number value from a numeric field
     */
    public static <T> Number getNumberFieldValue(Field field, T instance)
            throws IllegalArgumentException, IllegalAccessException {

        field.setAccessible(true);

        Class<?> type = field.getType();

        if (type == int.class ||
                type == float.class ||
                type == double.class) {
            return (Number) field.get(instance);
        }

        throw new IllegalArgumentException("Field type \"%s\" is not supported numeric type".formatted(type.getName()));
    }

    /**
     * Method to get a Boolean value from a boolean field
     */
    public static <T> boolean getBooleanFieldValue(Field field, T instance)
            throws IllegalArgumentException, IllegalAccessException {

        field.setAccessible(true);

        Class<?> type = field.getType();

        if (type == boolean.class ||
                type == Boolean.class) {
            return (Boolean) field.get(instance);
        }

        throw new IllegalArgumentException("Field type \"%s\" is not supported boolean type".formatted(type.getName()));
    }

    /**
     * Method to get a Number value from a numeric field
     */
    public static <T> String getStringFieldValue(Field field, T instance)
            throws IllegalArgumentException, IllegalAccessException {

        field.setAccessible(true);

        Class<?> type = field.getType();

        if (type == String.class) {
            return (String) field.get(instance);
        }

        throw new IllegalArgumentException("Field type \"%s\" is not supported String type".formatted(type.getName()));
    }

    /**
     * Method to reponse if a type is primitive (boolean, int, float, double and
     * String)
     */
    public static boolean isPrimitiveType(Class<?> type) {
        return type.isPrimitive() || type.equals(String.class);

    }

    /**
     * Method to get a list of values represented by a List<Object> field in
     * instance
     */
    public static <E, T> List<E> getValueFieldFromList(
            String fieldName,
            T instance,
            Class<T> clazz,
            Class<E> elementType)
            throws IllegalArgumentException, IllegalAccessException {

        Field field = findAnnotatedFieldByName(fieldName, clazz);

        if (field == null) {
            throw new IllegalArgumentException("No matching field found: " + fieldName);
        }

        // Verify if is list
        if (!List.class.isAssignableFrom(field.getType())) {
            throw new IllegalArgumentException("The type of a field mapped to ListField need be a List");
        }

        Class<?> realItemType = getGenericTypeInAListField(field);
        if (realItemType == null || realItemType != elementType) {
            throw new IllegalArgumentException(
                    "The internal type of the Field %s mapped to a ListField of %s mismatch".formatted(
                            realItemType,
                            elementType));
        }

        field.setAccessible(true);
        List<?> values = (List<?>) field.get(instance);

        List<E> mappedValues = new ArrayList<>();

        for (Object value : values) {
            mappedValues.add(elementType.cast(value));
        }

        return mappedValues;
    }

    /**
     * Method to get the internal Type of a List
     */
    private static Class<?> getGenericTypeInAListField(Field field) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        if (actualTypeArguments.length > 0) {

            Type itemType = actualTypeArguments[0];

            return (Class<?>) itemType;
        }
        return null;
    }

    /**
     * Method to get the an nested object inside one another based on the fieldName
     * and the internal class expected type
     */
    public static <T, E> T getSubInstance(
            String fieldName,
            E instance,
            Class<E> clazz,
            Class<T> internalClass)
            throws IllegalArgumentException, IllegalAccessException {

        Field field = findAnnotatedFieldByName(fieldName, clazz);

        if (field == null) {
            throw new IllegalArgumentException("No matching field found: " + fieldName);
        }

        // Verify if the field of the instance and the field of the internal class are
        // the same
        if (field.getType() != internalClass) {
            String msg = "The type of the field %s is %s but is expected %s".formatted(fieldName,
                    field.getType().getName(), internalClass.getName());
            throw new IllegalArgumentException(msg);
        }

        field.setAccessible(true);

        return internalClass.cast(field.get(instance));

    }

    /**
     * Method to get the annotated field based or the name of the field in the
     * compiled class or the name of the field in OrchestraField annotation
     */
    public static Field findAnnotatedFieldByName(String fieldName, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(OrchestraField.class)) {
                return null;
            }

            var fieldAnnotation = field.getAnnotation(OrchestraField.class);

            // If collection field name and the compiled field name are the same
            if (fieldName.equals(field.getName()) || fieldName.equals(fieldAnnotation.name())) {
                return field;
            }
        }
        return null;
    }

}
