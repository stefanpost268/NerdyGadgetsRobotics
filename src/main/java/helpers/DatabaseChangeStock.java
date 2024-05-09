package helpers;

import objects.ProductInfo;

import java.sql.*;

public class DatabaseChangeStock {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nerdygadgets";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public DatabaseChangeStock() {
        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
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


    public ProductInfo getProductInfo(ProductInfo productInfo) {
        String query =
                """
                        SELECT StockItemName, TypicalWeightPerUnit, UnitPrice, RecommendedRetailPrice, QuantityOnHand, Location\s
                        FROM stockitems i \s
                        JOIN stockitemholdings h ON i.StockItemID = h.StockItemID\s
                        WHERE i.StockItemID = ?;""";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, productInfo.getStockItemID());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                productInfo.setStockItemName(resultSet.getString("StockItemName"));
                productInfo.setTypicalWeightPerUnit(resultSet.getInt("TypicalWeightPerUnit"));
                productInfo.setUnitPrice(resultSet.getDouble("UnitPrice"));
                productInfo.setRecommendedRetailPrice(resultSet.getDouble("RecommendedRetailPrice"));
                productInfo.setQuantityPerOuter(resultSet.getInt("QuantityOnHand"));
                productInfo.setLocation(resultSet.getString("Location"));

                System.out.println(productInfo.getStockItemName());
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }
        return productInfo;
    }

    public void changeLocationAndAmount(String locatie, int voorraad, int id) {
        String query =
                """
                        UPDATE stockitems i  \s
                        JOIN stockitemholdings h ON i.StockItemID = h.StockItemID\s
                        SET Location = ?, QuantityOnHand = ?\s
                        WHERE i.stockitemID = ?\s
                        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, locatie);
            statement.setInt(2, voorraad);
            statement.setObject(3, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

    }


}