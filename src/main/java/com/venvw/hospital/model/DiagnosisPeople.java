package com.venvw.hospital.model;

import java.util.List;

public class DiagnosisPeople {
    private Diagnosis diagnosis;
    private List<People> people;

    public DiagnosisPeople(Diagnosis diagnosis, List<People> people) {
        this.diagnosis = diagnosis;
        this.people = people.stream().toList();
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<People> getPeople() {
        return people.stream().toList();
    }

    public void setPeople(List<People> people) {
        this.people = people.stream().toList();
    }

    @Override
    public String toString() {
        return "DiagnosisPeople{" +
                "diagnosis=" + diagnosis +
                ", people=" + people +
                '}';
    }
}
