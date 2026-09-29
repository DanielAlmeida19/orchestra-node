package io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata;

/**
 * Class that represents a simple Number value
 *
 * @author Daniel
 */
public record NumberValue(Number value) implements Value {

    @Override
    public String toValueString() {
        return String.valueOf(value);
    }
}
