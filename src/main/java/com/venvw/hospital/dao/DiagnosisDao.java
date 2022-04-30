package com.venvw.hospital.dao;

import com.venvw.hospital.model.*;
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
public class DiagnosisDao implements Dao<Diagnosis> {
    private final JdbcTemplate jdbcTemplate;

    public DiagnosisDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void persist(Diagnosis model) {
        Objects.requireNonNull(model);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            @NonNull
            public PreparedStatement createPreparedStatement(@NonNull Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO DIAGNOSIS(NAME) VALUES (?) RETURNING ID", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, model.getName());
                return statement;
            }
        }, keyHolder);

        model.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public Diagnosis find(Integer id) {
        Objects.requireNonNull(id);

        return jdbcTemplate.query("SELECT * FROM DIAGNOSIS WHERE ID=?", rs -> {
            if (!rs.next()) {
                return null;
            }

            return new Diagnosis(rs.getInt("id"), rs.getString("name"));
        }, id);
    }

    public DiagnosisPeople findPeople(Integer diagnosisId) {
        Objects.requireNonNull(diagnosisId);

        return jdbcTemplate.query("SELECT d.*, p.ID as p_id, p.FIRST_NAME as p_first_name, p.LAST_NAME as p_last_name, p.FATHER_NAME as p_father_name, p.WARD_ID as p_ward_id, p.DIAGNOSIS_ID as p_diagnosis_id FROM DIAGNOSIS d " +
                "LEFT JOIN PEOPLE p on d.ID = p.DIAGNOSIS_ID " +
                "WHERE d.ID=?", rs -> {
            if (!rs.next()) {
                return null;
            }

            Diagnosis diagnosis = new Diagnosis(rs.getInt("id"), rs.getString("name"));
            ArrayList<People> people = new ArrayList<>();

            do {
                int peopleId = rs.getInt("p_id");
                if (rs.wasNull()) {
                    continue;
                }

                people.add(new People(
                        peopleId,
                        rs.getString("p_first_name"),
                        rs.getString("p_last_name"),
                        rs.getString("p_father_name"),
                        rs.getInt("p_diagnosis_id"),
                        rs.getInt("p_ward_id")
                ));
            } while (rs.next());

            return new DiagnosisPeople(diagnosis, people);
        }, diagnosisId);
    }

    @Override
    public List<Diagnosis> findAll() {
        return jdbcTemplate.query("SELECT * FROM DIAGNOSIS", rs -> {
            ArrayList<Diagnosis> diagnoses = new ArrayList<>();

            while (rs.next()) {
                diagnoses.add(new Diagnosis(rs.getInt("id"), rs.getString("name")));
            }

            return diagnoses;
        });
    }

    @Override
    public void update(Diagnosis model) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(model.getId());

        jdbcTemplate.update("UPDATE DIAGNOSIS set NAME=? WHERE ID=?", model.getName(), model.getId());
    }

    @Override
    public void remove(Integer id) {
        Objects.requireNonNull(id);

        jdbcTemplate.update("DELETE FROM DIAGNOSIS WHERE ID=?", id);
    }
}
