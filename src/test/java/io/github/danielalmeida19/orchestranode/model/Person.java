package io.github.danielalmeida19.orchestranode.model;

import io.github.danielalmeida19.orchestranode.annotations.OrchestraCollection;
import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;
import io.github.danielalmeida19.orchestranode.annotations.PriorityLevel;

@OrchestraCollection(name = "Persons", priorityLevel = PriorityLevel.NORMAL)
public class Person {

    @OrchestraField(name = "name", nullable = false)
    private String name;

    @OrchestraField(name = "age")
    private int age;

    @OrchestraField
    private boolean active;

    private boolean test;
}
