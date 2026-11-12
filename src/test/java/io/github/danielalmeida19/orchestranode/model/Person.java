package io.github.danielalmeida19.orchestranode.model;

import java.util.List;

import io.github.danielalmeida19.orchestranode.annotations.CollectionId;
import io.github.danielalmeida19.orchestranode.annotations.FillingPolicy;
import io.github.danielalmeida19.orchestranode.annotations.OrchestraCollection;
import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;
import io.github.danielalmeida19.orchestranode.annotations.PriorityLevel;

@OrchestraCollection(name = "Persons", priorityLevel = PriorityLevel.NORMAL)
public class Person {

    @CollectionId(fillingPolicy = FillingPolicy.MANUALLY)
    private String id;

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

    public Person(String id, String name, String surname, boolean active, List<String> habilities, Address address) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Person [name=" + name + ", surname=" + surname + ", active=" + active + ", habilities=" + habilities
                + ", address=" + address + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((habilities == null) ? 0 : habilities.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        if (active != other.active)
            return false;
        if (habilities == null) {
            if (other.habilities != null)
                return false;
        } else if (!habilities.equals(other.habilities))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

}
