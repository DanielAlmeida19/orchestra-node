package io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata;

/**
 * Class that represents a Null value
 *
 * @author Daniel
 */
public record NullValue() implements Value {

    @Override
    public String toValueString() {
        return "null";
    }
}
