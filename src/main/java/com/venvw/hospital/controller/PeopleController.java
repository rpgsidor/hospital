package com.venvw.hospital.controller;

import com.venvw.hospital.dto.CreateUpdatePeopleDto;
import com.venvw.hospital.model.People;
import com.venvw.hospital.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PreAuthorize("hasAuthority('edit')")
    @PostMapping
    public People createPeople(@Valid @RequestBody CreateUpdatePeopleDto dto) {
        People people = new People(dto.getFirstName(), dto.getLastName(), dto.getFatherName(), dto.getDiagnosisId(), dto.getWardId());
        peopleService.createPeople(people);

        return people;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<People> readPeople() {
        return peopleService.readPeople();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public People readPeopleById(@PathVariable Integer id) {
        return peopleService.readPeopleById(id);
    }

    @PreAuthorize("hasAuthority('edit')")
    @PatchMapping("/{id}")
    public People updatePeople(@PathVariable Integer id, @Valid @RequestBody CreateUpdatePeopleDto dto) {
        People people = new People(id, dto.getFirstName(), dto.getLastName(), dto.getFatherName(), dto.getDiagnosisId(), dto.getWardId());

        peopleService.updatePeople(people);

        return people;
    }

    @PreAuthorize("hasAuthority('edit')")
    @DeleteMapping("/{id}")
    public void deletePeople(@PathVariable Integer id) {
        peopleService.deletePeople(id);
    }
}
