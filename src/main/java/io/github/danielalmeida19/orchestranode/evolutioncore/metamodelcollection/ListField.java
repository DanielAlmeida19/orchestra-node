package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class ListField<T> extends Field<List<T>> {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("> Field: ").append(this.getName()).append("\n");
        sb.append("> Type: ").append(this.getClass()).append("\n");

        // Determine the type of T using reflection
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> fieldType = (Class<?>) genericSuperclass.getActualTypeArguments()[0];

        sb.append("> Generic Type: ").append(fieldType.getName()).append("\n");

        return sb.toString();

    }
}
