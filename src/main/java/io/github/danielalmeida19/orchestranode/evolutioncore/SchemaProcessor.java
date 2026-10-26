package io.github.danielalmeida19.orchestranode.evolutioncore;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import io.github.danielalmeida19.orchestranode.annotations.OrchestraCollection;
import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.BooleanField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.Collection;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.CollectionField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ListField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.NumberField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.ObjectField;
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.StringField;

public class SchemaProcessor {

    public static Collection processClass(Class<?> clazz) {

        // If the class doesn't have the annotation Collection, ignore
        if (!clazz.isAnnotationPresent(OrchestraCollection.class)) {
            return null;
        }
        Collection collection = new Collection();

        // Obtain the annotation of the class
        var collectionAnnotation = clazz.getAnnotation(OrchestraCollection.class);

        // Define the name of the collection if filled, else the name of the class
        collection.setName(collectionAnnotation.name().isEmpty() ? clazz.getName() : collectionAnnotation.name());

        // Define the priority Level of collection
        collection.setPriorityLevel(collectionAnnotation.priorityLevel());

        // Iterates through the attributes declared in the class
        for (Field field : clazz.getDeclaredFields()) {
            CollectionField<?> newField = processField(field);

            if (newField == null) {
                continue;
            }

            collection.getFields().add(newField);
        }

        return collection;
    }

    public static CollectionField<?> processField(Field field) {

        // Verify if the attribute have the annotation @OrchestraField
        if (!field.isAnnotationPresent(OrchestraField.class)) {
            return null;
        }

        // Obtain the annotation of the attribute
        var fieldAnnotation = field.getAnnotation(OrchestraField.class);

        // String external = fieldAnnotation.external().isEmpty() ? null :
        // fieldAnnotation.external();

        // Define the name of the field if filled, else the name of the java field
        String fieldName = fieldAnnotation.name().isEmpty() ? field.getName() : fieldAnnotation.name();

        if (isPrimitiveType(field.getType())) {
            switch (field.getType().getName()) {
                case "java.lang.String":
                    return new StringField(fieldName, fieldAnnotation.external(), fieldAnnotation.nullable(),
                            fieldAnnotation.ignorable());

                case "int":
                case "double":
                case "float":
                    return new NumberField(fieldName, fieldAnnotation.external(), fieldAnnotation.nullable(),
                            fieldAnnotation.ignorable());

                case "boolean":
                    return new BooleanField(fieldName, fieldAnnotation.external(), fieldAnnotation.nullable(),
                            fieldAnnotation.ignorable());
                default:
                    break;
            }
        }

        // Verify if the object is a list
        if (isListOfObjects(field)) {

            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            if (actualTypeArguments.length > 0) {

                Type itemType = actualTypeArguments[0];
                if (isPrimitiveType(itemType.getClass())) {
                    switch (itemType.getTypeName()) {
                        case "java.lang.String":
                            return new ListField<String>(fieldName, String.class);

                        case "int":
                        case "float":
                        case "double":
                            return new ListField<Number>(fieldName, Number.class);

                        default:
                            return new ListField<>(fieldName, (Class<?>) itemType);
                    }
                }
            }
        }

        // If isn't any previous option, is an object field
        Collection internalCollection = processClass(field.getClass());

        if (!fieldAnnotation.external().isEmpty()) {
            System.out.println("Sorry, this treatment doesn't exists yet");
            return null;
        }

        return new ObjectField(fieldName, internalCollection.getFields());

    }

    public static boolean isListOfObjects(Field field) {

        // 1. Verify the type of the field is assinable for List
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        // 2. Verify if have declared generic arguments
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            if (actualTypeArguments.length > 0) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPrimitiveType(Class<?> type) {
        return type.isPrimitive() || type.equals(String.class);

    }

}
