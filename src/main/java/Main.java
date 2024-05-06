import database.DatabaseConnector;
import pages.DashboardPanel;

public class Main {
    public static void main(String[] args) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        new DashboardPanel(databaseConnector.getQueueData(), databaseConnector.getProcessingData());
    }
}