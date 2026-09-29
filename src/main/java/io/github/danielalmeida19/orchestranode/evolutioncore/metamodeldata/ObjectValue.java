package io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class that represents an entity, which by itself, represents a data instance.
 * ({@link io.github.danielalmeida19.orchestranode.evolutioncore.metamodeldata.FieldEntity})
 *
 */
public final class ObjectValue implements Value {

    /**
     * Attribute that represents the list of the fields that the Entity have
     */
    private final Map<String, Value> fields = new LinkedHashMap<>();

    /**
     * Adds a field with the specified key and value to the object.
     * 
     * @param key   The key for the field.
     * @param value The value for the field, which can be null.
     * @return This ObjectValue instance.
     */
    public ObjectValue put(String key, Value value) {
        // Adds a field with the specified key and value to the object.
        fields.put(key, value != null ? value : new NullValue());
        return this;
    }

    /**
     * Adds a string field with the specified key to the object.
     * 
     * @param key   The key for the field.
     * @param value The string value for the field, which can be null.
     * @return This ObjectValue instance.
     */
    public ObjectValue put(String key, String value) {
        // Adds a string field with the specified key to the object.
        return put(key, value != null ? new StringValue(value) : new NullValue());
    }

    /**
     * Adds a number field with the specified key to the object.
     * 
     * @param key   The key for the field.
     * @param value The number value for the field, which can be null.
     * @return This ObjectValue instance.
     */
    public ObjectValue put(String key, Number value) {
        // Adds a number field with the specified key to the object.
        return put(key, value != null ? new NumberValue(value) : new NullValue());
    }

    /**
     * Retrieves the value of the field with the specified key from the object.
     * 
     * @param key The key for the field.
     * @return The Value associated with the key, or null if the key does not exist.
     */
    public Value get(String key) {
        // Retrieves the value of the field with the specified key from the object.
        return fields.get(key);
    }

    /**
     * Returns an unmodifiable view of the fields in the object.
     * 
     * @return An unmodifiable Map containing the fields and their values.
     */
    public Map<String, Value> getFields() {
        // Returns an unmodifiable view of the fields in the object.
        return Collections.unmodifiableMap(fields);
    }

    @Override
    public String toValueString() {
        StringBuilder sb = new StringBuilder("{");
        var iterator = fields.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            sb.append("\"").append(entry.getKey()).append("\":").append(entry.getValue().toValueString());
            if (iterator.hasNext())
                sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }
}
