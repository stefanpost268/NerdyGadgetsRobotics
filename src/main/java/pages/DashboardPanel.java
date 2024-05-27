package pages;

import repositories.OrderRepository;
import visualComponents.*;
import javax.swing.*;
import java.util.List;
public class DashboardPanel extends JFrame {
    public DashboardPanel(OrderRepository orderRepository) {
        setTitle("Dashboard");
        setSize(1010, 710);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        QueueBox queueBox = new QueueBox(orderRepository);
        add(queueBox);

        ProcessingBox processingBox = new ProcessingBox(queueBox.getOrderInProgress());
        add(processingBox);

        setVisible(true);
    }
}
