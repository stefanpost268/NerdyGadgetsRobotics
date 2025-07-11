package pages;

import helpers.ConfigReader;
import repositories.OrderRepository;
import services.SerialCommunication;
import visualComponents.*;

import javax.swing.*;
import java.awt.*;

public class DashboardPage extends JPanel {
        private static final int MAIN_WIDTH = 1350;
        private static final int MAIN_HEIGHT = 720;
        private static final int LEFT_PANEL_WIDTH = (int) (MAIN_WIDTH * 0.7);
        private static final int RIGHT_PANEL_WIDTH = (int) (MAIN_WIDTH * 0.3);
        private static final int TOP_LEFT_PANEL_HEIGHT = (int) (MAIN_HEIGHT * 0.7);
        private static final int BOTTOM_LEFT_PANEL_HEIGHT = (int) (MAIN_HEIGHT * 0.3);
        private static final int TOP_RIGHT_PANEL_HEIGHT = (int) (MAIN_HEIGHT * 0.6);
        private static final int BOTTOM_RIGHT_PANEL_HEIGHT = (int) (MAIN_HEIGHT * 0.4);
        private static final int INSETS = 5;
        private static final int BORDER_THICKNESS = 2;

    public DashboardPage(OrderRepository orderRepository, SerialCommunication serialCommunication) {
        setLayout(new GridLayout(1, 2));
        JPanel panelFirst = new JPanel();
        panelFirst.setLayout(new GridLayout(2, 1));
   

        WarehouseMap map = new WarehouseMap(
                serialCommunication,
                Integer.valueOf(ConfigReader.getConfig("grid.height")),
                Integer.valueOf(ConfigReader.getConfig("grid.width"))
        );
        map.setPreferredSize(new Dimension(map.width, map.height));

        QueueBox queueBox = new QueueBox(orderRepository);
        ProcessingBox processingBox = new ProcessingBox(queueBox.getOrderInProgress());

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.7;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, MAIN_HEIGHT));
        add(leftPanel, constraints);

        // Right space constraints
        constraints.gridx = 1;
        constraints.weightx = 0.3;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, MAIN_HEIGHT));
        add(rightPanel, constraints);

        // Left panel constraints
        GridBagConstraints leftConstraints = new GridBagConstraints();
        leftConstraints.gridx = 0;
        leftConstraints.gridy = 0;
        leftConstraints.weightx = 1.0;
        leftConstraints.weighty = 0.7;
        leftConstraints.fill = GridBagConstraints.BOTH;
        leftConstraints.insets = new Insets(INSETS, INSETS, INSETS, INSETS);

        JPanel topLeftPanel = new JPanel(new BorderLayout());
        topLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_THICKNESS));
        topLeftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, TOP_LEFT_PANEL_HEIGHT));
        topLeftPanel.setMinimumSize(new Dimension(LEFT_PANEL_WIDTH, TOP_LEFT_PANEL_HEIGHT));
        leftPanel.add(topLeftPanel, leftConstraints);

        // Left panel bottom constraints
        leftConstraints.gridy = 1;
        leftConstraints.weighty = 0.3;

        JPanel bottomLeftPanel = new JPanel(new BorderLayout());
        bottomLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_THICKNESS));
        bottomLeftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, BOTTOM_LEFT_PANEL_HEIGHT));
        bottomLeftPanel.setMinimumSize(new Dimension(LEFT_PANEL_WIDTH, BOTTOM_LEFT_PANEL_HEIGHT));
        leftPanel.add(bottomLeftPanel, leftConstraints);

        // Right panel constraints
        GridBagConstraints rightConstraints = new GridBagConstraints();
        rightConstraints.gridx = 0;
        rightConstraints.gridy = 0;
        rightConstraints.weightx = 1.0;
        rightConstraints.weighty = 0.6;
        rightConstraints.fill = GridBagConstraints.BOTH;
        rightConstraints.insets = new Insets(INSETS, INSETS, INSETS, INSETS);

        JPanel topRightPanel = new JPanel(new BorderLayout());
        topRightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_THICKNESS));
        topRightPanel.setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, TOP_RIGHT_PANEL_HEIGHT));
        topRightPanel.setMinimumSize(new Dimension(RIGHT_PANEL_WIDTH, TOP_RIGHT_PANEL_HEIGHT));
        rightPanel.add(topRightPanel, rightConstraints);

        // Right panel bottom constraints
        rightConstraints.gridy = 1;
        rightConstraints.weighty = 0.4;

        JPanel bottomRightPanel = new JPanel(new BorderLayout());
        bottomRightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_THICKNESS));
        bottomRightPanel.setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, BOTTOM_RIGHT_PANEL_HEIGHT));
        bottomRightPanel.setMinimumSize(new Dimension(RIGHT_PANEL_WIDTH, BOTTOM_RIGHT_PANEL_HEIGHT));
        rightPanel.add(bottomRightPanel, rightConstraints);


        topLeftPanel.add(map, BorderLayout.CENTER);
        topLeftPanel.add(new visualComponents.LegendBox(serialCommunication, orderRepository), BorderLayout.WEST);
        topRightPanel.add(queueBox, BorderLayout.CENTER);
        bottomRightPanel.add(processingBox);
    }
}