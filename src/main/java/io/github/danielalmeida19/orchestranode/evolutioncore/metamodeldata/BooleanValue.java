package io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata;

/**
 * Class that represents a simple Boolean value (true or false)
 *
 * @author Daniel
 */
public record BooleanValue(Boolean value) implements Value {

    @Override
    public String toValueString() {
        return String.valueOf(value);
    }
}
