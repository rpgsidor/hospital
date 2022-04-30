package com.venvw.hospital.dao;

import com.venvw.hospital.model.People;
import com.venvw.hospital.model.Ward;
import com.venvw.hospital.model.WardPeople;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WardDao implements Dao<Ward> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void persist(Ward model) {
        Objects.requireNonNull(model);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            @NonNull
            public PreparedStatement createPreparedStatement(@NonNull Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO WARDS(NAME, MAX_COUNT) VALUES (?, ?) RETURNING ID", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, model.getName());
                statement.setInt(2, model.getMaxCount());
                return statement;
            }
        }, keyHolder);

        model.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public Ward find(Integer id) {
        Objects.requireNonNull(id);

        return jdbcTemplate.query("SELECT * FROM WARDS WHERE ID=?", rs -> {
            if (!rs.next()) {
                return null;
            }

            return new Ward(rs.getInt("id"), rs.getString("name"), rs.getInt("max_count"));
        }, id);
    }

    public WardPeople findWardPeople(Integer wardId) {
        Objects.requireNonNull(wardId);

        return jdbcTemplate.query("SELECT w.*, p.ID as p_id, p.FIRST_NAME as p_first_name, p.LAST_NAME as p_last_name, p.FATHER_NAME as p_father_name, p.WARD_ID as p_ward_id, p.DIAGNOSIS_ID as p_diagnosis_id FROM WARDS w " +
                "LEFT JOIN PEOPLE p on w.ID = p.WARD_ID " +
                "WHERE w.ID=?", rs -> {
            if (!rs.next()) {
                return null;
            }

            Ward ward = new Ward(rs.getInt("id"), rs.getString("name"), rs.getInt("max_count"));
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

            return new WardPeople(ward, people);
        }, wardId);
    }

    @Override
    public List<Ward> findAll() {
        return jdbcTemplate.query("SELECT * FROM WARDS", rs -> {
            ArrayList<Ward> wards = new ArrayList<>();

            while (rs.next()) {
                wards.add(new Ward(rs.getInt("id"), rs.getString("name"), rs.getInt("max_count")));
            }

            return wards;
        });
    }

    public List<WardPeople> findAllWardPeople() {
        return jdbcTemplate.query("SELECT w.*, p.ID as p_id, p.FIRST_NAME as p_first_name, p.LAST_NAME as p_last_name, p.FATHER_NAME as p_father_name, p.WARD_ID as p_ward_id, p.DIAGNOSIS_ID as p_diagnosis_id FROM WARDS w " +
                "LEFT JOIN PEOPLE p on w.ID = p.WARD_ID " +
                "ORDER BY ID", rs -> {
            ArrayList<WardPeople> wardPeople = new ArrayList<>();

            Ward ward = null;
            ArrayList<People> people = new ArrayList<>();

            while (rs.next()) {
                if (ward == null || rs.getInt("id") != ward.getId()) {
                    if (ward != null) {
                        wardPeople.add(new WardPeople(ward, people));
                        people.clear();
                    }

                    ward = new Ward(rs.getInt("id"), rs.getString("name"), rs.getInt("max_count"));
                }

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
            }

            if (ward != null) {
                wardPeople.add(new WardPeople(ward, people));
            }

            return wardPeople;
        });
    }

    @Override
    public void update(Ward model) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(model.getId());

        jdbcTemplate.update("UPDATE WARDS set NAME=?, MAX_COUNT=? WHERE ID=?", model.getName(), model.getMaxCount(), model.getId());
    }

    @Override
    public void remove(Integer id) {
        Objects.requireNonNull(id);

        jdbcTemplate.update("DELETE FROM WARDS WHERE ID=?", id);
    }
}
