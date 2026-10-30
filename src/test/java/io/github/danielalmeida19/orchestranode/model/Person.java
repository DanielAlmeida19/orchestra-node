package io.github.danielalmeida19.orchestranode.model;

import java.util.List;

import io.github.danielalmeida19.orchestranode.annotations.OrchestraCollection;
import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;
import io.github.danielalmeida19.orchestranode.annotations.PriorityLevel;

@OrchestraCollection(name = "Persons", priorityLevel = PriorityLevel.NORMAL)
public class Person {

    @OrchestraField(name = "name", nullable = false)
    private String name;

    @OrchestraField
    private String surname;

    @OrchestraField
    private boolean active;

    @OrchestraField
    private List<String> habilities;

    @OrchestraField
    private Address address;
}
