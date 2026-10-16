package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.util.List;

public class ListField<T> extends CollectionField<List<T>> {

    private Class<?> elementType;

    public ListField() {
    }

    public ListField(String name, Class<T> elementType) {
        super(name);
        this.elementType = elementType;
    }

    public ListField(String name, List<T> values, Class<T> elementType) {
        super(name, values);
        this.elementType = elementType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("> Field: ").append(this.getName()).append("\n");
        sb.append("> Type: ").append(this.getClass()).append("\n");
        sb.append("> Generic Type: ").append(this.elementType).append("\n");

        return sb.toString();

    }
}
