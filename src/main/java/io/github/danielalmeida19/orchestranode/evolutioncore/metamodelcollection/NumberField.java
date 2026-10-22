package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

public class NumberField extends CollectionField<Number> {

    public NumberField(String name, Number value) {
        super(name, value);
    }

    public NumberField(String name) {
        super(name);
    }

    public NumberField() {
    }

    public NumberField(String name, String external, boolean nullable, boolean ignorable) {
        super(name, external, nullable, ignorable);
    }
}
