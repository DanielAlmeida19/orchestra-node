package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

public class StringField extends CollectionField<String> {

    public StringField(String name, String value) {
        super(name, value);
    }

    public StringField(String name) {
        super(name);
    }

    public StringField() {
    }

    public StringField(String name, String external, boolean nullable, boolean ignorable) {
        super(name, external, nullable, ignorable);
    }
}
