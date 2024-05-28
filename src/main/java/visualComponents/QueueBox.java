package visualComponents;

import models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import repositories.OrderRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QueueBox extends JPanel {
    private DefaultTableModel queueTableModel;
    private Order orderInProgress = null;

    public QueueBox(OrderRepository orderRepository) {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        JLabel queueLabel = new JLabel("Bestelling wachtrij");
        add(queueLabel);

        Page<Order> orders = orderRepository.findUnfinishedOrders(PageRequest.of(0, 100));
        List<Object[]> orderData = new ArrayList<>();
        for(Order order : orders.getContent()) {
            if(orderInProgress == null && order.getStatus().equals("InProgress")) {
                orderInProgress = order;
            }
            Object[] orderArray = new Object[3];
            orderArray[0] = order.getOrderID();
            orderArray[1] = order.getOrderLines().size();
            orderArray[2] = order.getStatus();
            orderData.add(orderArray);
        }

        queueTableModel = new DefaultTableModel(
                new Object[]{"Bestel Nr", "Product aantal", "Status", "Actie"}, 0
        );

        //Create table
        JTable queueTable = new JTable(queueTableModel);
        queueTable.setDefaultEditor(Object.class, null); // read-only
        JScrollPane queueScrollPane = new JScrollPane(queueTable); // Add scroll to table
        queueScrollPane.setPreferredSize(new Dimension(380, 310));

        queueTable.getTableHeader().setReorderingAllowed(false); // Stop user column swipe
        fillQueueTable(orderData);
        add(queueScrollPane);
    }

    private void fillQueueTable(List<Object[]> data) {
        for (Object[] row : data) {
            queueTableModel.addRow(row);
        }
    }

    public Order getOrderInProgress() {
        return orderInProgress;
    }
}
