package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import io.github.danielalmeida19.orchestranode.annotations.PriorityLevel;
import io.github.danielalmeida19.orchestranode.utils.CollectionUtils;

public class Collection {

    private String name;
    private List<CollectionField<?>> fields;
    private PriorityLevel priorityLevel;

    public Collection() {
        fields = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Collection that = (Collection) obj;

        return name.equals(that.name) && isListOfFieldsEquals(that) && priorityLevel == that.priorityLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priorityLevel, fields != null ? new HashSet<>(fields) : null);
    }

    public Collection(String name, List<CollectionField<?>> fields, PriorityLevel priorityLevel) {
        this.name = name;
        this.fields = fields;
        this.priorityLevel = priorityLevel;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CollectionField<?>> getFields() {
        return fields;
    }

    public void setFields(List<CollectionField<?>> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("> Collection: ").append(this.name).append("\n");
        sb.append("> PriorityLevel: ").append(this.priorityLevel).append("\n");

        // fields
        sb.append("> Fields: \n");
        this.fields.forEach(f -> sb.append("\t").append(f.toString().replace("\n", "\n\t")).append("\n"));
        return sb.toString();
    }

    private boolean isListOfFieldsEquals(Collection comparedCollection) {
        List<CollectionField<?>> comparedCollectionFields = comparedCollection.getFields();

        if (fields.size() != comparedCollectionFields.size()) {
            return false;
        }

        for (CollectionField<?> collectionField : fields) {
            CollectionField<?> comparedField = getFieldByName(collectionField.getName());

            if (comparedField == null) {
                return false;
            }

            if (comparedField != collectionField) {
                return false;
            }
        }

        return true;
    }

    public CollectionField<?> getFieldByName(String fieldName) {
        for (CollectionField<?> field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

}
