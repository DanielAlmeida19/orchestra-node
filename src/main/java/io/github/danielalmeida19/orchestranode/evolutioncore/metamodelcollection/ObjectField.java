package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.util.List;

/**
 * @author Daniel
 */
public class ObjectField extends CollectionField<List<CollectionField<?>>> {

    public ObjectField() {
        super();
    }

    public ObjectField(String name) {
        super(name);
    }

    public ObjectField(String name, List<CollectionField<?>> value) {
        super(name, value);
    }

    public ObjectField(
            String name,
            List<CollectionField<?>> value,
            String external,
            boolean nullable,
            boolean ignorable) {
        super(name, value, external, nullable, ignorable);
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
