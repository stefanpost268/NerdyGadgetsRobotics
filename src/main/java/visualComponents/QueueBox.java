package visualComponents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import models.Order;
import models.OrderLines;
import models.StockItem;
import repositories.OrderRepository;
import services.RouteCalculator;

public class QueueBox extends JPanel implements ActionListener {
    private DefaultTableModel queueTableModel;
    JButton executeButton = new JButton("Verwerken");
    JTable queueTable;
    OrderRepository orderRepository;

    public QueueBox(List<Object[]> queueData, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT)); // align left
        JLabel queueLabel = new JLabel("Bestelling wachtrij");
        executeButton.addActionListener(this);
        add(queueLabel);

        //table model
        queueTableModel = new DefaultTableModel(
                new Object[]{"Bestel Nr", "Product aantal", "Status"}, 0
        );

        //Create table
        queueTable = new JTable(queueTableModel);
        queueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        queueTable.setDefaultEditor(Object.class, null); // read-only
        JScrollPane queueScrollPane = new JScrollPane(queueTable); // Add scroll to table
        queueScrollPane.setPreferredSize(new Dimension(320, 270));

        queueTable.getTableHeader().setReorderingAllowed(false); // Stop user column swipe
        add(queueScrollPane);




        fillQueueTable(queueData);
        //adding button to the bottom of the table
        add(executeButton);
    }

    private void fillQueueTable(List<Object[]> data) {
        for (Object[] row : data) {
            queueTableModel.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == executeButton) {
            int orderNumber = (int) queueTable.getValueAt(queueTable.getSelectedRow(), 0);
            Optional<Order> optionalOrder = orderRepository.findById(orderNumber);
            if (!optionalOrder.isPresent()) {
                return; // Order not found, exit the method
            }

            Order order = optionalOrder.get();

            //setting status to InProgress
            order.setStatus("InProgress");
            orderRepository.save(order);

            List<OrderLines> orderLines = order.getOrderLines();

            ArrayList<String> locations = new ArrayList<>();

            for (OrderLines orderLine : orderLines) {
                locations.add(orderLine.getStockItem().getItemLocation());
            }

           System.out.println( RouteCalculator.calculateRoute(locations));
        }
    }
}
