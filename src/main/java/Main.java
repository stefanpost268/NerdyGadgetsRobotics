import helpers.DatabaseConnector;
import org.flywaydb.core.Flyway;
import pages.*;

import javax.sql.DataSource;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.CardLayout;
import java.sql.SQLException;

public class Main extends JFrame {
    public static void main(String[] args) throws SQLException {
        new Main().gui();
    }

    public void gui() throws SQLException {
        DatabaseConnector database = new DatabaseConnector();
        migrateDatabase(database.getDataSource());


        setTitle("NerdyGadgetsRobotics");
        setSize(1350, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new BasicTabbedPaneUI());
        tabbedPane.addTab("Dashboard", new DashboardPage(database.getQueueData(), database.getProcessingData()));
        tabbedPane.addTab("Vooraad", new ProductPage());
        tabbedPane.setBorder(null);

        add(tabbedPane);
        setVisible(true);
    }

    public static void migrateDatabase(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .load();

        System.out.println(flyway.info());
    }
}
