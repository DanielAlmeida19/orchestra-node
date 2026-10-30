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

    public ListField(String name, String external, boolean nullable, boolean ignorable, Class<T> elementType) {
        super(name, external, nullable, ignorable);
        this.elementType = elementType;
    }

    public Class<?> getElementType() {
        return elementType;
    }

    public void setElementType(Class<?> elementType) {
        this.elementType = elementType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("> Field: ").append(this.getName()).append("\n");
        sb.append("> Nullable: ").append(this.isNullable()).append("\n");
        sb.append("> Ignorable: ").append(this.isIgnorable()).append("\n");
        sb.append("> External: ").append(this.getExternal()).append("\n");
        sb.append("> Type: ").append(this.getClass()).append("\n");
        sb.append("> Generic Type: ").append(this.elementType).append("\n");

        return sb.toString();

    }
}
