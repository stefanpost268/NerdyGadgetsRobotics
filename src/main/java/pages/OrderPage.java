package pages;

import javax.swing.*;
import dialogs.OrderInfoDialog;
import models.Order;
import repositories.OrderRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class OrderPage extends JPanel implements ActionListener {

    private OrderRepository orderRepository;
    private JButton orderButton = new JButton("Open order id 1");
    private OrderInfoDialog infoDialog;

    public OrderPage(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

        this.orderButton.addActionListener(this);
        add(this.orderButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.orderButton) {
            Optional<Order> order  = this.orderRepository.findById(1);
            if(order.isEmpty()) {
                System.out.println("Order not found");
                return;
            }

            if(this.infoDialog != null) {
                this.infoDialog.dispose();
            }

            this.infoDialog = new OrderInfoDialog(order.get());
        }
    }
}