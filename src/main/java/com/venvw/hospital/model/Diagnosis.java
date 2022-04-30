package com.venvw.hospital.model;

public class Diagnosis {
    private Integer id;
    private String name;

    public Diagnosis(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Diagnosis(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
