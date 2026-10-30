package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.util.Objects;

public abstract class CollectionField<T> {

    private String name;
    private T value;
    private String external;
    private boolean nullable;
    private boolean ignorable;

    public CollectionField() {

    }

    public CollectionField(String name, T value, String external, boolean nullable, boolean ignorable) {
        this.name = name;
        this.value = value;
        this.external = external;
        this.nullable = nullable;
        this.ignorable = ignorable;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        CollectionField<T> that = (CollectionField<T>) obj;

        return name.equals(that.name) && external.equals(that.external) && nullable == that.nullable
                && ignorable == that.ignorable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, external, nullable, ignorable);
    }

    public CollectionField(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public CollectionField(String name, String external, boolean nullable, boolean ignorable) {
        this.name = name;
        this.external = external;
        this.nullable = nullable;
        this.ignorable = ignorable;
    }

    public CollectionField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isIgnorable() {
        return ignorable;
    }

    public void setIgnorable(boolean ignorable) {
        this.ignorable = ignorable;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("> Field: ").append(this.name).append("\n");
        sb.append("> Nullable: ").append(this.nullable).append("\n");
        sb.append("> Ignorable: ").append(this.ignorable).append("\n");
        sb.append("> External: ").append(this.external).append("\n");
        sb.append("> Type: ").append(this.getClass().getName()).append("\n");
        return sb.toString();
    }

}
