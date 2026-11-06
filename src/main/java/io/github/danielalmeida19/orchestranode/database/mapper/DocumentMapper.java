package io.github.danielalmeida19.orchestranode.database.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.BooleanField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ListField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.NumberField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ObjectField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.StringField;
import io.github.danielalmeida19.orchestranode.utils.CollectionFieldUtils;
import io.github.danielalmeida19.orchestranode.utils.CollectionUtils;

/**
 * DocumentMapper
 */
public class DocumentMapper {

    private static final Logger log = LoggerFactory.getLogger(DocumentMapper.class);

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
                    document.put(b.getName(), getBooleanFieldValue(field, instance));
                    break;

                case StringField s:
                    document.put(s.getName(), getStringFieldValue(field, instance));
                    break;

                case NumberField n:
                    document.put(n.getName(), getNumberFieldValue(field, instance));
                    break;

                case ListField<?> l:
                    Class<?> elementType = l.getElementType();

                    document.put(
                            l.getName(),
                            getValueFieldFromList(
                                    l.getName(),
                                    instance,
                                    clazz,
                                    elementType));
                    break;

                case ObjectField o:

                    Class<?> internalClassType = o.getInternalClass();
                    Object actualFieldInstance = getSubInstance(o.getName(), instance, clazz,
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

    /**
     * Method to get a Number value from a numeric field
     */
    private static <T> Number getNumberFieldValue(Field field, T instance)
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
    private static <T> boolean getBooleanFieldValue(Field field, T instance)
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
    private static <T> String getStringFieldValue(Field field, T instance)
            throws IllegalArgumentException, IllegalAccessException {

        field.setAccessible(true);

        Class<?> type = field.getType();

        if (type == String.class) {
            return (String) field.get(instance);
        }

        throw new IllegalArgumentException("Field type \"%s\" is not supported String type".formatted(type.getName()));
    }

    /**
     * Method to get a list of values represented by a List<Object> field in
     * instance
     */
    private static <E, T> List<E> getValueFieldFromList(
            String fieldName,
            T instance,
            Class<T> clazz,
            Class<E> elementType)
            throws IllegalArgumentException, IllegalAccessException {

        Field field = CollectionFieldUtils.findAnnotatedFieldByName(fieldName, clazz);

        if (field == null) {
            throw new IllegalArgumentException("No matching field found: " + fieldName);
        }

        // Verify if is list
        if (!List.class.isAssignableFrom(field.getType())) {
            throw new IllegalArgumentException("The type of a field mapped to ListField need be a List");
        }

        Class<?> realItemType = CollectionFieldUtils.getGenericTypeInAListField(field);
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
     * Method to get the an nested object inside one another based on the fieldName
     * and the internal class expected type
     */
    private static <T, E> T getSubInstance(
            String fieldName,
            E instance,
            Class<E> clazz,
            Class<T> internalClass)
            throws IllegalArgumentException, IllegalAccessException {

        Field field = CollectionFieldUtils.findAnnotatedFieldByName(fieldName, clazz);

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

    public static <T> T getInstanceFromDocumentCollectionMapped(
            Document document,
            Class<T> clazz,
            List<CollectionField<?>> fields)

            throws IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            NoSuchMethodException {

        if (!CollectionUtils.thereIsCollectionByClass(clazz)) {
            String message = "The class \"%s\" is not a mapped collection. Check if you started the middleware and extracted the collections"
                    .formatted(clazz.getName());
            throw new IllegalArgumentException(message);
        }

        T instance;
        instance = clazz.getDeclaredConstructor().newInstance();
        for (CollectionField<?> field : fields) {
            String fieldName = field.getName();
            Object value = document.get(fieldName);

            log.debug("Field name:".formatted(fieldName));
            log.debug("Field value:".formatted(value));
            if (value != null) {
                try {
                    Field instanceField = CollectionFieldUtils.findAnnotatedFieldByName(fieldName, clazz);
                    if (instanceField != null) {
                        instanceField.setAccessible(true);
                        instanceField.set(instance, value);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to set field " + fieldName + " on " + clazz.getName(), e);
                }
            }
        }
        return instance;
    }

}
