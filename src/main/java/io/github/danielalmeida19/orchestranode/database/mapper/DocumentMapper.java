package io.github.danielalmeida19.orchestranode.database.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.danielalmeida19.orchestranode.annotations.FillingPolicy;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.BooleanField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.IdField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ListField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.NumberField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ObjectField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.StringField;
import io.github.danielalmeida19.orchestranode.utils.CollectionFieldUtils;

/**
 * Class with the mapping operations needed to documents store
 *
 * @author Daniel
 */
public class DocumentMapper {

    private static final Logger log = LoggerFactory.getLogger(DocumentMapper.class);

    /**
     * Method to transform a list of CollectionField and an instance in a document
     * to be saved or used
     */
    public static <T> Document getDocumentFromCollectionClass(
            List<CollectionField<?>> collectionFields,
            T instance,
            Class<T> clazz)
            throws IllegalArgumentException, IllegalAccessException {
        Document document = new Document();

        for (CollectionField<?> collectionField : collectionFields) {

            // Find the mapped field in the class
            Field field = CollectionFieldUtils.findAnnotatedFieldByName(collectionField.getName(), clazz);

            // Extract the values based on the type
            switch (collectionField) {
                case IdField<?> i:
                    Object value = getIDFieldValue(field, instance, i);
                    if (value != null) {
                        document.put("_id", value);
                    }

                    break;
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
                                    field,
                                    instance,
                                    clazz,
                                    elementType));
                    break;

                case ObjectField o:

                    Class<?> internalClassType = o.getInternalClass();

                    // Get the the nested object in memory
                    Object actualFieldInstance = getSubInstance(o.getName(), instance, clazz,
                            internalClassType);

                    // Recursive transformation
                    document.put(
                            o.getName(),
                            recursiveDocument(o.getValue(), actualFieldInstance, internalClassType));

                default:
                    break;

            }

        }

        return document;
    }

    /**
     * Method to build recursive documents based on nested objects in an instance
     * and nested structures (ObjectField)
     */
    private static <T> Document recursiveDocument(
            List<CollectionField<?>> collectionFields,
            Object instance,
            Class<T> clazz)
            throws IllegalArgumentException, IllegalAccessException {
        return getDocumentFromCollectionClass(collectionFields, clazz.cast(instance), clazz);
    }

    private static <T> Object getIDFieldValue(Field field, T instance, IdField<?> idField)
            throws IllegalAccessException {
        if (idField.getFillingPolicy() != FillingPolicy.MANUALLY) {
            return null;
        }

        switch (field.getType().getName()) {
            case "java.lang.String":
                String id = getStringFieldValue(field, instance);
                return new ObjectId(id);

            case "int":
            case "float":
            case "double":
                return new ObjectId(String.valueOf(getNumberFieldValue(field, instance)));
            default:
                break;
        }

        String message = "The type \"%s\" is not assignable to an ID type".formatted(field.getType().getName());
        throw new IllegalArgumentException(message);
    }

    /**
     * Method to get a Number value from a numeric field
     */
    private static <T> Number getNumberFieldValue(Field field, T instance)
            throws IllegalArgumentException, IllegalAccessException {

        field.setAccessible(true);

        Class<?> type = field.getType();

        // The value need to be int, float or double
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

        // The value need to be boolean
        if (type == boolean.class ||
                type == Boolean.class) {
            return (Boolean) field.get(instance);
        }

        String message = "Field type \"%s\" in the field \"%s\" is not supported boolean type"
                .formatted(type.getName(), field.getName());
        throw new IllegalArgumentException(message);
    }

    /**
     * Method to get a String value from a String field
     */
    private static <T> String getStringFieldValue(Field field, T instance)
            throws IllegalArgumentException, IllegalAccessException {

        field.setAccessible(true);

        Class<?> type = field.getType();

        // The value need to be String
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
            Field field,
            T instance,
            Class<T> clazz,
            Class<E> elementType)
            throws IllegalArgumentException, IllegalAccessException {

        // Verify if is list
        if (!List.class.isAssignableFrom(field.getType())) {
            throw new IllegalArgumentException("The type of a field mapped to ListField need be a List");
        }

        // Get the internal type of the List Field in the object in memory
        Class<?> realItemType = CollectionFieldUtils.getGenericTypeInAListField(field);

        // If the type is not equal, type mismatch
        if (realItemType == null || realItemType != elementType) {
            throw new IllegalArgumentException(
                    "The internal type of the Field %s mapped to a ListField of %s mismatch".formatted(
                            realItemType,
                            elementType));
        }

        field.setAccessible(true);
        List<?> values = (List<?>) field.get(instance);

        List<E> mappedValues = new ArrayList<>();

        // Cast the values
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

    /**
     * Method to transform an document in an instance of a class
     */
    public static <T> T getInstanceFromDocumentCollectionMapped(
            Document document,
            Class<T> clazz,
            List<CollectionField<?>> fields)

            throws IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            NoSuchMethodException {

        // Constructing the new instance in memory
        T instance;
        instance = clazz.getDeclaredConstructor().newInstance();

        // Filling the values
        for (CollectionField<?> field : fields) {
            String fieldName = field.getName();
            Object value = document.get(fieldName);

            // log.info("Field name: %s".formatted(fieldName));
            // log.info("Field value: %s".formatted(value.toString()));

            if (value != null) {
                try {

                    if (fieldName.equals("_id")) {
                        Field fieldId = CollectionFieldUtils.getFieldIdInClass(clazz);
                        if (fieldId != null) {
                            fieldId.setAccessible(true);
                            fieldId.set(instance, value);

                        }
                    } else {
                        // Get the mapped field
                        Field instanceField = CollectionFieldUtils.findAnnotatedFieldByName(fieldName, clazz);
                        if (instanceField != null) {
                            instanceField.setAccessible(true);

                            // If is a nested object, need a recursive treatment
                            if (field instanceof ObjectField objectField) {
                                Document documentValue = (Document) value;
                                Object subInstanceValue = getInstanceFromDocumentCollectionMapped(documentValue,
                                        objectField.getInternalClass(), objectField.getValue());

                                instanceField.set(instance, subInstanceValue);
                            } else {
                                instanceField.set(instance, value);
                            }
                        }
                    }

                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to set field " + fieldName + " on " + clazz.getName(), e);
                }
            }
        }
        return instance;
    }

}
