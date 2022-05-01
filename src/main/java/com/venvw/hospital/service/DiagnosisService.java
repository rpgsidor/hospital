package com.venvw.hospital.service;

import com.venvw.hospital.dao.DiagnosisDao;
import com.venvw.hospital.model.Diagnosis;
import com.venvw.hospital.model.DiagnosisPeople;
import com.venvw.hospital.model.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class DiagnosisService {
    private final DiagnosisDao diagnosisDao;

    @Autowired
    public DiagnosisService(DiagnosisDao diagnosisDao) {
        this.diagnosisDao = diagnosisDao;
    }

    @Transactional
    public void createDiagnosis(Diagnosis diagnosis) {
        Objects.requireNonNull(diagnosis);

        diagnosisDao.persist(diagnosis);
    }

    @Transactional
    public List<Diagnosis> readDiagnosis() {
        return diagnosisDao.findAll();
    }

    @Transactional
    public Diagnosis readDiagnosisById(Integer id) {
        Objects.requireNonNull(id);

        Diagnosis diagnosis = diagnosisDao.find(id);

        if (diagnosis == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "diagnosis not found");
        }

        return diagnosis;
    }

    @Transactional
    public void updateDiagnosis(Diagnosis diagnosis) {
        Objects.requireNonNull(diagnosis);

        diagnosisDao.update(diagnosis);
    }

    @Transactional
    public void deleteDiagnosis(Integer id) {
        Objects.requireNonNull(id);

        DiagnosisPeople diagnosis = diagnosisDao.findPeople(id);

        if (diagnosis == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "diagnosis not found");
        }

        if (!diagnosis.getPeople().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "there are people who has diagnosis");
        }

        diagnosisDao.remove(id);
    }

    @Transactional
    public List<People> readPeople(Integer diagnosisId) {
        Objects.requireNonNull(diagnosisId);

        DiagnosisPeople diagnosis = diagnosisDao.findPeople(diagnosisId);

        if (diagnosis == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "diagnosis not found");
        }

        return diagnosis.getPeople();
    }
}
