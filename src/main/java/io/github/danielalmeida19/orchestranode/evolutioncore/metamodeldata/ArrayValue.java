package io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents values that are Lists of another Objects
 *
 * @author Daniel
 */
public final class ArrayValue implements Value {

    /**
     * Attribute that represents the elements list
     */
    private final List<Value> elements = new ArrayList<>();

    /**
     * Adds a value to the array.
     * If the provided node is null, a NullValue is added instead.
     *
     * @param node The value to add to the array.
     * @return This ArrayValue instance for method chaining.
     */
    public ArrayValue add(Value node) {
        elements.add(node != null ? node : new NullValue());
        return this;
    }

    /**
     * Adds a string value to the array.
     * 
     * @param value The string value to add, which can be null.
     * @return The updated ArrayValue instance with the added element.
     */
    public ArrayValue add(String value) {
        return add(value != null ? new StringValue(value) : new NullValue());
    }

    /**
     * Retrieves an unmodifiable list of elements in the array.
     * 
     * @return A List of Value objects representing the elements of the array.
     */
    public List<Value> getElements() {
        return Collections.unmodifiableList(elements);
    }

    @Override
    public String toValueString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i).toValueString());
            if (i < elements.size() - 1)
                sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}
