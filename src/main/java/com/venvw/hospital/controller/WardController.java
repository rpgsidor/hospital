package com.venvw.hospital.controller;

import com.venvw.hospital.dto.CreateUpdateWardDto;
import com.venvw.hospital.model.People;
import com.venvw.hospital.model.Ward;
import com.venvw.hospital.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/wards")
public class WardController {
    private final WardService wardService;

    @Autowired
    public WardController(WardService wardService) {
        this.wardService = wardService;
    }

    @PreAuthorize("hasAuthority('edit')")
    @PostMapping
    public Ward createWard(@Valid @RequestBody CreateUpdateWardDto dto) {
        Ward ward = new Ward(dto.getName(), dto.getMaxCount());
        wardService.createWard(ward);

        return ward;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public Ward readWardById(@PathVariable Integer id) {
        return wardService.readWardById(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Ward> readWards() {
        return wardService.readWards();
    }

    @PreAuthorize("hasAuthority('edit')")
    @PatchMapping("/{id}")
    public Ward updateWardById(@PathVariable Integer id, @Valid @RequestBody CreateUpdateWardDto dto) {
        Ward ward = new Ward(id, dto.getName(), dto.getMaxCount());

        wardService.update(ward);

        return ward;
    }

    @PreAuthorize("hasAuthority('edit')")
    @DeleteMapping("/{id}")
    public void deleteWardById(@PathVariable Integer id) {
        wardService.delete(id);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/people")
    public List<People> readPeopleInsideWardById(@PathVariable Integer id) {
        return wardService.readWardPeopleById(id);
    }
}
