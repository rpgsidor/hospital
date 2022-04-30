package com.venvw.hospital.dao;

import java.util.List;

public interface Dao<T> {

    void persist(T model);

    T find(Integer id);

    List<T> findAll();

    void update(T model);

    void remove(Integer id);
}
