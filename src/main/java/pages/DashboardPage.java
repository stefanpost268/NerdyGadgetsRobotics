package pages;

import helpers.ConfigReader;
import visualComponents.WarehouseMap;

import javax.swing.JPanel;
import java.awt.*;

public class DashboardPage extends JPanel {
    public DashboardPage() {
        WarehouseMap map = new WarehouseMap(
                Integer.valueOf(ConfigReader.getConfig("grid.height")),
                Integer.valueOf(ConfigReader.getConfig("grid.width"))
        );
        map.setPreferredSize(new Dimension(1350, 720));
        add(map);
    }
}
