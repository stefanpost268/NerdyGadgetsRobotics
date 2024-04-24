import jdk.jfr.Description;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nerdygadgets";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseConnector() {
        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
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

    public List<Object[]> queryWachtrij() {
        List<Object[]> wachtrij = new ArrayList<>();
        String query = "SELECT l.OrderID, COUNT(*), o.WachtrijStatus\n" +
                "FROM orderlines l\n" +
                "JOIN orders o on o.OrderID = l.OrderID \n" +
                "WHERE o.WachtrijStatus Not Like 'Done'\n" +
                "GROUP BY l.orderID\n" +
                "ORDER BY l.OrderLineID;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                int aantal = resultSet.getInt("COUNT(*)");
                String wachtrijStatus = resultSet.getString("WachtrijStatus");
                wachtrij.add(new Object[]{orderID, aantal, wachtrijStatus});
            }

        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return wachtrij;
    }

    public List<Object[]> queryVerwerking() {
        List<Object[]> verwerking = new ArrayList<>();
        String query = "SELECT l.StockItemID, l.Description, l.quantity\n" +
                "FROM orderlines l\n" +
                "JOIN orders o on o.OrderID = l.OrderID \n" +
                "WHERE o.WachtrijStatus Not Like 'Done'\n" +
                "ORDER BY l.OrderLineID;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int stockItemID = resultSet.getInt("StockItemID");
                String description = resultSet.getString("Description");
                int aantal = resultSet.getInt("Quantity");
                verwerking.add(new Object[]{stockItemID, description, aantal});
            }

        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return verwerking;
    }
}
