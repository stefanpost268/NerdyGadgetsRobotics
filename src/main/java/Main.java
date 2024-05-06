import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.connect(); // Connect to the database

        List<Object[]> queueData = databaseConnector.queryQueue(); // Retrieve data for the first table
        List<Object[]> processingData = databaseConnector.queryProcessing(); // Retrieve data for the second table

        // close database connection when application closes
        Runtime.getRuntime().addShutdownHook(new Thread(databaseConnector::close));


        new DashboardPanel(queueData, processingData);
    }
}