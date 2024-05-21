package helpers;

import objects.OrderInfo;

import java.sql.*;

public class ChangeOrderInfoDatabase {
    private static final String JDBC_URL = "jdbc:mariadb://localhost:3306/nerdygadgets";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public ChangeOrderInfoDatabase() {
        try {
            // Register MySQL JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            connect();
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


    public OrderInfo getCustomerInfo(OrderInfo orderInfo) {
        String query =
                """
                        SELECT o.OrderID, ExpectedDeliveryDate, Status,
                        CustomerName, PhoneNumber, DeliveryPostalCode,\s
                        DeliveryAddressLine1, DeliveryAddressLine2
                        FROM orders o\s
                        JOIN customers c on c.CustomerID = o.CustomerID\s
                        WHERE o.OrderID = ?;
                                                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, "1");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orderInfo.setExpectedDeliveryDate(resultSet.getString("ExpectedDeliveryDate"));
                orderInfo.setStatus(resultSet.getString("Status"));
                orderInfo.setCustomerName(resultSet.getString("CustomerName"));
                orderInfo.setPhoneNumber(resultSet.getInt("PhoneNumber"));
                orderInfo.setDeliveryAdress(resultSet.getString("DeliveryPostalCode") + " " +
                        resultSet.getString("DeliveryAddressLine1") + " " +
                        resultSet.getString("DeliveryAddressLine2"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }
        return orderInfo;
    }

    public OrderInfo getOrderComments(OrderInfo orderInfo) {
        String query =
                """
                        SELECT Comments, DeliveryInstructions, InternalComments
                        FROM orders
                        WHERE ORDERID = ?;
                                                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, "id");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orderInfo.setComments(resultSet.getString("Comments"));
                orderInfo.setDeliveryInstructions(resultSet.getString("DeliveryInstructions"));
                orderInfo.setInternalComments(resultSet.getString("InternalComments"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }
        return orderInfo;
    }

    public OrderInfo getOrderdProducts(OrderInfo orderInfo) {
        String query =
                """
                        SELECT  s.StockItemID, StockitemName, Quantity, TypicalWeightPerUnit
                        FROM orderlines o JOIN stockitems s on s.StockItemID = o.StockItemID
                        WHERE OrderID = ?;
                                                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, "id");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // alle producten opvangen

            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }
        return orderInfo;
    }



//    public void changeOrderInfo() {
//        String query =
//                """
//                        UPDATE stockitems i  \s
//                        JOIN stockitemholdings h ON i.StockItemID = h.StockItemID\s
//                        SET Location = ?, QuantityOnHand = ?\s
//                        WHERE i.stockitemID = ?\s
//                        """;
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1,);
//            statement.setInt(2, );
//            statement.setObject(3, );
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Failed to execute the query!");
//            e.printStackTrace();
//        }

}


//}