import helpers.DatabaseConnector;
import pages.DashboardPanel;

public class Main {
    public static void main(String[] args) {
        DatabaseConnector database = new DatabaseConnector();
        new DashboardPanel(database.getQueueData(), database.getProcessingData());
    }
}