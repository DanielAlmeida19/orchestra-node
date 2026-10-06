package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

public abstract class Field<T> {

    private String name;
    private T value;

    public Field() {

    }

    public Field(String name, T value) {
        this.name = name;
        this.value = value;
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

    @Override
    public String toString() {
        return "{\n" +
                "\t" + "\"name\": " + "\"" + getName() + "\",\n" +
                "\t" + "\"value\": " + "\"" + getValue() + "\",\n" +
                "}\n";
    }
}
