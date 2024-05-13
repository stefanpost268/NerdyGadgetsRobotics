package models;

import services.MysqlConnection;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import interfaces.Model;

public abstract class BaseModel<T> implements Model<T> {
    private static MysqlConnection MYSQL;
    protected String rawSql = "";
    private String searchValue = null;

    public BaseModel() {
        if(MYSQL == null) {
            MYSQL = new MysqlConnection();
        }
    }

    /**
     * Get name of current table
     * @return String
     */
    public abstract String getTableName();

    /**
     * Return Object
     * @return
     */
    public abstract T createInstance();

    /**
     * Get database records
     */
    protected List<T> executeQuery(String query) {
        List<T> results = new ArrayList<>();

        try {
            PreparedStatement statement = MYSQL.getConnection().prepareStatement(query);
            if(this.searchValue != null) {
                statement.setString(1, this.searchValue);
            }

            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                results.add(convertToObject(row, createInstance()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Parse Object type to generic Object for JTable
     * @param data
     * @return
     */
    public Object[][] toTableData(List<Object[]> data) {
        String[] fillableFields = fillable();
        Object[][] tableData = new Object[data.size()][fillableFields.length];

        for (int i = 0; i < data.size(); i++) {
            Object[] item = data.get(i);
            for (int j = 0; j < fillableFields.length; j++) {
                try {
                    Field field = item.getClass().getDeclaredField(fillableFields[j]);
                    field.setAccessible(true);
                    Object value = field.get(item);

                    // Convert BigDecimal to formatted string with two decimal places
                    if (value instanceof BigDecimal) {
                        value = ((BigDecimal) value).setScale(2, RoundingMode.HALF_UP);
                    }
                    tableData[i][j] = value;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            }

        return tableData;
    }



    /**
     * Search specific column on relative records.
     */
    public T like(String rowName, String search) {
        this.rawSql += " WHERE " + rowName + " LIKE ?";
        this.searchValue = "%" + search + "%";
        return (T) this;
    }

    /**
     * Get records from database
     */
    public List<T> get() {
        if(this.rawSql.isEmpty()) {
            this.rawSql = "SELECT * FROM " + this.getTableName();
        } else {
            this.rawSql = "SELECT * FROM " + this.getTableName() + this.rawSql;
        }

        List<T> data = this.executeQuery(this.rawSql);
        this.rawSql = "";
        this.searchValue = null;
        return data;
    }

    //////////////////////

    public List<Object[]> getProductData(String searchTerm) {
        List<Object[]> productData = new ArrayList<>();
        String query = "SELECT s.StockItemID, s.StockItemName, s.UnitPrice, s.RecommendedRetailPrice, s.TypicalWeightPerUnit, h.QuantityOnHand " +
                "FROM StockItems s JOIN stockitemholdings h ON s.StockItemID = h.StockItemID ";

        // If a search term is provided, add a WHERE clause to filter by StockItemName
        if (!searchTerm.isEmpty()) {
            query += "WHERE s.StockItemName LIKE ?";
        }

        try (PreparedStatement statement = MYSQL.getConnection().prepareStatement(query)) {
            // If a search term is provided, set the parameter for the prepared statement
            if (!searchTerm.isEmpty()) {
                statement.setString(1, "%" + searchTerm + "%");
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int stockItemID = resultSet.getInt("StockItemID");
                String stockItemName = resultSet.getString("StockItemName");
                BigDecimal unitPrice = resultSet.getBigDecimal("UnitPrice");
                BigDecimal recommendedRetailPrice = resultSet.getBigDecimal("RecommendedRetailPrice");
                BigDecimal typicalWeightPerUnit = resultSet.getBigDecimal("TypicalWeightPerUnit");
                int quantityOnHand = resultSet.getInt("QuantityOnHand");
                productData.add(new Object[]{stockItemID, stockItemName, unitPrice, recommendedRetailPrice, typicalWeightPerUnit, quantityOnHand});
            }

        } catch (SQLException e) {
            System.err.println("Failed to execute the query!");
            e.printStackTrace();
        }

        return productData;
    }

}


