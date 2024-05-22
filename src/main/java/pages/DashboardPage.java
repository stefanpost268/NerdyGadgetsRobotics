package pages;

import helpers.ConfigReader;
import repositories.OrderRepository;
import visualComponents.ProcessingBox;
import visualComponents.QueueBox;
import visualComponents.WarehouseMap;
import visualComponents.ExportPdfButton;
import javax.swing.JPanel;
import java.awt.*;

public class DashboardPage extends JPanel {
    public DashboardPage(List<Object[]> queueData, List<Object[]> processingData, OrderRepository orderRepository) {
        setLayout(new GridLayout(1, 2));
        JPanel panelFirst = new JPanel();
        panelFirst.setLayout(new GridLayout(2, 1));

        WarehouseMap map = new WarehouseMap(
                Integer.valueOf(ConfigReader.getConfig("grid.height")),
                Integer.valueOf(ConfigReader.getConfig("grid.width"))
        );
        map.setPreferredSize(new Dimension(map.width, map.height));

        panelFirst.add(map);
        panelFirst.add(new ExportPdfButton());
        add(panelFirst);

        JPanel panel = new JPanel();

        QueueBox queueBox = new QueueBox(queueData, orderRepository);
        queueBox.setPreferredSize(new Dimension(330, 340));
        panel.add(queueBox);

        ProcessingBox processingBox = new ProcessingBox(queueBox.getOrderInProgress());
        processingBox.setPreferredSize(new Dimension(330, 300));
        panel.add(processingBox);

        add(panel);
    }
}
