package com.sunrise.dentalclinic.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton pattern: exactly one connection manager exists for the whole
 * application's lifetime. This avoids scattering DB credentials and
 * connection-opening logic across every screen/service, and means we
 * only ever configure the connection details in one place.
 *
 * NOTE: for a real production app you'd use a connection POOL (e.g. HikariCP)
 * rather than a single shared Connection — worth mentioning as a limitation /
 * "future improvement" in your report's evaluation section.
 */
public class DBConnectionManager {

    // These are WAMP's default MySQL settings: username root, blank password.
    // If your WAMP setup is different, this is the only place you'd need to change it.
    private static final String URL = "jdbc:mysql://localhost:3306/dental_clinic?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static DBConnectionManager instance;
    private Connection connection;

    // private constructor — prevents any other class from doing `new DBConnectionManager()`
    private DBConnectionManager() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database: " + e.getMessage(), e);
        }
    }

    // the only way to get access to the connection manager
    public static synchronized DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to re-establish DB connection: " + e.getMessage(), e);
        }
        return connection;
    }
}
