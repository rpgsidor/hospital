package com.venvw.hospital.utils;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public final class DatabaseEraseUtils {

    public static void Erase(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DELETE FROM PEOPLE");
        jdbcTemplate.execute("DELETE FROM WARDS");
        jdbcTemplate.execute("DELETE FROM DIAGNOSIS");

        jdbcTemplate.execute("ALTER SEQUENCE DIAGNOSIS_ID_SEQ RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE PEOPLE_ID_SEQ RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE WARDS_ID_SEQ RESTART WITH 1");
    }
}
