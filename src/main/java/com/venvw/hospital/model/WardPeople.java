package com.venvw.hospital.model;

import java.util.List;

public class WardPeople {
    private Ward ward;
    private List<People> people;

    public WardPeople(Ward ward, List<People> people) {
        this.ward = ward;
        this.people = people.stream().toList();
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public List<People> getPeople() {
        return people.stream().toList();
    }

    public void setPeople(List<People> people) {
        this.people = people.stream().toList();
    }

    @Override
    public String toString() {
        return "WardPeople{" +
                "ward=" + ward +
                ", people=" + people +
                '}';
    }
}
