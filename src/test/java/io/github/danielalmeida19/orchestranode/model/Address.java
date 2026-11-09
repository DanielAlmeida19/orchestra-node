package io.github.danielalmeida19.orchestranode.model;

import io.github.danielalmeida19.orchestranode.annotations.OrchestraField;

public class Address {

    @OrchestraField
    private String street;

    @OrchestraField
    private int number;

    public Address() {

    }

    public Address(String street, int number) {
        this.street = street;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", number=" + number + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + number;
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
        Address other = (Address) obj;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (number != other.number)
            return false;
        return true;
    }

}
