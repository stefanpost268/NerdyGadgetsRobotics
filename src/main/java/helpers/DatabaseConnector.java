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

    public List<Object[]> getQueueData() {
        List<Object[]> queueData = new ArrayList<>();
        String query = "SELECT l.OrderID, COUNT(*), o.Status\n" +
                "FROM orderlines l\n" +
                "JOIN orders o on o.OrderID = l.OrderID \n" +
                "WHERE o.Status Not Like 'Done'\n" +
                "GROUP BY l.orderID\n" +
                "ORDER BY o.Status, l.OrderID;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                int quantity = resultSet.getInt("COUNT(*)");
                String queueStatus = resultSet.getString("Status");
                queueData.add(new Object[]{orderID, quantity, queueStatus});
            }

        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return queueData;
    }
    public List<Object[]> getProcessingData() {
        List<Object[]> processingData = new ArrayList<>();
        String query = "SELECT l.StockItemID, l.Description, l.quantity\n" +
                "FROM orderlines l\n" +
                "JOIN orders o on o.OrderID = l.OrderID \n" +
                "WHERE o.Status = 'In progress'\n" +
                "ORDER BY l.OrderLineID;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int stockItemID = resultSet.getInt("StockItemID");
                String description = resultSet.getString("Description");
                int quantity = resultSet.getInt("Quantity");
                processingData.add(new Object[]{stockItemID, description, quantity});
            }

        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return processingData;
    }
}