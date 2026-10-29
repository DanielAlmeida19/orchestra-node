package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

public final class BooleanField extends CollectionField<Boolean> {

    public BooleanField(String name, Boolean value) {
        super(name, value);
    }

    public BooleanField(String name) {
        super(name);
    }

    public BooleanField() {
    }

    public BooleanField(String name, String external, boolean nullable, boolean ignorable) {
        super(name, external, nullable, ignorable);
    }
}
