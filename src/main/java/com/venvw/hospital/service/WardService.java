package com.venvw.hospital.service;

import com.venvw.hospital.dao.WardDao;
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
public class WardService {
    private final WardDao wardDao;

    @Autowired
    public WardService(WardDao wardDao) {
        this.wardDao = wardDao;
    }

    @Transactional
    public void createWard(Ward ward) {
        Objects.requireNonNull(ward);

        wardDao.persist(ward);
    }

    @Transactional
    public Ward readWardById(Integer id) {
        Objects.requireNonNull(id);

        Ward ward = wardDao.find(id);

        if (ward == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ward;
    }

    @Transactional
    public List<Ward> readWards() {
        return wardDao.findAll();
    }

    @Transactional
    public void update(Ward ward) {
        Objects.requireNonNull(ward);
        Objects.requireNonNull(ward.getId());

        WardPeople currentWard = wardDao.findWardPeople(ward.getId());

        if (currentWard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ward not found");
        }

        if (currentWard.getPeople().size() > ward.getMaxCount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "there are too many people to fit into");
        }

        wardDao.update(ward);
    }

    @Transactional
    public void delete(Integer id) {
        Objects.requireNonNull(id);

        WardPeople previousWard = wardDao.findWardPeople(id);

        if (previousWard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ward not found");
        }

        if (!previousWard.getPeople().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "there are people");
        }

        wardDao.remove(id);
    }

    @Transactional
    public List<People> readWardPeopleById(Integer wardId) {
        Objects.requireNonNull(wardId);

        WardPeople wardPeople = wardDao.findWardPeople(wardId);

        if (wardPeople == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ward not found");
        }

        return wardPeople.getPeople();
    }

}
