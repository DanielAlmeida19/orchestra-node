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
import io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection.StringField;

public class ChangeProcessor {

    public Collection processClass(Class<?> clazz) {

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
            collection.getFields().add(processField(field));
        }

        return collection;
    }

    public CollectionField<?> processField(Field field) {

        // Verify if the attribute have the annotation @OrchestraField
        if (!field.isAnnotationPresent(OrchestraField.class)) {
            return null;
        }

        // Obtain the annotation of the attribute
        var fieldAnnotation = field.getAnnotation(OrchestraField.class);

        // Define the name of the field if filled, else the name of the java field
        String fieldName = fieldAnnotation.name().isEmpty() ? field.getName() : fieldAnnotation.name();

        if (isPrimitiveType(field.getType())) {
            switch (field.getType().getName()) {
                case "java.lang.String":
                    return new StringField(fieldName);

                case "int":
                case "double":
                case "float":
                    return new NumberField(fieldName);

                case "boolean":
                    return new BooleanField(fieldName);
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

            }
        }

    }

    public boolean isListOfObjects(Field field) {

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

    public boolean isPrimitiveType(Class<?> type) {
        return type.isPrimitive() || type.equals(String.class);

    }

}
