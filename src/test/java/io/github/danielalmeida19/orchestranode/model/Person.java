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

    public Person() {
    }

    public Person(String name, String surname, boolean active, List<String> habilities, Address address) {
        this.name = name;
        this.surname = surname;
        this.active = active;
        this.habilities = habilities;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getHabilities() {
        return habilities;
    }

    public void setHabilities(List<String> habilities) {
        this.habilities = habilities;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
