package com.venvw.hospital.model;

public class People {
    private Integer id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private Integer diagnosisId;
    private Integer wardId;

    public People(Integer id, String firstName, String lastName, String fatherName, Integer diagnosisId, Integer wardId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.diagnosisId = diagnosisId;
        this.wardId = wardId;
    }

    public People(String firstName, String lastName, String fatherName, Integer diagnosisId, Integer wardId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.diagnosisId = diagnosisId;
        this.wardId = wardId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Integer getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Integer diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Integer getWardId() {
        return wardId;
    }

    public void setWardId(Integer wardId) {
        this.wardId = wardId;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", diagnosisId=" + diagnosisId +
                ", wardId=" + wardId +
                '}';
    }
}
