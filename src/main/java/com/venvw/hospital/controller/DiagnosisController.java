package com.venvw.hospital.controller;

import com.venvw.hospital.dto.CreateUpdateDiagnosisDto;
import com.venvw.hospital.model.Diagnosis;
import com.venvw.hospital.model.DiagnosisPeople;
import com.venvw.hospital.model.People;
import com.venvw.hospital.service.DiagnosisService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/diagnoses")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    @PreAuthorize("hasAuthority('edit')")
    @PostMapping
    public Diagnosis createDiagnosis(@Valid @RequestBody CreateUpdateDiagnosisDto dto) {
        Diagnosis diagnosis = new Diagnosis(dto.getName());
        diagnosisService.createDiagnosis(diagnosis);

        return diagnosis;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public Diagnosis readDiagnosisById(@PathVariable Integer id) {
        return diagnosisService.readDiagnosisById(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Diagnosis> readDiagnosis() {
        return diagnosisService.readDiagnosis();
    }

    @PreAuthorize("hasAuthority('edit')")
    @PatchMapping("/{id}")
    public Diagnosis updateDiagnosis(@PathVariable Integer id, @Valid @RequestBody CreateUpdateDiagnosisDto dto) {
        Diagnosis diagnosis = new Diagnosis(id, dto.getName());

        diagnosisService.updateDiagnosis(diagnosis);

        return diagnosis;
    }

    @PreAuthorize("hasAuthority('edit')")
    @DeleteMapping("/{id}")
    public void deleteDiagnosis(@PathVariable Integer id) {
        diagnosisService.deleteDiagnosis(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/people")
    public List<People> readPeopleWithDiagnosis(@PathVariable Integer id) {
        return diagnosisService.readPeople(id);
    }
}
