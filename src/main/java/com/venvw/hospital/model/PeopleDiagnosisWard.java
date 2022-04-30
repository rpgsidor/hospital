package com.venvw.hospital.model;

public class PeopleDiagnosisWard {
    private People people;
    private Diagnosis diagnosis;
    private Ward ward;

    public PeopleDiagnosisWard(People people, Diagnosis diagnosis, Ward ward) {
        this.people = people;
        this.diagnosis = diagnosis;
        this.ward = ward;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    @Override
    public String toString() {
        return "PeopleDiagnosisWard{" +
                "people=" + people +
                ", diagnosis=" + diagnosis +
                ", ward=" + ward +
                '}';
    }
}
