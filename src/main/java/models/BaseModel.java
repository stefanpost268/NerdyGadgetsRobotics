package models;

import services.MysqlConnection;
import java.lang.reflect.Field;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import interfaces.Model;

public abstract class BaseModel<T> implements Model<T> {
    private static MysqlConnection MYSQL;

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
            Statement statement = MYSQL.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
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
    public Object[][] toTableData(List<T> data) {
        String[] fillableFields = fillable();
        Object[][] tableData = new Object[data.size()][fillableFields.length];

        for (int i = 0; i < data.size(); i++) {
            T item = data.get(i);
            for (int j = 0; j < fillableFields.length; j++) {
                try {
                    Field field = item.getClass().getField(fillableFields[j]);
                    tableData[i][j] = field.get(item);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return tableData;
    }
}
