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
    private JButton orderButton = new JButton("Open Info Dialog");
    private JTextField orderTextField = new JTextField(5);
    private OrderInfoDialog infoDialog;

    public OrderPage(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

        this.orderButton.addActionListener(this);
        add(this.orderButton);
        add(this.orderTextField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.orderButton) {
            String input = this.orderTextField.getText();
            int orderId;
            try {
                orderId = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Graag een nummer invoeren", "Error: invoer geen nummer", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Optional<Order> order  = this.orderRepository.findById(orderId);
            if(order.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Order ID bestaat niet", "Error: Bestelling bestaat niet", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(this.infoDialog != null) {
                this.infoDialog.dispose();
            }

            this.infoDialog = new OrderInfoDialog(order.get());
        }
    }
}