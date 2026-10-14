package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.util.List;

/**
 * @author Daniel
 */
public class ObjectField extends Field<List<Field<?>>> {

    public ObjectField() {
        super();
    }

    public ObjectField(String name) {
        super(name);
    }

    public ObjectField(String name, List<Field<?>> value) {
        super(name, value);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        sb.append("> Fields: \n");
        this.getValue().forEach(f -> sb.append("\t").append(f.toString().replace("\n", "\n\t")).append("\n"));

        return sb.toString();
    }
}
