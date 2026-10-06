package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.util.List;

public class ListField<T> extends Field<List<T>> {

    @Override
    public String toString() {
        return "{\n" +
                "\t" + "\"name\": " + "\"" + getName() + "\",\n" +
                "\t" + "\"value\": " + "\"" + getValue() + "\",\n" +
                "}\n";
    }
}
