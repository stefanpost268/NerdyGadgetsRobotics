package helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/nerdygadgets";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public DatabaseConnector() {
        try {
            // Register MySQL JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            //connect to the database
            connect();

            //close the database connection when the application exits
            Runtime.getRuntime().addShutdownHook(new Thread(this::close));

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database!");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close the database connection!");
            e.printStackTrace();
        }
    }

}