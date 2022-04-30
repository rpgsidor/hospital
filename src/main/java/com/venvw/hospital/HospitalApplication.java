package com.venvw.hospital;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class HospitalApplication {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(HospitalApplication.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String connectionString = properties.getProperty("spring.datasource.url");
        String username = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");

        try (
                Connection connection = DriverManager.getConnection(connectionString, username, password);
                Statement statement = connection.createStatement()
        ) {
            try {
                statement.execute("CREATE TABLE TEST(PHRASE TEXT)");
                statement.execute("INSERT INTO TEST VALUES ('Hello, World!')");

                try (ResultSet rs = statement.executeQuery("SELECT PHRASE FROM TEST")) {
                    rs.next();
                    System.out.println(rs.getString("phrase"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                statement.execute("DROP TABLE IF EXISTS TEST");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
