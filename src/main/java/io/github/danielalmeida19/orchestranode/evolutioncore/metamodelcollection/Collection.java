package io.github.danielalmeida19.orchestranode.evolutioncore.metamodelcollection;

import java.util.List;

import io.github.danielalmeida19.orchestranode.annotations.PriorityLevel;

public class Collection {

    private String name;
    // private ObjectField body;
    private List<Field<?>> fields;
    private PriorityLevel priorityLevel;

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

    public List<Field<?>> getFields() {
        return fields;
    }

    public void setFields(List<Field<?>> fields) {
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

}
