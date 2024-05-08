package pages;

import dialogs.OrderInfoDialog;
import models.Order;
import repositories.OrderRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderPage extends JPanel implements ActionListener {

    private OrderRepository orderRepository;
    private JButton addOrderButton = new JButton("Aanmaken Bestelling");
    private JTable table;

    public OrderPage(OrderRepository orderRepository) {
        setLayout(new BorderLayout());
        this.orderRepository = orderRepository;

        this.addOrderButton.addActionListener(this);
//        add(this.orderButton);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(this.addOrderButton );
        add(buttonPanel, BorderLayout.PAGE_END);

        Iterable<Order> orders = this.orderRepository.findAllByDesc();
        ArrayList<Order> target = new ArrayList<>();

        orders.forEach(target::add);

        renderTable(target);
    }

    private void renderTable(ArrayList<Order> orders) {

        this.table = new JTable();

        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String[] columnNames = { "Bestel Nr","Naam","Status","Product aantal","Bestel datum" };
        Object[][] data = new Object[orders.size()][5];
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            data[i] = order.toObjectArray();
        }
        this.table = new JTable(data, columnNames);



        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.table.setFillsViewportHeight(true);
        this.table.setDefaultEditor(Object.class, null);

        add(scrollPane, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.addOrderButton) {
            OrderInfoDialog infoDialog = new OrderInfoDialog();
            infoDialog.add(new JLabel("Order Info Dialog"));
            infoDialog.setSize(300, 200);
            infoDialog.setVisible(true);
            infoDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}