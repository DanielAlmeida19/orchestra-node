package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.util.List;

/**
 * @author Daniel
 */
public class ObjectField extends CollectionField<List<CollectionField<?>>> {

    Class<?> internalClass;

    public ObjectField() {
        super();
    }

    public ObjectField(String name, Class<?> internalClass) {
        super(name);
        this.internalClass = internalClass;
    }

    public ObjectField(String name, List<CollectionField<?>> value, Class<?> internalClass) {
        super(name, value);
        this.internalClass = internalClass;
    }

    public ObjectField(
            String name,
            List<CollectionField<?>> value,
            String external,
            boolean nullable,
            boolean ignorable,
            Class<?> internalClass) {
        super(name, value, external, nullable, ignorable);
        this.internalClass = internalClass;
    }

    public Class<?> getInternalClass() {
        return internalClass;
    }

    public void setInternalClass(Class<?> internalClass) {
        this.internalClass = internalClass;
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
