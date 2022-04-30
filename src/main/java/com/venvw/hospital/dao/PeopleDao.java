package com.venvw.hospital.dao;

import com.venvw.hospital.model.Diagnosis;
import com.venvw.hospital.model.People;
import com.venvw.hospital.model.PeopleDiagnosisWard;
import com.venvw.hospital.model.Ward;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PeopleDao implements Dao<People> {
    private final JdbcTemplate jdbcTemplate;

    public PeopleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void persist(People model) {
        Objects.requireNonNull(model);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            @NonNull
            public PreparedStatement createPreparedStatement(@NonNull Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO PEOPLE(FIRST_NAME, LAST_NAME, FATHER_NAME, DIAGNOSIS_ID, WARD_ID) VALUES (?, ?, ?, ?, ?) RETURNING ID", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, model.getFirstName());
                statement.setString(2, model.getLastName());
                statement.setString(3, model.getFatherName());
                statement.setInt(4, model.getDiagnosisId());
                statement.setInt(5, model.getWardId());
                return statement;
            }
        }, keyHolder);

        Objects.requireNonNull(keyHolder.getKey());

        model.setId(keyHolder.getKey().intValue());
    }

    @Override
    public People find(Integer id) {
        Objects.requireNonNull(id);

        return jdbcTemplate.query("SELECT * FROM PEOPLE WHERE ID=?", rs -> {
            if (!rs.next()) {
                return null;
            }

            return new People(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("father_name"),
                    rs.getInt("diagnosis_id"),
                    rs.getInt("ward_id")
            );
        }, id);
    }

    public PeopleDiagnosisWard findDiagnosisWard(Integer peopleId) {
        Objects.requireNonNull(peopleId);

        return jdbcTemplate.query("SELECT p.*, d.id AS d_id, d.name AS d_name, w.id AS w_id, w.name AS w_name, w.max_count AS w_max_count FROM PEOPLE p " +
                "JOIN DIAGNOSIS d on d.id=p.diagnosis_id " +
                "JOIN WARDS w on w.id=p.ward_id " +
                "WHERE p.ID=?", rs -> {
            if (!rs.next()) {
                return null;
            }

            People people = new People(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("father_name"),
                    rs.getInt("diagnosis_id"),
                    rs.getInt("ward_id")
            );

            Diagnosis diagnosis = new Diagnosis(
                    rs.getInt("d_id"),
                    rs.getString("d_name")
            );

            Ward ward = new Ward(
                    rs.getInt("w_id"),
                    rs.getString("w_name"),
                    rs.getInt("w_max_count")
            );

            return new PeopleDiagnosisWard(people, diagnosis, ward);
        }, peopleId);
    }

    @Override
    public List<People> findAll() {
        return jdbcTemplate.query("SELECT * FROM PEOPLE", rs -> {
            ArrayList<People> people = new ArrayList<>();

            while (rs.next()) {
                people.add(new People(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("father_name"),
                        rs.getInt("diagnosis_id"),
                        rs.getInt("ward_id")
                ));
            }

            return people;
        });
    }

    public List<PeopleDiagnosisWard> findAllDiagnosisWard() {
        return jdbcTemplate.query("SELECT p.*, d.id AS d_id, d.name AS d_name, w.id AS w_id, w.name AS w_name, w.max_count AS w_max_count FROM PEOPLE p " +
                "JOIN DIAGNOSIS d on d.id=p.diagnosis_id " +
                "JOIN WARDS w on w.id=p.ward_id ", rs -> {
            ArrayList<PeopleDiagnosisWard> peopleDiagnosisWards = new ArrayList<>();

            while (rs.next()) {
                People people = new People(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("father_name"),
                        rs.getInt("diagnosis_id"),
                        rs.getInt("ward_id")
                );

                Diagnosis diagnosis = new Diagnosis(
                        rs.getInt("d_id"),
                        rs.getString("d_name")
                );

                Ward ward = new Ward(
                        rs.getInt("w_id"),
                        rs.getString("w_name"),
                        rs.getInt("w_max_count")
                );

                peopleDiagnosisWards.add(new PeopleDiagnosisWard(people, diagnosis, ward));
            }

            return peopleDiagnosisWards;
        });
    }

    @Override
    public void update(People model) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(model.getId());

        jdbcTemplate.update("UPDATE PEOPLE set FIRST_NAME=?, LAST_NAME=?, FATHER_NAME=?, DIAGNOSIS_ID=?, WARD_ID=? WHERE ID=?",
                model.getFirstName(), model.getLastName(), model.getFatherName(), model.getDiagnosisId(), model.getWardId(), model.getId());
    }

    @Override
    public void remove(Integer id) {
        Objects.requireNonNull(id);

        jdbcTemplate.update("DELETE FROM PEOPLE WHERE ID=?", id);
    }
}
