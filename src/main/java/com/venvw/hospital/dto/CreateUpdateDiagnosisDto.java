package com.venvw.hospital.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUpdateDiagnosisDto {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
