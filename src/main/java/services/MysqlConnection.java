package services;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MysqlConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/nerdygadgets";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public MysqlConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> executeQuery(String query) {
        List<Object[]> result = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                result.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}