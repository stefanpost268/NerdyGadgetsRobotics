package services;

import java.sql.Connection;
import java.sql.DriverManager;

public class MariaDBConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/nerdygadgets";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    /**
     * Create a new MariaDB connection
     */
    public MariaDBConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the current mysql connection
     * @return Connection
     */
    public Connection getConnection() {
        return this.connection;
    }
}