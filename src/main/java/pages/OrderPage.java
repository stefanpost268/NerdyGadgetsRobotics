package pages;

import javax.swing.*;

import dialogs.CreateOrderDialog;
import dialogs.OrderInfoDialog;
import models.Order;
import repositories.CustomerRepository;
import repositories.OrderRepository;
import repositories.PeopleRepository;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class OrderPage extends JPanel implements ActionListener {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private PeopleRepository peopleRepository;
    private JButton orderButton = new JButton("Open Info Dialog");
    private JTextField orderTextField = new JTextField(5);
    private OrderInfoDialog infoDialog;
    private CreateOrderDialog createOrderDialog;

    private JButton createOrderButton = new JButton("Bestelling aanmaken");

    public OrderPage(OrderRepository orderRepository, CustomerRepository customerRepository, PeopleRepository peopleRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.peopleRepository = peopleRepository;

        this.orderButton.addActionListener(this);
        add(this.orderButton);
        add(this.orderTextField);

        this.createOrderButton.addActionListener(this);
        add(this.createOrderButton);
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

        if(e.getSource() == this.createOrderButton) {
            Optional<Order> order  = this.orderRepository.findById(1);
            if(order.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Order ID bestaat niet", "Error: Bestelling bestaat niet", JOptionPane.ERROR_MESSAGE);
                return;
            }
            createOrderDialog = new CreateOrderDialog(order.get(), customerRepository, peopleRepository);
        }
    }
}