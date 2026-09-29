package io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata;

/**
 * Class that represents a simple String value.
 *
 * @author Daniel
 */
public record StringValue(String value) implements Value {

    @Override
    public final String toValueString() {
        return "\"" + value + "\"";
    }
}
