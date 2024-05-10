package pages;

import helpers.ConfigReader;
import visualComponents.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardPage extends JPanel {
    public DashboardPage(List<Object[]> queueData, List<Object[]> processingData) {
        setLayout(new GridLayout(1, 2));
        JPanel panelFirst = new JPanel();
        panelFirst.setLayout(new GridLayout(2, 1));

        WarehouseMap map = new WarehouseMap(
                Integer.valueOf(ConfigReader.getConfig("grid.height")),
                Integer.valueOf(ConfigReader.getConfig("grid.width"))
        );
        map.setPreferredSize(new Dimension(map.width, map.height));

        panelFirst.add(map);
        //panelFirst.add(new ExportPdfButton());
        panelFirst.add(new pakketbonbutton());
        add(panelFirst);

        JPanel panel = new JPanel();

        QueueBox queueBox = new QueueBox(queueData);
        queueBox.setPreferredSize(new Dimension(330, 340));
        panel.add(queueBox);

        ProcessingBox processingBox = new ProcessingBox(processingData);
        processingBox.setPreferredSize(new Dimension(330, 300));
        panel.add(processingBox);




        add(panel);

    }
}
