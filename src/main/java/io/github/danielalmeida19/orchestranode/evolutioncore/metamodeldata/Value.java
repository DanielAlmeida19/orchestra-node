package io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata;

/**
 * This represents a generic Value in the Structure of the data
 * 
 * @author Daniel
 */
public sealed interface Value permits
        ObjectValue,
        ArrayValue,
        StringValue,
        NumberValue,
        BooleanValue,
        NullValue {

    /**
     * Método utilitário para converter em representação impressa
     */
    String toValueString();
}
