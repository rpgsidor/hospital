package com.venvw.hospital.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUpdateWardDto {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    @NotNull
    @Min(1)
    private Integer maxCount;

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
}
