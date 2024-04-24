import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.connect(); // Connect to the database

        List<Object[]> wachtrijData = databaseConnector.queryWachtrij(); // Retrieve data for the first table
        List<Object[]> verwerkingData = databaseConnector.queryVerwerking(); // Retrieve data for the second table

        // sluit database connection af als applicatie afsluit
        Runtime.getRuntime().addShutdownHook(new Thread(databaseConnector::close));



        SwingUtilities.invokeLater(() -> {
            createAndShowGUI(wachtrijData, verwerkingData);
        });
    }

    private static void createAndShowGUI(List<Object[]> wachtrijData, List<Object[]> verwerkingData) {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1010, 710);
        frame.setLayout(new BorderLayout());

        // Dashboard panel
        JPanel dashboardPanel = new DashboardPanel(wachtrijData, verwerkingData); // Pass the data to DashboardPanel constructor
        frame.add(dashboardPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
