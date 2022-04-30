package com.venvw.hospital.model;

public class Ward {
    private Integer id;
    private String name;
    private Integer maxCount;

    public Ward(Integer id, String name, Integer maxCount) {
        this.id = id;
        this.name = name;
        this.maxCount = maxCount;
    }

    public Ward(String name, Integer maxCount) {
        this.name = name;
        this.maxCount = maxCount;
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

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public String toString() {
        return "Ward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxCount=" + maxCount +
                '}';
    }
}
