package pages;

import visualComponents.WarehouseMap;
import javax.swing.JFrame;
import java.awt.*;
import helpers.ConfigReader;

@Deprecated // use DashboardPage instead
public class Dashboard extends JFrame {
    public Dashboard() throws HeadlessException {
        setTitle("Dashboard");
        setSize(1350, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

//        getContentPane().add(
//            new WarehouseMap(Integer.valueOf(ConfigReader.getConfig("grid.height")),
//            Integer.valueOf(ConfigReader.getConfig("grid.width")))
//        );

        setVisible(true);
    }
}
