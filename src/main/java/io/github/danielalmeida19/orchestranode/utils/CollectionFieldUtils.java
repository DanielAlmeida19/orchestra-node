package io.github.danielalmeida19.orchestranode.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.github.danielalmeida19.orchestranode.annotations.CollectionId;
import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;

/**
 * Utilitary class to useful operations necessary for Collection Fields
 *
 * @author Daniel
 */
public class CollectionFieldUtils {

    // private static final Logger log =
    // LoggerFactory.getLogger(CollectionFieldUtils.class);

    /**
     * Method to reponse if a type is primitive (boolean, int, float, double and
     * String)
     */
    public static boolean isPrimitiveType(Class<?> type) {
        return type.isPrimitive() || type.equals(String.class);

    }

    /**
     * Method to get the internal Type of a List
     */
    public static Class<?> getGenericTypeInAListField(Field field) {
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        if (actualTypeArguments.length > 0) {

            Type itemType = actualTypeArguments[0];

            return (Class<?>) itemType;
        }
        return null;
    }

    /**
     * Method to get the annotated field based or the name of the field in the
     * compiled class or the name of the field in OrchestraField annotation
     */
    public static Field findAnnotatedFieldByName(String fieldName, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {

            if (field.isAnnotationPresent(CollectionId.class)) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }

            if (!field.isAnnotationPresent(OrchestraField.class)) {
                continue;
            }

            var fieldAnnotation = field.getAnnotation(OrchestraField.class);

            // If collection field name and the compiled field name are the same
            if (fieldName.equals(field.getName()) || fieldName.equals(fieldAnnotation.name())) {
                return field;
            }
        }
        return null;
    }

    public static Field getFieldIdInClass(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(CollectionId.class)) {
                return field;
            }
        }
        return null;
    }

}
