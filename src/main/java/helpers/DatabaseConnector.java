package helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is used to connect to the database.
 *
 * @deprecated This class is deprecated and will be removed in the future. Please use spring data JPA instead.
 */
@Deprecated
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

    public String[] getCustomerData(int orderid) {
        String[] customerData = new String[9];
        String query = "SELECT c.CustomerName, c.PhoneNumber, c.DeliveryAddressLine2, c.PostalCityID, o.OrderID, o.OrderDate, ol.StockItemID, ol.Quantity, ol.Description FROM customers c JOIN orders o ON o.CustomerID = c.CustomerID JOIN orderlines ol ON ol.OrderID = o.OrderID WHERE o.OrderID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                customerData[0] = String.valueOf(resultSet.getInt("o.OrderID")); // CustomerID opslaan als String
                customerData[1] = resultSet.getString("c.CustomerName"); // CustomerName opslaan
                customerData[2] = resultSet.getString("c.PhoneNumber"); // PhoneNumber opslaan
                customerData[3] =  resultSet.getString("c.DeliveryAddressLine2"); // adres opslaan
                customerData[4] = resultSet.getString("c.PostalCityID"); // cityid opslaan
                customerData[5] = resultSet.getString("o.OrderDate"); // orderdatum opslaan
                customerData[6] = resultSet.getString("ol.StockItemID"); // stockItemID opslaan
                customerData[7] = resultSet.getString("ol.Quantity"); // orderdatum opslaan
                customerData[8] = resultSet.getString("ol.Description"); // orderdatum opslaan
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return customerData;
    }

    public List<Object[]> getOrderlines(int orderid) {
        List<Object[]> customerDataList = new ArrayList<>();
        String query = "SELECT StockItemID, Quantity, Description FROM orderlines WHERE OrderID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderid);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int StockItemID = resultSet.getInt("StockItemID");
                int Quantity = resultSet.getInt("Quantity");
                String description = resultSet.getString("Description");
                customerDataList.add(new Object[]{StockItemID, Quantity, description});
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return customerDataList;
    }

    public List<Object[]> finishorders(){
        List<Object[]> orderlines = new ArrayList<>();
        String query = "SELECT ol.OrderID, o.PickingCompletedWhen, SUM(ol.Quantity) AS aantalproducten FROM orders o JOIN  orderlines ol ON ol.OrderID = o.OrderID GROUP BY  ol.OrderID,o.PickingCompletedWhen;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int OrderID = resultSet.getInt("OrderID");
                Timestamp pickingCompletedWhen = resultSet.getTimestamp("PickingCompletedWhen");
                int aantalProducten = resultSet.getInt("aantalproducten");
                orderlines.add(new Object[]{OrderID, pickingCompletedWhen, aantalProducten});
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return orderlines;
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