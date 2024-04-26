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



        SwingUtilities.invokeLater(() -> {
            createAndShowGUI(queueData, processingData);
        });
    }

    private static void createAndShowGUI(List<Object[]> queueData, List<Object[]> processingData) {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1010, 710);
        frame.setLayout(new BorderLayout());

        // Dashboard panel
        JPanel dashboardPanel = new DashboardPanel(queueData, processingData); // Pass the data to DashboardPanel constructor
        frame.add(dashboardPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
