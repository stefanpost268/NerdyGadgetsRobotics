import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nerdygadgets";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    private List<Object[]> queueData;
    private List<Object[]> processingData;

    public DatabaseConnector() {
        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //connect to the database
            connect();

            //retrieve data
            queryData();

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

    private void queryData() {
        queueData = queryQueueData(); // Retrieve queue data from the database
        processingData = queryProcessingData(); // Retrieve processing data from the database
    }


    private List<Object[]> queryQueueData() {
        List<Object[]> queue = new ArrayList<>();
        String query = "SELECT l.OrderID, COUNT(*), o.WachtrijStatus\n" +
                "FROM orderlines l\n" +
                "JOIN orders o on o.OrderID = l.OrderID \n" +
                "WHERE o.WachtrijStatus Not Like 'Done'\n" +
                "GROUP BY l.orderID\n" +
                "ORDER BY o.WachtrijStatus, l.OrderID;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                int quantity = resultSet.getInt("COUNT(*)");
                String queueStatus = resultSet.getString("WachtrijStatus");
                queue.add(new Object[]{orderID, quantity, queueStatus});
            }

        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return queue;
    }
    //
    private List<Object[]> queryProcessingData() {
        List<Object[]> processing = new ArrayList<>();
        String query = "SELECT l.StockItemID, l.Description, l.quantity\n" +
                "FROM orderlines l\n" +
                "JOIN orders o on o.OrderID = l.OrderID \n" +
                "WHERE o.WachtrijStatus = 'In progress'\n" +
                "ORDER BY l.OrderLineID;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int stockItemID = resultSet.getInt("StockItemID");
                String description = resultSet.getString("Description");
                int quantity = resultSet.getInt("Quantity");
                processing.add(new Object[]{stockItemID, description, quantity});
            }

        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return processing;
    }
    public List<Object[]> getQueueData() {
        return queueData;
    }

    public List<Object[]> getProcessingData() {
        return processingData;
    }
}
