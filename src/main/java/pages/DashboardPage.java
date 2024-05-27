package pages;

import helpers.ConfigReader;
import repositories.OrderRepository;
import visualComponents.ProcessingBox;
import visualComponents.QueueBox;
import visualComponents.WarehouseMap;
import visualComponents.ExportPdfButton;

import javax.swing.*;
import java.awt.*;

public class DashboardPage extends JPanel {
    private int borderThickness = 3;
    private int insets = 5;
    public DashboardPage(OrderRepository orderRepository) {
//        setLayout(new GridLayout(1, 2));
//        JPanel panelFirst = new JPanel();
//        panelFirst.setLayout(new GridLayout(2, 1));
//
//        WarehouseMap map = new WarehouseMap(
//                Integer.valueOf(ConfigReader.getConfig("grid.height")),
//                Integer.valueOf(ConfigReader.getConfig("grid.width"))
//        );
//        map.setPreferredSize(new Dimension(map.width, map.height));
//
//        panelFirst.add(map);
//        panelFirst.add(new ExportPdfButton());
//        add(panelFirst);
//
//        JPanel panel = new JPanel();
//
//        QueueBox queueBox = new QueueBox(orderRepository);
//        queueBox.setPreferredSize(new Dimension(330, 340));
//        panel.add(queueBox);
//
//        ProcessingBox processingBox = new ProcessingBox(queueBox.getOrderInProgress());
//        processingBox.setPreferredSize(new Dimension(330, 300));
//        panel.add(processingBox);
//
//        add(panel);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();


        // left space constraints
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.7;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        JPanel leftPanel = new JPanel(new GridBagLayout());
//        leftPanel.setBackground(Color.BLUE); // Set a background color for demonstration
        add(leftPanel, constraints);

        // right space constraints
        constraints.gridx = 1;
        constraints.weightx = 0.3;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        //right space
        JPanel rightPanel = new JPanel(new GridBagLayout());
//        rightPanel.setBackground(Color.GREEN); // Set a background color for demonstration
        add(rightPanel, constraints);

        // left panels constraints
        GridBagConstraints leftConstraints = new GridBagConstraints();
        leftConstraints.gridx = 0;
        leftConstraints.gridy = 0;
        leftConstraints.weightx = 1.0;
        leftConstraints.weighty = 0.7;
        leftConstraints.fill = GridBagConstraints.BOTH;
        leftConstraints.insets = new Insets(insets, insets, insets, insets);


        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setBackground(Color.CYAN);
        topLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, borderThickness));
        leftPanel.add(topLeftPanel, leftConstraints);

        //left panel constraints
        leftConstraints.gridy = 1;
        leftConstraints.weighty = 0.3;

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.MAGENTA);
        bottomLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, borderThickness));
        leftPanel.add(bottomLeftPanel, leftConstraints);

        // right panel constraints
        GridBagConstraints rightConstraints = new GridBagConstraints();
        rightConstraints.gridx = 0;
        rightConstraints.gridy = 0;
        rightConstraints.weightx = 1.0;
        rightConstraints.weighty = 0.6;
        rightConstraints.fill = GridBagConstraints.BOTH;
        rightConstraints.insets = new Insets(insets, insets, insets, insets);


        JPanel topRightPanel = new JPanel();
        topRightPanel.setBackground(Color.YELLOW);
        topRightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, borderThickness));
        rightPanel.add(topRightPanel, rightConstraints);

        // right panel constraints
        rightConstraints.gridy = 1;
        rightConstraints.weighty = 0.4;

        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.RED);
        bottomRightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, borderThickness));
        rightPanel.add(bottomRightPanel, rightConstraints);
    }
}