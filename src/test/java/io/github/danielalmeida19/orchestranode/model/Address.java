package io.github.danielalmeida19.orchestranode.model;

import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;

public class Address {

    @OrchestraField
    private String street;

    @OrchestraField
    private int number;
}
