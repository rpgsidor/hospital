package com.venvw.hospital.service;

import com.venvw.hospital.dao.DiagnosisDao;
import com.venvw.hospital.dao.PeopleDao;
import com.venvw.hospital.dao.WardDao;
import com.venvw.hospital.model.Diagnosis;
import com.venvw.hospital.model.People;
import com.venvw.hospital.model.Ward;
import com.venvw.hospital.model.WardPeople;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class PeopleService {
    private final PeopleDao peopleDao;
    private final WardDao wardDao;
    private final DiagnosisDao diagnosisDao;

    @Autowired
    public PeopleService(PeopleDao peopleDao, WardDao wardDao, DiagnosisDao diagnosisDao) {
        this.peopleDao = peopleDao;
        this.wardDao = wardDao;
        this.diagnosisDao = diagnosisDao;
    }

    @Transactional
    public void createPeople(People people) {
        Objects.requireNonNull(people);

        validatePeople(people);

        peopleDao.persist(people);
    }

    @Transactional
    public List<People> readPeople() {
        return peopleDao.findAll();
    }

    @Transactional
    public People readPeopleById(Integer id) {
        Objects.requireNonNull(id);

        People people = peopleDao.find(id);

        if (people == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "people not found");
        }

        return people;
    }

    @Transactional
    public void updatePeople(People people) {
        Objects.requireNonNull(people);

        validatePeople(people);

        peopleDao.update(people);
    }

    @Transactional
    public void deletePeople(Integer id) {
        Objects.requireNonNull(id);

        peopleDao.remove(id);
    }

    private void validatePeople(People people) {
        Objects.requireNonNull(people);
        Objects.requireNonNull(people.getDiagnosisId());
        Objects.requireNonNull(people.getWardId());

        Diagnosis diagnosis = diagnosisDao.find(people.getDiagnosisId());
        if (diagnosis == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "diagnosis not found");
        }

        WardPeople ward = wardDao.findWardPeople(people.getWardId());
        if (ward == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ward not found");
        }
        if (ward.getPeople().stream().filter(x -> !Objects.equals(people.getId(), x.getId())).count() + 1 > ward.getWard().getMaxCount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ward has no room");
        }
    }
}
